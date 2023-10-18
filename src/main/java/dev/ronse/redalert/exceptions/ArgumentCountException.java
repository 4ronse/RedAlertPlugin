package dev.ronse.redalert.exceptions;

public class ArgumentCountException extends ArgumentException {
    public ArgumentCountException(int required, int given) {
        super(String.format("%d arguments required, %d given", required, given));
    }
}
