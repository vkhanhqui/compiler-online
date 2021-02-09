package com.compiler.online.model.repository;

import com.compiler.online.model.CodeFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CodeFileRepository extends CrudRepository<CodeFile, UUID> {
}
