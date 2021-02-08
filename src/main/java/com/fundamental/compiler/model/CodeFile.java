package com.fundamental.compiler.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class CodeFile {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid", strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(length = 10, nullable = false)
    private String language;

    @Column(nullable = false, unique = true)
    private String link;

    @Column(nullable = false)
    private int result;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idExercise")
    private Exercise exercise;

    public CodeFile() {

    }

    public CodeFile(String language, String link, Exercise exercise) {
        this.language = language;
        this.link = link;
        this.exercise = exercise;
    }

    @JsonBackReference
    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
