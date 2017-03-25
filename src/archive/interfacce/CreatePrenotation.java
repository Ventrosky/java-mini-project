/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.interfacce;

import archive.data.Carta;

public interface CreatePrenotation extends RoomAvailability{
    boolean doPrenotation(int ns, String c, String n, String t, Carta carta, String tipo, String corsi); //prenota (num set, cognome, nome)
}
