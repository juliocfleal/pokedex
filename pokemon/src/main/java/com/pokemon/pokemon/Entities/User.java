package com.pokemon.pokemon.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;

    @Column(unique = true)
    private String email;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Integer> pokemonsIds;

    public boolean addPokemon(Pokemon pokemon) {
        if (pokemonsIds.size() >= 3) {
            throw new IllegalArgumentException("This user already has 3 pokemons.");
        }
        pokemonsIds.add(pokemon.getId());
        return true;
    }

    public boolean removePokemon(int id) {
        if (!pokemonsIds.isEmpty()) {
            pokemonsIds.remove(Integer.valueOf(id));
        }else{
            return false;
        }
        return true;
    }

}
