package io.github.ngraciano.locadora.controller;

import io.github.ngraciano.locadora.entity.CarroEntity;
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
        result.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.modelo").value("Honda Civic"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valorDiaria").value(150.0));


    }
}
