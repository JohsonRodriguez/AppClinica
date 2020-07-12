package com.example.loginactivity.adaptador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginactivity.R;
import com.example.loginactivity.model.Model;

import java.util.List;

import static com.example.loginactivity.R.layout.model_layout;

public class Adapter extends RecyclerView.Adapter<Adapter.CitasViewHolder> {
    List<Model> citas;

    @NonNull
    @Override
    public CitasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CitasViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class CitasViewHolder extends RecyclerView.ViewHolder{
        public TextView espe, doc, fecha, hora, estado;
        public CitasViewHolder(@NonNull View itemView) {
            super(itemView);
            espe=itemView.findViewById(R.id.tvespeC);
            doc=itemView.findViewById(R.id.tvdoctorC);
            fecha=itemView.findViewById(R.id.tvfechaC);
            hora=itemView.findViewById(R.id.tvHoraC);
            estado=itemView.findViewById(R.id.tvEstadoC);
        }
    }

}
