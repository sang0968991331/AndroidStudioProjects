package com.example.myapplication;

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

import java.util.HashMap;
import java.util.Map;

import static com.example.myapplication.MainActivity.userId;


public class Add_group extends AppCompatActivity {
private EditText ed_nameGroup;
    private Button btn_themgr;
    private TextView tv_trolaigr;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_group);
        firebaseFirestore=FirebaseFirestore.getInstance();
        ed_nameGroup=findViewById( R.id.edit_Namegroup);
        btn_themgr=findViewById( R.id.btn_addgroup );
        tv_trolaigr=findViewById( R.id.tv_trolai);
        tv_trolaigr.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
        btn_themgr.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tengroup = ed_nameGroup.getText().toString().trim();
                if (!tengroup.equals( "" )) {

                    Map<String, String> userMap = new HashMap<>();
                    userMap.put( "name", tengroup );
                    firebaseFirestore.collection( "user" ).document( userId ).collection( "Group" ).add( userMap ).addOnSuccessListener( new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            finish();
                            Toast.makeText( Add_group.this, "Thêm thành công", Toast.LENGTH_SHORT ).show();
                        }
                    } ).addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String error = e.getMessage();
                            Toast.makeText( Add_group.this, "Error", Toast.LENGTH_SHORT ).show();
                        }
                    } );


                } else {
                    Toast.makeText( Add_group.this, "Nhap du thong tin", Toast.LENGTH_SHORT ).show();
                }


            }

        } );
    }
}
