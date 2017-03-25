/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.data;
import archive.utility.AbstractPrenotation;
import archive.system.AgenziaSystem;
import archive.interfacce.ObjectWriter;
import archive.utility.SetArray;
import archive.utility.StringInverter;
import java.util.*;

public class Prenotazioni extends AbstractPrenotation{
    private String nomeVillaggio;
    private Set<Prenotazione> elenco = new SetArray();

    public Prenotazioni(String nome){
        nomeVillaggio = nome;
    }

    public boolean add(Prenotazione p){
        if(AgenziaSystem.day.compareTo(p.getNumeroSett())>=0)
               return elenco.add(p);
        return false;
    }
    public boolean remove(String cognome, int numWeek){
        if (cognome == null || (numWeek < 1 || numWeek > 52)) throw new IllegalArgumentException();
        for(Prenotazione x:elenco){
            if( x!=null && cognome.equals(x.getCognome()) && numWeek == x.getNumeroSett()){
                elenco.remove(x);
                return true;
            }
        }
        return false;
    }
    public boolean remove(Prenotazione p){
        return elenco.remove(p);
    }
    public String getNomeVillaggio(){
        return nomeVillaggio;
    }
    public Set<Prenotazione> getElenco(){
        return elenco;
    }
    public Prenotazione getPrenotazione(String cognome, int numWeek){
        if (cognome == null || (numWeek < 1 || numWeek > 52)) throw new IllegalArgumentException();
        for(Prenotazione x:elenco){
            if( x!=null && cognome.equals(x.getCognome()) && numWeek == x.getNumeroSett()){
                return x;
            }
        }
        return null;
    }
    public Set<Prenotazione> getPrenotazioniWeek(int numWeek){
        if (numWeek < 1 || numWeek > 52) throw new IllegalArgumentException();
        Set<Prenotazione> sP = new SetArray();
        for(Prenotazione x:elenco){
            if( x!=null && numWeek == x.getNumeroSett()){
                sP.add(x);
            }
        }
        return sP;
    }
    @Override
    public String toString(){
        ObjectWriter<Prenotazione> ow = new StringInverter<Prenotazione>();
        return nomeVillaggio+"\n"+ow.merge(elenco);
    }

    public boolean isPresentClient(Cliente c) {
        for(Prenotazione x: elenco ){
            if(x.getCognome().equals(c.getCognome()))
                return true;
        }
        return false;
    }

    public boolean isPresentClient(Cliente c, int n) {
        for(Prenotazione x: elenco ){
            if(x.getCognome().equals(c.getCognome()) && x.getNumeroSett() == n)
                return true;
        }
        return false;
    }

}
