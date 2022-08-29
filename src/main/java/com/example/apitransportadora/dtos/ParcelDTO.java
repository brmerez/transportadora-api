package com.example.apitransportadora.dtos;

import com.example.apitransportadora.entities.Parcel;
import lombok.Getter;

@Getter
public class ParcelDTO {
    private String origem;
    private String destino;

    public Parcel toEntity(){
        return new Parcel(origem, destino);
    }
}
