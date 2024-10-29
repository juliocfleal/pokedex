package com.pokemon.pokemon.clients;

import com.pokemon.pokemon.Entities.Pokemon;
import com.pokemon.pokemon.dto.PokemonDetails;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.boot.ApplicationRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PokeApiClient {

    @Autowired
    private WebClient webClient;

    private final List<Pokemon> allPokemons = new ArrayList<>();

    @PostConstruct
    public void getAllPokemons() {
        try {
            Map<String, Object> allPokemons = getAllPokemonsFromAPI();

            if (allPokemons != null && allPokemons.containsKey("results")) {
                List<Map<String, String>> results = (List<Map<String, String>>) allPokemons.get("results");
                results.forEach(this::createEachPokemon);
            } else {
                System.err.println("No Pokémon found.");
            }
            System.err.println("Cache all pokemons from API is complete.");
        } catch (Exception e) {
            System.err.println("Error when searching for Pokémon:" + e.getMessage());
        }
    }

    private void createEachPokemon(Map<String, String> pokemon) {
        System.out.println("Creating each pokemon: " + pokemon);
        if (pokemon.containsKey("url") && pokemon.containsKey("name")) {
            String url = pokemon.get("url");
            String name = pokemon.get("name");
            Pokemon newPokemon = new Pokemon();
            newPokemon.setName(name);
            newPokemon.setUrl(url);
            Map<String, Object> pokemonDetails = consultPokeApi(url);

            if (pokemonDetails != null) {
                newPokemon.setId(getPokemonId(url));
                newPokemon.setType(getPokemonTypes(pokemonDetails));
                newPokemon.setHabitat(getPokemonHabitats(pokemonDetails));
            } else {
                System.err.println("No response received for Pokémon in URL: " + url);
            }

            if (newPokemon != null) {
                addToAllPokemons(newPokemon);
            }
        } else {
            System.err.println("Objeto Pokémon não possui uma URL.");
        }
    }

    private int getPokemonId(String url) {
        int number = 0;
        Pattern pattern = Pattern.compile("/(\\d+)/$");
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            number = Integer.parseInt(matcher.group(1));
        } else {
            System.out.println("No numbers found.");
        }
        return number;
    }

    private void addToAllPokemons(Pokemon newPokemon) {
        allPokemons.add(newPokemon);
    }


    @CachePut(value = "pokemonCache", key = "#newPokemon.name")
    public void cachePokemon(Pokemon newPokemon) {

    }

    @Cacheable(value = "pokemonCache", key = "#name")
    public Pokemon getPokemonFromCache(String name) {
        return null;
    }

    @Cacheable(value = "pokemonCache")
    public List<Pokemon> getAllPokemonsCached() {
        return new ArrayList<>(allPokemons);
    }

    private String getPokemonHabitats(Map<String, Object> pokemonDetails) {
        Map<String, Object> speciesMap = (Map<String, Object>) pokemonDetails.get("species");
        if (speciesMap == null) {
            return null;
        }

        String speciesUrl = (String) speciesMap.get("url");
        if (speciesUrl == null) {
            return null;
        }
        Map<String, Object> speciesDetails = consultPokeApi(speciesUrl);

        if (speciesDetails != null && speciesDetails.containsKey("habitat")) {
            Map<String, Object> habitatMap = (Map<String, Object>) speciesDetails.get("habitat");
            String habitat = "";
            if(habitatMap != null){
                habitat = (String) habitatMap.get("name");
            }

            return habitat;
        }

        return "Unknown";
    }

    private List<String> getPokemonTypes(Map<String, Object> pokemonDetails) {
        List<String> allPokemonTypes = new ArrayList<>();

        List<Map<String, Object>> typesList = (List<Map<String, Object>>) pokemonDetails.get("types");

        if (typesList != null) {
            for (Map<String, Object> typeEntry : typesList) {
                Map<String, String> typeInfo = (Map<String, String>) typeEntry.get("type");
                if (typeInfo != null && typeInfo.containsKey("name")) {
                    allPokemonTypes.add(typeInfo.get("name"));
                }
            }
        }

        return allPokemonTypes;
    }

    private Map<String, Object> getAllPokemonsFromAPI() {
        return consultPokeApi("/pokemon?limit=100000");
    }

    public PokemonDetails getPokemonById(Integer id) {
        Map<String, Object> pokemonFullDetails =  consultPokeApi("/pokemon/" + id);
        PokemonDetails pokemonDetails = new PokemonDetails();
        pokemonDetails.setId(id);

        pokemonDetails.setName((String) pokemonFullDetails.get("name"));

        pokemonDetails.setType(getPokemonTypes(pokemonFullDetails));

        List<Map<String, Object>> abilitiesList = (List<Map<String, Object>>) pokemonFullDetails.get("abilities");
        List<String> abilities = new ArrayList<>();
        for (Map<String, Object> abilityMap : abilitiesList) {
            Map<String, Object> ability = (Map<String, Object>) abilityMap.get("ability");
            abilities.add((String) ability.get("name"));
        }
        pokemonDetails.setAbilities(abilities);

        pokemonDetails.setBaseExperience((Integer) pokemonFullDetails.get("base_experience"));

        pokemonDetails.setHeight((Integer) pokemonFullDetails.get("height"));

        Map<String, Object> sprites = (Map<String, Object>) pokemonFullDetails.get("sprites");
        String imageUrl = (String) sprites.get("front_default");
        pokemonDetails.setImageUrl(imageUrl);
        return pokemonDetails;
    }

    private Map<String, Object> consultPokeApi(String uri){
        return webClient.get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    throw new IllegalArgumentException();
                })
                .onStatus(HttpStatusCode::is5xxServerError, response ->{
                    throw new InternalException(response.toString());
                }
                )
                .bodyToMono(Map.class)
                .block();

    }
}
