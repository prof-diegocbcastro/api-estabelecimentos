package com.establishment.api.domain.port;

import java.util.ArrayList;

import org.springframework.data.domain.Page;

import com.establishment.api.domain.model.Establishment;

public interface EstablishmentPersistencePort {
    String createAll(ArrayList<Establishment> establishments);
    Establishment findByCnes(Integer cnes);
    Page<Establishment> findAllByNameAndFilteringAndType(String name, int state, int type, int shift, int page, int size);
    Page<Establishment> findAllByNameAndStateAndTypeAndShift(String name, int state, int type, int shift, int page, int size);
    Page<Establishment> findByLocalCoordinates(Double minLatitude, Double maxLatitude, Double minLongitude, Double maxLongitude, int page, int size);
    Page<Establishment> findByLocalCoordinatesAndFiltering(Double minLatitude, Double maxLatitude, Double minLongitude, Double maxLongitude, int type, int shift, int page, int size);
    Page<Establishment> findByLocalCoordinatesAndFilteringAndType(Double minLatitude, Double maxLatitude, Double minLongitude, Double maxLongitude, int type, int shift, int page, int size);
}
