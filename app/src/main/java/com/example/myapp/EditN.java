package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditN extends AppCompatActivity {
    EditText titletext,detailtext;
    String titlechange,detailchange;
    private DatabaseReference Da;
    private com.example.myapp.Listdata listdata;
    Button update,delete;
    private FirebaseAuth fAuth;

    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        fAuth = FirebaseAuth.getInstance();

        email = fAuth.getCurrentUser().getEmail();
        setContentView(R.layout.editnote);
        update=findViewById(R.id.updatebtn);
        delete=findViewById(R.id.deletebtn);
        final Intent in=getIntent();
        String gtitle=in.getStringExtra("title");
        String getdet=in.getStringExtra("desc");
        final String id=in.getStringExtra("id");
        titletext=findViewById(R.id.title);
        detailtext=findViewById(R.id.details);
        Da= FirebaseDatabase.getInstance().getReference();
        titletext.setText(gtitle);
        detailtext.setText(getdet);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateNote(id);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote(id);
            }
        });
    }
    private void deleteNote(String id) {
        Da.child(email.substring(0,2)).child("notes").child(id).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(com.example.myapp.EditN.this,"Note Deleted",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),ListN.class));

                    }
                });
    }
    private void UpdateNote(String id)
    {
        titlechange=titletext.getText().toString();
        detailchange=detailtext.getText().toString();
        com.example.myapp.Listdata listdata = new com.example.myapp.Listdata(id,titlechange,detailchange);
        Da.child(email.substring(0,2)).child("notes").child(id).setValue(listdata).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(com.example.myapp.EditN.this, "Notes Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),ListN.class));
                    }
                });

    }

}
