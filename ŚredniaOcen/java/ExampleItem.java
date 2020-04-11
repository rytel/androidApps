package com.example.kalkulatorocen;


public class ExampleItem {
    private String mText;
    private float grade;

    public ExampleItem(int position) {
        mText = "Ocena " + position;
        grade = 2;
    }

    public String getmText() {
        return mText;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }


}
