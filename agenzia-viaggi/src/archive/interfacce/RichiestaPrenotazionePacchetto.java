/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.interfacce;

import archive.data.Cliente;
import archive.data.Carta;

public interface RichiestaPrenotazionePacchetto extends CreatePrenotation{
    String[] showList(); // mostra elenco dei pacchetti
    String[] showDetails(String nome, int sett); //mostra dettagli villaggio, ritorna String[]
    Cliente requestInformation(String c, String n, String t, Carta carta); // richiede dati cliente
    boolean addInListaAttesa(int n, String cognome); //aggiunge in lista d'attesa
    
}
