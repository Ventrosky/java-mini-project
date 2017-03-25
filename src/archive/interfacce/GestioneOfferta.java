/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.interfacce;

import archive.exception.DoubleRoomFullException;
import archive.exception.AlternativeFlightException;
import java.io.*;
import archive.exception.SingleRoomFullException;

public interface GestioneOfferta {
    String[] showList(); //mostra elenco
    boolean modArchive(String sigla) throws AlternativeFlightException; // cancella volo
    boolean modArchive(String nome, int numWeek, String mod) throws DoubleRoomFullException, SingleRoomFullException; //modifica campi pacchetto
    boolean exportPrenotation(String nome) throws FileNotFoundException; //esporta prenotazioni (nomeFile)
}
