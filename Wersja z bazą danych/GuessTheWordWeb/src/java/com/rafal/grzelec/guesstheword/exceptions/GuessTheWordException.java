/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rafal.grzelec.guesstheword.exceptions;

/**
 * Exception class of aplication
 * @author Rafał Grzelec
 * @version 1.0
 */
public class GuessTheWordException extends Exception {

    /**
     * Default constructor for the exception
     */
    public GuessTheWordException() {
        super();
    }

    /**
     * Constructor with a message
     *
     * @param message exception's message
     */
    public GuessTheWordException(String message) {
        super(message);
    }
}