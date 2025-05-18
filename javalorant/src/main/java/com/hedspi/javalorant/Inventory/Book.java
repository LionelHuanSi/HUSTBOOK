package com.hedspi.javalorant.inventory;

public class Book extends Product {
    private String publisher;
    private String author;
    private String ISBN;

    public Book(String name, int quantity, double purchasePrice,
               double sellingPrice, String publisher, String author, String ISBN) {
        super(name, quantity, purchasePrice, sellingPrice);
        this.publisher = publisher;
        this.author = author;
        this.ISBN = ISBN;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    @Override
    public String getProductType() {
        return "Book";
    }

    @Override
    public String toString() {
        return "Book{" +
                "productID=" + getProductID() +
                ", name='" + getName() + '\'' +
                ", quantity=" + getQuantity() +
                ", purchasePrice=" + getPurchasePrice() +
                ", sellingPrice=" + getSellingPrice() +
                ", publisher='" + publisher + '\'' +
                ", author='" + author + '\'' +
                ", ISBN='" + ISBN + '\'' +
                '}';
    }
}
