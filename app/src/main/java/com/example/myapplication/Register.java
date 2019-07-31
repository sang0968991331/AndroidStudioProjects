package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import com.example.myapplication.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Register extends AppCompatActivity {
    private Button btn_dangki;
    private EditText ed_hoten;
    private EditText ed_email;
    private EditText ed_pass;
    private EditText ed_cfpass;
    private TextView tv_trolai;
    private FirebaseFirestore firebaseFirestore;

    private List<User> listUsers;
    private boolean checkRegister=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.register );
        firebaseFirestore = FirebaseFirestore.getInstance();
        init();
        Register();

    }
    public void Register() {
        btn_dangki.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  hoten= ed_hoten.getText().toString().trim();
                String email=ed_email.getText().toString().trim();
                String pass=ed_pass.getText().toString().trim();
                String cfpass=ed_cfpass.getText().toString().trim();

                if (!hoten.equals( "" ) && !email.equals( "" )&&!pass.equals( "" )) {
                    if(pass.equals( cfpass )){
                        Map<String , String> userMap= new HashMap<>(  );
                        userMap.put( "name",hoten );
                        userMap.put( "email", email );
                        userMap.put("pass", pass);
                        for(int i =0 ;i < listUsers.size();i++){
                            if(email.equals( listUsers.get( i ).email ) ){
                                Log.e("res",listUsers.get( i ).email+"vao day nhe" );
                                checkRegister = false;
                                break;
                            }
                        }
                        if(checkRegister){
                            firebaseFirestore.collection( "user" ).add( userMap ).addOnSuccessListener( new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Intent intent = new Intent( Register.this, Login.class );
                                    startActivity( intent );
                                    finish();
                                    Toast.makeText( Register.this, "dang ki thanh cong", Toast.LENGTH_SHORT ).show();
                                }
                            } ).addOnFailureListener( new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    String error = e.getMessage();
                                    Toast.makeText( Register.this, "Error", Toast.LENGTH_SHORT ).show();
                                }
                            } );

                        }else {
                            Toast.makeText( Register.this, "Email da ton tai", Toast.LENGTH_SHORT ).show();
                            checkRegister=true;
                        }
                    }else{
                        Toast.makeText( Register.this, "Nhap lai mat khau", Toast.LENGTH_SHORT ).show();
                    }

                }else{
                    Toast.makeText( Register.this, "Nhap du thong tin", Toast.LENGTH_SHORT ).show();
                }


            }

        } );
    }
    public void init(){
        btn_dangki=findViewById( R.id.btn_dangki );
       ed_hoten=findViewById( R.id.edit_Name );
        ed_email=findViewById( R.id.edit_Email );
        ed_pass=findViewById( R.id.edit_pass );
        ed_cfpass=findViewById( R.id.edit_cfpass );
        tv_trolai=findViewById( R.id.tv_trolai );
        listUsers = new ArrayList<>(  );
        tv_trolai.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent( Register.this, Login.class );
                        startActivity( intent );
                        finish();
                    }
                } );

        Query query=firebaseFirestore.collection( "user" );

        query .get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.e( "vvvv", document.getId() + " => " + document.getData() + document.get( "email" ) );
                        listUsers.add( new User( document.getId(),document.get( "name" ).toString()+"",document.get( "email" ).toString()+"",document.get( "pass" ).toString()+"") );
                    }
                } else {
                    Toast.makeText( Register.this, "Tai khoan khong dung", Toast.LENGTH_SHORT ).show();
                }

            }} ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String error= e.getMessage();
                Toast.makeText( Register.this, "Tai khoan khong dung"+error, Toast.LENGTH_SHORT ).show();
            }
        } );

    }
}
