package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import com.example.myapplication.model.Member;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.myapplication.MainActivity.userId;


public class Info_member extends AppCompatActivity {
    private Button btn_luu;
    String infoname;
    String infoemail;
    private EditText ed_hoten;
    private EditText ed_email;
    private EditText ed_sdt;
    private EditText ed_ngaysinh;
    private EditText ed_diachi;
    private EditText ed_congty;
    private EditText ed_thongtinkhac;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    List<String> list;
    String id;
    TextView tengroupif;
    private TextView tv_trolai;
    private FirebaseFirestore firebaseFirestore;
    String namemember;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.info_member );
        firebaseFirestore = FirebaseFirestore.getInstance();
        infoname = getIntent().getStringExtra( "infoname" );
        infoemail = getIntent().getStringExtra( "infoemail" );
        init();
        info();
        Update();
        //Register();

    }

    public void info() {
        firebaseFirestore.collection( "user" ).document( userId ).collection( "Lismember" ).whereEqualTo( "email", infoemail ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.e( "vvv", document.getId() + " => " + document.getData() + document.get( "email" ) );

                        id = document.getId();
                        ed_hoten.setText( document.get( "name" ).toString() + "" );
                        ed_email.setText( document.get( "email" ).toString() + "" );
                        ed_sdt.setText( document.get( "sdt" ).toString() + "" );
                        ed_diachi.setText( document.get( "dia chi" ).toString() + "" );
                        ed_congty.setText( document.get( "cong ty" ).toString() + "" );
                        ed_ngaysinh.setText( document.get( "ngay sinh" ).toString() + "" );
                        ed_thongtinkhac.setText( document.get( "thong tin khac" ).toString() + "" );
                        tengroupif.setText( document.get( "group" ).toString() + ""  );

                    }
                }
            }
        } );
    }

    public void Update() {
        btn_luu.setOnClickListener( new View.OnClickListener() {
            //  private Map<String, String> userMap;

            @Override
            public void onClick(View v) {
                String hoten = ed_hoten.getText().toString().trim();
                String email = ed_email.getText().toString().trim();
                String sdt = ed_sdt.getText().toString().trim();
                String ngaysinh = ed_ngaysinh.getText().toString().trim();
                String diachi = ed_diachi.getText().toString().trim();
                String congty = ed_congty.getText().toString().trim();
                String thongtin = ed_thongtinkhac.getText().toString().trim();
                String group = spinner.getSelectedItem().toString();

                if (!hoten.equals( "" ) && !email.equals( "" ) && !sdt.equals( "" )) {
                    Map<String, Object> note = (new Member( hoten, email, sdt, ngaysinh, diachi, congty, thongtin, group )).toMap();

//                    Map<String , String> userMap= new HashMap<>(  );
//
//                    userMap.put( "name",hoten );
//                    userMap.put( "email", email );
//                    userMap.put("sdt", sdt);
//                    userMap.put("ngay sinh", ngaysinh);
//                    userMap.put("dia chi", diachi);
//                    userMap.put("cong ty", congty);
//                    userMap.put("thong tin khac", thongtin);

                    firebaseFirestore.collection( "user" ).document( userId ).collection( "Lismember" ).document( id ).set( note ).addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            finish();
                            Log.e( "aaa", "Note document update successful!" );
                            Toast.makeText( getApplicationContext(), "Cập nhật thành công ", Toast.LENGTH_SHORT ).show();

                        }
                    } )
                            .addOnFailureListener( new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e( "bbb", "Error adding Note document", e );
                                    Toast.makeText( getApplicationContext(), "Note could not be updated!", Toast.LENGTH_SHORT ).show();
                                }
                            } );


                } else {
                    Toast.makeText( Info_member.this, "Nhap du thong tin", Toast.LENGTH_SHORT ).show();
                }


            }

        } );
    }

    public void init() {
        btn_luu = findViewById( R.id.btn_luu );
        ed_hoten = findViewById( R.id.edit_Name );
        ed_email = findViewById( R.id.edit_Email );
        ed_sdt = findViewById( R.id.ed_sdt );
        ed_ngaysinh = findViewById( R.id.ed_ngaysinh );
        ed_diachi = findViewById( R.id.ed_diachi );
        ed_congty = findViewById( R.id.ed_congty );
        ed_thongtinkhac = findViewById( R.id.ed_thongtin );
        tv_trolai = findViewById( R.id.tv_trolaiif );
        spinner = findViewById( R.id.snip_groupif );
         tengroupif=findViewById( R.id.tv_tengroup );
        //listUsers = new ArrayList<>(  );
        // btn_luu.setVisibility( View.GONE );
        tv_trolai.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
        ed_ngaysinh.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get( Calendar.YEAR );
                int month = calendar.get( Calendar.MONTH );
                int day = calendar.get( Calendar.DAY_OF_MONTH );

                DatePickerDialog datePickerDialog = new DatePickerDialog( Info_member.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener, year, month, day );
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
                            ArrayAdapter<String> adapter = new ArrayAdapter( Info_member.this, android.R.layout.simple_spinner_item, list );
                            adapter.setDropDownViewResource( android.R.layout.simple_list_item_single_choice );
                            spinner.setAdapter( adapter );
                            spinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    tengroupif.setText(  spinner.getSelectedItem().toString());
                                    //Toast.makeText(Info_member.this, spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                    tengroupif.setText("");
                                }
                            } );
                        }
                    }
                } );


    }
}

