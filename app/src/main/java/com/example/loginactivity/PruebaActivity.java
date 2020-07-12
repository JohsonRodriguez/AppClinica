package com.example.loginactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PruebaActivity extends AppCompatActivity {
    public static final String TAG="TAG";
    private EditText txtdni, txtnombre, txtapellido, txtcorreo, txtcelular, txtcontraseña;
    private Button btnregistrar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
        fAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        txtdni = (EditText) findViewById(R.id.etdni);
        txtnombre = (EditText) findViewById(R.id.txtnombre);
        txtapellido = (EditText) findViewById(R.id.txtapellido);
        txtcorreo = (EditText) findViewById(R.id.etcorreo);
        txtcelular = (EditText) findViewById(R.id.txtcelular);
        txtcontraseña = (EditText) findViewById(R.id.txtcontraseña);
        btnregistrar = (Button) findViewById(R.id.btnEditar);

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String dni = txtdni.getText().toString().trim();
                final String nombre = txtnombre.getText().toString().trim();
                final String apellido = txtapellido.getText().toString().trim();
                final String correo = txtcorreo.getText().toString().trim();
                final String celular = txtcelular.getText().toString().trim();
                final String contraseña = txtcontraseña.getText().toString().trim();

                if (TextUtils.isEmpty(dni)) {
                    txtdni.setError("Dni es requerido");
                    return;
                }
                if (TextUtils.isEmpty(nombre)) {
                    txtnombre.setError("Nombre es requerido");
                    return;
                }
                if (TextUtils.isEmpty(apellido)) {
                    txtapellido.setError("Apellido es requerido");
                    return;
                }
                if (TextUtils.isEmpty(correo)) {
                    txtcorreo.setError("Correo es requerido");
                    return;
                }
                if (TextUtils.isEmpty(celular)) {
                    txtcelular.setError("celular es requerido");
                    return;
                }
                if (TextUtils.isEmpty(contraseña)) {
                    txtcontraseña.setError("contraseña Requerido");
                    return;
                }
                if (contraseña.length() < 6) {
                    txtcontraseña.setError("La contraseña tiene que ser mas de 6 digitos");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(correo, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(PruebaActivity.this, "Usuario creado", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("paciente").document(userID);
                            Map<String, Object> paciente = new HashMap<>();
                            paciente.put("dni", dni);
                            paciente.put("nombre", nombre);
                            paciente.put("apellido", apellido);
                            paciente.put("correo", correo);
                            paciente.put("celular", celular);
                            paciente.put("contraseña", contraseña);
                            documentReference.set(paciente).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "paciente creado correctamente"+ userID);
                                }
                            });

                            startActivity(new Intent(getApplicationContext(), IntroActivity.class));
                        } else {
                            Toast.makeText(PruebaActivity.this, "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }

}


