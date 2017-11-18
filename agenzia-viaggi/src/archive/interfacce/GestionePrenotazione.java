/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.interfacce;
import archive.exception.DoubleRoomNotAvailableException;
import archive.data.Cliente;
import java.util.*;

public interface GestionePrenotazione<E> {
    Cliente requestInformation(String cognome); //ritorna il cliente
    boolean isRegistered(Cliente c); //ritorna true se il cliente presente
    Set<Prenotation<E>> showList(Cliente c); // mostra elenco prenotazioni del cliente
    boolean delPrenotation(Prenotation p); //ritorna vero se ha cancellato un prenotazione esistente per quel cliente
    boolean modPrenotation(Prenotation p, String modifiche) throws DoubleRoomNotAvailableException; //modifiche (in stringhe)
    void addInQueue(boolean addInQueue); // se doppie non disponibili alla modifica chiedi se aggiungere in lista attesa

}
