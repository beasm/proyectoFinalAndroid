package com.example.proyectofinalandroid;

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

public class FragmentDescargas extends Fragment {
    private static final String URL_TO_DOWNLOAD = "http://192.168.0.38:2000/Proyecto/juego/android.apk";
    private static final String URL_TO_ONLINE = "http://192.168.0.38:2000/Proyecto/juego/index.html";
    //private static final String URL_TO_DOWNLOAD = "https://upload.wikimedia.org/wikipedia/commons/0/0e/Googleplex-Patio-Aug-2014.JPG";
    private static final String NAME_FILE = "android.apk";
    private Context mContext;

    private OnFragmentInteractionListener mListener;

    public FragmentDescargas() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_descargas, container, false);
        final ImageView imagen_descargas = view.findViewById(R.id.imagen_descargas);
        imagen_descargas.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try{

//                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL_TO_DOWNLOAD));
//                    mContext.startActivity(browserIntent);
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(URL_TO_DOWNLOAD));
                    request.setDescription("Downloading file " + NAME_FILE);
                    request.setTitle(NAME_FILE);
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, NAME_FILE);

                    // get download service and enqueue file
                    DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                    manager.enqueue(request);
                }
                catch(Exception ex)
                {
                    Log.e("Requerido permisos: ", ex.getMessage());
                }
            }
        });

        final ImageView imagen_juego_online = view.findViewById(R.id.imagen_juego_online);
        imagen_juego_online.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL_TO_ONLINE));
                mContext.startActivity(browserIntent);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            mContext = context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
