package com.example.egear.customer.profile;

import java.io.Serializable;

public class Address implements Serializable {
   private double latitude;
   private double longitude;

   public Address() {
   }

   public Address(double latitude, double longitude) {
      this.latitude = latitude;
      this.longitude = longitude;
   }

   public double getLatitude() {
      return latitude;
   }

   public void setLatitude(double latitude) {
      this.latitude = latitude;
   }

   public double getLongitude() {
      return longitude;
   }

   public void setLongitude(double longitude) {
      this.longitude = longitude;
   }

   @Override
   public String toString() {
      return "Address{" +
              "latitude=" + latitude +
              ", longitude=" + longitude +
              '}';
   }
}
