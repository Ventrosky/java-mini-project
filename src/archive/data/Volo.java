/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.data;

public class Volo {
    public final String sigla;  		//stringa composta da due lettere maiuscole e due cifre (per esempio AZ34)
    public final String ori;    		//stringa (luogo di partenza)
    public final String dest;   		//stringa (luogo di arrivo)
    public final String part;   		//orario di partenza
    public final String arr;    		//orario di arrivo
    private int rimasti;                        //posti ancora disponibili (intero &ge 0)

    public Volo(String sigla,String ori,String dest,String part,String arr,int rimasti){
        this.sigla = sigla;
        this.ori = ori;
        this.dest = dest;
        this.part = part;
        this.arr = arr;
        if (rimasti < 0) throw new IllegalArgumentException();
        this.rimasti = rimasti;
    }
    public int getRimasti(){return rimasti;}
    public String getLPartenza(){ return ori; }
    public String getLArrivo(){ return dest; }
    public String getOPartenza(){ return part; }
    public String getOArrivo(){ return arr; }
    public String getSigla(){ return sigla; }
    public boolean subRimasti(int r){
        if (r < rimasti) return false;
        rimasti -= r;
        return true;
    }
    public void addRimasti( int r){
        if (r < 0) throw new IllegalArgumentException();
        rimasti += r;
    }
    @Override
    public String toString(){
        return sigla+";"+ori+";"+dest+";"+part+";"+arr+";"+rimasti;
    }

}
