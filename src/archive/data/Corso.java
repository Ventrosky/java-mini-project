/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package archive.data;

public enum Corso {

    V("vela"), E("equitazione"), T("tennis"),
    S("surf"), B("immersione con bombole");
    private String nome;

    Corso(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
    
}
