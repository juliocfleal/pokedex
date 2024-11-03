package com.pokemon.pokemon.ApiClientTest;

import com.pokemon.pokemon.Entities.Pokemon;
import com.pokemon.pokemon.clients.PokeApiClient;
import com.pokemon.pokemon.dto.PokemonDetails;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
    public void getPokemonDetailsByIdTest(){
        PokemonDetails pokemon = pokeApiClient.getPokemonDetailsById(1);
        System.out.println(pokemon);
        Assertions.assertEquals(1, pokemon.getId());
        Assertions.assertEquals("bulbasaur", pokemon.getName());

    }
}
