package com.elevensteps;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elevensteps.model.PuntoInteres;
import com.elevensteps.model.Ruta;
import com.elevensteps.provider.sqlite.SqliteProvider;

import java.util.ArrayList;
import java.util.Collection;

public class AniadirPuntosInteresAdapter extends RecyclerView.Adapter<AniadirPuntosInteresAdapter.PIViewHolder>{
    private static final String TAG = DescripcionRutasAdapter.class.getName();

    private PuntoInteres mPuntosInteres[];

    private final ListItemClickListener mListener;

    private ArrayList<PuntoInteres> puntosDeInteresSeleccionados = new ArrayList<PuntoInteres>();

    AniadirPuntosInteresAdapter(ListItemClickListener listener, Context context, Ruta miRuta){

        mListener = listener;

        SqliteProvider prov = new SqliteProvider(context);

        Collection<PuntoInteres> PuntosDeInteres= prov.retrieveAllPuntoInteres();

        Collection<PuntoInteres> puntosEliminar = prov.retrieveCamino(miRuta);

        mPuntosInteres = new PuntoInteres[PuntosDeInteres.size() - puntosEliminar.size()];
        int i = 0;
        for (PuntoInteres punto: PuntosDeInteres ) {

            if (!puntosEliminar.contains(punto)) {
                mPuntosInteres[i] = punto;
                i++;
            }
        }
    }

    interface ListItemClickListener {
        void onListItemClick(int itemIndex);
    }

    @NonNull
    @Override
    public PIViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int idItemLayout = R.layout.punto_interes_item;
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(idItemLayout, parent, false);

        return new PIViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PIViewHolder holder, int position) {
        // TODO MEJORAR: Almacenar el texto de la ruta en una variable
        Log.d(TAG, "#" + position + ":" + mPuntosInteres[position]);
        holder.bind(mPuntosInteres[position]);
    }
    public PuntoInteres getElement(int index){
        return mPuntosInteres[index];
    }

    @Override
    public int getItemCount() {
        return mPuntosInteres.length;
    }

    public PuntoInteres getItemAt(int index){
        return mPuntosInteres[index];
    }

    class PIViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView ListNameView;

        PIViewHolder(View itemView){
            super(itemView);

            ListNameView = itemView.findViewById(R.id.tv_item_punto_interes);

            itemView.setOnClickListener(this);
        }

        void bind(PuntoInteres puntoInteres){
            ListNameView.setText(puntoInteres.getNombre());
            if (puntosDeInteresSeleccionados.contains(puntoInteres)){
                ListNameView.setBackgroundColor(Color.GRAY);
            }
            else {
                ListNameView.setBackgroundColor(Color.TRANSPARENT);
            }
        }

        @Override
        public void onClick(View view) {
            int itemClickPosition = getAdapterPosition();
            mListener.onListItemClick(itemClickPosition);
            PuntoInteres puntoInteres = getItemAt(itemClickPosition);
            if (!puntosDeInteresSeleccionados.contains(puntoInteres)) {
                puntosDeInteresSeleccionados.add(puntoInteres);
                ListNameView.setBackgroundColor(Color.GRAY);
            }
            else {
                puntosDeInteresSeleccionados.remove(puntoInteres);
                ListNameView.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    public ArrayList<PuntoInteres> getPuntosInteresSeleccionados() {
        return puntosDeInteresSeleccionados;
    }
}

