package com.hari.customsearchdialog.dialog;

public class SpinnerItem {

    String cityId;
    String cityName;

    public SpinnerItem() {
    }

    public SpinnerItem(String cityId, String cityName) {
        this.cityId = cityId;
        this.cityName = cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
