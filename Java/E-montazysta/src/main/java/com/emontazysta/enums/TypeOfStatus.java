package com.emontazysta.enums;

public enum TypeOfStatus {
    PLANNED("PLANNED"),
    IN_PROGRESS("IN_PROGRESS"),
    FINISHED("FINISHED");


    private String name;

    TypeOfStatus(String name) {
        this.name = name;
    }

    public String getValue() {
        return name;
    }
}
