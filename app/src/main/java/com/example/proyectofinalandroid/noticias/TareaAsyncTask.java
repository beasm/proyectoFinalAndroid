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

//Creamos una clase que herede de la clase base AsyncTask con los
//tipos de parámetro de entrada, parámetro para la actualización del progreso
//en el hilo principal y el resultado devuelto del procesamiento.
public class TareaAsyncTask extends AsyncTask<String, Integer, Bitmap>
{
    //Se declaran los elementos necesarios para la descarga y
    //visionado de la imagen.
    private final WeakReference imageRef;
    private BufferedInputStream input;
    private Bitmap bitmap;
    private ImageView imgAA;

    public TareaAsyncTask(ImageView imgAA){
        imageRef = new WeakReference(imgAA);
    }
    //Método que ejecutará las tareas en segundo plano, encargado de conectarse
    //a una dirección URL con la imagen que se va a descargar.
    @Override
    protected Bitmap doInBackground(String... params) {
        try{
            URL url = new URL(params[0]);
            URLConnection conection = url.openConnection();
            conection.connect();
            input = new BufferedInputStream(url.openStream(), 8192);
            bitmap = BitmapFactory.decodeStream(input);
            input.close();
        }catch(Exception ex)
        {
            Log.e("Error en doInBackground: ", ex.getMessage());
        }
        return bitmap;
    }

    //Método que se ejecutará antes del procesamiento de la tarea,
    //indicando en un componente ProgressDialog,
    //que las tareas en segundo plano van a comenzar a ejecutarse
    @Override
    protected void onPreExecute() {
        try{
            super.onPreExecute();
        }catch(Exception ex)
        {
            Log.e("Error en onPreExecute: ", ex.getMessage());
        }
    }

    //Método que se procesará justo después de ejecutarse la tarea en
    //segundo plano, encargado de mostrar la imagen descargada en la
    //pantalla.
    @Override
    public void onPostExecute(Bitmap bitmap)
    {
        try{
//            if(isCancelled())
//            {
//                bitmap = null;
//            }
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

    //Método encargado de cancelar la ejecución de la tarea.
    @Override
    protected void onCancelled() {
        ImageView imageView = (ImageView) imageRef.get();
        imageView.setImageBitmap(null);
    }

}

