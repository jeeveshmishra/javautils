package my.utils;

public enum DateTimeFormat {
    DATE("yyyy-MM-dd"),
    DATE_TIME("yyyy-MM-dd'T'HH:mm:ss");

    String value;

    DateTimeFormat(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
