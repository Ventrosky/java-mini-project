/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.interfacce;

import archive.data.Cliente;
import archive.data.Carta;
import archive.data.Volo;

public interface RichiestaPrenotazioneVolo {
    String[] showList(); // mostra elenco dei voli
    Volo isAvaible(String partenza, String arrivo, String orario); // chiede se esiste volo e lo ritorna
    Cliente requestInformation(String c, String n, String t, Carta carta); // richiede dati cliente
    boolean doPrenotation(Volo v, String c, String n, String t, Carta carta); //prenota (num set, cognome, nome)
}
