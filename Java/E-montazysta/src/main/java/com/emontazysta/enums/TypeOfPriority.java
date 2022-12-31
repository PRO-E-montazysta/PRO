package com.emontazysta.enums;

public enum TypeOfPriority {
    NORMAL("NORMAL"),
    IMPORTANT("IMPORTANT"),
    IMMEDIATE("IMMEDIATE");


    private String name;

    TypeOfPriority(String name) {
        this.name = name;
    }

    public String getValue() {
        return name;
    }
}
