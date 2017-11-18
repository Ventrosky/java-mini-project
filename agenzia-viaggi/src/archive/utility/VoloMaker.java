/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.utility;

import archive.data.Volo;
import archive.data.Archivio;

public class VoloMaker extends StringConverter{
    public VoloMaker(Archivio arch){
        super(arch);
    }
    @Override
    public void create() {
        Volo newVolo;
        String[][] mat = getMatrix();
        for(int i = 0; i < mat.length; i++){
            newVolo = new Volo(mat[i][0],mat[i][1],mat[i][2],mat[i][3],mat[i][4],Integer.parseInt(mat[i][5]));
            arch.addVolo(newVolo);
        }
    }
}
