package com.online.compiler.service.impl;

import com.online.compiler.exception.EntityCreateFailedException;
import com.online.compiler.exception.EntityDeleteFailedException;
import com.online.compiler.exception.EntityUpdateFailedException;
import com.online.compiler.model.Exercise;
import com.online.compiler.model.dto.ExerciseDTO;
import com.online.compiler.model.repository.ExerciseRepository;
import com.online.compiler.service.ExerciseService;
import com.online.compiler.utils.MapperUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
        Exercise exerciseEntity = exerciseRepository.findById(UUID.fromString(id))
                .orElseThrow(EntityNotFoundException::new);
        return MapperUtils.toExerciseDTO(exerciseEntity);
    }

    @Override
    public ExerciseDTO addExercise(int result) {
        try {
            Exercise exercise = new Exercise(result);
            Exercise exerciseEntity = exerciseRepository.save(exercise);
            return MapperUtils.toExerciseDTO(exerciseEntity);
        } catch (IllegalArgumentException ex) {
            throw new EntityCreateFailedException(ex.getMessage());
        }
    }

    @Override
    public ExerciseDTO editExercise(String id, int result) {
        try {
            Exercise exerciseEntity = exerciseRepository.findById(UUID.fromString(id))
                    .orElseThrow(EntityNotFoundException::new);
            exerciseEntity.setResult(result);
            Exercise exerciseEdited = exerciseRepository.save(exerciseEntity);
            return MapperUtils.toExerciseDTO(exerciseEdited);
        } catch (IllegalArgumentException ex) {
            throw new EntityUpdateFailedException(ex.getMessage());
        }
    }

    @Override
    public void deleteExerciseById(String id) {
        try {
            exerciseRepository.deleteById(UUID.fromString(id));
        } catch (IllegalArgumentException ex) {
            throw new EntityDeleteFailedException(ex.getMessage());
        }
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
