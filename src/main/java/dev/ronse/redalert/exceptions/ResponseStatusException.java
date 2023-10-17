package dev.ronse.redalert.exceptions;

public class ResponseStatusException extends Exception {
    public ResponseStatusException(int a, int expected) {
        super(String.format("HTTP Request error. Expected status code: %d | Response status code: %d", expected, a));
    }
}
