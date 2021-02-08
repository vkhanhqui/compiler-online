package com.fundamental.compiler.controller;

import com.fundamental.compiler.FundamentalCompilerApplication;
import com.fundamental.compiler.model.dto.CodeFileDTO;
import com.fundamental.compiler.model.dto.ExerciseDTO;
import com.fundamental.compiler.service.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("compiler")
public class FileUploadController {

    private final StorageService storageService;

    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping
    public ResponseEntity<String> getResult(@RequestParam("id") String codeFile_id) {
        return ResponseEntity.ok(storageService.getResult(codeFile_id));
    }

    @PostMapping("files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFileUrl(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping
    public ResponseEntity<CodeFileDTO> handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("language") String language
//            @RequestParam("idExercise") String idExercise
    ) {
        return ResponseEntity.ok(
                storageService.store(file, language, FundamentalCompilerApplication.idExercise));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll() {
        storageService.deleteAll();
        storageService.init();
        return ResponseEntity.ok("You successfully deleted");
    }

    @GetMapping("all-exercises")
    public ResponseEntity<List<ExerciseDTO>> getAllExercises() {
        return ResponseEntity.ok(storageService.getAllExercises());
    }

    @GetMapping("all-code-files")
    public ResponseEntity<Set<CodeFileDTO>> getAllCodeFiles() {
        return ResponseEntity.ok(storageService.getAllCodeFiles());
    }
}
