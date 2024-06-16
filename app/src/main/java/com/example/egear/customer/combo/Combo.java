package com.example.egear.customer.combo;

public class Combo {
    private String id;
    private String name;
    private String value_discount;
    private String percent_discount;
    private String is_percent;
    private String max_discount;

    public Combo(String id, String name, String value_discount, String percent_discount, String is_percent, String max_discount) {
        this.id = id;
        this.name = name;
        this.value_discount = value_discount;
        this.percent_discount = percent_discount;
        this.is_percent = is_percent;
        this.max_discount = max_discount;
    }

    public Combo(String name, String value_discount, String percent_discount, String is_percent, String max_discount) {
        this.name = name;
        this.value_discount = value_discount;
        this.percent_discount = percent_discount;
        this.is_percent = is_percent;
        this.max_discount = max_discount;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue_discount() {
        return value_discount;
    }

    public void setValue_discount(String value_discount) {
        this.value_discount = value_discount;
    }

    public String getPercent_discount() {
        return percent_discount;
    }

    public void setPercent_discount(String percent_discount) {
        this.percent_discount = percent_discount;
    }

    public String getIs_percent() {
        return is_percent;
    }

    public void setIs_percent(String is_percent) {
        this.is_percent = is_percent;
    }

    public String getMax_discount() {
        return max_discount;
    }

    public void setMax_discount(String max_discount) {
        this.max_discount = max_discount;
    }

}