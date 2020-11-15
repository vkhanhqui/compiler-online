package com.fundamental.compiler.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    public void init();
    public void store(MultipartFile file);
    public Stream<Path> loadAll();
    public void deleteAll();
    public Resource loadAsResource(String filename);
    public int readRs(String filename);
}
