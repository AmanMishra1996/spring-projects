package com.example.miniproject.service.externalAPI;

import org.springframework.stereotype.Service;


@Service
public class CountryStateCityApiService {


    /*private final List<Country> countryList;
    private final Map<String,List<State>> stateListByCountryIso;
    private final Map<String,List<City>> cityListByStateIso;
    public CountryStateCityApiService() {
        this.countryList = getGetAListOfAllCountries();
        this.stateListByCountryIso = getCountryIsoAndStateMap();
        this.cityListByStateIso = null;
    }

    List<Country> getGetAListOfAllCountries(){
        String url = "https://api.countrystatecity.in/v1/countries";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-CSCAPI-KEY","");
        headers.add("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Country[]> countries = restTemplate.exchange(url, HttpMethod.GET, entity, Country[].class);
        return Arrays.asList(countries.getBody());
    }

    Map<String, List<State>> getCountryIsoAndStateMap(){
        Map<String, List<State>> result = this.countryList.stream()
                .map(country -> country.getIso2())
                .collect(Collectors.toMap(iso -> iso, iso -> getStateListByCountryIso(iso)));
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
        return Arrays.asList(states.getBody());
    }



*/
}
