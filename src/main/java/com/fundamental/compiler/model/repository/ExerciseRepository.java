package com.fundamental.compiler.model.repository;

import com.fundamental.compiler.model.CodeFile;
import com.fundamental.compiler.model.Exercise;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, UUID> {
    Optional<Exercise> findByCodeFiles(CodeFile codeFile);
}
