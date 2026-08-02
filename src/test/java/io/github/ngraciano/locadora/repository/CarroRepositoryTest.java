package io.github.ngraciano.locadora.repository;

import io.github.ngraciano.locadora.entity.CarroEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
class CarroRepositoryTest {

    @Autowired
    CarroRepository repository;
    CarroEntity carro;

    @BeforeEach
    void setUp(){
        carro=new CarroEntity("Civic",200.0,2027);
    }

    @Test
    void deveSalvarUmCarro(){
    var car1=new CarroEntity("Sedan",100.0,2007);
    repository.save(car1);

    assertNotNull(car1.getId());
    }

    @Test
    @Sql("/sql/popular-carros.sql")
    void deveBuscarCarroPorModelo(){
        List<CarroEntity> lista=repository.findByModelo("Suv");

        var carro= lista.stream().findFirst().get();
        assertEquals(1,lista.size());
        assertThat(carro.getValorDiaria()).isEqualTo(150.0);
        assertThat(carro.getModelo()).isEqualTo("Suv");
    }

    @Test
    void deveBuscarCarroPorId(){
        CarroEntity carroSalvo = repository.save(carro);

        Optional<CarroEntity> carroEncontrado = repository.findById(carroSalvo.getId());

        assertThat(carroEncontrado).isPresent();
        assertThat(carroEncontrado.get().getModelo()).isEqualTo("Civic");
    }

    @Test
    void deveAtualizarCarro(){
        var carroSalvo= repository.save(carro);
        carroSalvo.setAno(2028);

        var carroAtualizado=repository.save(carroSalvo);

        assertThat(carroAtualizado.getAno()).isEqualTo(2028);


    }

    @Test
    void deveDeletarCarro(){
        var carroSalvo=repository.save(carro);
        repository.deleteById(carro.getId());

        Optional<CarroEntity> carroEncontrado=repository.findById(carro.getId());
        assertThat(carroEncontrado).isEmpty();
    }

}