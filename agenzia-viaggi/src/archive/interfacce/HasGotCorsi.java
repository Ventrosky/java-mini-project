/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.interfacce;

import archive.data.Corso;
import java.util.*;

public interface HasGotCorsi {
    Set<Corso> getCorsi(); // ritorna un set di corsi
    boolean addCorso(Corso c);
    void addCorso(String s);
    public boolean containCorso(Corso c);
    public boolean removeCorso(Corso c);
    public void resetCorsi();
}
