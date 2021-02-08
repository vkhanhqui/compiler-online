package com.fundamental.compiler.model.repository;

import com.fundamental.compiler.model.CodeFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CodeFileRepository extends CrudRepository<CodeFile, UUID> {
}
