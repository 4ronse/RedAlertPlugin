package dev.ronse.redalert.exceptions;

public class EntryRequired extends Exception {
    public EntryRequired(String entryName) {
        super(String.format("%s entry is not preset in config file!", entryName));
    }
}
