package com.example.apitransportadora.controllers;

import com.example.apitransportadora.dtos.EtapaDTO;
import com.example.apitransportadora.dtos.ParcelDTO;
import com.example.apitransportadora.entities.Parcel;
import com.example.apitransportadora.entities.Status;
import com.example.apitransportadora.services.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parcels")
@CrossOrigin(origins = "*")
public class ParcelController {

    private final ParcelService parcelService;

    @Autowired
    public ParcelController(ParcelService parcelService){
        this.parcelService = parcelService;
    }

    @GetMapping
    public ResponseEntity<List<Parcel>> getAllParcels(){
        List<Parcel> parcels = this.parcelService.getAllParcels();
        return new ResponseEntity<>(parcels, HttpStatus.OK);
    }

    @PostMapping(consumes ="application/json")
    public ResponseEntity<Parcel> createParcel(@RequestBody ParcelDTO parcel){
        Parcel result = this.parcelService.saveParcel(parcel.toEntity());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{codRastreio}")
    public ResponseEntity<Parcel> getParcelByRastreio(@PathVariable String codRastreio){
       Optional<Parcel> match = this.parcelService.getParcelByRastreio(codRastreio);
       if(match.isPresent()){
           return new ResponseEntity<>(match.get(), HttpStatus.OK);
       }
       else{
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }

    @PutMapping(value = "/{codRastreio}/update", consumes = "application/json")
    public ResponseEntity addStep(@PathVariable(name="codRastreio") String codRastreio, @RequestBody EtapaDTO etapa){
        Optional<Parcel> match = this.parcelService.getParcelByRastreio(codRastreio);
        if(match.isEmpty()){
            return new ResponseEntity<>("Parcela não encontrada.",HttpStatus.NOT_FOUND);
        }

        Parcel parcel = match.get();
        try{
            switch (etapa.getAction()){
                case dropoff:
                    parcel.addEtapa(Status.AGUARDANDO_TRANSPORTE, etapa.getEndereco());
                    break;
                case pickup:
                    parcel.addEtapa(Status.EM_TRANSPORTE, etapa.getEndereco());
                    break;
                case delivered:
                    parcel.addEtapa(Status.ENTREGUE, parcel.getDestino());
                    break;
                case lost:
                    parcel.addEtapa(Status.EXTRAVIADO, etapa.getEndereco());
                    break;
                default:
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Parcel saved = parcelService.saveParcel(parcel);
            return new ResponseEntity<>(saved, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
