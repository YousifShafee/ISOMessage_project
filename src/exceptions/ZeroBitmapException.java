/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author IslaM
 * date:1/8/2018
 * details: exception of only zeros bitmap
 */
public class ZeroBitmapException extends WrongMessageException {

    public ZeroBitmapException(String reason, String cause) {
        super(reason + " : " + cause);
    }
}
