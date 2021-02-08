package com.fundamental.compiler;

import com.fundamental.compiler.model.Exercise;
import com.fundamental.compiler.model.repository.ExerciseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FundamentalCompilerApplication {

    public static String idExercise = "";

    public static void main(String[] args) {
        SpringApplication.run(FundamentalCompilerApplication.class, args);
    }

    @Bean
    CommandLineRunner createExercises(ExerciseRepository exerciseRepository) {
        Exercise exercise = new Exercise();
        exercise.setResult(12);
        return (args) -> {
            Exercise exerciseEntity = exerciseRepository.save(exercise);
            idExercise = exerciseEntity.getId().toString();
            System.out.println(exerciseEntity.toString());
        };
    }

}
