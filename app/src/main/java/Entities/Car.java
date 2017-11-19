package Entities;

/**
 * Created by Android on 11/16/2017.
 */

public class Car {
    long id;
    String model;
    int year;
    String color;

    public Car(long id,String model, int year, String color) {
        this.model = model;
        this.year = year;
        this.color = color;
        this.id=id;
    }

    public Car(String model, int year, String color) {
        this.model = model;
        this.year = year;
        this.color = color;
    }

    public String getModel() {
        return model;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
