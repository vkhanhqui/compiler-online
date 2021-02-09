package com.online.compiler.service.impl;

import com.online.compiler.model.Exercise;
import com.online.compiler.model.dto.ExerciseDTO;
import com.online.compiler.model.repository.ExerciseRepository;
import com.online.compiler.utils.MapperUtils;
import com.online.compiler.service.ExerciseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    private final ExerciseRepository exerciseRepository;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public ExerciseDTO getExerciseById(String id) {
        Exercise exerciseEntity = exerciseRepository.findById(UUID.fromString(id)).get();
        return MapperUtils.toExerciseDTO(exerciseEntity);
    }

    @Override
    public ExerciseDTO addExercise(int result) {
        Exercise exercise = new Exercise(result);
        Exercise exerciseEntity = exerciseRepository.save(exercise);
        return MapperUtils.toExerciseDTO(exerciseEntity);
    }

    @Override
    public ExerciseDTO editExercise(String id, int result) {
        Exercise exerciseEntity = exerciseRepository.findById(UUID.fromString(id)).get();
        exerciseEntity.setResult(result);
        Exercise exerciseEdited = exerciseRepository.save(exerciseEntity);
        return MapperUtils.toExerciseDTO(exerciseEdited);
    }

    @Override
    public void deleteExerciseById(String id) {
        exerciseRepository.deleteById(UUID.fromString(id));
    }

    @Override
    public List<ExerciseDTO> getAllExercises() {
        List<Exercise> exercises = (List<Exercise>) exerciseRepository.findAll();
        return exercises.stream()
                .map(MapperUtils::toExerciseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllExercises() {
        exerciseRepository.deleteAll();
    }
}
