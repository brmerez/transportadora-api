package com.example.apitransportadora.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EtapaDTO {
    private Actions action;
    private String endereco;

}
