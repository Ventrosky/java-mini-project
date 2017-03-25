

package archive.data;

import archive.utility.AbstractPrenotation;

public class PrenotazioneVolo extends AbstractPrenotation{
    private String cognome;
    private String nome;
    private String sigla;               //stringa composta da due lettere maiuscole e due cifre (per esempio AZ34)
    private String ori;    		//stringa (luogo di partenza)
    private String dest;   		//stringa (luogo di arrivo)

    public PrenotazioneVolo(String c, String m,String sigla,String ori,String dest){
        cognome = c;
        nome = m;
        this.sigla = sigla;
        this.ori = ori;
        this.dest = dest;
    }
    public String getCognome(){return cognome;}
    public String getNome(){return nome;}
    public String getSigla(){return sigla;}
    public String getOri(){return ori;}
    public String getDest(){return dest;}
    public void setSigla(String s){sigla = s;}
    @Override
    public boolean equals(Object o){
        if(o==null) return false;
        if(o instanceof PrenotazioneVolo){
            PrenotazioneVolo c=(PrenotazioneVolo) o;
           return (c.getCognome().equals(cognome)&& (c.getSigla().equals(sigla)));
        }else
            return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.cognome != null ? this.cognome.hashCode() : 0);
        hash = 29 * hash + (this.sigla != null ? this.sigla.hashCode() : 0);
        hash = 29 * hash + (this.ori != null ? this.ori.hashCode() : 0);
        return hash;
    }
    @Override
    public String toString(){
        return cognome+";"+nome+";"+sigla+";"+ori+";"+dest;
    }

    public boolean isPresentClient(Cliente c) {
        return c.getCognome().equals(cognome);
    }
}
