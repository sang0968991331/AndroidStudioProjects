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


public class Add_member extends AppCompatActivity {
    private Button btn_them;
    String userId;
    private EditText ed_hoten;
    private EditText ed_email;
    private EditText ed_sdt;
    private EditText ed_ngaysinh;
    private EditText ed_diachi;
    private EditText ed_congty;
    private EditText ed_thongtinkhac;

    private TextView tv_trolai;
    private FirebaseFirestore firebaseFirestore;

    private List<User> listUsers;
    private boolean checkRegister=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_member);
        firebaseFirestore = FirebaseFirestore.getInstance();
        userId = getIntent().getStringExtra( "IdUser" );

        init();
        AddMember();

    }
    public void AddMember() {
        btn_them.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String  hoten= ed_hoten.getText().toString().trim();
                final String email=ed_email.getText().toString().trim();
                final String sdt=ed_sdt.getText().toString().trim();
                final String ngaysinh=ed_ngaysinh.getText().toString().trim();
                final String diachi=ed_diachi.getText().toString().trim();
                final String congty=ed_congty.getText().toString().trim();
                final String thongtin=ed_thongtinkhac.getText().toString().trim();


                if (!hoten.equals( "" ) && !email.equals( "" )&&!sdt.equals( "" )) {

                        Map<String , String> userMap= new HashMap<>(  );
                        userMap.put( "name",hoten );
                        userMap.put( "email", email );
                        userMap.put("sdt", sdt);
                        userMap.put("ngay sinh", ngaysinh);
                        userMap.put("dia chi", diachi);
                        userMap.put("cong ty", congty);
                        userMap.put("thong tin khac", thongtin);

                            firebaseFirestore.collection( "user" ).document( userId ).collection( "Lismember" ).add( userMap ).addOnSuccessListener( new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
//                                    Intent intent = new Intent( Add_member.this, Info_member.class );
//                                    intent.putExtra( "name",hoten );
//                                    intent.putExtra( "email",email );
//                                    intent.putExtra( "sdt",sdt );
//                                    intent.putExtra( "ngay sinh",ngaysinh );
//                                    intent.putExtra( "dia chi",diachi );
//                                    intent.putExtra( "cong ty",congty );
//                                    intent.putExtra( "thong tin khac",thongtin );
//                                    startActivity( intent );

                                    finish();
                                    Toast.makeText( Add_member.this, "Thêm thành công", Toast.LENGTH_SHORT ).show();
                                }
                            } ).addOnFailureListener( new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    String error = e.getMessage();
                                    Toast.makeText( Add_member.this, "Error", Toast.LENGTH_SHORT ).show();
                                }
                            } );



                }else{
                    Toast.makeText( Add_member.this, "Nhap du thong tin", Toast.LENGTH_SHORT ).show();
                }


            }

        } );
    }
    public void init(){
        btn_them=findViewById( R.id.btn_them );
        ed_hoten=findViewById( R.id.edit_Name );
        ed_email=findViewById( R.id.edit_Email );
        ed_sdt=findViewById( R.id.ed_sdt );
        ed_ngaysinh=findViewById( R.id.ed_ngaysinh );
        ed_diachi=findViewById( R.id.ed_diachi );
        ed_congty=findViewById( R.id.ed_congty );
        ed_thongtinkhac=findViewById( R.id.ed_thongtin );
        tv_trolai=findViewById( R.id.tv_trolai );
        listUsers = new ArrayList<>(  );
        tv_trolai.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        } );



    }
    }

