package io.github.ngraciano.locadora.controller;

import io.github.ngraciano.locadora.entity.CarroEntity;
import io.github.ngraciano.locadora.exception.EntityNotFoundException;
import io.github.ngraciano.locadora.service.CarroService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarroController.class)
public class CarroControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    CarroService service;

    @Test
    void deveSalvarCarro() throws Exception {
        CarroEntity carro=new CarroEntity(1L,"Honda Civic",150.0,2027);

        when(service.salvar(Mockito.any())).thenReturn(carro);

        String json= """
                {
                    "modelo":"Honda Civic",
                    "valorDiaria":150.0,
                    "ano":2027
                }
                """;

        ResultActions result = mvc.perform(
                MockMvcRequestBuilders.post("/carros/salvar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        );
        result.andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.modelo").value("Honda Civic"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valorDiaria").value(150.0));


    }

    @Test
    void deveObterDetalhesDoCarro() throws Exception{
        when(service.buscarPorId(Mockito.any())).thenReturn(new CarroEntity(1L,"Civic",250.0,2028));
        mvc.perform(MockMvcRequestBuilders.get("/carros/1")).andExpect(status().isOk()).
                andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.modelo").value("Civic"))
                .andExpect(jsonPath("$.valorDiaria").value(250.0))
                .andExpect(jsonPath("$.ano").value(2028));



    }


    @Test
    void deveRetornarNotFound() throws Exception{
        when(service.buscarPorId(Mockito.any())).thenThrow(EntityNotFoundException.class);
        mvc.perform(MockMvcRequestBuilders.get("/carros/1")).andExpect(status().isNotFound());

    }

    @Test
    void deveListarTodos() throws Exception{
        var listagem = List.of(
                new CarroEntity(1L,"Argo",150.0,2020),
                  new CarroEntity(2L,"Celta",50.0,2006)
        );

      when(service.listarTodos()).thenReturn(listagem);

      mvc.perform(MockMvcRequestBuilders.get("/carros")).
              andExpect(status().isOk()).
              andExpect(jsonPath("$[0].modelo").value("Argo")).
              andExpect(jsonPath("$[1].modelo").value("Celta")).
              andExpect(jsonPath("$[1].valorDiaria").value(50.0)).
              andExpect(jsonPath("$[0].valorDiaria").value(150.0));


    };

    @Test
    void deveAtualizarUmCarro() throws Exception{
        when(service.atualizar(Mockito.any(),Mockito.any())).thenReturn(new CarroEntity(1l,"Celta",100.0,2025));

        String json= """
                {
                    "modelo":"Celta",
                    "valorDiaria":100.0,
                    "ano":2025
                }
                """;

        mvc.perform(MockMvcRequestBuilders.put("/carros/1").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isNoContent());
    }

    @Test
    void deveRetornarNotFoundAoTentarAtualizarCarroInexistente() throws Exception{
        String json= """
                {
                    "modelo":"Celta",
                    "valorDiaria":100.0,
                    "ano":2025
                }
                """;
        when(service.atualizar(Mockito.any(),Mockito.any())).thenThrow(EntityNotFoundException.class);
        mvc.perform(MockMvcRequestBuilders.put("/carros/1").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNotFound());

    }

    @Test
    void deveDeletarUmCarro() throws Exception{
        Mockito.doNothing().when(service).deletar(Mockito.any());

        mvc.perform(MockMvcRequestBuilders.delete("/carros/1")).andExpect(status().isNoContent());
        }

    @Test
    void deveRetornarNotFoundAoDeletarUmCarroInexistente() throws Exception{
        Mockito.doThrow(EntityNotFoundException.class).when(service).deletar(Mockito.any());

        mvc.perform(MockMvcRequestBuilders.delete("/carros/1")).andExpect(status().isNotFound());
    }
}
