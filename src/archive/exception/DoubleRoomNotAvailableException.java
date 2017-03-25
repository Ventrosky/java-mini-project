/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.exception;

public class DoubleRoomNotAvailableException extends Exception {

    /**
     * Creates a new instance of <code>DoubleRoomNotAvailable</code> without detail message.
     */
    public DoubleRoomNotAvailableException() {
        
    }


    /**
     * Constructs an instance of <code>DoubleRoomNotAvailable</code> with the specified detail message.
     * @param msg the detail message.
     */
    public DoubleRoomNotAvailableException(String msg) {
        super(msg);
    }
}
