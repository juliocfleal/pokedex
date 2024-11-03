package com.pokemon.pokemon.services;

import com.pokemon.pokemon.Entities.Pokemon;
import com.pokemon.pokemon.clients.PokeApiClient;
import com.pokemon.pokemon.dto.PagedAllPokemons;
import com.pokemon.pokemon.dto.PagedDetails;
import com.pokemon.pokemon.dto.PokemonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PokemonServices {

    @Autowired
    private PokeApiClient pokeApiCliente;

    public PagedAllPokemons getPokemons(String name, String habitat, String type, int page, int size) {
        List<Pokemon> cachedPokemons = pokeApiCliente.getAllPokemonsCached();

        List<Pokemon> filteredPokemons = cachedPokemons.stream()
                .filter(pokemon -> {
                    return pokemon.getName().toLowerCase().contains(name.toLowerCase());
                })
                .filter(pokemon -> {
                    if (habitat != null && !habitat.isEmpty()) {
                        return pokemon.getHabitat() != null &&
                                pokemon.getHabitat().toLowerCase().contains(habitat.toLowerCase());
                    }
                    return true;
                })
                .filter(pokemon -> {
                    if (type != null && !type.isEmpty()) {
                        return pokemon.getType() != null &&
                                pokemon.getType().stream()
                                        .anyMatch(t -> t.toLowerCase().contains(type.toLowerCase()));
                    }
                    return true;
                })
                .collect(Collectors.toList());


        PagedDetails pagedDetails = new PagedDetails(filteredPokemons.size());
        int start = Math.min(page * size, filteredPokemons.size());
        int end = Math.min(start + size, filteredPokemons.size());

        PagedAllPokemons allPokemons = new PagedAllPokemons(filteredPokemons.subList(start, end), pagedDetails);
        return allPokemons;
    }



    public PagedAllPokemons getAllPokemonsPaginated(int page, int size) {
        List<Pokemon> cachedPokemons = pokeApiCliente.getAllPokemonsCached();

        int start = Math.min(page * size, cachedPokemons.size());
        int end = Math.min(start + size, cachedPokemons.size());
        PagedDetails pagedDetails = new PagedDetails(cachedPokemons.size());
        PagedAllPokemons allPokemons = new PagedAllPokemons(cachedPokemons.subList(start, end),pagedDetails);
        return allPokemons;
    }

    public Pokemon getPokemonById(int id) {
        Pokemon pokemon = pokeApiCliente.getPokemonById(id);
        return pokemon;
    }

    public PokemonDetails getById(Integer id) {
        PokemonDetails pokemon = pokeApiCliente.getPokemonDetailsById(id);
        return pokemon;
    }
}
