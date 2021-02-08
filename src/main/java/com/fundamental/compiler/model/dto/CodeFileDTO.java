package com.fundamental.compiler.model.dto;

import java.util.UUID;

public class CodeFileDTO {

    private UUID id;

    private String language;

    private String link;

    private int result;

    private UUID idExercise;

    public CodeFileDTO(){

    }

    public CodeFileDTO(UUID id, String language, String link, int result, UUID idExercise) {
        this.id = id;
        this.language = language;
        this.link = link;
        this.result = result;
        this.idExercise = idExercise;
    }

    public UUID getIdExercise() {
        return idExercise;
    }

    public void setIdExercise(UUID idExercise) {
        this.idExercise = idExercise;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
