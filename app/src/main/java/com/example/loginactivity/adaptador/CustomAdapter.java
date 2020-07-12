package com.example.loginactivity.adaptador;

import android.app.ListActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginactivity.CitasActivity;
import com.example.loginactivity.R;
import com.example.loginactivity.model.Model;
import com.example.loginactivity.model.ViewHolder;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
   public CitasActivity listActivity;
    List<Model> modelList;

    public CustomAdapter(CitasActivity listActivity, List<Model> modelList) {
        this.listActivity = listActivity;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate
                (R.layout.model_layout,viewGroup,false);

        ViewHolder viewHolder= new ViewHolder(itemView);

        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String Especialidad= modelList.get(position).getEspecialidad();
                String Doctor= modelList.get(position).getDoctor();
                String Fecha= modelList.get(position).getFecha();
                String Hora= modelList.get(position).getHora();
                String Estado= modelList.get(position).getEstado();
                Toast.makeText(listActivity, Especialidad+"\n"+Doctor +"\n"+Fecha+"\n"+Hora+"\n"+Estado, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
       viewHolder.espe.setText(modelList.get(i).getEspecialidad());
        viewHolder.doc.setText(modelList.get(i).getDoctor());
        viewHolder.fecha.setText(modelList.get(i).getFecha());
        viewHolder.hora.setText(modelList.get(i).getHora());
        viewHolder.estado.setText(modelList.get(i).getEstado());
    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
