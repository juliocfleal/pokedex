package com.pokemon.pokemon.controllers.Exceptions;

public class PokemonAlreadyHaveATrainerException extends RuntimeException{

    public PokemonAlreadyHaveATrainerException(String msg) {
        super(msg);
    }
}
