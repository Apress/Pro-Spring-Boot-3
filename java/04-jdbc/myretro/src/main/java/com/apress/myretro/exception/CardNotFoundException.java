package com.apress.myretro.exception;

public class CardNotFoundException extends RuntimeException{
    public CardNotFoundException() {
        super("Card Not Found");
    }

    public CardNotFoundException(String message) {
        super(String.format("Card Not Found: {}", message));
    }

    public CardNotFoundException(String message, Throwable cause) {
        super(String.format("Card Not Found: {}", message), cause);
    }
}
