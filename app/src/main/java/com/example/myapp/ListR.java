package com.example.myapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class ListR extends AppCompatActivity {
    public FloatingActionButton floatingActionButton1,btnback;
    FirebaseDatabase database;
    DatabaseReference ref;
    RecyclerView recyclerView;
    LinearLayoutManager mLayout;
    String title;
    String USERID;
    FirebaseAuth fAuth;
    Reminder_data reminder_data=new Reminder_data();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listlay);
        fAuth = FirebaseAuth.getInstance();


        String  USERID = fAuth.getCurrentUser().getEmail();


        floatingActionButton1=findViewById(R.id.floatingActionButton3);
        btnback=findViewById(R.id.rback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(com.example.myapp.ListR.this, dashboard.class);
                startActivity(intent);
            }
        });

        recyclerView=findViewById(R.id.rvf);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        database= FirebaseDatabase.getInstance();
        ref=database.getReference(USERID.substring(0,2)).child("remainder");

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(com.example.myapp.ListR.this, remainder.class);
                startActivity(intent);
            }
        });

    }

    protected void onStart() {



        super.onStart();
        FirebaseRecyclerOptions<Reminder_data> options=
                new FirebaseRecyclerOptions.Builder<Reminder_data>()
                        .setQuery(ref,Reminder_data.class)
                        .build();

        FirebaseRecyclerAdapter<Reminder_data, com.example.myapp.VH> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Reminder_data, com.example.myapp.VH>(options) {
            @NonNull
            @Override
            public com.example.myapp.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.reminder_item,parent,false);
                return new com.example.myapp.VH(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull com.example.myapp.VH vh, int i, @NonNull Reminder_data reminder_data) {
                vh.setView(getApplicationContext(),reminder_data.getTitle(),reminder_data.getCmnt(),reminder_data.getTime(),reminder_data.getDate(),reminder_data.getRepeat());
                vh.setonClickListener(new com.example.myapp.VH.ClickListener() {
                    @Override
                    public void onItemlongClick(View view, int p) {
                        title=getItem(p).getTitle();
                        showDeleteDialog(title);
                    }
                });
            }

        };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    private void showDeleteDialog(String title){
        AlertDialog.Builder b=new AlertDialog.Builder(com.example.myapp.ListR.this);
        b.setTitle("Delete Reminder");
        b.setMessage("Are you sure to delete the reminder");
        b.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Query q=ref.orderByChild("title").equalTo(title);
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds :snapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(com.example.myapp.ListR.this,"Data is deleted",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        b.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog AD=b.create();
        AD.show();
    }

}

