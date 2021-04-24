package tech.hamid.bookery;

import java.io.Serializable;

public class MyStory implements Serializable {
    String title;
    String story;

    String color;

    public MyStory() {

    }
    public MyStory(String title, String story, String color) {
        this.title = title;
        this.story = story;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}