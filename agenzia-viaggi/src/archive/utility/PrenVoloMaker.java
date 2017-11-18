/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.utility;

import archive.data.PrenotazioneVolo;
import archive.data.Archivio;

public class PrenVoloMaker  extends StringConverter{
    public PrenVoloMaker(Archivio arch){
        super(arch);
    }
    @Override
    public void create() {
        PrenotazioneVolo newPrenVolo;
        String[][] mat = getMatrix();
        for(int i = 0; i < mat.length; i++){
            newPrenVolo = new PrenotazioneVolo(mat[i][0],mat[i][1],mat[i][2],mat[i][3],mat[i][4]);
            arch.addPrenVolo(newPrenVolo);
        }
    }
}