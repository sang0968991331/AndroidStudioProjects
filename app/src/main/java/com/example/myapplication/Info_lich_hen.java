package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.myapplication.model.LichHen;
import com.example.myapplication.model.Member;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Map;

import static android.R.style.Theme_Holo_Light_Dialog;
import static com.example.myapplication.MainActivity.userId;

public class Info_lich_hen extends AppCompatActivity {
    String infoID;
    private EditText ed_noidung;
    private TextView tv_ngayhen,tv_giohen;
    private Button btn_ngayhen,btn_giohen,btn_themlich;
    FirebaseFirestore firebaseFirestore;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private TimePickerDialog.OnTimeSetListener onTimeSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.info_lich_hen );
        firebaseFirestore=FirebaseFirestore.getInstance();
        infoID=getIntent().getStringExtra( "infolichhen" );
        init();
        infoLH();
        UpdateLH();
    }
    public void infoLH() {
       firebaseFirestore.collection( "user" ).document( userId ).collection( "Lichhen" ).document( infoID )
               .get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               if (task.isSuccessful()) {
                   DocumentSnapshot document = task.getResult();
                   Log.e( "vvv", document.getId() + " => " + document.getData() + document.get( "email" ) );
                   //   id = document.getId();
                   ed_noidung.setText( document.get( "noidung" ).toString() + "" );
                   tv_ngayhen.setText( document.get( "ngay" ).toString() + "" );
                   tv_giohen.setText( document.get( "gio" ).toString() + "" );
               }
           }
       });
    }
    public void UpdateLH() {
        btn_themlich.setOnClickListener( new View.OnClickListener() {
            //  private Map<String, String> userMap;
            @Override
            public void onClick(View v) {
                String noidung = ed_noidung.getText().toString().trim();
                String ngay = tv_ngayhen.getText().toString().trim();
                String gio = tv_giohen.getText().toString().trim();


                if (!noidung.equals( "" ) && !ngay.equals( "" ) && !gio.equals( "" )) {
                    Map<String, Object> note = (new LichHen(noidung,ngay,gio )).toMap() ;

                    firebaseFirestore.collection( "user" ).document( userId ).collection( "Lichhen" ).document( infoID ).set( note ).addOnSuccessListener( new OnSuccessListener<Void>() {
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
                    Toast.makeText( Info_lich_hen.this, "Nhap du thong tin", Toast.LENGTH_SHORT ).show();
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

                DatePickerDialog datePickerDialog = new DatePickerDialog( Info_lich_hen.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener, year, month, day );
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
                TimePickerDialog timePickerDialog= new TimePickerDialog( Info_lich_hen.this, Theme_Holo_Light_Dialog,onTimeSetListener,hour,minute,false );
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
