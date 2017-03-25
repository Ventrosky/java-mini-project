/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.interfacce;

import java.util.*;

public interface ObjectWriter<E> {
    String merge(Set<E> set); //da oggetti passa a stringa
    String[] split(String s); // da stringa ritorna arrai diviso agli /n
}
