package joseangelpardo.volleyandroidstudyjam;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by josea on 10/11/2015.
 */
public class PostAdapter extends ArrayAdapter {

    // Atributos
    private String URL_BASE = "http://servidorexterno.site90.com/datos";
    private static final String URL_JSON = "/social_media.json";
    private static final String TAG = "PostAdapter";
    List<Post> items;
    private RequestQueue requestQueue;

    public PostAdapter(Context context) {
        super(context, 0);

        // Gestionar petición del archivo JSON
        requestQueue= Volley.newRequestQueue(context);
        nuevaPeticion();
    }

    private void nuevaPeticion(){
        // Nueva petición JSONObject
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_BASE + URL_JSON,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        items = PostReader.parseJson(response);
                        notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error Respuesta en JSON: " + error.getMessage());

                    }
                }
        );
        // Añadir petición a la cola
        MySocialMediaSingleton.getInstance(getContext()).addToRequestQueue(jsArrayRequest);
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        //Salvando la referencia del View de la fila
        View listItemView = convertView;

        //Comprobando si el View no existe
        if (null == convertView) {
            //Si no existe, entonces inflarlo
            listItemView = layoutInflater.inflate(
                    R.layout.item,
                    parent,
                    false);
        }

        // Obtener el item actual
        Post item = items.get(position);

        // Obtener Views
        TextView textoTitulo = (TextView) listItemView.
                findViewById(R.id.textoTitulo);
        TextView textoDescripcion = (TextView) listItemView.
                findViewById(R.id.textoDescripcion);
        final com.android.volley.toolbox.NetworkImageView imagenPost = (com.android.volley.toolbox.NetworkImageView) listItemView.
                findViewById(R.id.imagenPost);


        // Actualizar los Views
        textoTitulo.setText(item.getTitulo());
        textoDescripcion.setText(item.getDescripcion());

        
        // Obtener el image loader
        ImageLoader imageLoader= MySocialMediaSingleton.getInstance(getContext()).getImageLoader();
// Petición
        
        imagenPost.setImageUrl(URL_BASE +item.getImagen(), imageLoader);

        // Procesar item

        return listItemView;
    }
}
