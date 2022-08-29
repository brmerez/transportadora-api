package com.example.apitransportadora.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Table(name="etapas")
public class Etapa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name="parcel_id")
    Parcel parcel;

    @Column(nullable = false,updatable = false)
    Status status;

    @Column(nullable = false,updatable = false)
    String endereco;

    private Date timestamp;

    public Etapa(Parcel parcel, Status status, String endereco){
        this.parcel = parcel;
        this.status = status;
        this.endereco = endereco;
        this.timestamp = new Date();
    }

}
