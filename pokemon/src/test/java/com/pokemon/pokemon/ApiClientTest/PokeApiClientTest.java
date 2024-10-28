package com.pokemon.pokemon.ApiClientTest;

import com.pokemon.pokemon.Entities.Pokemon;
import com.pokemon.pokemon.clients.PokeApiClient;
import com.pokemon.pokemon.dto.PagedAllPokemons;
import com.pokemon.pokemon.dto.PokemonDetails;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Map;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class PokeApiClientTest {

    @Autowired
    PokeApiClient pokeApiClient;

    @Test
    public void getAllPokemonsCachedTest() {
        List<Pokemon> cachedPokemons = pokeApiClient.getAllPokemonsCached();
        System.out.println(cachedPokemons);
        Assertions.assertEquals(2, cachedPokemons.get(1).getId());
        Assertions.assertEquals("ivysaur", cachedPokemons.get(1).getName());
    }

    @Test
    public void getPokemonByIdTest(){
        PokemonDetails pokemon = pokeApiClient.getPokemonById(1);
        System.out.println(pokemon);
        Assertions.assertEquals(1, pokemon.getId());
        Assertions.assertEquals("bulbasaur", pokemon.getName());

    }
}
