package com.pokemon.pokemon.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pokemon {

    private int id;
    private String name;
    private String url;
    private String habitat;
    private List<String> type;
}
