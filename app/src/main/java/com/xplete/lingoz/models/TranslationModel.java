package com.xplete.lingoz.models;

public class TranslationModel {
    private int lemma_id;
    private int locale_code;
    private String translation;
    private String locale_caption;


    public TranslationModel(int lemma_id, int locale_code, String translation) {
        this.lemma_id = lemma_id;
        this.locale_code = locale_code;
        this.translation = translation;
    }

    public TranslationModel(int lemma_id, int locale_code, String translation, String locale_caption) {
        this.lemma_id = lemma_id;
        this.locale_code = locale_code;
        this.translation = translation;
        this.locale_caption = locale_caption;
    }

    // Getters

    public String getLocale_caption() { return locale_caption;  }

    public int getLemma_id() {
        return lemma_id;
    }

    public int getLocale_code() {
        return locale_code;
    }

    public String getTranslation() {
        return translation;
    }
}
