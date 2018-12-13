package com.example.danny.apprepositorio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.danny.apprepositorio.entidades.Foro;
import com.example.danny.apprepositorio.utilidades.UtilidadesForo;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ForoPrincipalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ForoPrincipalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForoPrincipalFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ListView listViewForo;
    ArrayList<String> listaInformacion;
    ArrayList<Foro> listaForo;
    ConexionSQLiteHelper conn;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ForoPrincipalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForoPrincipalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForoPrincipalFragment newInstance(String param1, String param2) {
        ForoPrincipalFragment fragment = new ForoPrincipalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private void consultarListaForo() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Foro foro =null;
        listaForo=new ArrayList<Foro>();
        //select * from usuarios
        Cursor cursor = db.rawQuery("SELECT * FROM "+UtilidadesForo.TABLA_FORO,null);

        while (cursor.moveToNext()){
            foro = new Foro();
            foro.setId(cursor.getInt(0));
            foro.setTitulo(cursor.getString(1));
            foro.setLenguaje(cursor.getString(2));
            foro.setDescripcion(cursor.getString(3));
            foro.setAutor(cursor.getString(4));
            foro.setFecha(cursor.getString(5));

            listaForo.add(foro);
        }

        obtenerLista();
    }

    public void consultarlist(){
        conn = new ConexionSQLiteHelper(getActivity(),"bd_app",null,1);

        try{
            consultarListaForo();

            ArrayAdapter adaptador = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,listaInformacion);
            listViewForo.setAdapter(adaptador);
            //Comentario
            listViewForo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String informacion = "id: "+listaForo.get(position).getId()+"\n";
                    informacion+="Titulo: "+listaForo.get(position).getTitulo()+"\n";
                    informacion+="Autor: "+listaForo.get(position).getAutor();

                    Toast.makeText(getActivity(), informacion, Toast.LENGTH_SHORT).show();
                }
            });}catch (Exception e){
            Toast.makeText(getActivity(), "Error we", Toast.LENGTH_SHORT).show();
        }
    }
    private void obtenerLista() {
        listaInformacion = new ArrayList<String>();
        for (int i=0;i<listaForo.size();i++){
            listaInformacion.add(listaForo.get(i).getTitulo() +" - "+
                    listaForo.get(i).getAutor());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_foro_principal, container, false);
        listViewForo = (ListView)v.findViewById(R.id.entradaforo);
        consultarlist();
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
