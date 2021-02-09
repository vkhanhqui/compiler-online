package com.compiler.online.model.dto;

import java.util.UUID;

public class CodeFileDTO {

    private UUID id;

    private String language;

    private int result;

    private boolean status;

    private UUID idExercise;

    public CodeFileDTO() {

    }

    public CodeFileDTO(UUID id, String language, int result, UUID idExercise) {
        this.id = id;
        this.language = language;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
