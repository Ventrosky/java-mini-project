/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.interfacce;

import archive.data.Cliente;

public interface Prenotation<E> {
        boolean isPresentClient(Cliente c); //ritorna true se il cliente ha almeno una prenotazione
}
