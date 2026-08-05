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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


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
}
