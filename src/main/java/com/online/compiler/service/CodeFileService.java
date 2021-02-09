package com.online.compiler.service;

import com.online.compiler.model.dto.CodeFileDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface CodeFileService {

    void initRoot();

    CodeFileDTO addCodeFile(MultipartFile file, String language, String idExercise);

    void deleteAllCodeFiles();

    Resource loadAsResource(String filename);

    CodeFileDTO getCodeFileById(String idCodeFile);

    Set<CodeFileDTO> getAllCodeFiles();

    void deleteCodeFileById(String id);
}
