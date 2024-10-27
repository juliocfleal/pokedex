package com.pokemon.pokemon.dto;

import com.pokemon.pokemon.Entities.Pokemon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagedAllPokemons {
    private List<Pokemon> pokemons;
    private PagedDetails pagedDetails;
}
