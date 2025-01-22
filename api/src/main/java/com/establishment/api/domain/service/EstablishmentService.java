package com.establishment.api.domain.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.establishment.api.domain.exception.EstablishmentNotFoundException;
import com.establishment.api.domain.exception.InvalidEstablishmentAttribute;
import com.establishment.api.domain.logic.EstablishmentLogic;
import com.establishment.api.domain.model.Establishment;
import com.establishment.api.domain.port.EstablishmentPersistencePort;
import com.establishment.api.domain.port.EstablishmentServicePort;

public class EstablishmentService implements EstablishmentServicePort {

    private EstablishmentLogic establishmentLogic;

    public EstablishmentPersistencePort establishmentPersistencePort;

    public EstablishmentService(EstablishmentPersistencePort establishmentPersistencePort) {
        this.establishmentLogic = new EstablishmentLogic();
        this.establishmentPersistencePort = establishmentPersistencePort;
    }

    @Override
    public String importer(MultipartFile file) throws IOException {
        String line = null;
        ArrayList<Establishment> establishments = new ArrayList<>();
        ArrayList<Establishment> establishmentsOut = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.ISO_8859_1), ';');
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            String[] column = line.split(";");

            establishmentsOut = this.establishmentLogic.filterColumns(column, establishments);
        }
        
        String message = this.establishmentPersistencePort.createAll(establishmentsOut);
        return message;
    }

    @Override
    public Page<Establishment> findAllByName(String name, int page, int size) {
        Page<Establishment> establishments = this.establishmentPersistencePort.findAllByName(name, page, size);
        if (establishments == null) {
            throw new EstablishmentNotFoundException("Establishment not found");
        }
        return establishments;
    }

    @Override
    public Page<Establishment> findAllByNameAndStateAndTypeAndShift(String name, String state, String type, String shift, int page, int size) {
        int nState = state.trim() != "" ? Integer.valueOf(state) : -1;
        int nType = type.trim() != "" ? Integer.valueOf(type) : -1;
        int nShift = shift.trim() != "" ? Integer.valueOf(shift) : -1;

        if (name.trim() == "") {
            throw new InvalidEstablishmentAttribute("Name cannot be NULL or EMPTY");
        }

        Page<Establishment> establishments = this.establishmentPersistencePort.findAllByNameAndStateAndTypeAndShift(name, nState, nType, nShift, page, size);
        if (establishments == null) {
            throw new EstablishmentNotFoundException("Establishment not found");
        }
        return establishments;
    }
}
