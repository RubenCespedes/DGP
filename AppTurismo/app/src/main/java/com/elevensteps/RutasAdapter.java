package com.elevensteps;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

public class RutasAdapter extends RecyclerView.Adapter<RutasAdapter.RutasViewHolder>{
    private static final String TAG = RutasAdapter.class.getName();

    private String [] mNombresRutas;

    private final ListItemClickListener mListener;

    RutasAdapter(String [] nombres, ListItemClickListener listener){
        mNombresRutas = nombres;
        mListener = listener;
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
