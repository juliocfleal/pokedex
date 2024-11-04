package com.pokemon.pokemon.controllers;

import com.pokemon.pokemon.Entities.Pokemon;
import com.pokemon.pokemon.Entities.User;
import com.pokemon.pokemon.clients.PokeApiClient;
import com.pokemon.pokemon.dto.AddPokemonToUserDTO;
import com.pokemon.pokemon.dto.PokemonDetails;
import com.pokemon.pokemon.dto.UserDetailsDTO;
import com.pokemon.pokemon.services.PokemonServices;
import com.pokemon.pokemon.services.UserServices;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private PokemonServices pokemonServices;

    @MockBean
    private PokeApiClient pokeApiClient;

    @MockBean
    private UserServices userService;

    @Test
    public void getUserDetailsTest() throws Exception {
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        User user = new User();
        user.setName("Test");
        user.setEmail("test@test.com");
        userDetailsDTO.setUser(user);
        List<Pokemon> pokemonsList = new ArrayList<>();
        Pokemon pokemon = createPokemon();

        pokemonsList.add(pokemon);
        userDetailsDTO.setPokemons(pokemonsList);

        when(userService.getUserDetails()).thenReturn(userDetailsDTO);
        UserDetailsDTO userDetailsResponse = userService.getUserDetails();
        Assertions.assertEquals(1, userDetailsResponse.getPokemons().get(0).getId());
        Assertions.assertEquals("bulbasaur", userDetailsResponse.getPokemons().get(0).getName());
        Assertions.assertEquals("Test", userDetailsResponse.getUser().getName());
        Assertions.assertEquals("test@test.com", userDetailsResponse.getUser().getEmail());
    }

    @Test
    public void insertPokemonToUserTest() throws Exception {
        AddPokemonToUserDTO pokemonToUserDTO = new AddPokemonToUserDTO();
        pokemonToUserDTO.setPokemonId(1);
        Pokemon pokemon = createPokemon();

        when(userService.insertPokemonToUser(pokemonToUserDTO)).thenReturn(pokemon);
        Pokemon pokemonResponse = userService.insertPokemonToUser(pokemonToUserDTO);
        Assertions.assertEquals(1, pokemonResponse.getId());
        Assertions.assertEquals("bulbasaur", pokemonResponse.getName());
    }

    private Pokemon createPokemon() {
        Pokemon pokemon = new Pokemon();
        pokemon.setName("bulbasaur");
        pokemon.setId(1);
        return pokemon;
    }

}
