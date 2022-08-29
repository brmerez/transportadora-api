package com.example.apitransportadora.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.utility.RandomString;

import javax.persistence.*;
import java.util.*;

@Getter
@NoArgsConstructor
@Table(name="parcels")
@Entity(name = "parcel")
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, updatable = false)
    private String codRastreio;

    @Column
    private String origem;
    @Column
    private String destino;
    @Column
    private Status status;

    @OneToMany(mappedBy = "parcel", cascade = CascadeType.ALL)
    private List<Etapa> etapas;

    public Parcel(String origem, String destino){
        this.destino = destino;
        this.origem = origem;
        this.codRastreio = Parcel.generateCodRastreio();
        this.status = Status.AGUARDANDO_TRANSPORTE;
        this.etapas = new ArrayList<>();
        this.etapas.add(new Etapa(this, Status.AGUARDANDO_TRANSPORTE, origem));
    }

    @Override
    public String toString() {
        return String.format("PARCELA:\t%s\nORIGEM: \t%s\nDESTINO:\t%s\nSTATUS: \t%s", this.codRastreio, this.origem, this.destino, this.status);
    }


    public static String generateCodRastreio(){
        return RandomString.make(9).toUpperCase().concat("BR");
    }

    @JsonIgnore
    public Set<Status> getLegalStatus(){
        if(this.status == Status.AGUARDANDO_TRANSPORTE){
            return Set.of(Status.EM_TRANSPORTE, Status.EXTRAVIADO);
        }
        else if(this.status == Status.EM_TRANSPORTE){
            return Set.of(Status.ENTREGUE, Status.EXTRAVIADO, Status.AGUARDANDO_TRANSPORTE);
        }
        else{
            return Set.of();
        }
    }

    public void addEtapa(Status status, String endereco){

        // Uma mercadoria começa com um status de AGUARDANDO_TRANSPORTE
        // Disso, ela só pode ser adicionada uma etapa de TRANSPORTE
        // Se a mercadoria já estiver em TRANSPORTE, ela pode pode voltar a AGUARDANDO_TRANSPORTE,
        // pode ser ENTREGUE ou ser EXTRAVIADA.

        if(this.status == status){
            throw new IllegalStateException("A mercadoria já está em " + status.name());
        }

        if(this.status == Status.ENTREGUE || this.status == Status.EXTRAVIADO){
            throw new IllegalStateException("A mercadoria já foi entregue ou extraviada");
        }

        if(!this.getLegalStatus().contains(status)){
            throw new IllegalStateException("A mercadoria não pode mudar de " + this.status.name() + " para " + status.name());
        }

        Etapa etapa = new Etapa(this, status, endereco);
        this.status = status;
        this.etapas.add(etapa);
    }

}
