package com.ante.homework.corona.controller;

import com.ante.homework.corona.model.PostModel;
import com.ante.homework.corona.entity.Information;
import com.ante.homework.corona.repository.InfoRepository;
import com.ante.homework.corona.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/info")
@CrossOrigin(origins = "http://localhost:3000")
public class InfoController {

    @Autowired
    private InfoRepository repository;

    @Autowired
    private InfoService service;


    @GetMapping("/find-all")
    public List<Information> listAll() {
        return repository.findAll();
    }

    @PostMapping(value = "/post-news",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Information> postNews(@RequestBody PostModel news) throws URISyntaxException, ParseException {
        Information result = service.saveInfo(service.stringParser(news.text));
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/data-of-city")
    public void findCountOfCity(@RequestBody String cityName) {
        service.getCounts(cityName);
    }

    @GetMapping("/get-counts")
    public List<Integer> getCounts() {
        return service.countModel().getCountList();
    }

    @PostMapping("/data-of-city-date")
    public void findCountOfCityDate(@RequestBody String values) {
        service.getCountOfDateCity(values);
    }

    @GetMapping("/get-all-counts")
    public List<Integer> getAllCounts() {
        return service.getAllCounts();
    }

    @GetMapping("/get-date-count")
    public List<Integer> getDateCounts() {
        return service.countModel().getCountList();
    }

    @GetMapping("/get-cities")
    public List<String> getCities() {
        return service.getCities();
    }

    @GetMapping("/get-dates")
    public List<String> getDates() {
        return service.getDates();
    }

}
