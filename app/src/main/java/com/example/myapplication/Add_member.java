package com.example.myapplication;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.model.Group;
import com.example.myapplication.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
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
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private TextView tv_trolai;
    TextView tengroup;
    Spinner spinner =null;
    private FirebaseFirestore firebaseFirestore;
    List<String> list;
    private List<User> listUsers;
    private boolean checkRegister = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.add_member );
        firebaseFirestore = FirebaseFirestore.getInstance();
        userId = getIntent().getStringExtra( "IdUser" );
        init();
        AddMember();
    }

    public void AddMember() {
        btn_them.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String hoten = ed_hoten.getText().toString().trim();
                final String email = ed_email.getText().toString().trim();
                final String sdt = ed_sdt.getText().toString().trim();
                final String ngaysinh = ed_ngaysinh.getText().toString().trim();
                final String diachi = ed_diachi.getText().toString().trim();
                final String congty = ed_congty.getText().toString().trim();
                final String thongtin = ed_thongtinkhac.getText().toString().trim();
                final String group = spinner.getSelectedItem().toString();


                if (!hoten.equals( "" ) && !email.equals( "" ) && !sdt.equals( "" )) {

                    Map<String, String> userMap = new HashMap<>();
                    userMap.put( "name", hoten );
                    userMap.put( "email", email );
                    userMap.put( "sdt", sdt );
                    userMap.put( "group", group );
                    userMap.put( "ngay sinh", ngaysinh );
                    userMap.put( "dia chi", diachi );
                    userMap.put( "cong ty", congty );
                    userMap.put( "thong tin khac", thongtin );

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


                } else {
                    Toast.makeText( Add_member.this, "Nhap du thong tin", Toast.LENGTH_SHORT ).show();
                }


            }

        } );
    }

    public void init() {
        btn_them = findViewById( R.id.btn_them );
        ed_hoten = findViewById( R.id.edit_Name );
        ed_email = findViewById( R.id.edit_Email );
        ed_sdt = findViewById( R.id.ed_sdt );
        ed_ngaysinh = findViewById( R.id.ed_ngaysinh );
        ed_diachi = findViewById( R.id.ed_diachi );
        ed_congty = findViewById( R.id.ed_congty );
        ed_thongtinkhac = findViewById( R.id.ed_thongtin );
        tv_trolai = findViewById( R.id.tv_trolai );
        listUsers = new ArrayList<>();
        spinner = findViewById( R.id.snip_group );
        tengroup = findViewById( R.id.tv_tengroupadd );
        ed_ngaysinh.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year=calendar.get( Calendar.YEAR );
                int month = calendar.get( Calendar.MONTH );
                int day = calendar.get( Calendar.DAY_OF_MONTH );
                DatePickerDialog datePickerDialog = new DatePickerDialog( Add_member.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener, year, month, day );
                datePickerDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
                datePickerDialog.show();
            }
        } );
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                ed_ngaysinh.setText( date );
            }
        };

        tv_trolai.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
        firebaseFirestore.collection( "user" ).document( userId ).collection( "Group" )
                .get()
                .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            list = new ArrayList<String>();

                            for (DocumentSnapshot doc : task.getResult()) {
                                Group group = new Group();
                                // Member member = doc.toObject(Member.class);
                                //  group.setId_group( doc.getId().toString() );
                                //Log.e("list",doc.get( "name" )+"ss");
                                // group.setName_group( doc.get( "name" ).toString() );
                                list.add( doc.get( "name" ).toString() );
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter( Add_member.this, android.R.layout.simple_spinner_item, list );
                            adapter.setDropDownViewResource( android.R.layout.simple_list_item_single_choice );
                            spinner.setAdapter( adapter );
                            spinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                  tengroup.setText(  spinner.getSelectedItem().toString());
                                //    Toast.makeText( Add_member.this, spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT ).show();
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            } );
                        }
                    }
                } );

//        String arr[]=new String[list.size()];
//        arr=list.toArray(arr);
//        String arr[]={
//                "Hàng điện tử",
//                "Hàng hóa chất",
//                "Hàng gia dụng"};
//        //Log.e("listt",list+"ss");
//        Log.e("listt",arr.length+"ss");
//        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,arr);
//        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(Add_member.this, spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

    }
}


