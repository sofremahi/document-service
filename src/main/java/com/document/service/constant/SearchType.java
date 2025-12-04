package com.document.service.constant;

import lombok.Getter;

@Getter
public enum SearchType {
    TITLE("title"),
    CONTENT("content"),
    TAG("tag"),
    ALL("all");

    private final String value;

    SearchType(String value) { this.value = value; }

    public static SearchType from(String input) {
        if (input == null) return ALL;

        for (SearchType type : values()) {
            if (type.value.equalsIgnoreCase(input) || type.name().equalsIgnoreCase(input)) {
                return type;
            }
        }

        throw new IllegalArgumentException(
                "Invalid mode '" + input + "'. Allowed values: title, content, tag, all"
        );
    }
}
