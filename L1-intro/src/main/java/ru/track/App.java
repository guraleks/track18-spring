package ru.track;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.json.JSONException;

/**
 * TASK:
 * POST request to  https://guarded-mesa-31536.herokuapp.com/track
 * fields: name,github,email
 *
 * LIB: http://unirest.io/java.html
 *
 *
 */
public class App {

    public static final String URL = "http://guarded-mesa-31536.herokuapp.com/track";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_GITHUB = "github";
    public static final String FIELD_EMAIL = "email";
    public static boolean success = false;

    public static void main(String[] args) throws Exception {
        // 1) Use Unirest.post()
        // 2) Get response .asJson()
        // 3) Get json body and JsonObject
        // 4) Get field "success" from JsonObject
        HttpResponse<JsonNode> jsonResponse = Unirest.post(URL)
                .field(FIELD_NAME, "Aleksey")
                .field(FIELD_GITHUB, "guraleks")
                .field(FIELD_EMAIL, "gurov.aleksei@yandex.ru")
                .asJson();

        try {
            success = (Boolean) jsonResponse.getBody().getObject().get("success");
        } catch (JSONException e) {
        }

//        System.out.println(success);
    }

}
