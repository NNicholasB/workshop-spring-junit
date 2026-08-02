package io.github.ngraciano.locadora.repository;

import io.github.ngraciano.locadora.entity.CarroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarroRepository extends JpaRepository<CarroEntity,Long> {

    List<CarroEntity> findByModelo(String modelo);
}
