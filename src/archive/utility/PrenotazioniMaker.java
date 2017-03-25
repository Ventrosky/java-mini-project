/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.utility;

import archive.data.Prenotazioni;
import archive.data.Prenotazione;
import archive.data.Archivio;

public class PrenotazioniMaker extends StringConverter{
    public PrenotazioniMaker(Archivio arch){
        super(arch);
    }
    @Override
    public void create() {
        Prenotazioni newPrenotazioni;
        String[][] mat = getMatrix();
        newPrenotazioni = new Prenotazioni(mat[0][0]);
        Prenotazione newPrenotazione = null;
   for(int i = 1; i < mat.length; i++){
            newPrenotazione = new Prenotazione(Integer.parseInt(mat[i][0]),mat[i][1],mat[i][2]);
            newPrenotazione.addTipo(mat[i][3]);
             if(mat[i].length==5)
                   newPrenotazione.addCorso(mat[0][0],mat[i][4]);
                else
                  newPrenotazione.addCorso(mat[0][0]," ");
            newPrenotazioni.add(newPrenotazione);
            arch.addPrenotazioni(newPrenotazioni);
        }
    
        
    }

}
