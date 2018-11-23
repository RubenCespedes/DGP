package com.elevensteps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.elevensteps.model.Ruta;
import com.elevensteps.provider.sqlite.SqliteProvider;

import java.util.Collection;

public class RutasAdapter extends RecyclerView.Adapter<RutasAdapter.RutasViewHolder>{
    private static final String TAG = RutasAdapter.class.getName();

    private String [] mNombresRutas;

    private final ListItemClickListener mListener;

    RutasAdapter( ListItemClickListener listener, Context context){

        mListener = listener;

        SqliteProvider prov = new SqliteProvider(context);
        Collection<Ruta> Rutas = prov.retrieveAllRutas();

        mNombresRutas = new String[Rutas.size()];
        int i = 0;
        for (Ruta ruta: Rutas ) {
            mNombresRutas[i] = ruta.getNombre();
            i++;
        }
    }

    interface ListItemClickListener {
        void onListItemClick(int itemIndex);
    }

    @NonNull
    @Override
    public RutasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int idItemLayout = R.layout.rutas_list_item;
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(idItemLayout, parent, false);

        return new RutasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RutasViewHolder holder, int position) {
        // TODO MEJORAR: Almacenar el texto de la ruta en una variable
        Log.d(TAG, "#" + position + ":" + mNombresRutas[position]);
        holder.bind(mNombresRutas[position]);
    }

    @Override
    public int getItemCount() {
        return mNombresRutas.length;
    }

    class RutasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView ListNameView;

        RutasViewHolder(View itemView){
            super(itemView);

            ListNameView = itemView.findViewById(R.id.tv_item_ruta);

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
