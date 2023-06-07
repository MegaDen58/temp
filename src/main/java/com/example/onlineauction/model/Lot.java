package com.example.onlineauction.model;

import com.example.onlineauction.constants.StatusLot;

public class Lot {
    private int id;
    private String name;
    private String description;
    private double startPrice;
    private double currentPrice;
    private double stepPrice;
    private String publicationDate;
    private String closingDate;
    private String condition;
    private StatusLot statusLot;
    private int sellerId;
    private int currentBuyerId;
    private int categoryId;
    private String statusString;
    private String category;

    public Lot(String name, String category, double startPrice, double currentPrice, String statusString) {
        this.name = name;
        this.category = category;
        this.startPrice = startPrice;
        this.currentPrice = currentPrice;
        this.statusString = statusString;
    }
    public Lot(){

    }

    public Lot(String name, String description, double startPrice, double currentPrice, double stepPrice,
               String publicationDate, String closingDate, String condition) {
        this.name = name;
        this.description = description;
        this.startPrice = startPrice;
        this.currentPrice = currentPrice;
        this.stepPrice = stepPrice;
        this.publicationDate = publicationDate;
        this.closingDate = closingDate;
        this.condition = condition;
        this.statusLot = StatusLot.AWAITING_CONFIRMATION; // Установка значения по умолчанию
    }

    // Геттеры и сеттеры

    public int getId() {
        return id;
    }
    public String getStatusString() {
        return statusString;
    }
    public String getCategory() {
        return category;
    }
    public void setId(int id) {
        this.id = id;
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

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getStepPrice() {
        return stepPrice;
    }

    public void setStepPrice(double stepPrice) {
        this.stepPrice = stepPrice;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public StatusLot getStatusLot() {
        return statusLot;
    }

    public void setStatusLot(StatusLot statusLot) {
        this.statusLot = statusLot;
    }

    public int getCurrentBuyerId() {
        return currentBuyerId;
    }

    public void setCurrentBuyerId(int currentBuyerId) {
        this.currentBuyerId = currentBuyerId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Lot{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startPrice=" + startPrice +
                ", currentPrice=" + currentPrice +
                ", stepPrice=" + stepPrice +
                ", publicationDate='" + publicationDate + '\'' +
                ", closingDate='" + closingDate + '\'' +
                ", condition='" + condition + '\'' +
                ", statusLot=" + statusLot +
                ", sellerId=" + sellerId +
                ", currentBuyerId=" + currentBuyerId +
                ", categoryId=" + categoryId +
                ", category=" + category +
                ", statusString=" + statusString +
                '}';
    }
}
