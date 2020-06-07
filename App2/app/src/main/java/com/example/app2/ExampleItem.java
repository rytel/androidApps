package com.example.app2;

public class ExampleItem {


    private long id;
    private String Pole1;
    private String Pole2;
    private boolean isSelected;

    public ExampleItem(long id, String pole1, String pole2) {
        this.id = id;
        Pole1 = pole1;
        Pole2 = pole2;
        isSelected = false;
    }

    public long getId() {
        return id;
    }

    public String getPole1() {
        return Pole1;
    }

    public String getPole2() {
        return Pole2;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
