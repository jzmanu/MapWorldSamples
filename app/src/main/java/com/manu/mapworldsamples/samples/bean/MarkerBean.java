package com.manu.mapworldsamples.samples.bean;

/**
 * Powered by jzman.
 * Created on 2018/6/25 0025.
 */
public class MarkerBean {
    private String title;
    private String snippet;
    private int latitude;
    private int longitude;

    public MarkerBean(String title, String snippet, int latitude, int longitude) {
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

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
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
