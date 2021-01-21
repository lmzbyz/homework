package com.ante.homework.corona.service;

import com.ante.homework.corona.model.CountModel;
import com.ante.homework.corona.model.EntityModel;
import com.ante.homework.corona.entity.Information;

import java.text.ParseException;

import java.util.List;

public interface InfoService {
    EntityModel stringParser(String news) throws ParseException;

    Information saveInfo(EntityModel model);

    List<Integer> getCounts(String cityName);

    List<Integer> getCountOfDateCity(String values);

    List<Integer> getAllCounts();

    CountModel countModel();

    List<String> getCities();

    List<String> getDates();

}
