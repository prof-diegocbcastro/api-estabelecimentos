package com.establishment.api.domain.port;

import java.util.ArrayList;

import org.springframework.data.domain.Page;

import com.establishment.api.domain.model.Establishment;

public interface EstablishmentPersistencePort {
    String createAll(ArrayList<Establishment> establishments);
    Page<Establishment> findAllByName(String name, int page, int size);
}
