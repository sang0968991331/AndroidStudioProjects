package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.R.style.Theme_Holo_Light;
import static android.R.style.Theme_Holo_Light_Dialog;
import static com.example.myapplication.MainActivity.userId;

public class Add_LichHen extends AppCompatActivity {
        private EditText ed_noidung;
        private TextView tv_ngayhen,tv_giohen;
        private Button btn_ngayhen,btn_giohen,btn_themlich;
        FirebaseFirestore firebaseFirestore;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private TimePickerDialog.OnTimeSetListener onTimeSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.add__lich_hen );
        firebaseFirestore=FirebaseFirestore.getInstance();
        init();
        add_lichhen();
    }

    public void add_lichhen() {
        btn_themlich.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String noidung = ed_noidung.getText().toString().trim();
                final String ngay = tv_ngayhen.getText().toString().trim();
                final String gio = tv_giohen.getText().toString().trim();


                if (!noidung.equals( "" ) && !ngay.equals( "" ) && !gio.equals( "" )) {

                    Map<String, String> userMap = new HashMap<>();
                    userMap.put( "noidung", noidung );
                    userMap.put( "ngay", ngay );
                    userMap.put( "gio", gio );

                    firebaseFirestore.collection( "user" ).document( userId ).collection( "Lichhen" ).add( userMap ).addOnSuccessListener( new OnSuccessListener<DocumentReference>() {
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
                            Toast.makeText( Add_LichHen.this, "Thêm thành công", Toast.LENGTH_SHORT ).show();
                        }
                    } ).addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String error = e.getMessage();
                            Toast.makeText( Add_LichHen.this, "Error", Toast.LENGTH_SHORT ).show();
                        }
                    } );


                } else {
                    Toast.makeText( Add_LichHen.this, "Nhap du thong tin", Toast.LENGTH_SHORT ).show();
                }


            }

        } );
    }


    public  void init(){
        ed_noidung=findViewById( R.id.ed_noidung );
        tv_ngayhen=findViewById( R.id.tv_ngayhen );
        tv_giohen=findViewById( R.id.tv_giolichhen );
        btn_ngayhen=findViewById( R.id.btn_ngayhen );
        btn_giohen=findViewById( R.id.btn_giohen );
        btn_themlich=findViewById( R.id.btn_themlichhen );

        btn_ngayhen.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get( Calendar.YEAR );
                int month = calendar.get( Calendar.MONTH );
                int day = calendar.get( Calendar.DAY_OF_MONTH );

                DatePickerDialog datePickerDialog = new DatePickerDialog( Add_LichHen.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener, year, month, day );
                datePickerDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
                datePickerDialog.show();
            }
        } );
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                tv_ngayhen.setText( date );
            }
        };

        btn_giohen.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use the current time as the default values for the picker
                final Calendar c = Calendar.getInstance();
                int hour = c.get( Calendar.HOUR_OF_DAY );
                int minute = c.get( Calendar.MINUTE );
                TimePickerDialog timePickerDialog= new TimePickerDialog( Add_LichHen.this, Theme_Holo_Light_Dialog,onTimeSetListener,hour,minute,false );
                timePickerDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
                timePickerDialog.show();

            }
        } );
        onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time= hourOfDay+":"+minute;
                tv_giohen.setText( time );
            }
        };
    }
}
