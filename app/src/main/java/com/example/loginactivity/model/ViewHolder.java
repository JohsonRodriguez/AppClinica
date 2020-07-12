package com.example.loginactivity.model;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginactivity.R;

import java.text.BreakIterator;

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView espe, doc, fecha, hora, estado;
    public View mView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        /*clic simple*/
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v,getAdapterPosition());

            }
        });
        /*clic fuerte*/
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v,getAdapterPosition());
                return true;
            }
        });

        /*Llenar variables*/
        espe=itemView.findViewById(R.id.tvespeC);
        doc=itemView.findViewById(R.id.tvdoctorC);
        fecha=itemView.findViewById(R.id.tvfechaC);
        hora=itemView.findViewById(R.id.tvHoraC);
        estado=itemView.findViewById(R.id.tvEstadoC);
    }
    private ViewHolder.ClickListener mClickListener;
    /*inteaces de liestener*/
    public interface  ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener=clickListener;

    }
}
