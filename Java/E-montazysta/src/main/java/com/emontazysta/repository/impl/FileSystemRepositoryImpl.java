package com.emontazysta.repository.impl;

import com.emontazysta.repository.FileSystemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FileSystemRepositoryImpl implements FileSystemRepository {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Path save(byte[] content, String fileName) throws IOException {

        String fileExtension = getFileExtension(fileName).orElseThrow();
        String uniqueName = getUniqueFileName();
        Path newFile = Paths.get(RESOURCES_DIR + uniqueName + "." + fileExtension).normalize();
        Files.createDirectories(newFile.getParent());

        Files.write(newFile, content);

        return newFile;
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

    private String getUniqueFileName() {
        String currentDate = String.valueOf(System.currentTimeMillis());
        return cleanSpecialCharacters(bCryptPasswordEncoder.encode(currentDate));
    }

    private Optional<String> getFileExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    private String cleanSpecialCharacters(String fileName) {
        return fileName.replaceAll("[\\\\/:*?\"<>|]", "");
    }
}
