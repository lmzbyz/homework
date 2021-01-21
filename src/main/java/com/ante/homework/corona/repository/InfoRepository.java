package com.ante.homework.corona.repository;

import com.ante.homework.corona.entity.Information;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InfoRepository extends MongoRepository<Information, Integer> {
    List<Information> findByName(String cityName);

    List<Information> findByDate(String date);
}
