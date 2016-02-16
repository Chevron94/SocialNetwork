package network.console;

import network.dao.ContinentDao;
import network.dao.implementation.*;
import network.entity.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public static void main(String[] args) {
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
*/
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
        System.exit(0);
    }
}
