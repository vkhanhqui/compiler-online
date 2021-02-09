package com.online.compiler.model.repository;

import com.online.compiler.model.CodeFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CodeFileRepository extends CrudRepository<CodeFile, UUID> {
}
