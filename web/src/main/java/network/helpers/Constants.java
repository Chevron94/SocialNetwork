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
        HOST_URL = System.getenv().get("HOST_URL");
        FILES_PATH = System.getenv().get("FILES_PATH");
        EMAIL_LOGIN = System.getenv().get("EMAIL_LOGIN");
        EMAIL_PASSWORD = System.getenv().get("EMAIL_PASSWORD");
        SMTP_HOST = System.getenv().get("SMTP_HOST");
        }
}
