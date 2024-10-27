package com.pokemon.pokemon.controllers;

import com.pokemon.pokemon.Entities.Pokemon;
import com.pokemon.pokemon.dto.PagedAllPokemons;
import com.pokemon.pokemon.dto.PagedDetails;
import com.pokemon.pokemon.dto.PokemonDetails;
import com.pokemon.pokemon.services.PokemonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/pokemon")
public class PokemonController {

    private final PokemonServices pokemonService;

    @Autowired
    public PokemonController(PokemonServices pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<PagedAllPokemons> getPokemons(@RequestParam(defaultValue = "") String name,
                              @RequestParam(defaultValue = "") String habitat,
                              @RequestParam(defaultValue = "") String type,
                              @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        PagedAllPokemons pokemons = pokemonService.getPokemons(name, habitat, type, page, size);
        return ResponseEntity.ok().body(pokemons);
    }

    @GetMapping
    public ResponseEntity<PagedAllPokemons> getPokemons(
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        PagedAllPokemons pokemons = pokemonService.getAllPokemonsPaginated(page, size);
        return ResponseEntity.ok().body(pokemons);
    }

    @GetMapping(value = "/getbyid/{id}")
    public ResponseEntity<PokemonDetails> getPokemonById(@PathVariable("id") Integer id) {
        PokemonDetails pokemon = pokemonService.getById(id);
        return ResponseEntity.ok().body(pokemon);
    }
}