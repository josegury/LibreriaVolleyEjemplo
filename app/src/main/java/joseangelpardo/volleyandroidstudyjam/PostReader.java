package joseangelpardo.volleyandroidstudyjam;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by josea on 10/11/2015.
 */
public class PostReader {

    private static final String TAG = "PostREader";
    public static List<Post> parseJson(JSONObject jsonObject){
        // Variables locales
        List<Post> posts = new ArrayList();
        JSONArray jsonArray= null;

        try {
            // Obtener el array del objeto
            jsonArray = jsonObject.getJSONArray("items");

            for(int i=0; i<jsonArray.length(); i++){

                try {
                    JSONObject objeto= jsonArray.getJSONObject(i);

                    Post post = new Post(
                            objeto.getString("titulo"),
                            objeto.getString("descripcion"),
                            objeto.getString("imagen"));


                    posts.add(post);

                } catch (JSONException e) {
                    Log.e(TAG, "Error de parsing: " + e.getMessage());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return posts;
    }
}
