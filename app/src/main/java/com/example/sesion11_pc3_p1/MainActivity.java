package com.example.sesion11_pc3_p1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sesion11_pc3_p1.modelo.Tarea;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText editTextTarea;
    private ListView lvTareas;
    Button btnAgregar;
    FirebaseDatabase db;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseDatabase.getInstance();
        dbRef =db.getReference(Tarea.class.getSimpleName());

        editTextTarea = findViewById(R.id.editTextTextTarea);
        btnAgregar = findViewById(R.id.buttonAgregar);
        lvTareas = findViewById(R.id.lvTareas);

        regitrarTarea();
        listarTareas();

    }

    private void regitrarTarea() {
        btnAgregar.setOnClickListener(v-> {
            String descripcion = editTextTarea.getText().toString().trim();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date date = new Date();
            String fecha = dateFormat.format(date);
            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Tarea tarea = new Tarea(descripcion, fecha);
                    dbRef.push().setValue(tarea);

                    Toast.makeText(MainActivity.this, "Tarea registrada correctamente", Toast.LENGTH_SHORT).show();
                    editTextTarea.setText("");

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void listarTareas(){
        ArrayList<Tarea> tareas = new ArrayList<>();
        ArrayAdapter<Tarea> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tareas);
        lvTareas.setAdapter(adapter);

        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Tarea tarea = snapshot.getValue(Tarea.class);
                tareas.add(tarea);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}