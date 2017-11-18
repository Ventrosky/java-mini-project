package archive.interfacce;

import java.io.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

public interface FileExport {
    void write(String path) throws FileNotFoundException;
    void exportPrenVoli(String path) throws FileNotFoundException;
    void exportPrenPacchetti(String path) throws FileNotFoundException;
}
