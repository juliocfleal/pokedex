package com.pokemon.pokemon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PokemonDetails {
    private int id;
    private String name;
    private List<String> type;
    private List<String> abilities;
    private Integer baseExperience;
    private Integer height;
    private String imageUrl;
    private String trainerName;
}
