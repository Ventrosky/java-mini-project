/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.system;

import archive.utility.RequestInfo;
import archive.data.Settimana;
import archive.interfacce.RichiestaPrenotazionePacchetto;
import archive.data.Prenotazione;
import archive.data.Pacchetto;
import archive.data.Cliente;
import archive.data.Carta;
import java.util.*;

public class PRequestPacchettoManager extends RequestInfo implements RichiestaPrenotazionePacchetto{

    private final char doppia = 'D';
    private final char suite = 'S';
    private int freeD = 0;
    private int freeS = 0;
    private String nomeVillaggio = "";

    public String[] showList() {
        Set<Pacchetto> pacchetti = AgenziaSystem.archivio.getPacchetti();
        String[] s = new String[pacchetti.size()];
        int i = 0;
        for (Pacchetto p:pacchetti){
            s[i] = p.getNome();
            i++;
        }
        return s;
    }

    public void setNomeVillaggio(String nome){ nomeVillaggio = nome; }

    public String[] showDetails(String nome, int sett) {
        setNomeVillaggio(nome);
        String[] s = new String[3];
        Pacchetto p = AgenziaSystem.archivio.getPacchetto(nome);
        s[0] = p.getNome();
        s[1] = p.getIndirizzo();
        String[] recapiti = p.getRecapiti();
        s = Arrays.copyOf(s, s.length + recapiti.length + 1);
        for(int i = 2; i < s.length - 2; i++){
            s[i] = recapiti[i-2];
        }
        isAvaible(sett, nome);
        s[s.length -2] = "Numero doppie disponibili: " + freeD;
        s[s.length -1] = "Numero suite disponibili: " + freeS;
        return s;
    }

    private int countPrenRoom(Set<Prenotazione> sP, char c){
        int x = 0;
        for(Prenotazione p: sP){
            if(p.getTipo() == c) x++;
        }
        return x;
    }
    private boolean avaibility(int sett, String nome) {
        Pacchetto p = AgenziaSystem.archivio.getPacchetto(nome);
        if (p == null) throw new NoSuchElementException();
        Settimana s = p.getSettimana(sett);
        Set<Prenotazione> sP =AgenziaSystem.archivio.getOnePrenotazioni(nome).getPrenotazioniWeek(sett);
        int nPrenSuite = countPrenRoom(sP, suite);
        int nPrenDoppia = countPrenRoom(sP, doppia);
        if (s.getNSD() > nPrenDoppia || s.getNSU() > nPrenSuite){
            freeD = s.getNSD() - nPrenDoppia;
            freeS = s.getNSU() - nPrenSuite;
            return true;
        }
        freeD = 0;
        freeS = 0;
        return false;
    }
    public boolean isAvaible(int sett, String nome) {
        if(AgenziaSystem.day.compareTo(sett) <= 0 ) throw new IllegalArgumentException();
        return avaibility(sett, nome);
    }
    public boolean anyWeekAvaible(int sett, String nome) {
        return avaibility(sett, nome);
    }
    public boolean addInListaAttesa(int n, String cognome){ //probabile da spostare in archivio
        return AgenziaSystem.archivio.getPacchetto(nomeVillaggio).getSettimana(n).addInAttesa(cognome);
    }

    public boolean doPrenotation(int ns, String c, String n, String t, Carta carta, String tipo, String corsi){
        switch (tipo.charAt(0)){
            case doppia:
                if (freeD <= 0) throw new NoSuchElementException(); //chiede se aggiungere in lista attesa
            case suite:
                if (freeS <= 0) return false;
        }
            Cliente client = requestInformation(c, n, t,carta);
            if(AgenziaSystem.archivio.hasGotPrenotation(client, ns)) return false;
            Prenotazione newPren = new Prenotazione(ns, client.getCognome(), client.getNome());
            newPren.addTipo(tipo);
            newPren.addCorso(nomeVillaggio, corsi);
            AgenziaSystem.archivio.addPrenotazione(newPren, nomeVillaggio);
      
        return true;
    }

    public int getFreeRoom(char tipo){
        switch(tipo){
            case 'D':
                return freeD;
            case 'S':
                return freeS;
            default:
                return 0;
        }
    }

}
