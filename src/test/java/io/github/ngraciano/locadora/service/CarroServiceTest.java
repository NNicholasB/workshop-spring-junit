package io.github.ngraciano.locadora.service;

import io.github.ngraciano.locadora.entity.CarroEntity;
import io.github.ngraciano.locadora.exception.EntityNotFoundException;
import io.github.ngraciano.locadora.repository.CarroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class CarroServiceTest {

    @InjectMocks
    CarroService service;

    @Mock
    CarroRepository repository;

  @Test
    void deveSalvarUmCarro(){
      CarroEntity carro = new CarroEntity("Sedan",10.0,2027);

      CarroEntity carroParaRetornar=new CarroEntity("Sedan",10.0,2027);

      carroParaRetornar.setId(1L);
      Mockito.when(repository.save(Mockito.any())).thenReturn(carroParaRetornar);

     var carroSalvo=service.salvar(carro);

     assertNotNull(carroSalvo);
     assertEquals("Sedan",carroSalvo.getModelo());
     Mockito.verify(repository).save(Mockito.any());
  }


  @Test
    void deveDarErroAoTentarSalvarCarroComDiariaNegativa(){
      CarroEntity carro=new CarroEntity("Sedan",0,2027);
      var erro= catchThrowable(()-> service.salvar(carro));
      assertThat(erro).isInstanceOf(IllegalArgumentException.class);
      Mockito.verify(repository,Mockito.never()).save(Mockito.any());
  }

  @Test
    void deveAtualizarUmCarro(){

      var carroExiste=new CarroEntity("Gol",80.0,2026);
      Mockito.when(repository.findById(1L)).thenReturn(Optional.of(carroExiste));

      var carroAtualizado=new CarroEntity("Gol",80.0,2026);
      carroAtualizado.setId(1L);
      Mockito.when(repository.save(Mockito.any())).thenReturn(carroAtualizado);
      Long id=1L;
      var carro= new CarroEntity("Sedan",0,2027);
      var result=service.atualizar(id,carro);

      assertEquals(result.getModelo(),"Gol");
      Mockito.verify(repository,Mockito.times(1)).save(Mockito.any());
  }

  @Test
    void deveDarErroAoTentarAtualizarCarroInexistente(){
      Long id=1L;
      var carro= new CarroEntity("Sedan",0,2027);

      Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());

      var erro=catchThrowable(()->service.atualizar(id,carro));

      assertThat(erro).isInstanceOf(EntityNotFoundException.class);

      Mockito.verify(repository,Mockito.never()).save(Mockito.any());

  }

    @Test
    void deveDarErroAoTentarDeletarCarroInexistente(){
        Long id=1L;
        var carro= new CarroEntity("Sedan",0,2027);

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());

        var erro=catchThrowable(()->service.deletar(id));

        assertThat(erro).isInstanceOf(EntityNotFoundException.class);

        Mockito.verify(repository,Mockito.never()).deleteById(Mockito.any());

    }

    @Test
    void deveDeletarUmCarro(){
        Long id=1L;
        var carro= new CarroEntity("Sedan",10,2027);

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(carro));
        service.deletar(id);

        Mockito.verify(repository,Mockito.times(1)).deleteById(carro.getId());

    }

    @Test
    void deveBuscarCarroPorId(){
        Long id=1L;
        var carro= new CarroEntity("Sedan",10,2027);
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(carro));

        var carroEncontrado=service.buscarPorId(id);

        assertThat(carroEncontrado.getModelo()).isEqualTo("Sedan");
        assertThat(carroEncontrado.getValorDiaria()).isEqualTo(10);

    }

    @Test
    void deveListartodos(){

        var carro1= new CarroEntity(1L,"Sedan",10,2027);

        var carro2= new CarroEntity(2L,"Hatch",10,2027);

        var lista= List.of(carro1,carro2);
        Mockito.when(repository.findAll()).thenReturn(lista);

        List<CarroEntity> resultado = service.listarTodos();

        assertThat(resultado).hasSize(2);
        Mockito.verify(repository,Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(repository);
    }
}