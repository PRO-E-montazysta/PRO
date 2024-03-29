package com.emontazysta.repository;

import org.springframework.core.io.FileSystemResource;

import java.io.IOException;
import java.nio.file.Path;

public interface FileSystemRepository {

    String RESOURCES_DIR = "files/";

    Path save(byte[] content, String fileName) throws IOException;
    FileSystemResource get(String fileName) throws IOException;
    boolean delete(String location) throws IOException;
}
