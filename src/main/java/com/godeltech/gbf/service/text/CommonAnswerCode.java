package com.godeltech.gbf.service.text;

public enum CommonAnswerCode {
    COUNTRY_SELECTED_CODE("country.selected"),
    COUNTRY_CITY_START_CODE("country_city.start"),
    COUNTRY_CITY_FINISH_CODE("country_city.finish"),
    DATE_START_CODE("date.start"),
    DATE_FINISH_CODE("date.finish"),
    DATE_TODAY_CODE("date.today"),
    CARGO_DATA_CODE("cargo.data"),
    CONFIRMATION_MESSAGE_CODE("confirmation.message"),
    COMMENT_CONTENT_CODE("comment.content");
    private final String code;

    CommonAnswerCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
