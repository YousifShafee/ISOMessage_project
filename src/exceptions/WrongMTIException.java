/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Omar
 */
public class WrongMTIException extends WrongMessageException {

    public WrongMTIException(String reason, String cause) {
        super(reason + " : " + cause);
    }

}
