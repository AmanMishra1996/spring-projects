package com.example.miniproject.service.externalAPI;

import com.example.miniproject.model.countrystatecityAPI.Country;
import com.example.miniproject.model.countrystatecityAPI.State;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Lazy
@Service
public class CountryStateCityApiService {


    private final List<Country> countryList;
    private final Map<String,List<State>> stateListByCountryIso;

    //private final Map<String,List<City>> cityListByStateIso;
    public CountryStateCityApiService() {
        this.countryList = getGetAListOfAllCountries();
        System.out.println(this.countryList);
        this.stateListByCountryIso = getCountryIsoAndStateMap();

        System.out.println(this.stateListByCountryIso);
      //  this.cityListByStateIso = null;
    }



    List<Country> getGetAListOfAllCountries(){
        String url = "https://api.countrystatecity.in/v1/countries";
        System.out.println(url);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-CSCAPI-KEY","");
        headers.add("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Country[]> countries = restTemplate.exchange(url, HttpMethod.GET, entity, Country[].class);
        return Arrays.asList(Objects.requireNonNull(countries.getBody()));
    }

    Map<String, List<State>> getCountryIsoAndStateMap(){
        Map<String, List<State>> result = this.countryList.parallelStream()
                .map(Country::getIso2)
                .collect(Collectors.toMap(iso -> iso, this::getStateListByCountryIso));
        System.out.println(result);
        return result;
    }

    List<State> getStateListByCountryIso(String countryIso){
        String url = "https://api.countrystatecity.in/v1/countries/"+ countryIso+"/states";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-CSCAPI-KEY","");
        headers.add("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<State[]> states = restTemplate.exchange(url, HttpMethod.GET, entity, State[].class);
        return Arrays.asList(Objects.requireNonNull(states.getBody()));
    }




}
