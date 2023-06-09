package com.emontazysta.repository.impl;

import com.emontazysta.repository.FileSystemRepository;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Repository
public class FileSystemRepositoryImpl implements FileSystemRepository {

    @Override
    public String save(byte[] content, String fileName) throws IOException {

        Path newFile = Paths.get(RESOURCES_DIR + new Date().getTime() + "-" + fileName);
        Files.createDirectories(newFile.getParent());

        //TODO UNCOMMENT IT TO SAVE FILES!
        //Files.write(newFile, content);

        return newFile.toAbsolutePath().toString();
    }

    @Override
    public FileSystemResource get(String location) throws IOException {

        try {
            return new FileSystemResource(Paths.get(location));
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @Override
    public boolean delete(String location) throws IOException {
        return Files.deleteIfExists(Paths.get(location));
    }
}
