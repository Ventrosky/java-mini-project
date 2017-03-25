/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package archive.system;

import archive.utility.Day;
import archive.data.Archivio;
import java.io.*;

public class AgenziaSystem {

    public static Archivio archivio;
    public static Day day = new Day();
    private FileSystem fs;
    private String filePath;


    public AgenziaSystem(String path) throws FileNotFoundException, IOException {
        filePath = path;
        fs = new FileSystem(filePath);
        archivio = fs.read();
    }

    public void save() throws FileNotFoundException {
        fs.write(filePath);
    }

}
