/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.utility;

import archive.interfacce.ObjectReader;
import archive.data.Archivio;

public abstract class StringConverter implements ObjectReader{
    protected Archivio arch;
    private String[][] matrix;
    public StringConverter(Archivio archivio){
        arch = archivio;
    }
    public void split(String s) {
        String[] linee = s.split("\n");
        int n = linee.length;
        matrix = new String[n][];
        for(int i = 0;i<linee.length; i++){
            matrix[i]=linee[i].split(";");
        }
    }
    public abstract void create();
    public String[][] getMatrix(){return matrix.clone();}
}
