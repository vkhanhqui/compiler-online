package com.online.compiler.controller;

import com.online.compiler.model.dto.ExerciseDTO;
import com.online.compiler.service.ExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("exercises")
public class ExerciseController {
    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public ResponseEntity<List<ExerciseDTO>> getAllExercises() {
        return ResponseEntity.ok(exerciseService.getAllExercises());
    }

    @GetMapping("{id}")
    public ResponseEntity<ExerciseDTO> getExerciseById(
            @PathVariable("id") String id
    ) {
        return ResponseEntity.ok(exerciseService.getExerciseById(id));
    }

    @PostMapping
    public ResponseEntity<ExerciseDTO> addExercise(
            @RequestParam int result
    ) {
        return ResponseEntity.ok(exerciseService.addExercise(result));
    }

    @PutMapping("{id}")
    public ResponseEntity<ExerciseDTO> editExercise(
            @PathVariable("id") String id,
            @RequestParam int result
    ) {
        return ResponseEntity.ok(exerciseService.editExercise(id, result));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteExerciseById(
            @PathVariable("id") String id
    ) {
        exerciseService.deleteExerciseById(id);
        return ResponseEntity.ok("successfully delete Exercise with id " + id);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllExercise() {
        exerciseService.deleteAllExercises();
        return ResponseEntity.ok("All Exercises are successfully deleted");
    }
}
