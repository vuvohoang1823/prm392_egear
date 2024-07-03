package com.example.egear.admin.combo;

public class Image {
    private Long id;
    private String media_name;
    private String media_url;
    private String media_type;

    public Image(Long id, String media_name, String media_url, String media_type) {
        this.id = id;
        this.media_name = media_name;
        this.media_url = media_url;
        this.media_type = media_type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedia_name() {
        return media_name;
    }

    public void setMedia_name(String media_name) {
        this.media_name = media_name;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    @Override
    public String toString() {
        return "ImageResponse{" +
                "id=" + id +
                ", media_name='" + media_name + '\'' +
                ", media_url='" + media_url + '\'' +
                ", media_type='" + media_type + '\'' +
                '}';
    }
}
