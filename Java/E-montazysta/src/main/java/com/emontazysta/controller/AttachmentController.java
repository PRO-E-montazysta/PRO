package com.emontazysta.controller;

import com.emontazysta.model.dto.AttachmentDto;
import com.emontazysta.repository.FileSystemRepository;
import com.emontazysta.service.AttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/attachments", produces = MediaType.APPLICATION_JSON_VALUE)
public class AttachmentController {

    private final AttachmentService attachmentService;
    private final FileSystemRepository fileSystemRepository;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Attachments.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<List<AttachmentDto>> getAll() {
        return ResponseEntity.ok().body(attachmentService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get Attachment by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<AttachmentDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(attachmentService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Attachment.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<AttachmentDto> add(@Valid @RequestPart AttachmentDto attachmentDto, @RequestPart MultipartFile file) {
        AttachmentDto dto = attachmentService.add(attachmentDto, file);
        if (dto == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Attachment by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteById(@PathVariable Long id) {
        attachmentService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Allows to update Attachment by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<AttachmentDto> update(@PathVariable Long id, @Valid @RequestPart AttachmentDto attachmentDto, @RequestPart MultipartFile file) {
        AttachmentDto dto = attachmentService.update(id, attachmentDto, file);
        if (dto == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/download/{uniqueName}")
    @Operation(description = "Allows to get file from Attachment of id passed in path variable.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<byte[]> downloadImage(@PathVariable String uniqueName) {
        try {
            AttachmentDto attachmentDto = attachmentService.getByUniqueName(uniqueName);
            byte[] file = Files.readAllBytes(fileSystemRepository.get(attachmentDto.getPath()).getFile().toPath());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
            httpHeaders.set(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length));
            httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + attachmentDto.getFileName() + "\"");
            return ResponseEntity.ok().headers(httpHeaders).body(file);
        } catch (IOException ex) {
            log.error("Error occurred while downloading file", ex);
            return ResponseEntity.internalServerError().build();
        }
    }
}
