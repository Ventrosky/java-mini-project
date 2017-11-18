/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.data;


public class Cliente {
    private final String cognome;
    private final String nome;
    private String telefono;
    private Carta carta;

    public Cliente(String c, String n, String t, Carta carta){
        cognome = c;
        nome = n;
        telefono = t;
        this.carta = carta;
    }
    @Override
    public boolean equals(Object o){
        if(o==null) return false;
        if(o instanceof Cliente){
            Cliente c=(Cliente) o;
           return (c.cognome.equals(cognome));
        }else
            return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.cognome != null ? this.cognome.hashCode() : 0);
        return hash;
    }
    public String getCognome(){return cognome;}
    public String getNome(){return nome;}
    public String getTelefono(){return telefono;}
    public Carta getCarta(){return carta;}
    public void setTelefono(String t){telefono = t;}
    public void setCarta(Carta c){carta = c;}

    @Override
    public String toString(){
        return (cognome+";"+nome+";"+telefono+";"+carta);
    }
}
