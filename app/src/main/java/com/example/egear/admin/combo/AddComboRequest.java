package com.example.egear.admin.combo;

import java.util.List;

public class AddComboRequest {
    private String name;
    private String description;
    private String status;
    private String discount_by_percent;
    private Long discount_by_value;
    private Long main_media_id;
    private List<Long> product_ids;

    public AddComboRequest() {
    }

    public AddComboRequest(String name, String description, String status, String discount_by_percent, Long discount_by_value) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.discount_by_percent = discount_by_percent;
        this.discount_by_value = discount_by_value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDiscount_by_percent() {
        return discount_by_percent;
    }

    public void setDiscount_by_percent(String discount_by_percent) {
        this.discount_by_percent = discount_by_percent;
    }

    public Long getDiscount_by_value() {
        return discount_by_value;
    }

    public void setDiscount_by_value(Long discount_by_value) {
        this.discount_by_value = discount_by_value;
    }

    public Long getMain_media_id() {
        return main_media_id;
    }

    public void setMain_media_id(Long main_media_id) {
        this.main_media_id = main_media_id;
    }

    public List<Long> getProduct_ids() {
        return product_ids;
    }

    public void setProduct_ids(List<Long> product_ids) {
        this.product_ids = product_ids;
    }

    @Override
    public String toString() {
        return "AddComboRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", discount_by_percent='" + discount_by_percent + '\'' +
                ", discount_by_value=" + discount_by_value +
                ", main_media_id=" + main_media_id +
                ", product_ids=" + product_ids +
                '}';
    }
}
