package com.ante.homework.corona.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class EntityModel {

    public String name;
    public Integer deaths;
    public Integer recovered;
    public Integer confirmed;
    public String date;
}
