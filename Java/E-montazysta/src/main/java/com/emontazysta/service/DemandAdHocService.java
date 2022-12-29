package com.emontazysta.service;

import com.emontazysta.model.DemandAdHoc;

import java.util.List;

public interface DemandAdHocService {

    List<DemandAdHoc> getAll();
    DemandAdHoc getById(Long id);
    void add(DemandAdHoc demandAdHoc);
    void delete(Long id);
}
