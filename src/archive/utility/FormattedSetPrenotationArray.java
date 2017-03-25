/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package archive.utility;

import java.util.Arrays;

public class FormattedSetPrenotationArray {
    public static String[] formattedSetPrenotation(String[] s){
    String[] array = new String[0];
       String[] appo = new String[0];
        for (int i = 0; i < s.length; i++){
            appo =s[i].split("\n");
            int nArray = array.length;
            array = Arrays.copyOf(array, nArray + appo.length);
            nArray = array.length;
           for (int j = nArray - appo.length; j < nArray; j++){
               array[j] = appo[j - (nArray - appo.length)];
           }
       }
        return array;
}
}
