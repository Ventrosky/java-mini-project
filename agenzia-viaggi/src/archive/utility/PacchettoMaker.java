/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.utility;

import archive.data.Settimana;
import archive.data.Pacchetto;
import archive.data.Archivio;
import java.util.*;

public class PacchettoMaker extends StringConverter{
    public PacchettoMaker(Archivio arch){
        super(arch);
    }
    @Override
    public void create() {
        Pacchetto newPacchetto;
        String[][] mat = getMatrix();
       if(mat[0][0].equals("")) return;
        String nome = mat[0][0];
        String indirizzo = mat[1][0];
        String[] recapiti = new String[mat[2].length];
        recapiti = mat[2].clone();
        Settimana[] settimane = setSettimana(mat);
        newPacchetto = new Pacchetto(nome, indirizzo, recapiti, settimane);
        arch.addPacchetto(newPacchetto);
    }
    private Settimana[] setSettimana(String[][] mat){
        if (mat.length == 106){
            mat=Arrays.copyOf(mat,107 );
            mat[106]=new String[1];
            mat[106][0]="";
        }

        Settimana[] settimane = new Settimana[52];
        Settimana newSettimana = null;
       for ( int i = 3; i < mat.length; i++){
            if (i%2!=0){
                newSettimana = new Settimana(Integer.parseInt(mat[i][0]),Integer.parseInt(mat[i][1]),Integer.parseInt(mat[i][2]),Integer.parseInt(mat[i][3]),Integer.parseInt(mat[i][4]));
                 if(mat[i].length==6)
                   newSettimana.addCorso(mat[i][5]);
                else
                  newSettimana.addCorso(" ");
            }else{
                 newSettimana.addInAttesa(mat[i]);
                 settimane[(i/2)-2]=newSettimana;
                }
             
        }

        return settimane;
    }
}
