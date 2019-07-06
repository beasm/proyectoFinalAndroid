package com.example.proyectofinalandroid.descarga;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.proyectofinalandroid.R;

public class FragmentDescargas extends Fragment {
    private static final String URL_TO_DOWNLOAD = "https://beasm.github.io/proyectoFinalWeb/WebContent/download/juego.apk";
    private static final String NAME_FILE = "juego.apk";

    /**
     *  Constructor vacío es necesitado
     */
    public FragmentDescargas() {
    }

    /**
     * Inicializamos y cargamos la página al crearse la vista
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     *
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Obtenemos la referencia de la vista fragment_descargas
        View view = inflater.inflate(R.layout.fragment_descargas, container, false);

        // Obtenemos la referencia de la imagen
        final ImageView imagen_descargas = view.findViewById(R.id.imagen_descargas);

        // activamos un escuchador del evento de on click en la imagen
        imagen_descargas.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try{
                    // Se prepara la request para poder descargar el fichero
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(URL_TO_DOWNLOAD));
                    request.setDescription("Descargando el fichero " + NAME_FILE);
                    request.setTitle(NAME_FILE);
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, NAME_FILE);
                    // se prepara el manage para solicitar la descarga
                    DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                    manager.enqueue(request);
                }
                catch(Exception ex)
                {
                    Log.e("Requerido permisos: ", ex.getMessage());
                }
            }
        });

        return view;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
