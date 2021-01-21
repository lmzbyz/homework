package com.ante.homework.corona.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "information")
public class Information extends BaseEntity {

    public String name;
    public Integer deaths;
    public Integer recovered;
    public Integer confirmed;
    public String date;
}
