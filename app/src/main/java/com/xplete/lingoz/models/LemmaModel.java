package com.xplete.lingoz.models;

public class LemmaModel {
    private int id;
    private String caption;
    private int pos;
    private String definition;
    private String example;

    public LemmaModel(int id, String caption, int pos, String definition, String example) {
        this.id = id;
        this.caption = caption;
        this.pos = pos;
        this.definition = definition;
        this.example = example;
    }

    public int getId() {
        return id;
    }

    public String getCaption() {
        return caption;
    }

    public int getPos() {
        return pos;
    }

    public String getDefinition() {
        return definition;
    }

    public String getExample() {
        return example;
    }

}
