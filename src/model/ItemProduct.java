package model;

import java.io.Serializable;

public class ItemProduct implements Serializable {

    private int itemProductId;
    private String name;
    private String labelName;
    private double price;
    
    public ItemProduct() {
    	
    }
    
    public ItemProduct(int itemProductId, String name, String labelName, 
    		double price) {
    	this.itemProductId = itemProductId;
    	this.name = name;
    	this.labelName = labelName;
    	this.price = price;
    }

    public int getItemProductId() {
        return itemProductId;
    }

    public void setItemProductId(int itemProductId) {
        this.itemProductId = itemProductId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
