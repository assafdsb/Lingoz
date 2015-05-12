package com.xplete.lingoz.consts;


public class CONSTS_POS {
    public static final int NOUN = 1;
    public static final int VERB = 2;
    public static final int ADJECTIVE = 3;
    public static final int WRITTEN_ABBREVIATION = 4;
    public static final int PHRASAL_VERB = 5;
    public static final int NOUN_SINGULAR = 6;
    public static final int ADVERB = 7;
    public static final int PREFIX = 8;
    public static final int INTERFJECTION = 9;
    public static final int PREPOSITION = 10;
    public static final int NOUN_PLURAL = 11;
    public static final int SUFFIX = 12;
    public static final int ABBREVIATION = 13;
    public static final int PRONOUN = 14;
    public static final int CONJUNCTION = 15;


    public static String getPosCaption(int pos) {
        String caption = null;
        switch (pos) {
            case VERB:
                caption = "Verb";
                break;
            case NOUN:
                caption = "Noun";
                break;
            case ADJECTIVE:
                caption = "Adjective";
                break;
            case WRITTEN_ABBREVIATION:
                caption = "Written Abbreviation";
                break;
            case PHRASAL_VERB:
                caption = "Phrasal Verb";
                break;
            case NOUN_SINGULAR:
                caption = "Noun Singular";
                break;
            case ADVERB:
                caption = "Adverb";
                break;
            case PREFIX:
                caption = "Prefix";
                break;
            case INTERFJECTION:
                caption = "Interjection";
                break;
            case PREPOSITION:
                caption = "Preposition";
                break;
            case NOUN_PLURAL:
                caption = "Noun Plural";
                break;
            case SUFFIX:
                caption = "Suffix";
                break;
            case ABBREVIATION:
                caption = "Abbreviation";
                break;
            case PRONOUN:
                caption = "pronoun";
                break;
            case CONJUNCTION:
                caption = "Conjunction";
                break;
        }
        return caption;
    }
}