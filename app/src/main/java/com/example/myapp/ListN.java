package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListN extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseDatabase firebaseD;
    DatabaseReference databaseRef;
    List<com.example.myapp.Listdata> list1 = new ArrayList<>();
    Context context;
    FloatingActionButton back;
    private FirebaseAuth fAuth;
    private Intent intent;


    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nlist);

        fAuth = FirebaseAuth.getInstance();


        email = fAuth.getCurrentUser().getEmail();
        back=findViewById(R.id.nback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent = new Intent(com.example.myapp.ListN.this, com.example.myapp.dashboard.class));
            }
        });

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(com.example.myapp.ListN.this);
        recyclerView.setLayoutManager(layoutManager);
        final com.example.myapp.NAdapter notesAdapter = new com.example.myapp.NAdapter(list1, this);
        recyclerView.setAdapter(notesAdapter);
        FloatingActionButton f = findViewById(R.id.floatingActionButton4);
        firebaseD = FirebaseDatabase.getInstance();
        databaseRef = firebaseD.getReference(email.substring(0,2)).child("notes");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshotn: dataSnapshot.getChildren()) {
                    com.example.myapp.Listdata listdata = dataSnapshotn.getValue(com.example.myapp.Listdata.class);
                    list1.add(listdata);
                }
                notesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), com.example.myapp.notes.class));
            }
        });

    }
}
