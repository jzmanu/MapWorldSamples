package com.manu.test.bean;

/**
 * Powered by jzman.
 * Created on 2018/6/25 0025.
 */
public class MarkerBean {
    private String title;
    private String snippet;
    private String latitude;
    private String longitude;

    public MarkerBean(String title, String snippet, String latitude, String longitude) {
        this.title = title;
        this.snippet = snippet;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "MarkerBean{" +
                "title='" + title + '\'' +
                ", snippet='" + snippet + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
