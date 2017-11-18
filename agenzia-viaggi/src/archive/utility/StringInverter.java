/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.utility;

import archive.interfacce.ObjectWriter;
import java.util.*;

public class StringInverter<E> implements ObjectWriter<E>{

    public String merge(Set<E> set) {
        String s = "";
        for (E e: set){
            s+=e.toString()+"\n";
        }
        return s;
    }

    public String[] split(String s) {
        if (s == null) throw new NullPointerException();
        String[] linee = s.split("\n");
        return linee;
    }

}
