package com.example.egear.customer.cart;

import java.io.Serializable;

public class Cart implements Serializable {
   private String name;
   private double price;
   private int quantity;
   private int imageResource;

   public Cart(String name, double price, int quantity, int imageResource) {
      this.name = name;
      this.price = price;
      this.quantity = quantity;
      this.imageResource = imageResource;
   }

   public String getName() {
      return name;
   }

   public double getPrice() {
      return price;
   }

   public int getQuantity() {
      return quantity;
   }

   public int getImageResource() {
      return imageResource;
   }

   public void setQuantity(int quantity) {
      this.quantity = quantity;
   }
}

