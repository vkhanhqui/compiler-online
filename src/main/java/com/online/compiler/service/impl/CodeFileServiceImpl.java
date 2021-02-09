package com.online.compiler.service.impl;

import com.online.compiler.model.CodeFile;
import com.online.compiler.model.Exercise;
import com.online.compiler.model.dto.CodeFileDTO;
import com.online.compiler.model.repository.CodeFileRepository;
import com.online.compiler.model.repository.ExerciseRepository;
import com.online.compiler.service.CodeFileService;
import com.online.compiler.utils.CmdUtils;
import com.online.compiler.utils.MapperUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class CodeFileServiceImpl implements CodeFileService {

    private final CodeFileRepository codeFileRepository;
    private final ExerciseRepository exerciseRepository;

    private final Path rootLocation = Paths.get("upload-dir");

    public CodeFileServiceImpl(CodeFileRepository codeFileRepository, ExerciseRepository exerciseRepository) {
        this.codeFileRepository = codeFileRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public void initRoot() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            System.out.println("Could not initialize storage: " + e);
        }
    }

    @Override
    public CodeFileDTO addCodeFile(MultipartFile file, String language, String idExercise) {
        try {
            Exercise exerciseEntity = exerciseRepository.findById(UUID.fromString(idExercise)).get();
            CodeFile codeFileRequest = new CodeFile(language, exerciseEntity);
            CodeFile codeFileEntity = codeFileRepository.save(codeFileRequest);
            Path destinationFile = MapperUtils.toDestinationFile(this.rootLocation, codeFileEntity.getFileName());
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                System.out.println("Cannot store file outside current directory");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
                compileCodeAndCompare(exerciseEntity, codeFileEntity);
                return MapperUtils.modelMapper.map(codeFileRepository.save(codeFileEntity), CodeFileDTO.class);
            }
        } catch (IOException e) {
            System.out.println("Failed to store file: " + e);
            return null;
        }
    }

    @Override
    public void deleteAllCodeFiles() {
        codeFileRepository.deleteAll();
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                System.out.println("Could not read file: " + filename);
                return null;
            }
        } catch (MalformedURLException e) {
            System.out.println("Could not read file: " + filename + e);
            return null;
        }
    }

    @Override
    public CodeFileDTO getCodeFileById(String idCodeFile) {
        CodeFile codeFileEntity = codeFileRepository.findById(UUID.fromString(idCodeFile)).get();
        return MapperUtils.toCodeFileDTO(codeFileEntity);
    }

    @Override
    public Set<CodeFileDTO> getAllCodeFiles() {
        List<CodeFile> codeFileList = (List<CodeFile>) codeFileRepository.findAll();
        return MapperUtils.mapSet(codeFileList, CodeFileDTO.class);
    }

    @Override
    public void deleteCodeFileById(String id) {
        codeFileRepository.deleteById(UUID.fromString(id));
    }

    private void compileCodeAndCompare(Exercise exerciseEntity, CodeFile codeFileEntity) {
        File dir = new File(rootLocation.toAbsolutePath().toString());
        Runtime runtime = Runtime.getRuntime();
        int result = CmdUtils.compileFile(codeFileEntity.getId().toString(), codeFileEntity.getFileName(), runtime, dir);
        codeFileEntity.setResult(result);
        if (exerciseEntity.getResult() == codeFileEntity.getResult()) {
            codeFileEntity.setStatus(true);
        }
    }
}
