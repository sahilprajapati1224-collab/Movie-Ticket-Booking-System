package model;

public class Movie {
    private int id;
    private String title;
    private String genre;
    private String language;
    private String showTime;
    private double price;

    public Movie(int id, String title, String genre, String language, String showTime, double price) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.language = language;
        this.showTime = showTime;
        this.price = price;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return id + ". " + title + " [" + genre + ", " + language + "] - " + showTime + " - Rs." + price;
    }
}
