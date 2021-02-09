package com.compiler.online.controller;

import com.compiler.online.model.dto.CodeFileDTO;
import com.compiler.online.service.CodeFileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;


@RestController
@RequestMapping("code-files")
public class CodeFileController {

    private final CodeFileService codeFileService;

    public CodeFileController(CodeFileService codeFileService) {
        this.codeFileService = codeFileService;
    }

    @GetMapping("{id}")
    public ResponseEntity<CodeFileDTO> getCodeFileById(@PathVariable("id") String codeFile_id) {
        return ResponseEntity.ok(codeFileService.getCodeFileById(codeFile_id));
    }

    @PostMapping("files/{filename:.+}")
    public ResponseEntity<Resource> getFileUrl(@PathVariable String filename) {
        Resource file = codeFileService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping
    public ResponseEntity<CodeFileDTO> addCodeFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("language") String language,
            @RequestParam("idExercise") String idExercise
    ) {
        return ResponseEntity.ok(
                codeFileService.addCodeFile(file, language, idExercise));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllCodeFiles() {
        codeFileService.deleteAllCodeFiles();
        codeFileService.initRoot();
        return ResponseEntity.ok("All code files are successfully deleted");
    }

    @GetMapping
    public ResponseEntity<Set<CodeFileDTO>> getAllCodeFiles() {
        return ResponseEntity.ok(codeFileService.getAllCodeFiles());
    }
}
