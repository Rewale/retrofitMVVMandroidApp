package com.example.movieappmvvmretrofit2.utlis;

public class Credentials {

    public static final String BASE_URL = "http://10.0.2.2:8000/";

    //public static final String API_KEY = "52a18783ed514602a5facb15a0177e61";
    public static final String BASE_URL_IMAGE = "http://10.0.2.2:8000/media/";

    // Для авторизации в гугл

    private final static String G_PLUS_SCOPE =
            "oauth2:https://www.googleapis.com/auth/plus.me";
    private final static String USERINFO_SCOPE =
            "https://www.googleapis.com/auth/userinfo.profile";
    private final static String EMAIL_SCOPE =
            "https://www.googleapis.com/auth/userinfo.email";
    public final static String SCOPES = G_PLUS_SCOPE + " " + USERINFO_SCOPE;


    // Не

    public static String TokenApi = "";
}
