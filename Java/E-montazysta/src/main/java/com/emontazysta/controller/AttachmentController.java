package com.emontazysta.controller;

import com.emontazysta.model.dto.AttachmentDto;
import com.emontazysta.service.AttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/attachments", produces = MediaType.APPLICATION_JSON_VALUE)
public class AttachmentController {

    private final AttachmentService attachmentService;

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
    public AttachmentDto add(@Valid @RequestBody AttachmentDto attachmentDto) {
        return attachmentService.add(attachmentDto);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Allows to delete Attachment by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteById(@PathVariable Long id) {
        attachmentService.delete(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Allows to update Attachment by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public AttachmentDto update(@PathVariable Long id, @Valid @RequestBody AttachmentDto attachmentDto) {
        return attachmentService.update(id, attachmentDto);
    }
}
