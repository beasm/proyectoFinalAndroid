package com.example.proyectofinalandroid.noticias;

import java.io.BufferedInputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

/**
 * Creamos la clase que herede de la clase base AsyncTask con los
 * tipos de parámetro de entrada
 */
public class TareaAsyncTask extends AsyncTask<String, Integer, Bitmap>
{
    // referencia de la imagen necesario para la descarga de la imagen
    private final WeakReference imageRef;

    /**
     * Constructor con la referencia de la imagen por parámetro
     *
     * @param imgAA ImageView
     */
    public TareaAsyncTask(ImageView imgAA){
        imageRef = new WeakReference(imgAA);
    }

    /**
     * Método que ejecutará las tareas en segundo plano, encargado de conectarse
     * a una dirección URL con la imagen que se va a descargar.
     */
    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        try{
            URL url = new URL(params[0]);
            URLConnection conection = url.openConnection();
            conection.connect();
            BufferedInputStream input = new BufferedInputStream(url.openStream(), 8192);
            bitmap = BitmapFactory.decodeStream(input);
            input.close();
        }catch(Exception ex)
        {
            Log.e("Error en doInBackground: ", ex.getMessage());
        }
        return bitmap;
    }

    /**
     * Método que se ejecutará antes del procesamiento de la tarea,
     * indicando en un componente ProgressDialog,
     * que las tareas en segundo plano van a comenzar a ejecutarse
     */
    @Override
    protected void onPreExecute() {
        try{
            super.onPreExecute();
        }catch(Exception ex)
        {
            Log.e("Error en onPreExecute: ", ex.getMessage());
        }
    }

    /**
     * Método que se procesará justo después de ejecutarse la tarea en
     * segundo plano, encargado de mostrar la imagen descargada en la
     * pantalla.
     */
    @Override
    public void onPostExecute(Bitmap bitmap)
    {
        try{
            if (imageRef != null) {
                ImageView imageView = (ImageView) imageRef.get();
                if (imageView != null && bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    Log.e("Error en onPostExecute ", "imageView or bitmap");
                }
            }
        }catch(Exception ex)
        {
            Log.e("Error en onPostExecute: ", ex.getMessage());
        }
    }

    /**
     * Método encargado de cancelar la ejecución de la tarea.
     */
    @Override
    protected void onCancelled() {
        ImageView imageView = (ImageView) imageRef.get();
        imageView.setImageBitmap(null);
    }

}
