package io.github.ngraciano.locadora.model;

import io.github.ngraciano.locadora.exception.ReservaInvalidaException;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservaTest {
Client client1;
Carro car1;

    @BeforeEach
      void setUp(){
         car1=new Carro("Sedan",300.0);
         client1=new Client("Nicholas");
    }

@Test
@DisplayName("Deve calcular o valor da Reserva corretamente.")
void deveCalcularRervaCorretamente(){

        var reserva=new Reserva(car1,client1,2);
    Assertions.assertTrue(car1.getModelo().startsWith("S"));
    var valor=reserva.valorReserva();
    Assertions.assertEquals(600.0,valor);
}

    @Test
    @DisplayName("Deve calcular o valor da reserva corretamente com desconto.")
    void deveCalcularRervaCorretamenteComDesconto(){

        var reserva=new Reserva(car1,client1,6);
        Assertions.assertTrue(car1.getModelo().startsWith("S"));
        Assertions.assertEquals("Nicholas", client1.getNome());
        var valor=reserva.valorReserva();
        Assertions.assertEquals(1750.0,valor);
    }
    @Test
    @DisplayName("Deve negar a criacao de uma reserva com valor negativo de dias.")
    void testErroComDiasNegativos(){
    Assertions.assertThrows(ReservaInvalidaException.class,()-> new Reserva(car1,client1,-4));
    var erro= org.assertj.core.api.Assertions.catchThrowable(()-> new Reserva(car1,client1,-4));
        org.assertj.core.api.Assertions.assertThat(erro).isInstanceOf(ReservaInvalidaException.class).hasMessage("Dias nao podem ser negativos");
}



}
