package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class notes extends AppCompatActivity {
String ntitle,Ndes;
EditText t1,t2;

private DatabaseReference dref;
private FirebaseAuth fAuth;
String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        fAuth = FirebaseAuth.getInstance();


        email = fAuth.getCurrentUser().getEmail();
        t1=findViewById(R.id.title);
        t2=findViewById(R.id.details);
        dref= FirebaseDatabase.getInstance().getReference();
    }
    public void AddNote(View View) {
        ntitle = t1.getText().toString();
        Ndes = t2.getText().toString();
        if (ntitle.isEmpty() || Ndes.isEmpty()) {
            Toast.makeText(this,"Enter all details",Toast.LENGTH_SHORT).show();
            return;
        }
        AddNote(ntitle,Ndes);
    }
        private void AddNote( String ntitle, String Ndes){
            String id=dref.push().getKey();
            Listdata listdata=new Listdata(id,ntitle,Ndes);
            dref.child(email.substring(0,2)).child("notes").child(id).setValue(listdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(com.example.myapp.notes.this,"Notes Added",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),ListN.class));
                }
            });
        }
    }
