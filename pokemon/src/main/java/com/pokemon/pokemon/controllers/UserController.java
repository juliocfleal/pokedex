package com.pokemon.pokemon.controllers;

import com.pokemon.pokemon.Entities.Pokemon;
import com.pokemon.pokemon.dto.AddPokemonToUserDTO;
import com.pokemon.pokemon.dto.DefaultError;
import com.pokemon.pokemon.services.PokemonServices;
import com.pokemon.pokemon.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/user")
public class UserController {

    private final PokemonServices pokemonService;

    @Autowired
    public UserController(PokemonServices pokemonService) {
        this.pokemonService = pokemonService;
    }

    @Autowired
    private UserServices userService;


    @PostMapping(value = "/insertPokemonToUser")
    public ResponseEntity<Pokemon> insertPokemonToUser(@RequestBody AddPokemonToUserDTO pokemonToUserDTO) throws Exception {
        return ResponseEntity.ok().body(userService.insertPokemonToUser(pokemonToUserDTO));
    }
}