package io.github.ngraciano.locadora.repository;

import io.github.ngraciano.locadora.entity.CarroEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
class CarroRepositoryTest {

    @Autowired
    CarroRepository repository;

    @Test
    void deveSalvarUmCarro(){
    var car1=new CarroEntity("Sedan",100.0);
    repository.save(car1);

    assertNotNull(car1.getId());
    }
}