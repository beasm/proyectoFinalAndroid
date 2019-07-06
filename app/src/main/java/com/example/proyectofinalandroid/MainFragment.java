package com.example.proyectofinalandroid;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MainFragment extends Fragment {

    /**
     *  Constructor vacío
     */
    public MainFragment() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    /**
     * Esta interfaz debe ser implementada por actividades que contengan Fragment
     * para permitir una interacción entre ellos. Ejemplo implements FragmentEventos.OnFragmentInteractionListener
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
