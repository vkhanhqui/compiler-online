package com.fundamental.compiler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class StorageServiceImpl implements StorageService {

    private final Path rootLocation;

    @Autowired
    public StorageServiceImpl() {
        this.rootLocation = Paths.get("upload-dir");
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
    public void store(MultipartFile file) {
        try {
            Path destinationFile = this.rootLocation.resolve(
                    Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                System.out.println("Cannot store file outside current directory");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            System.out.println("Failed to store file: " + e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            System.out.println("Failed to read stored files: " + e);
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
    public int readRs(String filename) {
        int rs = 0;
        File dir = new File(rootLocation.toAbsolutePath().toString());
        if (!filename.isEmpty()) {
            try {
                Runtime runtime = Runtime.getRuntime();
                String fileToExecute = filename.replace("http://localhost:9999/upload/files/", "");
                Process processToExecuteCode = runtime.exec("gcc "+ fileToExecute, null, dir);
                Process processToGetRS = runtime.exec(dir.getAbsolutePath() + "\\a.exe", null, dir);
                BufferedReader reader = new BufferedReader(new InputStreamReader(processToGetRS.getInputStream()));
                String saveRs = reader.readLine();
                if (!saveRs.isEmpty())
                    rs = Integer.parseInt(saveRs);
                processToExecuteCode.destroy();
                processToGetRS.destroy();
            } catch (IOException io) {
                System.out.println(io);
            }
        }
        return rs;
    }
}
