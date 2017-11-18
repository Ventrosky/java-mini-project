package archive.exception;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

public class ErrorCardException extends Exception {

    /**
     * Creates a new instance of <code>ErrorCardException</code> without detail message.
     */
    public ErrorCardException() {
    }

    /**
     * Constructs an instance of <code>ErrorCardException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ErrorCardException(String msg) {
        super(msg);
    }
}
