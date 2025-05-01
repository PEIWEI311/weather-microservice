package com.example.details.service;

import com.example.details.config.EndpointConfig;
import com.example.details.pojo.City;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeatherServiceImpl implements WeatherService{

    private final RestTemplate restTemplate;

    public WeatherServiceImpl(@Qualifier("detailsRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @Retryable(include = IllegalAccessError.class)
    public List<Integer> findCityIdByName(String city) {
        
        Map<String, Integer> cityMap = new HashMap<>();
        cityMap.put("london", 44418);
        cityMap.put("new york", 2459115);
        cityMap.put("paris", 615702);
        cityMap.put("beijing", 2151330);
        cityMap.put("tokyo", 1118370);
        
        
        String cityLower = city.toLowerCase();
        List<Integer> result = new ArrayList<>();
        
        
        if (cityMap.containsKey(cityLower)) {
            result.add(cityMap.get(cityLower));
        } else {
            
            for (Map.Entry<String, Integer> entry : cityMap.entrySet()) {
                if (entry.getKey().contains(cityLower) || cityLower.contains(entry.getKey())) {
                    result.add(entry.getValue());
                }
            }
        }
        
        return result;
    }

    @Override
    //change findcitynamebyid => find weather details by id
    public Map<String, Map> findCityNameById(int id) {
        Map<String, Map> ans = restTemplate.getForObject(EndpointConfig.queryWeatherById + id, HashMap.class);
        return ans;
    }
}
