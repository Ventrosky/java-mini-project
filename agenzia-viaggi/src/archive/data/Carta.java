/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.data;

public class Carta{
        public String tipo;
        public String numero;

        public Carta(String t){
            String[] s = div(t);
            tipo = s[0];
            numero =s[1];
        }
        private String[] div(String s){
            String[] t = new String[2];
            int i;
            for (i = 0; i < s.length() && (s.charAt(i) > '9' || s.charAt(i) < '0' ); i++)
            {}
            if (i == s.length()-1) throw new IllegalArgumentException();
            t[0] = s.substring(0, i);
            t[1] = s.substring(i);
            return t;
        }
        public void setCarta(String t, String n){
            tipo = t;
            numero = n;
        }
        public String getTipo(){
            return tipo;
        }
        public String getNumero(){
            return numero;
        }

    @Override
        public String toString(){
            return tipo+numero;
        }
}
