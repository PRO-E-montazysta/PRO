package com.emontazysta.service;

import com.emontazysta.model.ToolRelease;

import java.util.List;

public interface ToolReleaseService {

    List<ToolRelease> getAll();
    ToolRelease getById(Long id);
    void add(ToolRelease toolRelease);
    void delete(Long id);
    ToolRelease update(Long id, ToolRelease toolRelease);
}
