package com.emontazysta.service;

import com.emontazysta.model.Fitter;

import java.util.List;

public interface FitterService {

    List<Fitter> getAll();
    Fitter getById(Long id);
    void add(Fitter fitter);
    void delete(Long id);

}
