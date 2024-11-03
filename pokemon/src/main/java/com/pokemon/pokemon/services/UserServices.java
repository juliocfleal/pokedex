package com.pokemon.pokemon.services;

import com.pokemon.pokemon.Entities.Pokemon;
import com.pokemon.pokemon.Entities.User;
import com.pokemon.pokemon.clients.PokeApiClient;
import com.pokemon.pokemon.componets.SecurityFilter;
import com.pokemon.pokemon.dto.AddPokemonToUserDTO;
import com.pokemon.pokemon.repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;

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

    public Pokemon insertPokemonToUser(AddPokemonToUserDTO pokemonToUserDTO) throws Exception {

            Pokemon pokemon = pokeApiCliente.getPokemonById(pokemonToUserDTO.getPokemonId());
            if (pokemon == null) {
                throw new IllegalArgumentException("The pokemon id does not exist.");
            }
            if (pokemon.getNameTrainer() != null) {
                throw new IllegalArgumentException("Pokemon already have a trainer.");
            }
            System.out.println(pokemon);

            User user = securityFilter.getAuthenticatedUser();
            if (user == null) {
                throw new IllegalArgumentException("User Not Found");
            }
            System.out.println(user.getPokemonsIds());
            if(user.getPokemonsIds().size() > 3){
                throw new IllegalArgumentException("Too many pokemon ids.");
            }
            user.addPokemon(pokemon);
            userRepositories.save(user);
            pokemon.setNameTrainer(user.getName());
            System.out.println(user.toString());
            pokeApiCliente.cachePokemon(pokemon);
            return pokemon;


    }


}
