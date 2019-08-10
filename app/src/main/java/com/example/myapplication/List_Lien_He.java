package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Adapter.Adapter_LienHe;
import com.example.myapplication.Adapter.Adapter_member;
import com.example.myapplication.model.LienHe;
import com.example.myapplication.model.Member;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.CALL_PHONE;
import static com.example.myapplication.MainActivity.userId;

public class List_Lien_He extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerView;
    String nameLH;
    String sdtLH;
    ImageView img_call;
    public static String idLH;
    Adapter_LienHe adapter_lienHe;
    TextView tv_name;
    TextView tv_sdt;
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.list__lien__he );
        firebaseFirestore=FirebaseFirestore.getInstance();
        idLH=getIntent().getStringExtra( "idLienhe" );
        nameLH=getIntent().getStringExtra( "nameLienhe" );
        sdtLH=getIntent().getStringExtra( "sdtLienhe" );
        recyclerView=findViewById( R.id. recy_lienhe) ;
        tv_name=findViewById(R.id.name_lienhe  );
        tv_sdt=findViewById( R.id.sdt_lienhe );
        img_call=findViewById( R.id.img_call );
        appBarLayout=findViewById( R.id.abar_call );
        tv_name.setText(nameLH  );
        tv_sdt.setText( sdtLH );
        call();
        call_abar();
        loadNotesList();

    }
    public void call() {
        img_call.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent( Intent.ACTION_CALL );
                callIntent.setData( Uri.parse( "tel:" + sdtLH ) );

                if (ContextCompat.checkSelfPermission( getApplication(), CALL_PHONE ) == PackageManager.PERMISSION_GRANTED) {
                    startActivity( callIntent );
                } else {
                    requestPermissions( new String[]{CALL_PHONE}, 1 );
                }     //You already have permission
            }
        } );
    }
        public void call_abar(){
            appBarLayout.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" +sdtLH ));

                    if (ContextCompat.checkSelfPermission(getApplication(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        startActivity(callIntent);
                    } else {
                        requestPermissions(new String[]{CALL_PHONE}, 1);
                    }     //You already have permission
                }
            } );

//        img_call.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent callIntent =new Intent(Intent.ACTION_CALL);
//                callIntent.setData( Uri.parse("tel:"+sdtLH));
//                startActivity(callIntent);
//            }
//        } );
    }
    private void loadNotesList() {
        firebaseFirestore.collection( "user" ).document( userId ).collection( "Lismember" ).document(idLH).collection( "LienHe" )
                .get()
                .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<LienHe> notesList = new ArrayList<>();
                            for (DocumentSnapshot doc : task.getResult()) {
                                LienHe lienHe = new LienHe();
                                // Member member = doc.toObject(Member.class);
                                lienHe.setId( doc.getId().toString() );
                                lienHe.setNgaygoi( doc.get( "ngay" ).toString() );
                                lienHe.setGiogoi( doc.get( "gio" ).toString() );
                                notesList.add( lienHe );
                            }
                            adapter_lienHe = new Adapter_LienHe( notesList, getApplicationContext(), firebaseFirestore );
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager( getApplicationContext() );
                            recyclerView.setLayoutManager( mLayoutManager );
                            recyclerView.setItemAnimator( new DefaultItemAnimator() );
                            recyclerView.setAdapter( adapter_lienHe );
                        } else {
                            Log.d( "aaa", "Error getting documents: ", task.getException() );
                        }
                    }
                } );
    }
}
