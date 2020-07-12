package com.example.loginactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class PerfilActivity extends AppCompatActivity {
    TextView nombre, apellido,dni, celular, correo, logout;
    Button editar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        nombre = findViewById(R.id.tvnombreP);
        apellido = findViewById(R.id.tvapellidoP);
        dni = findViewById(R.id.etdniP);
        celular = findViewById(R.id.etcelularP);
        correo = findViewById(R.id.etcorreoP);
        editar = findViewById(R.id.btnEditar);
        logout=findViewById(R.id.tvLogout);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = fAuth.getCurrentUser().getUid();
        final DocumentReference documentReference = fStore.collection("paciente").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                nombre.setText(documentSnapshot.getString("nombre"));
                apellido.setText(documentSnapshot.getString("apellido"));
                dni.setText(documentSnapshot.getString("dni"));
                celular.setText(documentSnapshot.getString("celular"));
                correo.setText(documentSnapshot.getString("correo"));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

    }
}