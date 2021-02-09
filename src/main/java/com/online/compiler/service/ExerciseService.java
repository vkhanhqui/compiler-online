package com.online.compiler.service;

import com.online.compiler.model.dto.ExerciseDTO;

import java.util.List;

public interface ExerciseService {
    ExerciseDTO getExerciseById(String id);

    ExerciseDTO addExercise(int result);

    ExerciseDTO editExercise(String id, int result);

    void deleteExerciseById(String id);

    List<ExerciseDTO> getAllExercises();

    void deleteAllExercises();

}
