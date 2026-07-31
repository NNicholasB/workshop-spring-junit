package io.github.ngraciano.locadora.model;

public class Carro {
    private String modelo;
    private double valorDiario;

    public Carro(String modelo, double valorDiario) {
        this.modelo = modelo;
        this.valorDiario = valorDiario;
    }

    public double calcularValorAluguel(int dias){
        return dias*valorDiario;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getValorDiario() {
        return valorDiario;
    }

    public void setValorDiario(double valorDiario) {
        this.valorDiario = valorDiario;
    }
}
