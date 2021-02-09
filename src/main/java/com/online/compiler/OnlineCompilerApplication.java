package com.online.compiler;

import com.online.compiler.model.Exercise;
import com.online.compiler.model.repository.ExerciseRepository;
import com.online.compiler.service.CodeFileService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class OnlineCompilerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineCompilerApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ExerciseRepository exerciseRepository, CodeFileService codeFileService) {
        Set<Exercise> exerciseSet = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            exerciseSet.add(new Exercise(12));
        }
        exerciseRepository.saveAll(exerciseSet).forEach(
                (exercise) -> System.out.println(exercise.getId())
        );
        codeFileService.initRoot();
        return args -> System.out.println("init exercises");
    }

}
