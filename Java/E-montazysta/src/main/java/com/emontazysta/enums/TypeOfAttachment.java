package com.emontazysta.enums;

public enum TypeOfAttachment {
    OTHER("OTHER"),
    MANUAL("MANUAL"),
    FAULT_PHOTO("FAULT_PHOTO"),
    DESIGN("DESIGN"),
    ACCEPTANCE_PROTOCOL("ACCEPTANCE_PROTOCOL"),
    PROFILE_PICTURE("PROFILE_PICTURE");


    private String name;

    TypeOfAttachment(String name) {
        this.name = name;
    }

    public String getValue() {
        return name;
    }
}
