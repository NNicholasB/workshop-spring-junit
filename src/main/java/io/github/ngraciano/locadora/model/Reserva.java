package io.github.ngraciano.locadora.model;

import io.github.ngraciano.locadora.exception.ReservaInvalidaException;

public class Reserva {
    private Carro carro;
    private Client client;
    private int dias;

    public Reserva(Carro carro, Client client, int dias) {
        if (dias<=0){
            throw new ReservaInvalidaException("Dias nao podem ser negativos");
        }
        this.carro = carro;
        this.client = client;
        this.dias = dias;
    }
    public double valorReserva(){
       return carro.calcularValorAluguel(this.dias);
    }

}
