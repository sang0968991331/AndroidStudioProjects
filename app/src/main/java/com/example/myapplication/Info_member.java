package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Info_member extends AppCompatActivity {
    private Button btn_luu;
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
    String namemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_member);
        firebaseFirestore = FirebaseFirestore.getInstance();
        userId = getIntent().getStringExtra( "IdUser" );
        namemember = getIntent().getStringExtra( "emailm" );
        init();
        info();
        //Register();

    }
    public void info(){
        ed_hoten.setText( getIntent().getStringExtra( "name" ).toString() );
        ed_email.setText( getIntent().getStringExtra( "email" ).toString() );
        ed_sdt.setText( getIntent().getStringExtra( "sdt" ).toString() );
        ed_ngaysinh.setText( getIntent().getStringExtra( "ngay sinh" ).toString() );
        ed_diachi.setText( getIntent().getStringExtra( "dia chi" ).toString() );
        ed_congty.setText( getIntent().getStringExtra( "cong ty" ).toString() );
        ed_thongtinkhac.setText( getIntent().getStringExtra( "thong tin khac" ).toString() );


    }
    public void Register() {
        btn_luu.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  hoten= ed_hoten.getText().toString().trim();
                String email=ed_email.getText().toString().trim();
                String sdt=ed_sdt.getText().toString().trim();
                String ngaysinh=ed_ngaysinh.getText().toString().trim();
                String diachi=ed_diachi.getText().toString().trim();
                String congty=ed_congty.getText().toString().trim();
                String thongtin=ed_thongtinkhac.getText().toString().trim();


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
                            Intent intent = new Intent( Info_member.this, MainActivity.class );
                            startActivity( intent );
                            finish();
                            Toast.makeText( Info_member.this, "Thêm thành công", Toast.LENGTH_SHORT ).show();
                        }
                    } ).addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String error = e.getMessage();
                            Toast.makeText( Info_member.this, "Error", Toast.LENGTH_SHORT ).show();
                        }
                    } );



                }else{
                    Toast.makeText( Info_member.this, "Nhap du thong tin", Toast.LENGTH_SHORT ).show();
                }


            }

        } );
    }
    public void init(){
        btn_luu=findViewById( R.id.btn_Lưu );
        ed_hoten=findViewById( R.id.edit_Name );
        ed_email=findViewById( R.id.edit_Email );
        ed_sdt=findViewById( R.id.ed_sdt );
        ed_ngaysinh=findViewById( R.id.ed_ngaysinh );
        ed_diachi=findViewById( R.id.ed_diachi );
        ed_congty=findViewById( R.id.ed_congty );
        ed_thongtinkhac=findViewById( R.id.ed_thongtin );
       // tv_trolai=findViewById( R.id.tv_trolaiif );
        //listUsers = new ArrayList<>(  );
        btn_luu.setVisibility( View.GONE );
//        tv_trolai.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent =new Intent( Info_member.this, MainActivity.class );
//                startActivity( intent );
//                finish();
//            }
//        } );



    }
    }

