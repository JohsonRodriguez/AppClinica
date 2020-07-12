package com.example.loginactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.loginactivity.adaptador.CustomAdapter;
import com.example.loginactivity.model.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CitasActivity extends AppCompatActivity {
    List<Model> modelList=new ArrayList<>();
    RecyclerView mRecyclerView;
FloatingActionButton mAddBtn;

    RecyclerView.LayoutManager layoutManager;

    FirebaseFirestore db;
    CustomAdapter adapter;
  ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas);
        db=FirebaseFirestore.getInstance();

        mRecyclerView= findViewById(R.id.recyclerCita);
        mAddBtn=findViewById(R.id.addBtn);
        mRecyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        pd= new ProgressDialog(this);


        showData();
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CitasActivity.this,CitasActivity.class));
                finish();
            }
        });
    }

    private void showData() {
        pd.setTitle("Cargando sus citas");
        pd.show();
        db.collection("citas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        pd.dismiss();
                        for(DocumentSnapshot doc:task.getResult()){
                            Model model = new Model(doc.getString("id"),
                               doc.getString("especialidad"),
                                    doc.getString("doctor"),
                                    doc.getString("fecha"),
                                    doc.getString("hora"),
                                    doc.getString("estado"));
                            modelList.add(model);

                        }

                        adapter= new CustomAdapter(CitasActivity.this,modelList);
                        mRecyclerView.setAdapter(adapter);
                        Toast.makeText(CitasActivity.this, "Se cargo sus citas", Toast.LENGTH_SHORT).show();
                    }
                }

                )
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(CitasActivity.this, "No se puedo cargar sus citas", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}