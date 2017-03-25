/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.data;

public class Pacchetto {
    private String nome;
    private String indirizzo;
    private String[] recapiti;
    private Settimana[] settimane = new Settimana[52];

    public Pacchetto(String n, String i, String[] r, Settimana[] s){
        nome = n;
        indirizzo = i;
        recapiti = r.clone();
        settimane = s.clone();
    }
    public String getNome(){return nome;}
    public String getIndirizzo(){return indirizzo;}
    public String[] getRecapiti(){return recapiti.clone();}
    public Settimana getSettimana(int n){
        if (n<1||n>52) throw new IllegalArgumentException();
        n--;
        return settimane[n];
    }
    @Override
    public boolean equals(Object o){
        if(o==null) return false;
        if(o instanceof Pacchetto){
            Pacchetto c=(Pacchetto) o;
           return (c.nome.equals(nome));
        }else
            return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString(){
        String rec = recapiti[0];
        for (int i = 1; i < recapiti.length; i++){
            rec+=";"+recapiti[i];
        }
        String sett = "";
        for (int i = 0; i < settimane.length; i++){
            sett+=settimane[i];
        }
        return nome+"\n"+indirizzo+"\n"+rec+"\n"+sett+"\n";
    }
}
