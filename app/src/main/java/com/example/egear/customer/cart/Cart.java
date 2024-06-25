package com.example.egear.customer.cart;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.RequestBuilder;

import java.io.Serializable;

public class Cart implements Serializable {
   private Long id;
   private String name;
   private Long price;
   private int quantity;
   private String image;

   public Cart(Long id, String name, Long price, int quantity, String image) {
      this.name = name;
      this.price = price;
      this.quantity = quantity;
      this.image = image;
   }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   public String getName() {
      return name;
   }

   public Long getPrice() {
      return price;
   }

   public int getQuantity() {
      return quantity;
   }

   public void setQuantity(int quantity) {
      this.quantity = quantity;
   }

   public String getImage() {
      return image;
   }

   public void setImage(String image) {
      this.image = image;
   }
}

