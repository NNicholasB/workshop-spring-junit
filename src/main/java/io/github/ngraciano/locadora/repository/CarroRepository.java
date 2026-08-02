package io.github.ngraciano.locadora.repository;

import io.github.ngraciano.locadora.entity.CarroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarroRepository extends JpaRepository<CarroEntity,Long> {
}
