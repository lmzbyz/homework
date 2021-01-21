package com.ante.homework.corona.service;

import com.ante.homework.corona.model.CountModel;
import com.ante.homework.corona.model.EntityModel;
import com.ante.homework.corona.entity.Information;
import com.ante.homework.corona.repository.InfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class InfoServiceImpl implements InfoService {

    @Autowired
    InfoRepository repository;

    CountModel countModel = new CountModel();

    @Override
    public EntityModel stringParser(String news) throws ParseException {
        EntityModel model = new EntityModel();

        String date = "\\s*\\d{2}\\.\\d{2}\\.\\d{4}\\s*";
        Pattern datePattern = Pattern.compile(date);
        Matcher matcherDate = datePattern.matcher(news);

        String dateD = "02.01.2020";
        String cityName = "";
        int vakaCount = 0;
        int taburcuCount = 0;

        if (matcherDate.find()) {
            dateD = matcherDate.group(0);
        }
        Date date1 = new SimpleDateFormat("dd.MM.yyyy").parse(dateD);
        model.setDate(date1.toLocaleString());

        String[] sentences = news.split("\\.");
        String vefat = ".*[v|V]efat.*";
        String taburcu = ".*[t|T]aburcu.*";
        String vaka = ".*[v|V]aka.*";
        String sayi = "[\\s]\\d{1,5}";
        String sehir = "(\\s)*[A-Z][a-z]*((\\sda)|(\\siçin)|($)|(\\s))+";

        Pattern sayiPattern = Pattern.compile(sayi);
        Matcher matcherSayi;
        Pattern sehirPattern = Pattern.compile(sehir);
        Matcher matcherSehir;

        String[] cities = new String[]{"İstanbul", "Ankara", "İzmir", "Adana", "Adıyaman", "Afyonkarahisar", "Ağrı", "Aksaray",
                "Amasya", "Antalya", "Ardahan", "Artvin", "Aydın", "Balıkesir", "Bartın", "Batman", "Bayburt",
                "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa", "Çanakkale", "Çankırı", "Çorum", "Denizli",
                "Diyarbakır", "Düzce", "Edirne", "Elazığ", "Erzincan", "Erzurum", "Eskişehir", "Gaziantep", "Giresun",
                "Gümüşhane", "Hakkari", "Hatay", "Iğdır", "Isparta", "Kahramanmaraş", "Karabük", "Karaman", "Kars",
                "Kastamonu", "Kayseri", "Kırıkkale", "Kırklareli", "Kırşehir", "Kilis", "Kocaeli", "Konya", "Kütahya",
                "Malatya", "Manisa", "Mardin", "Mersin", "Muğla", "Muş", "Nevşehir", "Niğde", "Ordu", "Osmaniye",
                "Rize", "Sakarya", "Samsun", "Siirt", "Sinop", "Sivas", "Şırnak", "Tekirdağ", "Tokat", "Trabzon",
                "Tunceli", "Şanlıurfa", "Uşak", "Van", "Yalova", "Yozgat", "Zonguldak"};

        List<String> cityNames = Arrays.asList(cities);

        for (String sentence : sentences) {
            sentence = sentence.replaceAll("İ", "I");
            sentence = sentence.replaceAll("Ç", "C");
            sentence = sentence.replaceAll("Ş", "S");

            if (ObjectUtils.isEmpty(cityName)) {
                matcherSehir = sehirPattern.matcher(sentence);
                if (matcherSehir.find()) {
                    cityName = matcherSehir.group(0).trim().split(" ")[0];

                    cityName = cityName.replaceAll("I", "İ");
                    cityName = cityName.replaceAll("C", "Ç");
                    cityName = cityName.replaceAll("S", "Ş");

                    if (cityNames.contains(cityName)) {
                        model.setName(cityName);
                    } else
                        cityName = "";

//                    model.setName(cityName.trim());
                }
            }
            if (sentence.matches(vefat)) {
                matcherSayi = sayiPattern.matcher(sentence);
                if (matcherSayi.find()) {
                    int vefatCount = Integer.parseInt(matcherSayi.group(0).trim());
                    model.setDeaths(vefatCount);
                }
            }
            if (sentence.matches(taburcu)) {
                matcherSayi = sayiPattern.matcher(sentence);
                if (matcherSayi.find()) {
                    taburcuCount = Integer.parseInt(matcherSayi.group(0).trim());
                    model.setRecovered(taburcuCount);
                }
            }
            if (sentence.matches(vaka)) {
                matcherSayi = sayiPattern.matcher(sentence);
                if (matcherSayi.find()) {
                    vakaCount = Integer.parseInt(matcherSayi.group(0).trim());
                    model.setConfirmed(vakaCount);

                }
            }
        }
        return model;
    }

    @Override
    public Information saveInfo(EntityModel model) {
        Information info = new Information();
        info.setName(model.getName());
        info.setRecovered(model.getRecovered());
        info.setConfirmed(model.getConfirmed());
        info.setDeaths(model.getDeaths());
        info.setDate(model.getDate().substring(0, model.getDate().length() - 8));

        List<Information> checkInfo = repository.findByName(info.getName());
        for (Information check : checkInfo) {
            if (!check.getDate().equals(info.getDate())) {
                continue;
            }
            return null;
        }
        repository.save(info);
        return info;
    }

    @Override
    public List<Integer> getAllCounts() {
        List<Information> listOfInfo = repository.findAll();
        List<Integer> countOfData = new ArrayList<>();

        int countDeath = 0;
        int countRecovered = 0;
        int countConfirmed = 0;

        return getIntegers(listOfInfo, countOfData, countDeath, countRecovered, countConfirmed);
    }

    @Override
    public List<Integer> getCounts(String cityName) {

        List<Information> listOfInfo = repository.findByName(cityName);
        List<Integer> countOfData = new ArrayList<>();

        int countDeath = 0;
        int countRecovered = 0;
        int countConfirmed = 0;

        return getIntegers(listOfInfo, countOfData, countDeath, countRecovered, countConfirmed);
    }

    @Override
    public CountModel countModel() {
        return countModel;
    }

    @Override
    public List<String> getCities() {
        List<String> cities = new ArrayList<>();
        for (Information info : repository.findAll()) {
            if (!cities.contains(info.getName())) {
                cities.add(info.getName());
            }
        }
        return cities;
    }

    @Override
    public List<String> getDates() {
        List<String> dates = new ArrayList<>();

        for (Information info : repository.findAll()) {
            if (!dates.contains(info.getDate())) {
                dates.add(info.getDate());
            }
        }
        return dates;
    }

    @Override
    public List<Integer> getCountOfDateCity(String values) {
        String[] vals = values.split(",");
        String cityName = vals[0];
        String date = vals[1];

        List<Information> listOfInfo = repository.findByName(cityName);
        List<Information> cityAndDate = new ArrayList<>();
        List<Integer> countOfData = new ArrayList<>();

        int countDeath = 0;
        int countRecovered = 0;
        int countConfirmed = 0;

        for (Information city : listOfInfo) {
            if (city.getDate().equals(date)) {
                cityAndDate.add(city);
            }
        }

        return getIntegers(cityAndDate, countOfData, countDeath, countRecovered, countConfirmed);
    }

    private List<Integer> getIntegers(List<Information> cityAndDate, List<Integer> countOfData, int countDeath, int countRecovered, int countConfirmed) {
        for (Information info : cityAndDate) {
            if (info.getDeaths() != null)
                countDeath += info.getDeaths();
            if (info.getConfirmed() != null)
                countConfirmed += info.getConfirmed();
            if (info.getRecovered() != null)
                countRecovered += info.getRecovered();
        }

        countOfData.add(0, countDeath);
        countOfData.add(1, countRecovered);
        countOfData.add(2, countConfirmed);

        countModel.countList = countOfData;
        return countOfData;
    }

}
