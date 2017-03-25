/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.data;
import archive.utility.AbstractPrenotation;
import archive.system.AgenziaSystem;
import archive.utility.SetArray;
import java.util.*;

public class Prenotazione extends AbstractPrenotation implements Comparable{

    public enum Tipo { D, S} //doppia o suite
    private int numeroSett;
    private String cognome;
    private String nome;
    private Tipo tipo;
    private Set<Corso> seq = new SetArray();

    public Prenotazione(int ns, String c, String n){
        numeroSett = ns;
        cognome = c;
        nome = n;
    }
    public int getNumeroSett(){return numeroSett;}
    public String getCognome(){return cognome;}
    public String getNome(){return nome;}
    public char getTipo(){
        char c = ' ';
        switch(tipo){
            case D:
                c = 'D';
                break;
            case S:
                c = 'S';
                break;
        }
        return c;
    }
    public Set<Corso> getCorsi(){return seq;}
    public void setCorsi(Set<Corso> sc){
        for(Corso c:sc){
            seq.add(c);
        }
    }
    public void setNumeroSett(int ns){
        if(ns < 1 || ns > 52) throw new IllegalArgumentException();
        numeroSett = ns;
    }
    public void setCognome(String s){
        cognome = s;
    }
    public void setNome(String s){
        nome = s;
    }
    public void setTipo(Tipo t){
        tipo = t;
    }
    public void setTipo(char t){
        t = Character.toUpperCase(t);
        switch(t){
            case 'D':
                setTipo(Tipo.D);
                break;
            case 'S':
                setTipo(Tipo.S);
                break;
        }
    }
    public void addTipo(String s){
        if(s == null) throw new NullPointerException();
            switch (s.charAt(0)){
                case 'D':
                    setTipo(Tipo.D);
                    break;
                case 'S':
                    setTipo(Tipo.S);
                    break;
            }
    }
    public boolean isCorso(String nome, Corso c){
        try{
            return AgenziaSystem.archivio.getPacchetto(nome).getSettimana(numeroSett).containCorso(c);
        }catch(NullPointerException ex){ return true; }
    }
    public boolean addCorso(String nome,Corso c){
        if (!isCorso(nome,c)) return false;
        return seq.add(c);
    }
    public void addCorso(String nome,String s){
        if(s == null) throw new NullPointerException();
        for(int i = 0; i < s.length(); i++){
            switch (s.charAt(i)){
                case 'V':
                    addCorso(nome,Corso.V);
                    break;
                case 'E':
                    addCorso(nome,Corso.E);
                    break;
                case 'T':
                    addCorso(nome,Corso.T);
                    break;
                case 'S':
                    addCorso(nome,Corso.S);
                    break;
                case 'B':
                    addCorso(nome,Corso.B);
                    break;
            }
        }
    }
    public void resetCorsi(){
        seq = new SetArray();
    }
    @Override
    public boolean equals(Object o){
        if(o==null) return false;
        if(o instanceof Prenotazione){
            Prenotazione c=(Prenotazione) o;
           return (c.getCognome().equals(cognome)&& c.numeroSett==numeroSett);
        }else
            return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.numeroSett;
        hash = 97 * hash + (this.cognome != null ? this.cognome.hashCode() : 0);
        return hash;
    }
    
    @Override
    public String toString(){
        String sc = "";
        for (Corso c: seq){
            switch(c.getNome().charAt(0)){
                case 'v':
                    sc+="V";
                    break;
                case 'e':
                    sc+="E";
                    break;
                case 't':
                    sc+="T";
                    break;
                case 's':
                    sc+="S";
                    break;
                case 'i':
                    sc+="B";
                    break;

            }
        }
        char t=' ';
        switch(tipo){
            case D:
                t = 'D';
                break;
            case S:
                t = 'S';
                break;
        }
        return numeroSett+";"+cognome+";"+nome+";"+t+";"+sc;
    }

    public boolean isPresentClient(Cliente c) {
        return c.getCognome().equals(cognome);
    }
    
    
    public int compareTo(Object o) {
        Prenotazione p = (Prenotazione)o;
        if(numeroSett == p.getNumeroSett()) return 0;
            else{
                return (numeroSett > p.getNumeroSett()   ? 1 : -1);
            }
   
    }
}
