package com.pokemon.pokemon.services;

import com.pokemon.pokemon.Entities.Pokemon;
import com.pokemon.pokemon.Entities.User;
import com.pokemon.pokemon.clients.PokeApiClient;
import com.pokemon.pokemon.componets.SecurityFilter;
import com.pokemon.pokemon.controllers.Exceptions.PokemonAlreadyHaveATrainerException;
import com.pokemon.pokemon.dto.AddPokemonToUserDTO;
import com.pokemon.pokemon.dto.UserDetailsDTO;
import com.pokemon.pokemon.repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServices {

    @Autowired
    private PokeApiClient pokeApiCliente;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepositories userRepositories;

    @Autowired
    SecurityFilter securityFilter;

    @Transactional
    public Pokemon insertPokemonToUser(AddPokemonToUserDTO pokemonToUserDTO) throws Exception {

            Pokemon pokemon = pokeApiCliente.getPokemonById(pokemonToUserDTO.getPokemonId());
            if (pokemon == null) {
                throw new IllegalArgumentException("The pokemon id does not exist.");
            }
            if (pokemon.getNameTrainer() != null) {
                throw new PokemonAlreadyHaveATrainerException("Pokemon already have a trainer. The trainer is: " + pokemon.getNameTrainer());
            }

            User user = securityFilter.getAuthenticatedUser();
            if (user == null) {
                throw new IllegalArgumentException("User Not Found");
            }
            if(user.getPokemonsIds().size() > 3){
                throw new IllegalArgumentException("Too many pokemon ids.");
            }
            user.addPokemon(pokemon);
            userRepositories.save(user);
            pokemon.setNameTrainer(user.getName());
            pokeApiCliente.cachePokemon(pokemon);
            return pokemon;
    }


    public void deletePokemonFromUser(int id) {
        Pokemon pokemon = pokeApiCliente.getPokemonById(id);
        if (pokemon == null) {
            throw new IllegalArgumentException("The pokemon id does not exist.");
        }
        if (pokemon.getNameTrainer() == null) {
            throw new IllegalArgumentException("Pokemon does not have a trainer.");
        }
        User user = securityFilter.getAuthenticatedUser();
        if (user == null) {
            throw new IllegalArgumentException("User Not Found");
        }
        user.removePokemon(id);
        pokemon.setNameTrainer(null);
        userRepositories.save(user);
        pokeApiCliente.cachePokemon(pokemon);
    }

    public void deleteUser() {
        User user = securityFilter.getAuthenticatedUser();
        if (user == null) {
            throw new IllegalArgumentException("User Not Found");
        }
        user.getPokemonsIds().forEach(pokemonId -> {
            Pokemon pokemonToDelete = pokeApiCliente.getPokemonById(pokemonId);
            pokemonToDelete.setNameTrainer(null);
            pokeApiCliente.cachePokemon(pokemonToDelete);
        });
        userRepositories.delete(user);
    }

    public UserDetailsDTO getUserDetails() {
        User user = securityFilter.getAuthenticatedUser();
        if (user == null) {
            throw new IllegalArgumentException("User Not Found");
        }
        user.setPassword(null);
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setUser(user);
        List<Pokemon> pokemons = new ArrayList<Pokemon>();
        user.getPokemonsIds().forEach(
                pokemonId -> {
                    Pokemon pokemon = pokeApiCliente.getPokemonById(pokemonId);
                    pokemons.add(pokemon);
                }
        );
        userDetailsDTO.setPokemons(pokemons);
        return userDetailsDTO;
    }
}
