package com.pokemon.pokemon.controllers;

import com.pokemon.pokemon.clients.PokeApiClient;
import com.pokemon.pokemon.dto.PagedAllPokemons;
import com.pokemon.pokemon.dto.PokemonDetails;
import com.pokemon.pokemon.services.PokemonServices;
import com.pokemon.pokemon.util.PokemonTestUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class PokemonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PokemonServices pokemonServices;

    @MockBean
    private PokeApiClient pokeApiClient;

    @Test
    public void getPokemonByIdTest() throws Exception {
        PokemonDetails mockPokemon = PokemonTestUtil.createAPokemonDetails();
        when(pokemonServices.getById(1)).thenReturn(mockPokemon);

        mockMvc.perform(get("/pokemon/getbyid/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("bulbasaur"))
                .andExpect(jsonPath("$.id").value(1));
    }


    @Test
    public void getPokemons() throws Exception {

        PagedAllPokemons pagedAllPokemons = PokemonTestUtil.listAllPokemonsForTest();

        when(pokemonServices.getPokemons("saur","","",0,10)).thenReturn(pagedAllPokemons);

        mockMvc.perform(get("/pokemon/filter?name=saur")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pokemons[0].name").value("bulbasaur"))
                .andExpect(jsonPath("$.pokemons[0].id").value(1));
    }

    @Test
    public void getAllPokemons() throws Exception {

        PagedAllPokemons pagedAllPokemons = PokemonTestUtil.listAllPokemonsForTest();

        when(pokemonServices.getAllPokemonsPaginated(0,10)).thenReturn(pagedAllPokemons);

        mockMvc.perform(get("/pokemon")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pokemons[0].name").value("bulbasaur"))
                .andExpect(jsonPath("$.pokemons[0].id").value(1))
                .andExpect(jsonPath("$.pokemons[1].name").value("ivysaur"))
                .andExpect(jsonPath("$.pokemons[1].id").value(2));
    }


}
