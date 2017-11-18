/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.utility;

import archive.data.Cliente;
import archive.data.Archivio;
import archive.data.Carta;

public class ClientMaker extends StringConverter{
    public ClientMaker(Archivio arch){
        super(arch);
    }
    @Override
    public void create() {
        Cliente newCliente;
        String[][] mat = getMatrix();
        for(int i = 0; i < mat.length; i++){
            newCliente = new Cliente(mat[i][0],mat[i][1],mat[i][2],new Carta(mat[i][3]));
            arch.addCliente(newCliente);
        }
    }

}
