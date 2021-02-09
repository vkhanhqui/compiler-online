package com.compiler.online.service;

import com.compiler.online.model.dto.ExerciseDTO;

import java.util.List;

public interface ExerciseService {
    ExerciseDTO getExerciseById(String id);

    ExerciseDTO addExercise(int result);

    ExerciseDTO editExercise(String id, int result);

    void deleteExerciseById(String id);

    List<ExerciseDTO> getAllExercises();

    void deleteAllExercises();

}
