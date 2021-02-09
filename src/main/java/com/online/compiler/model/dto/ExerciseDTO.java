package com.online.compiler.model.dto;


import java.util.Set;
import java.util.UUID;

public class ExerciseDTO {

    private UUID id;

    private int result;

    private Set<UUID> idCodeFiles;

    public ExerciseDTO(){

    }

    public ExerciseDTO(UUID id, int result, Set<UUID> idCodeFiles) {
        this.id = id;
        this.result = result;
        this.idCodeFiles = idCodeFiles;
    }

    public Set<UUID> getIdCodeFiles() {
        return idCodeFiles;
    }

    public void setIdCodeFiles(Set<UUID> idCodeFiles) {
        this.idCodeFiles = idCodeFiles;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
