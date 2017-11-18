/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.utility;

import archive.data.Cliente;
import archive.data.Carta;
import archive.system.AgenziaSystem;

public class RequestInfo {
    
    public Cliente requestInformation(String c, String n, String t, Carta carta) {
        if(!ValCardCheck.check()) throw new IllegalArgumentException();
        Cliente client = AgenziaSystem.archivio.getCliente(c);
        if (client == null){
            client = new Cliente(c,n,t,carta);
            AgenziaSystem.archivio.addCliente(client);
        }
        return client;
    }
}
