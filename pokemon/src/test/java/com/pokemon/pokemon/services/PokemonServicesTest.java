package com.pokemon.pokemon.services;

import com.pokemon.pokemon.Entities.Pokemon;
import com.pokemon.pokemon.clients.PokeApiClient;
import com.pokemon.pokemon.dto.PagedAllPokemons;
import com.pokemon.pokemon.dto.PokemonDetails;
import com.pokemon.pokemon.util.PokemonTestUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Assertions;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class PokemonServicesTest {


    @Autowired
    private PokemonServices pokemonServices;

    @MockBean
    private PokeApiClient pokeApiClient;

    @Test
    public void getPokemonByIdTest() throws Exception {
        PokemonDetails mockPokemon = new PokemonDetails();
        mockPokemon.setName("bulbasaur");
        mockPokemon.setId(1);
        when(pokeApiClient.getPokemonDetailsById(1)).thenReturn(mockPokemon);
        PokemonDetails pokemonResponse = pokemonServices.getById(1);
        Assertions.assertEquals(1, pokemonResponse.getId());
    }

    @Test
    public void getAllPokemonsPaginatedTest() throws Exception {
        List<Pokemon> listPokemon = PokemonTestUtil.crateListPokemon();

        when(pokeApiClient.getAllPokemonsCached()).thenReturn(listPokemon);

        PagedAllPokemons pagedAllPokemons = pokemonServices.getAllPokemonsPaginated(0,10);
        Assertions.assertEquals(2, pagedAllPokemons.getPokemons().size());
        Assertions.assertEquals(1, pagedAllPokemons.getPokemons().get(0).getId());
        Assertions.assertEquals("bulbasaur", pagedAllPokemons.getPokemons().get(0).getName());
        Assertions.assertEquals(2, pagedAllPokemons.getPokemons().get(1).getId());
        Assertions.assertEquals("ivysaur", pagedAllPokemons.getPokemons().get(1).getName());

        Assertions.assertEquals(2, pagedAllPokemons.getPagedDetails().getTotalPokemons());
    }

    @Test
    public void getPokemonsTest() throws Exception {
        List<Pokemon> listPokemon = PokemonTestUtil.crateListPokemon();

        when(pokeApiClient.getAllPokemonsCached()).thenReturn(listPokemon);

        PagedAllPokemons pagedAllPokemons = pokemonServices.getPokemons("saur","","",0,10);
        Assertions.assertEquals(2, pagedAllPokemons.getPokemons().size());
        Assertions.assertEquals(1, pagedAllPokemons.getPokemons().get(0).getId());
        Assertions.assertEquals("bulbasaur", pagedAllPokemons.getPokemons().get(0).getName());
        Assertions.assertEquals(2, pagedAllPokemons.getPokemons().get(1).getId());
        Assertions.assertEquals("ivysaur", pagedAllPokemons.getPokemons().get(1).getName());

        Assertions.assertEquals(2, pagedAllPokemons.getPagedDetails().getTotalPokemons());
    }
}
