package com.online.compiler.model.repository;

import com.online.compiler.model.CodeFile;
import com.online.compiler.model.Exercise;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, UUID> {
    Optional<Exercise> findByCodeFiles(CodeFile codeFile);
}
