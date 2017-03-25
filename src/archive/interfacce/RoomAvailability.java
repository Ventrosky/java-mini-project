package archive.interfacce;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

public interface RoomAvailability {
    boolean anyWeekAvaible(int sett, String nome); //controlla disponibilita delle stanze senza limiti
    boolean isAvaible(int sett, String nome); //controlla disponibilita delle stanze
    int getFreeRoom(char tipo); // return #tipo avaliable
}
