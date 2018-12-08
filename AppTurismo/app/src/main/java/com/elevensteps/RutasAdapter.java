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

import org.w3c.dom.Text;

import java.util.Collection;

public class RutasAdapter extends RecyclerView.Adapter<RutasAdapter.RutasViewHolder>{
    private static final String TAG = RutasAdapter.class.getName();

    private Ruta [] mNombresRutas;

    private final ListItemClickListener mListener;

    RutasAdapter( ListItemClickListener listener, Context context, String tipo){

        mListener = listener;

        SqliteProvider prov = new SqliteProvider(context);
        Log.i("Adapter", tipo);

        Collection<Ruta> Rutas;

        if (tipo == "Todas") {
            Rutas = prov.retrieveAllRutas();
        }
        else {
            Rutas = prov.retrieveAllKindOfRutas(tipo);
        }

        mNombresRutas = new Ruta[Rutas.size()];
        int i = 0;
        for (Ruta ruta: Rutas ) {

            mNombresRutas[i] = ruta;
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

    public Ruta getElement(int index){
        return mNombresRutas[index];
    }

    @Override
    public int getItemCount() {
        return mNombresRutas.length;
    }

    class RutasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView ListNameView;
        private TextView ListBodyView;
        private TextView ListCosteView;

        RutasViewHolder(View itemView){
            super(itemView);

            ListNameView = itemView.findViewById(R.id.tv_item_ruta);
            ListBodyView = itemView.findViewById(R.id.tv_body_item_descripcion);
            ListCosteView = itemView.findViewById(R.id.tv_body_item_coste);

            itemView.setOnClickListener(this);
        }

        void bind(Ruta ruta){

            ListNameView.setText(ruta.getNombre().toUpperCase());
            ListBodyView.setText(ruta.getDescripcion());
            ListCosteView.setText(String.valueOf(ruta.getNivelCoste()));
        }

        @Override
        public void onClick(View view) {
            int itemClickPosition = getAdapterPosition();
            mListener.onListItemClick(itemClickPosition);
        }
    }
}
