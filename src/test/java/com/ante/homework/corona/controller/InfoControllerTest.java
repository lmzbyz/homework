package com.ante.homework.corona.controller;

import com.ante.homework.corona.controller.InfoController;
import com.ante.homework.corona.entity.Information;
import com.ante.homework.corona.model.EntityModel;
import com.ante.homework.corona.model.PostModel;
import com.ante.homework.corona.repository.InfoRepository;
import com.ante.homework.corona.service.InfoService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ExtendWith(MockitoExtension.class)
public class InfoControllerTest {

    @InjectMocks
    InfoController infoController;

    @Mock
    InfoRepository infoRepository;

    @Mock
    InfoService infoService;

    @Test
    public void testFindAll() {
        // given
        Information info1 = new Information("Ankara", 1, 2, 3, "01.03.2020");
        Information info2 = new Information("İstanbul", 2, 5, 6, "02.03.2020");
        List<Information> infos = new ArrayList<>();
        infos = Arrays.asList(info1, info2);

        when(infoRepository.findAll()).thenReturn(infos);

        // when
        List<Information> result = infoController.listAll();

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo(info1.getName());
        assertThat(result.get(1).getName()).isEqualTo(info2.getName());
    }

    @Test
    public void testPostNews() throws URISyntaxException, ParseException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        PostModel model = new PostModel();
        model.setText("Ankara da 02.05.2020 korona virüs tablosu. 3 vefat. 2 vaka. Taburcu sayısı 7.");
        ResponseEntity<Information> responseEntity = infoController.postNews(model);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

        model.setText("Korona 02.05.2020 tablosu İstanbul . 3 vefat. 7 vaka. Taburcu sayısı 7.");
        ResponseEntity<Information> responseEntity2 = infoController.postNews(model);
        assertThat(responseEntity2.getStatusCodeValue()).isEqualTo(200);

        model.setText("");
        ResponseEntity<Information> responseEntity3 = infoController.postNews(model);
        assertThat(responseEntity3.getStatusCodeValue()).isEqualTo(400);

        model.setText(" ");
        ResponseEntity<Information> responseEntity4 = infoController.postNews(model);
        assertThat(responseEntity4.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    public void testFindCountOfCity() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        String cityName = "Ankara";

        ResponseEntity responseEntity = infoController.findCountOfCity(cityName);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }


    @Test
    public void testFindCountOfCityDate() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        String str = "Ankara,01.01.2020";

        ResponseEntity responseEntity = infoController.findCountOfCityDate(str);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void testGetCities() {
        // given
        String city1 = "Ankara";
        String city2 = "İstanbul";
        List<String> cities = new ArrayList<>();
        cities = Arrays.asList(city1, city2);

        when(infoService.getCities()).thenReturn(cities);

        // when
        List<String> result = infoController.getCities();

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).isEqualTo(city1);
        assertThat(result.get(1)).isEqualTo(city2);
    }

    @Test
    public void testGetDates() {
        // given
        String date1 = "01.02.2020";
        String date2 = "03.05.2020";
        List<String> dates = new ArrayList<>();
        dates = Arrays.asList(date1, date2);

        when(infoService.getDates()).thenReturn(dates);

        // when
        List<String> result = infoController.getDates();

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).isEqualTo(date1);
        assertThat(result.get(1)).isEqualTo(date2);
    }

}
