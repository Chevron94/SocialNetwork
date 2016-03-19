package network.console;

import network.dao.ContinentDao;
import network.dao.DialogDao;
import network.dao.implementation.*;
import network.entity.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.io.File;
import java.sql.Date;
import java.util.*;

public class Launcher {
    private static Scanner inputReader = new Scanner(System.in);
    private static List<User> users = new ArrayList<User>();
    private static List<Continent> continents = new ArrayList<Continent>();
    private static List<Country> countries = new ArrayList<Country>();
    private static List<Gender> genders = new ArrayList<Gender>();
    private static List<City> cities = new ArrayList<City>();

    private static GenderDaoImplementation genderDao = new GenderDaoImplementation();
    private static UserDaoImplementation userDao = new UserDaoImplementation();
    private static ContinentDaoImplementation continentDao = new ContinentDaoImplementation();
    private static CountryDaoImplementation countryDao = new CountryDaoImplementation();
    private static CityDaoImplementation cityDao = new CityDaoImplementation();
    private static DialogDao dialogDao = new DialogDaoImplementation();

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
/*
        Continent africa = new Continent("Africa");
        Continent asia = new Continent("Asia");
        Continent australia = new Continent("Australia and Oceania");
        Continent europe = new Continent("Europe");
        Continent northAmerica = new Continent("North America");
        Continent southAmerica = new Continent("South America");
        africa = continentDao.create(africa);
        asia = continentDao.create(asia);
        australia = continentDao.create(australia);
        europe = continentDao.create(europe);
        northAmerica = continentDao.create(northAmerica);
        southAmerica = continentDao.create(southAmerica);
        String[] locales = Locale.getISOCountries();

        HashMap<String, String> countriesAndContinents = new HashMap<String, String>();
        HashMap<String, List<String>> citiesAndCountries = new HashMap<String, List<String>>();
        HashMap<String, String> isoFips = new HashMap<String, String>();
        String countryToContinent = "D:\\cout.txt";
        String isoToFips = "C:\\Users\\Роман\\Desktop\\GEODATASOURCE-COUNTRY.TXT";
        String countryToCity = "C:\\Users\\Роман\\Desktop\\GEODATASOURCE-CITIES-FREE.TXT";

        Country country;
        String code="";
        City city;
        int rows = 0;
        BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(countryToContinent));
            while ((sCurrentLine = br.readLine()) != null) {
                String[] strings = sCurrentLine.split(" ");
                countriesAndContinents.put(strings[0].trim(),strings[strings.length-1].trim());
            }

            br = new BufferedReader(new FileReader(isoToFips));
            while ((sCurrentLine = br.readLine()) != null) {
                String[] strings = sCurrentLine.split("\t");
                if (!strings[0].trim().equals("-") && !strings[1].trim().equals("-"))
                    isoFips.put(strings[1].trim(),strings[0].trim());
            }

            br = new BufferedReader(new FileReader(countryToCity));
            while ((sCurrentLine = br.readLine()) != null) {
                rows ++;
                String[] strings = sCurrentLine.split("\t");
                if (strings[0].trim().length()>0) {
                    List<String> cities = citiesAndCountries.get(strings[0].trim());
                    if (cities == null) {
                        cities = new ArrayList<String>();
                    }
                    if (strings.length > 1 && strings[1].trim().length() > 0) {
                        cities.add(strings[1].trim());
                        citiesAndCountries.put(strings[0].trim(), cities);
                    }
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        String path ="D:\\Labs\\SocialNetwork\\web\\src\\main\\webapp\\resources\\images\\flags\\";
        int i = 0;
        for (String countryCode : locales) {

            Locale obj = new Locale("", countryCode);
            File file = new File(path+obj.getCountry().toLowerCase()+".png");
            if (file.exists()) {
                code = countriesAndContinents.get(obj.getCountry()).trim();

                if (code.equals("EU")){
                    country = new Country(obj.getDisplayCountry(Locale.ENGLISH),"/resources/images/flags/"+obj.getCountry().toLowerCase()+".png",europe);
                }else if (code.equals("AS")){
                    country = new Country(obj.getDisplayCountry(Locale.ENGLISH),"/resources/images/flags/"+obj.getCountry().toLowerCase()+".png",asia);
                }else if (code.equals("AF")){
                    country = new Country(obj.getDisplayCountry(Locale.ENGLISH),"/resources/images/flags/"+obj.getCountry().toLowerCase()+".png",africa);
                }else if (code.equals("OC")){
                    country = new Country(obj.getDisplayCountry(Locale.ENGLISH),"/resources/images/flags/"+obj.getCountry().toLowerCase()+".png",australia);
                }else if (code.equals("NA")){
                    country = new Country(obj.getDisplayCountry(Locale.ENGLISH),"/resources/images/flags/"+obj.getCountry().toLowerCase()+".png",northAmerica);
                }else country = new Country(obj.getDisplayCountry(Locale.ENGLISH),"/resources/images/flags/"+obj.getCountry().toLowerCase()+".png",southAmerica);
                country.setIso(obj.getCountry());
                    //
                i++;
                country = countryDao.create(country);
                String fips = isoFips.get(country.getIso());
                if (citiesAndCountries.get(fips) == null){
                    city = new City(country.getName(),country);
                    cityDao.create(city);
                    System.out.println("==========NO CITIES==========");
                }else {
                    for (String s : citiesAndCountries.get(fips)) {
                        city = new City(s, country);
                        cityDao.create(city);
                    }
                }

                System.out.println("Country Code = " + obj.getCountry()
                        + ", Country Name = " + obj.getDisplayCountry(Locale.ENGLISH)
                        + ", flag =  " + path + obj.getCountry().toLowerCase() + ".png");
            }

        }
        System.out.println(i);
        System.exit(0);
/*
        HashMap<String,List<String>> countryAndCity = new HashMap<String, List<String>>();
        BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader("C:\\Users\\Роман\\Desktop\\GEODATASOURCE-CITIES-FREE.TXT"));
            while ((sCurrentLine = br.readLine()) != null) {
                String[] strings = sCurrentLine.split("\t");
                List<String> cities = countryAndCity.get(strings[0]);
                if(cities == null){
                    cities = new ArrayList<String>();
                }
                cities.add(strings[1]);
                countryAndCity.put(strings[0],cities);
                countriesAndContinents.put(strings[0],strings[strings.length-1]);
            }

        }
        catch (Exception e)
        {

        }
        finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }



        /*City city = cityDao.getCityById(292l);
        Country country = countryDao.getCountryById(1l);
        Gender gender = genderDao.getGenderById(1l);
        User user = new User("Roman",
                "Chevron",
                "4297f44b13955235245b2497399d7a93",
                "chevron19941@gmail.com",
                Date.valueOf("1994-10-31"),
                "http://cs623129.vk.me/v623129384/38800/fRIVx2YTlXk.jpg",
                city,
                gender
                );
        user.setCountry(country);
        user.setConfirmed(true);
        user.setRole(1);
        user.setIsLocked(false);
        userDao.create(user);

        gender = genderDao.getGenderById(2l);

        user = new User("Anna",
                "Anna",
                "4297f44b13955235245b2497399d7a93",
                "Anna.Solntseva@gmail.com",
                Date.valueOf("1996-06-02"),
                "http://cs622423.vk.me/v622423638/3cd94/iPgfRBdHjGI.jpg",
                city,
                gender
        );
        user.setCountry(country);
        user.setConfirmed(true);
        user.setRole(1);
        user.setIsLocked(false);
        userDao.create(user);

        Gender gender = new Gender("Female");
        genderDao.create(gender);
        gender = new Gender("Male");
        genderDao.create(gender);

        Continent continent = new Continent("Europe");
        continent = continentDao.create(continent);
        Country country = new Country("Russia","",continent);
        country = countryDao.create(country);
        City city;
        BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader("C:\\Users\\Роман\\Desktop\\rus"));
            while ((sCurrentLine = br.readLine()) != null) {
                city = new City(sCurrentLine.trim(),country);
                cityDao.create(city);
            }

            }
        catch (Exception e)
            {

            }
        finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        */
        Dialog d = dialogDao.getDialogByTwoUser(1l,2l);
        System.exit(0);
    }
}
