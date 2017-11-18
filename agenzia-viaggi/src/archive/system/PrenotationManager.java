/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.system;

import archive.utility.AbstractPrenotation;
import archive.data.Volo;
import archive.data.Settimana;
import archive.data.Prenotazioni;
import archive.data.PrenotazioneVolo;
import archive.data.Prenotazione;
import archive.interfacce.GestionePrenotazione;
import archive.interfacce.Prenotation;
import archive.interfacce.RichiestaPrenotazionePacchetto;
import archive.interfacce.RoomAvailability;
import archive.utility.SetArray;
import archive.exception.DoubleRoomNotAvailableException;
import archive.data.Cliente;
import java.util.*;

public class PrenotationManager<E extends AbstractPrenotation> implements GestionePrenotazione{
    private Set<Prenotation<E>> prenotation;
    private String nomeVillaggio = null;
    private int appoNumWeek = 0;
    
    public PrenotationManager(Set<Prenotation<E>> p){
        prenotation = p;
    }
    public void setVillaggio(String nome){
        nomeVillaggio = nome;
    }
    public Cliente requestInformation(String cognome) {
        if (cognome == null) throw new NullPointerException();
        Cliente c = AgenziaSystem.archivio.getCliente(cognome);
        if (c == null || !isRegistered(c)) throw new NoSuchElementException();
        return c;
    }
    private Prenotazione defaultPrenotation(String cognome, int numWeek){
        Cliente client = AgenziaSystem.archivio.getCliente(cognome);
        Prenotazione newPren = new Prenotazione(numWeek, client.getCognome(), client.getNome());
        newPren.addTipo("D");
        return newPren;
    }
    public boolean delPrenotation(Prenotation p) {
        if (p == null) throw new NullPointerException();
        if (p instanceof PrenotazioneVolo) {
            PrenotazioneVolo prenV = (PrenotazioneVolo)p;
            return AgenziaSystem.archivio.removePrenVolo(prenV);
        }else if(p instanceof Prenotazione){
            if(AgenziaSystem.archivio.removePrenotazione((Prenotazione)p,nomeVillaggio)){
                Prenotazione pren = (Prenotazione)p;
                if(pren.getTipo()=='D'){
                    Settimana set = AgenziaSystem.archivio.getPacchetto(nomeVillaggio).getSettimana(pren.getNumeroSett());
                    if(!set.isQueueEmpty()){
                        String cognome = set.removeFromAttesa();
                        AgenziaSystem.archivio.addPrenotazione(defaultPrenotation(cognome,pren.getNumeroSett()), nomeVillaggio);
                    }
                }else{
                    //(Suite)
                }
            }
        }
        return true;
    }
    private boolean modPrenVolo(PrenotazioneVolo p, String ora){
        PrenotazioneVolo oldPren = p;
        for(Volo x: AgenziaSystem.archivio.getVoli()){
            if((x != null) && x.getOPartenza().equals(ora) && (p.getDest().equals(x.getLArrivo())) && (p.getOri().equals(x.getLPartenza()))){
                p.setSigla(x.getSigla());
                AgenziaSystem.archivio.addPrenVolo(p);
                AgenziaSystem.archivio.removePrenVolo(oldPren);
                return true;
            }
        }
        return false;
    }
    // ?;XXX
    //Stringa modifica: N(modNWeek)-T(modTipo)-S(modSeq)-C(complete/save) ; String mod
    private boolean modPrenPach(Prenotazione p, String mod) throws DoubleRoomNotAvailableException{
        String appo = mod.substring(2);
        RichiestaPrenotazionePacchetto rpp = new PRequestPacchettoManager();
        switch(mod.charAt(0)){
            case 'N':
                if (Integer.parseInt(appo) == p.getNumeroSett()){
                    appoNumWeek = p.getNumeroSett();
                    return true;
                } //se premo avanti e nn voglio modificare la modifica == vecchio attributo
                if ((AgenziaSystem.day.compareTo(Integer.parseInt(appo)) <= 0) || !(rpp.isAvaible(Integer.parseInt(appo), nomeVillaggio))) return false;
                appoNumWeek = p.getNumeroSett();
                p.setNumeroSett(Integer.parseInt(appo));
                return true;
            case 'T':
                if (appo.charAt(0) == p.getTipo()) return true; //==
                RoomAvailability ra = new PRequestPacchettoManager();
                if(ra.isAvaible(p.getNumeroSett(), nomeVillaggio) && ra.getFreeRoom(appo.charAt(0))> 0){
                    p.setTipo(appo.charAt(0));
                }else{
                    if(appo.charAt(0) == 'D')
                        throw new DoubleRoomNotAvailableException();
                }
                break;
            case 'S':
                p.resetCorsi();
                p.addCorso(nomeVillaggio, appo);
                break;
            case 'C':
                AgenziaSystem.archivio.removePrenotazione(p.getCognome(),appoNumWeek, nomeVillaggio);
                if (toAddInQueue){
                    AgenziaSystem.archivio.getPacchetto(nomeVillaggio).getSettimana(p.getNumeroSett()).addInAttesa(p.getCognome());
                    toAddInQueue = false;
                }else{
                    AgenziaSystem.archivio.addPrenotazione(p, nomeVillaggio);
                }
                break;
        }
        return true;
    }
    private boolean toAddInQueue = false;
    public void addInQueue(boolean addInQueue){
        toAddInQueue = addInQueue;
    }
    public boolean modPrenotation(Prenotation p, String modifiche) throws DoubleRoomNotAvailableException{
        if (p instanceof PrenotazioneVolo) {
            PrenotazioneVolo prenV = (PrenotazioneVolo)p;
            return modPrenVolo(prenV,modifiche);
        }else if(p instanceof Prenotazione){
            Prenotazione pren = (Prenotazione)p;
            return modPrenPach(pren, modifiche);
        }
        return false;
    }

    public boolean isRegistered(Cliente c) {
        if (c == null) throw new NullPointerException();
        return AgenziaSystem.archivio.getClienti().contains(c);
    }

    public boolean hasGotPrenotation(Cliente c) {
        if (c == null) throw new NullPointerException();
        for (Prenotation<E> p : prenotation)
            if (p.isPresentClient(c)) return true;
        return false;
    }

    public Set<Prenotation<E>> showList(Cliente c) {
        Set<Prenotation<E>> prenCliente = new SetArray<Prenotation<E>>();
        if (c == null) throw new NullPointerException();
        PrenotazioneVolo prenv;
        Prenotazioni prenp;
        for (Prenotation<E> p:prenotation){
            if (p instanceof PrenotazioneVolo){
                prenv = (PrenotazioneVolo)p;
                if(prenv.getCognome().equals(c.getCognome())){
                    prenCliente.add(p);
                }
            }else if(p instanceof Prenotazioni){
                prenp = (Prenotazioni)p;
                if(prenp.isPresentClient(c)){
                  Prenotazioni appoPren = new Prenotazioni(prenp.getNomeVillaggio());
                  for(Prenotazione x : prenp.getElenco()){
                      if(x.getCognome().equals(c.getCognome())){
                          appoPren.add(x);
                      }
                  }
                  prenCliente.add(appoPren);
                }
            }
        }
        return prenCliente;
    }
   
}
