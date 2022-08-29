package com.example.apitransportadora.repositories;

import com.example.apitransportadora.entities.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParcelRepository  extends JpaRepository<Parcel, UUID> {
    Optional<Parcel> findByCodRastreio(String codRastreio);
}
