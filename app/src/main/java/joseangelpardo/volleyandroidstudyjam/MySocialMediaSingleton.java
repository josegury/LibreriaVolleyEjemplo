package joseangelpardo.volleyandroidstudyjam;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by josea on 10/11/2015.
 */
public final class MySocialMediaSingleton {
    // Atributos
    private static MySocialMediaSingleton singleton;
    private RequestQueue requestQueue;
    private static Context context;
    private ImageLoader imageLoader;

    private MySocialMediaSingleton(Context context) {
        MySocialMediaSingleton.context = context;
        requestQueue = getRequestQueue();

        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            // LruCache representa una cache gestionada por un algoritmo que prioriza a los elementos mas usados
                    cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized MySocialMediaSingleton getInstance(Context context) {
        if (singleton == null) {
            singleton = new MySocialMediaSingleton(context);
        }
        return singleton;
    }

    /**
     * obtiene la instancia de la cola de peticiones que se usará a través de toda la aplicación
     * @return
     */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    /**
     *  agregar un nueva petición
     * @param req
     */
    public  void addToRequestQueue(Request req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

}