package com.compiler.online.model.repository;

import com.compiler.online.model.CodeFile;
import com.compiler.online.model.Exercise;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, UUID> {
    Optional<Exercise> findByCodeFiles(CodeFile codeFile);
}
