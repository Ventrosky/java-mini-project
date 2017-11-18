/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package archive.data;
import archive.interfacce.HasGotCorsi;
import archive.utility.SetArray;
import java.util.*;

public class Settimana implements HasGotCorsi{


    private int nsett;//Numero della settimana (un intero tra 1 e 52)
    private int nsd;//Numero stanze doppie disponibili per la settimana (un intero ≥ 0)
    private int psd;//Prezzo stanza doppia (un intero ≥ 0)
    private int nsu;//Numero suite multi-stanze disponibili per la settimana (un intero ≥ 0)
    private int psu;//Prezzo suite multi-stanze (un intero ≥ 0)
    private Set<Corso> seq = new SetArray();
    private Queue<String> lista = new LinkedList();

    public Settimana(){
        
    }
    
    public Settimana(int nsett, int nsd, int psd, int nsu, int psu) {
        this.nsett = nsett;
        this.nsd = nsd;
        this.psd = psd;
        this.nsu = nsu;
        this.psu = psu;
    }

    public int getNSett(){ return nsett;}
    public int getNSD(){ return nsd;}
    public int getPSD(){ return psd;}
    public int getNSU(){ return nsu;}
    public int getPSU(){ return psu;}
    public Set<Corso> getCorsi() { return seq; }

    public boolean removeNSD(){
        if (nsd == 0) return false;
        nsd--;
        return true;
    }
    public boolean removeNSU(){
        if (nsu == 0) return false;
        nsu--;
        return true;
    }
    public String removeFromAttesa(){
        return lista.poll();
    }
    public void addNSD(){
        nsd++;
    }
    public void addNSU(){
        nsu++;
    }
    public void resetCorsi(){
        seq = new SetArray();
    }
    public boolean setCorsi(Set<Corso> newCorsi){
        resetCorsi();
        for(Corso c : newCorsi){
            seq.add(c);
        }
        return true;
    }

    public boolean setPSD(int p){
        if (p < 0) throw new IllegalArgumentException();
        psd = p;
        return true;
    }
    public boolean setPSU(int p){
        if (p < 0) throw new IllegalArgumentException();
        psu = p;
        return true;
    }
    public void setLista(Queue<String> lista){
        if (lista == null) throw new NullPointerException();
        this.lista = lista;
    }
    public boolean isQueueEmpty(){
        return ((lista.peek()==null)||(lista.peek().equals("")));
        //return lista.isEmpty();
    }
    public boolean containCorso(Corso c){
        return seq.contains(c);
    }
    public boolean removeCorso(Corso c){
        return seq.remove(c);
    }
    public boolean addInAttesa(String cognome){
        if(lista.contains(cognome)) return false;
        return lista.add(cognome);
    }
    public boolean addInAttesa(String[] cognome){
        return lista.addAll(Arrays.asList(cognome));
      
    }
    public boolean addCorso(Corso c){
        return seq.add(c);
    }

    public void addCorso(String s){
        if(s == null) throw new NullPointerException();
        for(int i = 0; i < s.length(); i++){
            switch (s.charAt(i)){
                case 'V':
                    addCorso(Corso.V);
                    break;
                case 'E':
                    addCorso(Corso.E);
                    break;
                case 'T':
                    addCorso(Corso.T);
                    break;
                case 'S':
                    addCorso(Corso.S);
                    break;
                case 'B':
                    addCorso(Corso.B);
                    break;
            }
        }
    }

    public String stampaCorsi(){
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
        return sc;  
  }

    @Override
    public String toString(){
        String sc =stampaCorsi();
        String l = "";
        if (!lista.isEmpty()){
            for(String x: lista){
                if(!x.equals(""))
                l+=x+";";
            }
            l=l.substring(0, l.length());
        }
        return nsett+";"+nsd+";"+psd+";"+nsu+";"+psu+";"+sc+"\n"+l+"\n";
    }

}
