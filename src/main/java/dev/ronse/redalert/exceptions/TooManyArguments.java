package dev.ronse.redalert.exceptions;

public class TooManyArguments extends ArgumentException {
    public TooManyArguments(int required, int given) {
        super(String.format("%d arguments required, %d given", required, given));
    }
}
