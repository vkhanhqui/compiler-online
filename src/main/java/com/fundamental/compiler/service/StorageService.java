package com.fundamental.compiler.service;

import com.fundamental.compiler.model.Exercise;
import com.fundamental.compiler.model.dto.CodeFileDTO;
import com.fundamental.compiler.model.dto.ExerciseDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface StorageService {
    void init();

    CodeFileDTO store(MultipartFile file, String language, String idExercise);

    void deleteAll();

    Resource loadAsResource(String filename);

    String getResult(String idCodeFile);

    List<ExerciseDTO> getAllExercises();

    Set<CodeFileDTO> getAllCodeFiles();
}
