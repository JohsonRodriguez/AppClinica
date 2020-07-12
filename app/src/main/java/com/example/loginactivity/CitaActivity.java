package com.example.loginactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginactivity.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CitaActivity extends AppCompatActivity {
    TextView volver;
    EditText fecha;
    Button registar;
    public static final String TAG="TAG";
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID, citaID;

    /*Progress Dialog*/
    ProgressDialog pd;

    Spinner especialidases, doctores, horario;
    ArrayList<String> arrayList_espe;
    ArrayAdapter<String> arrayAdapter_espe;

    ArrayList<String> arrayList_hora;
    ArrayAdapter<String>arrayAdapter_hora;

    ArrayList<String> arrayList_car, arrayList_der, arrayList_endo, arrayList_gas, arrayList_geri, arrayList_medi;
    ArrayAdapter<String> arrayAdapter_doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cita);
        fAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        /*Progress Dialog*/
        pd= new ProgressDialog(this);

        volver = findViewById(R.id.etVolver);
        fecha = findViewById(R.id.etfecha);
        Calendar C = Calendar.getInstance();
        final int month = C.get(Calendar.MONTH);
        final int day = C.get(Calendar.DAY_OF_MONTH);
        final int year = C.get(Calendar.YEAR);
        especialidases = findViewById(R.id.spEspecialidad);
        doctores = findViewById(R.id.spDoctor);
        horario=findViewById(R.id.spHorario);
        registar=findViewById(R.id.btnRegistrarCita);


        llenarSpiner();

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CitaActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        fecha.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });

       /* if (fAuth.getCurrentUser() != null) {
            finish();
        }*/

        registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setTitle("Generando Cita");
                pd.show();
                String citaID= UUID.randomUUID().toString();

                userID = fAuth.getCurrentUser().getUid();
                final String CitaEspe=especialidases.getSelectedItem().toString();
                final String CitaDoc=doctores.getSelectedItem().toString();
                final String CitaFecha= fecha.getText().toString();
                final String CitaHora=horario.getSelectedItem().toString();
                final String estado="pendiente";

                Map<String,Object>map=new HashMap<>();
                map.put("paciente",userID);
                map.put("especialidad",CitaEspe);
                map.put("doctor",CitaDoc);
                map.put("fecha",CitaFecha);
                map.put("hora",CitaHora);
                map.put("estado",estado);

                fStore.collection("citas").document(citaID).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                       Toast.makeText(CitaActivity.this, "Su cita fue creada con exito", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), IntroActivity.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(CitaActivity.this, "Hubo un error al crear la cita", Toast.LENGTH_SHORT).show();

                    }
                });




            }
        });


        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), IntroActivity.class));
            }
        });


    }


    public void llenarSpiner() {
        /*Lista de Horarios*/
        arrayList_hora = new ArrayList<>();

        arrayList_hora.add("9:00 am - 10:00 am");
        arrayList_hora.add("10:00 am - 11:00 am");
        arrayList_hora.add("11:00 am - 12:00 am");
        arrayList_hora.add("12:00 m - 1:00 pm");
        arrayList_hora.add("1:00 pm - 2:00 pm");
        arrayList_hora.add("2:00 pm - 3:00 pm");
        arrayList_hora.add("3:00 pm - 4:00 pm");
        arrayList_hora.add("4:00 pm - 5:00 pm");
        arrayList_hora.add("5:00 pm - 6:00 pm");
        arrayAdapter_hora= new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,arrayList_hora);
        horario.setAdapter(arrayAdapter_hora);

        /*Lista de Especialidades*/
        arrayList_espe = new ArrayList<>();
        arrayList_espe.add("Cardiología");
        arrayList_espe.add("Dermatología");
        arrayList_espe.add("Endocrinología");
        arrayList_espe.add("Gastroenterología");
        arrayList_espe.add("Geriatría");
        arrayList_espe.add("Medicina General");

        arrayAdapter_espe = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_espe);
        especialidases.setAdapter(arrayAdapter_espe);

        /*Lista de Doctores Cardiologia*/
        arrayList_car = new ArrayList<>();
        arrayList_car.add("Antonio Garcia");
        arrayList_car.add("Maria Martinez");
        arrayList_car.add("Jose Lopez");
        arrayList_car.add("Maria Sanchez");

        /*Lista de Doctores Dermatologia*/
        arrayList_der = new ArrayList<>();
        arrayList_der.add("Francisco Gonzales");
        arrayList_der.add("Josefa Gomez");
        arrayList_der.add("Juan Fernandez");
        arrayList_der.add("Isabel Moreno");

        /*Lista de Doctores Endocrinologia*/
        arrayList_endo = new ArrayList<>();
        arrayList_endo.add("Manuel Jimenez");
        arrayList_endo.add("Maria Perez");
        arrayList_endo.add("Pedro Rodriguez");
        arrayList_endo.add("Carmen Navarro");

        /*Lista de Doctores Gastroentrnologia*/
        arrayList_gas = new ArrayList<>();
        arrayList_gas.add("Jesus Ruiz");
        arrayList_gas.add("Carmen Navarro");
        arrayList_gas.add("Maria Sanchez");
        arrayList_gas.add("Isabel Moreno");

        /*Lista de Doctores Gediatria*/
        arrayList_geri = new ArrayList<>();
        arrayList_geri.add("Maria Perez");
        arrayList_geri.add("Josefa Gomez");
        arrayList_geri.add("Francisco Gonzales");
        arrayList_geri.add("Pedro Rodriguez");

        /*Lista de Doctores Medicina General*/
        arrayList_medi = new ArrayList<>();
        arrayList_medi.add("Alejandro Serrano");
        arrayList_medi.add("David Muñoz");
        arrayList_medi.add("Rafael Alfaro");
        arrayList_medi.add("Rosario Castillo");

        especialidases.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    arrayAdapter_doc = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_car);
                }
                if (position == 1) {
                    arrayAdapter_doc = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_der);
                }
                if (position == 2) {
                    arrayAdapter_doc = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_endo);
                }
                if (position == 3) {
                    arrayAdapter_doc = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_gas);
                }
                if (position == 4) {
                    arrayAdapter_doc = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_geri);
                }
                if (position == 5) {
                    arrayAdapter_doc = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_medi);
                }
                doctores.setAdapter(arrayAdapter_doc);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}