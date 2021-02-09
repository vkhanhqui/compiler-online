package com.compiler.online.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Exercise {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid", strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private int result;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CodeFile> codeFiles = new HashSet<>();

    public Exercise() {

    }

    public Exercise(int result, Set<CodeFile> codeFiles) {
        this.result = result;
        this.codeFiles = codeFiles;
    }

    public Exercise(int result){
        this.result = result;
    }

    @JsonManagedReference
    public Set<CodeFile> getCodeFiles() {
        return codeFiles;
    }

    public void setCodeFiles(Set<CodeFile> codeFiles) {
        this.codeFiles = codeFiles;
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
