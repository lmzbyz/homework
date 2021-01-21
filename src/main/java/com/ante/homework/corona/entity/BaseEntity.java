package com.ante.homework.corona.entity;

import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String id;
}
