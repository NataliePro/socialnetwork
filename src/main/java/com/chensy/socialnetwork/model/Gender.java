package com.chensy.socialnetwork.model;

import java.util.Locale;

public enum Gender {
    MALE("m"),
    FEMALE("f"),
    UNKNOWN("?");

    private final String gender;

    Gender(String sex) {
        gender = sex;
    }

    public static Gender getGenderByLetter(String genderLetter) {
        if (genderLetter == null)
            return UNKNOWN;

        switch (genderLetter.toLowerCase()) {
            case "m":
                return MALE;
            case "f":
                return FEMALE;
            default:
                return UNKNOWN;
        }
    }

    public String getGenderLetter() {
        return this.gender;
    }
}
