package com.example.loginactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText etcorreo, etpassword;
    TextView tvReset, tvRegistro;
    Button btnLogin, btnRegistrar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_main);

        tvReset = findViewById(R.id.etReset);
        btnRegistrar=findViewById(R.id.btnRegistrar);
        etcorreo = findViewById(R.id.etdni);
        etpassword = findViewById(R.id.etpassword);
        btnLogin = findViewById(R.id.btnEditar);
        fAuth = FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = etcorreo.getText().toString().trim();
                String password = etpassword.getText().toString().trim();

                if (TextUtils.isEmpty(correo)) {
                    etcorreo.setError("Ingrese correo");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    etpassword.setError("Ingrese contraseña");
                    return;
                }
                fAuth.signInWithEmailAndPassword(correo, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), IntroActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Usuario y contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PruebaActivity.class));
            }
        });

    

        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetCorreo = new EditText(v.getContext());
                AlertDialog.Builder newpassword = new AlertDialog.Builder(v.getContext());
                newpassword.setTitle("Cambiar contraseña");
                newpassword.setMessage("Ingresa tu email para enviar el enlace");
                newpassword.setView(resetCorreo);

                newpassword.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       final String mail = resetCorreo.getText().toString().trim();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LoginActivity.this, "Se ha enviado un enlace a :"+ mail, Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Error link no enviado"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) ;

                    }
                });

                newpassword.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                newpassword.create().show();

            }
        });


    }


}