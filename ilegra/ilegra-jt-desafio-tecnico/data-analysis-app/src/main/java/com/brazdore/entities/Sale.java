package com.brazdore.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sale {

    private String id;
    private List<Item> itemList = new ArrayList<>();
    private BigDecimal revenue;
    private String salesmanName;

    public Sale() {
    }

    public Sale(String id, List<Item> itemList, BigDecimal revenue, String salesmanName) {
        this.id = id;
        this.itemList = itemList;
        this.revenue = revenue;
        this.salesmanName = salesmanName;
    }

    public Sale(String id, List<Item> itemList, String name) {
        this.id = id;
        this.itemList = itemList;
        this.salesmanName = name;
    }

    public String getId() {
        return id;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return id.equals(sale.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id='" + id + '\'' +
                ", itemList=" + itemList +
                ", revenue=" + revenue +
                ", salesmanName='" + salesmanName + '\'' +
                '}';
    }
}
