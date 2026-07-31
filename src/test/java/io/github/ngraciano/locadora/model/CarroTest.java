package io.github.ngraciano.locadora.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CarroTest {

    @Test
    @DisplayName("Deve calcular o valor do aluguel corretamente.")
    void deveCalcularValorAluguel(){
        Carro carro=new Carro("Sedan",100.0);
        double total=carro.calcularValorAluguel(3);
        Assertions.assertEquals(300.0,total);
    }

    @Test
    @DisplayName("Deve calcular o valor do aluguel corretamente com desconto.")
    void deveCalcularValorAluguelComDesconto(){
        Carro carro=new Carro("Sedan",100.0);
        int dias=5;
        double total=carro.calcularValorAluguel(dias);
        Assertions.assertEquals(450.0,total);
    }



}
