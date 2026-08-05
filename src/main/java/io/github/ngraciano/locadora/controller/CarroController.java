package io.github.ngraciano.locadora.controller;

import io.github.ngraciano.locadora.entity.CarroEntity;
import io.github.ngraciano.locadora.exception.EntityNotFoundException;
import io.github.ngraciano.locadora.service.CarroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carros")
public class CarroController {

    private final CarroService service;

    public CarroController(CarroService service) {
        this.service = service;
    }

    @PostMapping("/salvar")
    public ResponseEntity<Object> salvar(@RequestBody CarroEntity carro){
        try {
            CarroEntity salvar = service.salvar(carro);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvar);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }

          }

    @GetMapping("{id}")
    public ResponseEntity<CarroEntity> detalhesCarro(@PathVariable Long id){
        try{
            var carroEncontrado=service.buscarPorId(id);
            return ResponseEntity.ok(carroEncontrado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
