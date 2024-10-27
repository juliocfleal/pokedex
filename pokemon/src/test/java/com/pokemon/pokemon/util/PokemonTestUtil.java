package com.pokemon.pokemon.util;

import com.pokemon.pokemon.Entities.Pokemon;
import com.pokemon.pokemon.dto.PagedAllPokemons;
import com.pokemon.pokemon.dto.PagedDetails;
import com.pokemon.pokemon.dto.PokemonDetails;

import java.util.ArrayList;
import java.util.List;

public class PokemonTestUtil {
    public static PagedAllPokemons listAllPokemonsForTest(){
        Pokemon mockPokemon1 = new Pokemon();
        mockPokemon1.setName("bulbasaur");
        mockPokemon1.setId(1);
        Pokemon mockPokemon2 = new Pokemon();
        mockPokemon2.setName("ivysaur");
        mockPokemon2.setId(2);

        List<Pokemon> pokemons = new ArrayList<>();
        pokemons.add(mockPokemon1);
        pokemons.add(mockPokemon2);

        PagedDetails pagedDetails =new PagedDetails(2);

        return new PagedAllPokemons(pokemons, pagedDetails);
    }

    public static PokemonDetails createAPokemonDetails(){
        PokemonDetails mockPokemon = new PokemonDetails();
        mockPokemon.setName("bulbasaur");
        mockPokemon.setId(1);
        return mockPokemon;
    }

    public static List<Pokemon> crateListPokemon(){
        Pokemon mockPokemon1 = new Pokemon();
        mockPokemon1.setName("bulbasaur");
        mockPokemon1.setId(1);
        Pokemon mockPokemon2 = new Pokemon();
        mockPokemon2.setName("ivysaur");
        mockPokemon2.setId(2);
        List<Pokemon> pokemons = new ArrayList<>();
        pokemons.add(mockPokemon1);
        pokemons.add(mockPokemon2);
        return pokemons;
    }
}
