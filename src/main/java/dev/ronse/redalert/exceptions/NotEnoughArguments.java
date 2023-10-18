package dev.ronse.redalert.exceptions;

public class NotEnoughArguments extends ArgumentException {
    public NotEnoughArguments(int required, int given) {
        super(String.format("%d arguments required, %d given", required, given));
    }
}
