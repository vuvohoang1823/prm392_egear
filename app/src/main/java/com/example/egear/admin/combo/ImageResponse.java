package com.example.egear.admin.combo;

import java.util.List;

public class ImageResponse {
    private String message;
    private List<Image> data;

    public ImageResponse(String message, List<Image> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Image> getData() {
        return data;
    }

    public void setData(List<Image> data) {
        this.data = data;
    }
}
