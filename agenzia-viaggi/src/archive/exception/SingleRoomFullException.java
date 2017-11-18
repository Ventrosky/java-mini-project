/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.exception;

public class SingleRoomFullException extends Exception {

    /**
     * Creates a new instance of <code>SingleRoomFullException</code> without detail message.
     */
    public SingleRoomFullException() {
    }


    /**
     * Constructs an instance of <code>SingleRoomFullException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public SingleRoomFullException(String msg) {
        super(msg);
    }
}
