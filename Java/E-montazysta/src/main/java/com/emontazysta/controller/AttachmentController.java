package com.emontazysta.controller;

import com.emontazysta.model.Attachment;
import com.emontazysta.service.impl.AttachmentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_BASE_CONSTANT + "/attachments", produces = MediaType.APPLICATION_JSON_VALUE)
public class AttachmentController {

    private final AttachmentServiceImpl attachmentService;

    @GetMapping
    @Operation(description = "Allows to get all Attachments.", security = @SecurityRequirement(name = "bearer-key"))
    public List<Attachment> getAll() {
        return attachmentService.getAll();
    }

    @GetMapping("{id}")
    @Operation(description = "Allows to get Attachment by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public Attachment getById(@PathVariable Long id) {
        return attachmentService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Allows to add new Attachment.", security = @SecurityRequirement(name = "bearer-key"))
    public void add(@RequestBody Attachment attachment) {
        attachmentService.add(attachment);
    }

    @DeleteMapping("{id}")
    @Operation(description = "Allows to delete Attachment by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public void deleteById(@PathVariable Long id) {
        attachmentService.delete(id);
    }
}
