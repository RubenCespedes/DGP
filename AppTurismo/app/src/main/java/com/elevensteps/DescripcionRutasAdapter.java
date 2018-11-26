package com.elevensteps;

import android.content.Context;
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

import java.util.Collection;

public class DescripcionRutasAdapter extends RecyclerView.Adapter<DescripcionRutasAdapter.RutasViewHolder>{
    private static final String TAG = DescripcionRutasAdapter.class.getName();

    private PuntoInteres mPuntosInteres[];

    private final ListItemClickListener mListener;

    DescripcionRutasAdapter(ListItemClickListener listener, Context context, Ruta miRuta){

        mListener = listener;

        SqliteProvider prov = new SqliteProvider(context);

        Log.d("MiDebug", miRuta.getNombre() + " Previa query");
        Collection<PuntoInteres> PuntosDeInteres = prov.retrieveCamino(miRuta);
        Log.d("MiDebug","PostQuery");

        Log.d("MiDebug", Integer.toString(PuntosDeInteres.size()));
        mPuntosInteres = new PuntoInteres[PuntosDeInteres.size()];
        int i = 0;
        for (PuntoInteres punto: PuntosDeInteres ) {

            mPuntosInteres[i] = punto;

            Log.d("MiDebug", "Nombres Punto Interes :" + mPuntosInteres[i].getNombre());

            i++;
        }
    }

    interface ListItemClickListener {
        void onListItemClick(int itemIndex);
    }

    @NonNull
    @Override
    public RutasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int idItemLayout = R.layout.punto_interes_item;
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(idItemLayout, parent, false);

        return new RutasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RutasViewHolder holder, int position) {
        // TODO MEJORAR: Almacenar el texto de la ruta en una variable
        Log.d(TAG, "#" + position + ":" + mPuntosInteres[position]);
        holder.bind(mPuntosInteres[position].getNombre());
    }
    public PuntoInteres getElement(int index){
        return mPuntosInteres[index];
    }

    @Override
    public int getItemCount() {
        return mPuntosInteres.length;
    }

    class RutasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView ListNameView;

        RutasViewHolder(View itemView){
            super(itemView);

            ListNameView = itemView.findViewById(R.id.tv_item_punto_interes);

            itemView.setOnClickListener(this);
        }

        void bind(String nombreRuta){
            ListNameView.setText(nombreRuta);
        }

        @Override
        public void onClick(View view) {
            int itemClickPosition = getAdapterPosition();
            mListener.onListItemClick(itemClickPosition);
        }
    }
}
