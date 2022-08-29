package com.example.apitransportadora.services;

import com.example.apitransportadora.entities.Parcel;
import com.example.apitransportadora.repositories.ParcelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParcelService {

    private final ParcelRepository parcelRepository;

    @Autowired
    public ParcelService(ParcelRepository parcelRepository){
        this.parcelRepository = parcelRepository;
    }

    public Optional<Parcel> getParcelById(UUID id){
        return this.parcelRepository.findById(id);
    }

    public Optional<Parcel> getParcelByRastreio(String rastreio){
        return this.parcelRepository.findByCodRastreio(rastreio);
    }

    public List<Parcel> getAllParcels(){
        return (List<Parcel>)this.parcelRepository.findAll();
    }

    public Parcel saveParcel(Parcel parcel){
        return this.parcelRepository.save(parcel);
    }

}
