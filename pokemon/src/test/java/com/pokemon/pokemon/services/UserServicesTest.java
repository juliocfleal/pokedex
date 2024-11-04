package com.pokemon.pokemon.services;

import com.pokemon.pokemon.Entities.Pokemon;
import com.pokemon.pokemon.Entities.User;
import com.pokemon.pokemon.clients.PokeApiClient;
import com.pokemon.pokemon.componets.SecurityFilter;
import com.pokemon.pokemon.dto.AddPokemonToUserDTO;
import com.pokemon.pokemon.dto.PagedAllPokemons;
import com.pokemon.pokemon.dto.UserDetailsDTO;
import com.pokemon.pokemon.repositories.UserRepositories;
import com.pokemon.pokemon.util.PokemonTestUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class UserServicesTest {

    @MockBean
    private PokeApiClient pokeApiClient;

    @MockBean
    SecurityFilter securityFilter;

    @MockBean
    TokenService tokenService;

    @MockBean
    UserRepositories userRepositories;

    @Autowired
    private UserServices userServices;

    @Test
    public void insertPokemonToUserTest() throws Exception {

        Pokemon pokemon = createPokemon();

        User user = createUser();

        AddPokemonToUserDTO pokemonToUserDTO = new AddPokemonToUserDTO();
        pokemonToUserDTO.setPokemonId(1);

        when(pokeApiClient.getPokemonById(pokemonToUserDTO.getPokemonId())).thenReturn(pokemon);
        when(securityFilter.getAuthenticatedUser()).thenReturn(user);
        when(userRepositories.save(any())).thenReturn(user);
        when(pokeApiClient.cachePokemon(any())).thenReturn(pokemon);
        Pokemon pokemonResponse = userServices.insertPokemonToUser(pokemonToUserDTO);

        Assertions.assertEquals("TestUser", pokemonResponse.getNameTrainer());
        Assertions.assertEquals("Test", pokemonResponse.getName());
        Assertions.assertEquals(1, pokemonResponse.getId());
    }

    @Test
    public void getUserDetailsTest() throws Exception {

        Pokemon pokemon = createPokemon();

        User user = createUser();

        List<Pokemon> pokemonsList = new ArrayList();
        pokemonsList.add(pokemon);

        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setUser(user);
        userDetailsDTO.setPokemons(pokemonsList);

        when(securityFilter.getAuthenticatedUser()).thenReturn(user);

        when(pokeApiClient.getPokemonById(1)).thenReturn(pokemon);

        UserDetailsDTO UserDetailsDTOResponse = userServices.getUserDetails();

        Assertions.assertEquals("TestUser", UserDetailsDTOResponse.getUser().getName());
        Assertions.assertEquals("Test", UserDetailsDTOResponse.getPokemons().get(0).getName());

    }

    private User createUser() {
        User user = new User();
        user.setName("TestUser");
        user.setEmail("test@test.com");
        user.setId("1");
        List<Integer> list = new ArrayList<>();
        list.add(1);
        user.setPokemonsIds(list);
        return user;
    }

    private Pokemon createPokemon() {
        Pokemon pokemon = new Pokemon();
        pokemon.setId(1);
        pokemon.setName("Test");
        return pokemon;
    }

}
