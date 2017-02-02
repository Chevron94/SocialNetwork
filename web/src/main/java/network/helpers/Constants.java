package network.helpers;

/**
 * Created by Роман on 30.01.2017.
 */
public class Constants {
    public static final String HOST_URL;// = "https://img.hello-from.tk/";
    public static final String FILES_PATH;// = "/home/roman/hellofrom";
    public static final String EMAIL_LOGIN;// = "no-reply@hello-from.tk";
    public static final String EMAIL_PASSWORD;// = "Chevron94Vrn";
    public static final String SMTP_HOST;// = "smtp.yandex.ru";

    static {
        HOST_URL = System.getProperty("HOST_URL");
        FILES_PATH = System.getProperty("FILES_PATH");
        EMAIL_LOGIN = System.getProperty("EMAIL_LOGIN");
        EMAIL_PASSWORD = System.getProperty("EMAIL_PASSWORD");
        SMTP_HOST = System.getProperty("SMTP_HOST");
    }
}
