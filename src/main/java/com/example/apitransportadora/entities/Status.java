package com.example.apitransportadora.entities;

public enum Status {
    AGUARDANDO_TRANSPORTE, // Aguardando embarque em um CD
    EM_TRANSPORTE, // Em movimento dentro do veículo
    EXTRAVIADO, // Parcela perdida (fim)
    ENTREGUE  // Entregue (fim)
}
