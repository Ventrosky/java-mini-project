package archive.system;

import archive.data.Volo;
import archive.exception.SingleRoomFullException;
import archive.data.Settimana;
import archive.data.Prenotazione;
import archive.data.Pacchetto;
import archive.exception.DoubleRoomFullException;
import archive.data.Corso;
import archive.data.Cliente;
import archive.exception.AlternativeFlightException;
import java.io.FileNotFoundException;
import java.util.Set;
import archive.interfacce.CreatePrenotation;
import archive.interfacce.FileExport;
import archive.interfacce.GestioneOfferta;
import archive.interfacce.HasGotCorsi;
import archive.interfacce.ObjectWriter;
import archive.interfacce.RoomAvailability;
import archive.utility.SetArray;
import archive.utility.StringInverter;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

public class OfferManager implements GestioneOfferta{
    private boolean ispacchetto;

    public OfferManager(Boolean ispacchetto){
        this.ispacchetto = ispacchetto;
    }

    private String[] showListPackage() {
        Set<Pacchetto> pacchetti = AgenziaSystem.archivio.getPacchetti();
        String[] s = new String[pacchetti.size()];
        int i = 0;
        for (Pacchetto p:pacchetti){
            s[i] = p.getNome();
            i++;
        }
        return s;
    }
    private String[] showListFlight() {
        ObjectWriter ow = new StringInverter<Volo>();
        String[] s = ow.split(ow.merge(AgenziaSystem.archivio.getVoli()));
        return s;
    }
    // ?;XXX
    //Stringa modifica: D(numDoppie)-X(PrezzoD)-S(numSuite)-Y(PrezzoS)-C(sequenza) ; String mod
    private boolean modPackage(String nome, int numWeek, String mod) throws DoubleRoomFullException, SingleRoomFullException{
        String appo = mod.substring(2);
        switch(mod.charAt(0)){
            case 'D':
                if(appo.charAt(0) == '+'){
                    AgenziaSystem.archivio.getPacchetto(nome).getSettimana(numWeek).addNSD();
                    if(!AgenziaSystem.archivio.getPacchetto(nome).getSettimana(numWeek).isQueueEmpty()){
                        String c = AgenziaSystem.archivio.getPacchetto(nome).getSettimana(numWeek).removeFromAttesa();
                        Cliente client = AgenziaSystem.archivio.getCliente(c);
                        CreatePrenotation cp = new PRequestPacchettoManager();
                        cp.anyWeekAvaible(numWeek, nome);
                        cp.doPrenotation(numWeek, client.getCognome(), client.getNome(), client.getTelefono(), client.getCarta(), "D", "");
                    }
                }else{
                    RoomAvailability ra = new PRequestPacchettoManager();
                    ra.anyWeekAvaible(numWeek, nome);
                    if(ra.getFreeRoom('D') > 0){
                        AgenziaSystem.archivio.getPacchetto(nome).getSettimana(numWeek).removeNSD();
                    }else{
                        throw new DoubleRoomFullException();
                    }
                }
                break;
            case 'X':
                AgenziaSystem.archivio.getPacchetto(nome).getSettimana(numWeek).setPSD(Integer.parseInt(appo));
                break;
            case 'S':
                if(appo.charAt(0) == '+'){
                    AgenziaSystem.archivio.getPacchetto(nome).getSettimana(numWeek).addNSU();
                }else{
                    RoomAvailability ra = new PRequestPacchettoManager();
                    ra.anyWeekAvaible(numWeek, nome);
                    if(ra.getFreeRoom('S') > 0){
                        AgenziaSystem.archivio.getPacchetto(nome).getSettimana(numWeek).removeNSU();
                    }else{
                        throw new SingleRoomFullException();
                    }
                }
                break;
            case 'Y':
                AgenziaSystem.archivio.getPacchetto(nome).getSettimana(numWeek).setPSU(Integer.parseInt(appo));
                break;
            case 'C':
                Set<Corso> exCorsi = AgenziaSystem.archivio.getPacchetto(nome).getSettimana(numWeek).getCorsi();
                HasGotCorsi fakeSettimana = new Settimana();
                fakeSettimana.addCorso(appo);
                Set<Corso> newCorsi = fakeSettimana.getCorsi();
                Set<Corso> deleteCorsi = new SetArray<Corso>();
                for (Corso c : exCorsi){
                    if (!newCorsi.contains(c)) deleteCorsi.add(c);
                }
                AgenziaSystem.archivio.getPacchetto(nome).getSettimana(numWeek).setCorsi(newCorsi);
                Set<Prenotazione> set = AgenziaSystem.archivio.getOnePrenotazioni(nome).getPrenotazioniWeek(numWeek);
                for(Prenotazione p : set){
                    for (Corso y: deleteCorsi){
                        if(p.getCorsi().contains(y))
                            p.getCorsi().remove(y);
                    }
                }
                break;
        }
        return true;
    }

    public boolean movePrenotation(String nomeExVillaggio,String nomeVillaggio, int numWeek, boolean move, char tipo){
        if (!move || nomeExVillaggio.equals(nomeVillaggio)) return false;
        RoomAvailability ra = new PRequestPacchettoManager();
        ra.anyWeekAvaible(numWeek, nomeVillaggio);
        if(ra.getFreeRoom(tipo)==0)return false;
        Prenotazione p = null;
        Set<Prenotazione> set = AgenziaSystem.archivio.getOnePrenotazioni(nomeExVillaggio).getPrenotazioniWeek(numWeek);
        for (Prenotazione x : set ){
            if (x.getTipo() == tipo) p = x;
        }
        if ( p == null) return false;
        AgenziaSystem.archivio.removePrenotazione(p, nomeExVillaggio);
        AgenziaSystem.archivio.addPrenotazione(p, nomeVillaggio);
        return true;
    }

    private boolean modVolo(String sigla) throws AlternativeFlightException{
        if(!AgenziaSystem.archivio.getPrenVoli(sigla).isEmpty()) throw new AlternativeFlightException();
        AgenziaSystem.archivio.removeVolo(AgenziaSystem.archivio.getVolo(sigla));
        return true;
    }

    public boolean modArchive(String nome, int numWeek, String mod) throws DoubleRoomFullException, SingleRoomFullException{
        return modPackage(nome, numWeek, mod);
    }
    public boolean modArchive(String sigla)  throws AlternativeFlightException{
            return modVolo(sigla);
    }

    public boolean exportPrenotation(String nome) throws FileNotFoundException{
        FileExport fe = new FileSystem();
        if( ispacchetto){
            fe.exportPrenPacchetti(nome);
        }else{
            fe.exportPrenVoli(nome);
        }
        return true;
    }



    public String[] showList() {
        if( ispacchetto){
            return showListPackage();
        }else{
            return showListFlight();
        }
    }

}
