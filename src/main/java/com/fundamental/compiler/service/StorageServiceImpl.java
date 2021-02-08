package com.fundamental.compiler.service;

import com.fundamental.compiler.model.CodeFile;
import com.fundamental.compiler.model.Exercise;
import com.fundamental.compiler.model.dto.CodeFileDTO;
import com.fundamental.compiler.model.dto.ExerciseDTO;
import com.fundamental.compiler.model.repository.CodeFileRepository;
import com.fundamental.compiler.model.repository.ExerciseRepository;
import com.fundamental.compiler.utils.CmdUtils;
import com.fundamental.compiler.utils.MapperUtils;
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
import java.util.stream.Collectors;

@Service
public class StorageServiceImpl implements StorageService {

    private final CodeFileRepository codeFileRepository;
    private final ExerciseRepository exerciseRepository;

    private final Path rootLocation = Paths.get("upload-dir");

    public StorageServiceImpl(CodeFileRepository codeFileRepository, ExerciseRepository exerciseRepository) {
        this.codeFileRepository = codeFileRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            System.out.println("Could not initialize storage: " + e);
        }
    }

    @Override
    public CodeFileDTO store(MultipartFile file, String language, String idExercise) {
        try {
            Exercise exerciseEntity = exerciseRepository.findById(UUID.fromString(idExercise)).get();
            CodeFile codeFileRequest = new CodeFile(language, "", exerciseEntity);
            CodeFile codeFileEntity = codeFileRepository.save(codeFileRequest);
            Path destinationFile = MapperUtils.toDestinationFile(this.rootLocation, codeFileEntity.getId().toString());
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                System.out.println("Cannot store file outside current directory");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
                codeFileEntity.setLink(destinationFile.toAbsolutePath().toString());
                return MapperUtils.modelMapper.map(codeFileRepository.save(codeFileEntity), CodeFileDTO.class);
            }
        } catch (IOException e) {
            System.out.println("Failed to store file: " + e);
            return null;
        }
    }

    @Override
    public void deleteAll() {
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
    public String getResult(String idCodeFile) {
        CodeFile codeFile = codeFileRepository.findById(UUID.fromString(idCodeFile)).get();
        File dir = new File(rootLocation.toAbsolutePath().toString());
        Runtime runtime = Runtime.getRuntime();
        CmdUtils.compileFile(codeFile.getId().toString(), runtime, dir);
        int request = CmdUtils.getResult(runtime, dir);
        int result = exerciseRepository.findByCodeFiles(codeFile).get().getResult();
        if (result == request)
            return "equal";
        return "not equal";
    }

    @Override
    public List<ExerciseDTO> getAllExercises() {
        List<Exercise> exercises = (List<Exercise>) exerciseRepository.findAll();
        return exercises.stream()
                .map(MapperUtils::toExerciseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Set<CodeFileDTO> getAllCodeFiles() {
        List<CodeFile> codeFileList = (List<CodeFile>) codeFileRepository.findAll();
        return MapperUtils.mapSet(codeFileList, CodeFileDTO.class);
    }
}
