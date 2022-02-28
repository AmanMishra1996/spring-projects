package com.example.miniproject.service;

import com.example.miniproject.model.User;
import com.example.miniproject.model.countrystatecityAPI.City;
import com.example.miniproject.model.countrystatecityAPI.Country;
import com.example.miniproject.model.countrystatecityAPI.State;
import com.example.miniproject.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(int initial) {
        return new ArrayList<>(userRepository.
                findAllByUserStatus("ACTIVE", PageRequest.of(initial,10)));
    }

    public Optional<User> getUsersById(long id) {
        return Optional.ofNullable(userRepository.findAllByIdAndUserStatus(id,"ACTIVE"));
    }

    public void addOrEditUser(User user, String xCscApiKeyValue)    {

        if(validate(user, xCscApiKeyValue)) {
            Optional<User> u = Optional.ofNullable(userRepository.findAllByIdAndUserStatus(user.getId(), "DISABLE"));
            if (!u.isPresent()) {
                user.setUserStatus("ACTIVE");
                userRepository.save(user);
            }
        }
    }

    public void softDelete(long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            user.get().setUserStatus("DISABLE");
            userRepository.save(user.get());
        }
    }

    public boolean validate(User user, String xCscApiKeyValue) {
        return validateCountry(user, xCscApiKeyValue);

    }

    private boolean validateCountry(User user, String xCscApiKeyValue) {
        String url = "https://api.countrystatecity.in/v1/countries";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-CSCAPI-KEY",xCscApiKeyValue);
        headers.add("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36");
        HttpEntity <String> entity = new HttpEntity<>(headers);
        ResponseEntity<Country[]> countries = restTemplate.exchange(url, HttpMethod.GET, entity, Country[].class);
        List<Country> country = Arrays.stream(Objects.requireNonNull(countries.getBody()))
                                    .filter(c -> c.getName().equalsIgnoreCase(user.getCountry()))
                .collect(Collectors.toList());
        if(country.isEmpty()){
            return false;
        }else{
            System.out.println(country.get(0));
            return validateStateByCountry(user, url + "/"+ country.get(0).getIso2()+"/states", restTemplate, entity);

        }
    }

    private boolean validateStateByCountry(User user, String url, RestTemplate restTemplate, HttpEntity<String> entity) {
        ResponseEntity<State[]> states = restTemplate.exchange(url, HttpMethod.GET, entity, State[].class);
        List<State> state = Arrays.stream(Objects.requireNonNull(states.getBody()))
                .filter(s -> s.getName().equalsIgnoreCase(user.getState()))
                .collect(Collectors.toList());
        if(state.isEmpty()){
            return false;
        }else{
            System.out.println(state.get(0));
            return validateCityByStateAndCountry(user, url + "/"+ state.get(0).getIso2()+"/cities", restTemplate, entity);
        }
    }

    private boolean validateCityByStateAndCountry(User user, String url, RestTemplate restTemplate, HttpEntity<String> entity) {
        ResponseEntity<City[]> cities = restTemplate.exchange(url, HttpMethod.GET, entity, City[].class);
        List<City> city = Arrays.stream(Objects.requireNonNull(cities.getBody()))
                .filter(c -> c.getName().equalsIgnoreCase(user.getCity()))
                .collect(Collectors.toList());

        return !city.isEmpty();
    }
}
