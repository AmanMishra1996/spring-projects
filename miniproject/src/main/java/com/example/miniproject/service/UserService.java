package com.example.miniproject.service;

import com.example.miniproject.model.countrystatecityAPI.City;
import com.example.miniproject.model.countrystatecityAPI.Country;
import com.example.miniproject.model.countrystatecityAPI.State;
import com.example.miniproject.model.User;
import com.example.miniproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    UserRepository userRepository;
    // Enum might be better choice for Status possible values, similar gender validation,
    // post method might also notify at the end operation suceess or not
    // Hardcoded values can be set in properties
    // Need to understand how line 30 works
    //Hibernate: select user0_.user_id as user_id1_0_, user0_.address as address2_0_, user0_.city as city3_0_, user0_.country as country4_0_, user0_.date_of_birth as date_of_5_0_, user0_.first_name as first_na6_0_, user0_.highest_education as highest_7_0_, user0_.last_name as last_nam8_0_, user0_.sex as sex9_0_, user0_.state as state10_0_, user0_.user_status as user_st11_0_ from user user0_ where user0_.user_status=? limit ?, ?
    // seems like it is querying all records then making a Page  for each page query runs need a better understanding
    public List<User> getAllUsers(int initial) {
        return new ArrayList<>(userRepository.
                findAllByUserStatus("ACTIVE", PageRequest.of(initial,10)));
    }

    public User getUsersById(long id) {
        return userRepository.findAllByIdAndUserStatus(id,"ACTIVE");
    }

    public void addOrEditUser(User user)    {
        if(validate(user)) {
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

   /* public void update(User user, long userId) {
        userRepository.save(user);
    }*/

    public boolean validate(User user) {
        return validateCountry(user);

    }

    private boolean validateCountry(User user) {
        String url = "https://api.countrystatecity.in/v1/countries";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-CSCAPI-KEY","");
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
