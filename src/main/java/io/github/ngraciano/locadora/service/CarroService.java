package io.github.ngraciano.locadora.service;


import io.github.ngraciano.locadora.entity.CarroEntity;
import io.github.ngraciano.locadora.exception.EntityNotFoundException;
import io.github.ngraciano.locadora.repository.CarroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarroService {

    private final CarroRepository repository;

    public CarroService(CarroRepository repository) {
        this.repository = repository;
    }

    public CarroEntity  salvar(CarroEntity carro){
        if (carro.getValorDiaria() <=0){
            throw new IllegalArgumentException("Preco da diaria nao pode ser negativo");
        }
        return repository.save(carro);
    }

    public CarroEntity atualizar(Long id,CarroEntity carroAtualizado){

        var carroExistente=repository.findById(id).orElseThrow(()->new EntityNotFoundException("Carro nao encontrado"));

        carroExistente.setAno(carroAtualizado.getAno());
        carroExistente.setModelo(carroAtualizado.getModelo());
        carroExistente.setValorDiaria(carroAtualizado.getValorDiaria());

        return repository.save(carroExistente);

    }

    public void deletar(Long id){
        var carroExistente=repository.findById(id).orElseThrow(()->new EntityNotFoundException("Carro nao encontrado"));

        repository.deleteById(id);
    }

    public CarroEntity buscarPorId(Long id){
        return repository.findById(id).orElseThrow(()->new EntityNotFoundException("Carro nao encontrado"));

    }

    public List<CarroEntity> listarTodos(){
        return repository.findAll();
    }

}
