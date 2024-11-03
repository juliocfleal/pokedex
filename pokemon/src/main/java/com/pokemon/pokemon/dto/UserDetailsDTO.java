package com.pokemon.pokemon.dto;

import com.pokemon.pokemon.Entities.Pokemon;
import com.pokemon.pokemon.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDTO {
    private User user;
    private List<Pokemon> pokemons;
}
