package com.compiler.online;

import com.compiler.online.model.Exercise;
import com.compiler.online.model.repository.ExerciseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class CompilerOnlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompilerOnlineApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ExerciseRepository exerciseRepository) {
        Set<Exercise> exerciseSet = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            exerciseSet.add(new Exercise(12));
        }
        exerciseRepository.saveAll(exerciseSet).forEach(
                (exercise) -> System.out.println(exercise.getId())
        );
        return args -> System.out.println("init exercises");
    }

}
