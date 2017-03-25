/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.exception;

public class DoubleRoomFullException extends Exception {

    /**
     * Creates a new instance of <code>DoubleRoomFullException</code> without detail message.
     */
    public DoubleRoomFullException() {
    }


    /**
     * Constructs an instance of <code>DoubleRoomFullException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public DoubleRoomFullException(String msg) {
        super(msg);
    }
}
