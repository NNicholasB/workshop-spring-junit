package io.github.ngraciano.locadora.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ClientTest {

    @Test
    void deveCriarClientComNome(){
        var client=new Client("Maria");
        String nome= client.getNome();
        Assertions.assertEquals(nome, nome="Maria");
    }
}
