package com.example.egear.admin.products;

public class AddProductRequest {
    private String name;
    private String description;
    private Long price;
    private String status;
    private String category;
    private int quantity;
    private Long main_media_id;

    public AddProductRequest(String name, String description, Long price, String status, String category, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.category = category;
        this.quantity = quantity;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getMain_media_id() {
        return main_media_id;
    }

    public void setMain_media_id(Long main_media_id) {
        this.main_media_id = main_media_id;
    }

    @Override
    public String toString() {
        return "AddProductRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", category='" + category + '\'' +
                ", quantity=" + quantity +
                ", main_media_id=" + main_media_id +
                '}';
    }
}
