/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.exception;

public class AlternativeFlightException extends Exception {

    /**
     * Creates a new instance of <code>AlternativeFlightException</code> without detail message.
     */
    public AlternativeFlightException() {
    }


    /**
     * Constructs an instance of <code>AlternativeFlightException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public AlternativeFlightException(String msg) {
        super(msg);
    }
}
