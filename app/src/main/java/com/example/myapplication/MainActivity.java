package com.example.myapplication;


import com.example.myapplication.Adapter.Adapter_lichhen;
import com.example.myapplication.Adapter.Adapter_member;
import com.example.myapplication.Adapter.Adapter_ngayle;
import com.example.myapplication.Notificatuon.MyNotificationManager;
import com.example.myapplication.Notificatuon.Notifica_Receiver;
import com.example.myapplication.Notificatuon.myService;
import com.example.myapplication.model.Member;
import com.example.myapplication.model.NgayLe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


import com.google.common.util.concurrent.Service;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {
    public TextView user_Name;
    public TextView user_Email;
    public Layout layout;
    public static String userId;
    public static String userName;
    public static String userEmail;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    // DatabaseReference reference;
    private RecyclerView recyclerView;
    ArrayList<Member> list;
    private Adapter_member adapter_member;
    private ListenerRegistration listenerRegistration;
    FirebaseFirestore firebaseFirestore;
    FloatingActionButton btn_addmember;
    FloatingActionButton btn_addlichhen;
    SharedPreferences sharedPreferences;
    String ngay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        sharedPreferences = getSharedPreferences( "dataLogin", MODE_PRIVATE );
        drawerLayout = findViewById( R.id.drawer );
        actionBarDrawerToggle = new ActionBarDrawerToggle( this, drawerLayout, R.string.open, R.string.close );
        drawerLayout.addDrawerListener( (DrawerLayout.DrawerListener) actionBarDrawerToggle );
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        NavigationView nvDraver = findViewById( R.id.nav_view );
        setup( nvDraver );
        View head = nvDraver.getHeaderView( 0 );
        user_Name = head.findViewById( R.id.tv_userName );
        user_Email = head.findViewById( R.id.tv_userEmail );
        userId = sharedPreferences.getString( "IdUser", "" );
        user_Email.setText( sharedPreferences.getString( "email", "" ) );
        user_Name.setText( sharedPreferences.getString( "name", "" ) );
        Log.e( "userid", userId + "   " + userName + "  " + userEmail );
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById( R.id.recy_main );
        loadNotesList();
        add_member();
        add_lichhen();
        Notifica();
        Calendar calendar = Calendar.getInstance();
         ngay = (calendar.get( Calendar.MONTH ) + 1) + "/" + calendar.get( Calendar.DATE );
       startService();
        Notification_Sinhnhat();
        Notification_Lichhen();
        Notification_Ngayle();

        listenerRegistration = firebaseFirestore.collection( "user" ).document( userId ).collection( "Lismember" )
                .addSnapshotListener( new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.e( "aaa", "Listen failed!", e );
                            return;
                        }
                        List<Member> notesList = new ArrayList<>();
                        for (DocumentSnapshot doc : documentSnapshots) {
                            Member member = new Member();
                            // Member member = doc.toObject(Member.class);
                            member.setId( doc.getId().toString() );
                            member.setName( doc.get( "name" ).toString() );
                            member.setEmail( doc.get( "email" ).toString() );
                            member.setSdt( doc.get( "sdt" ).toString() );
                            notesList.add( member );
                        }
                        adapter_member = new Adapter_member( notesList, getApplicationContext(), firebaseFirestore );
                        recyclerView.setAdapter( adapter_member );
                    }
                } );
    }
    private void loadNotesList() {
        firebaseFirestore.collection( "user" ).document( userId ).collection( "Lismember" )
                .get()
                .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Member> notesList = new ArrayList<>();
                            for (DocumentSnapshot doc : task.getResult()) {
                                Member member = new Member();
                                // Member member = doc.toObject(Member.class);
                                member.setId( doc.getId().toString() );
                                member.setName( doc.get( "name" ).toString() );
                                member.setEmail( doc.get( "email" ).toString() );
                                member.setSdt( doc.get( "sdt" ).toString() );
                                notesList.add( member );
                            }
                            adapter_member = new Adapter_member( notesList, getApplicationContext(), firebaseFirestore );
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager( getApplicationContext() );
                            recyclerView.setLayoutManager( mLayoutManager );
                            recyclerView.setItemAnimator( new DefaultItemAnimator() );
                            recyclerView.setAdapter( adapter_member );
                        } else {
                            Log.d( "aaa", "Error getting documents: ", task.getException() );
                        }
                    }
                } );
    }
    public void add_member() {
        btn_addmember = findViewById( R.id.add_member );
        btn_addmember.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, Add_member.class );
                intent.putExtra( "IdUser", userId );
                startActivity( intent );
            }
        } );
    }
    public void add_lichhen() {
        btn_addlichhen = findViewById( R.id.add_lichhen );
        btn_addlichhen.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, Add_LichHen.class );
                intent.putExtra( "IdUser", userId );
                startActivity( intent );
            }
        } );
    }
    @SuppressLint("RestrictedApi")
    public void selectIterDrawer(MenuItem menuItem) {
        FrameLayout layout = (FrameLayout) findViewById( R.id.manhinhchinh );
        Fragment myFragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.home:
                fragmentClass = Home.class;
                btn_addmember.setVisibility( View.VISIBLE );
                btn_addlichhen.setVisibility( View.GONE );
                recyclerView.setVisibility( View.VISIBLE );
                break;
            case R.id.search:
                btn_addmember.setVisibility( View.GONE );
                btn_addlichhen.setVisibility( View.GONE );
                fragmentClass = Seach.class;
                recyclerView.setVisibility( View.GONE );
                break;
            case R.id.group:
                fragmentClass = f_Group.class;
                btn_addlichhen.setVisibility( View.GONE );
                btn_addmember.setVisibility( View.GONE );
                recyclerView.setVisibility( View.GONE );
                break;
            case R.id.event:
                btn_addlichhen.setVisibility( View.GONE );
                btn_addmember.setVisibility( View.GONE );
                fragmentClass = SuKien.class;
                recyclerView.setVisibility( View.GONE );
                break;
            case R.id.lich:
                btn_addlichhen.setVisibility( View.VISIBLE );
                btn_addmember.setVisibility( View.GONE );
                fragmentClass = LichHen.class;
                recyclerView.setVisibility( View.GONE );
                break;
            case R.id.logout:
                fragmentClass = LogOut.class;
                recyclerView.setVisibility( View.GONE );
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Intent intent = new Intent( MainActivity.this, Login.class );
                editor.remove( "IdUser" );
                editor.commit();
                startActivity( intent );
                finish();
                break;
            default:
                fragmentClass = Home.class;
                btn_addlichhen.setVisibility( View.GONE );
                recyclerView.setVisibility( View.VISIBLE );
        }
        try {
            myFragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace( R.id.manhinhchinh, myFragment ).commit();
        menuItem.setChecked( true );
        setTitle( menuItem.getTitle() );
        drawerLayout.closeDrawers();
    }

    private void setup(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                selectIterDrawer( menuItem );
                return true;
            }
        } );
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected( item )) {
            return true;
        }
        return super.onOptionsItemSelected( item );
    }
    public void Notifica() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel( "MyNotifications", "MyNotifications", NotificationManager.IMPORTANCE_DEFAULT );
            NotificationManager manager = getSystemService( NotificationManager.class );
            manager.createNotificationChannel( channel );

        }
    }
    public void Notification_Sinhnhat() {

        firebaseFirestore.collection( "user" ).document( userId ).collection( "Lismember" )
                .get()
                .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                           // List<Member> notesList = new ArrayList<>();
                         //   Log.e( "dsa", "vao day" );
                            for (DocumentSnapshot doc : task.getResult()) {
                                Member member = new Member();
                                member.setId( doc.getId().toString() );
                                member.setName( doc.get( "name" ).toString() );
                                String ngaysinh = doc.get( "ngay sinh" ).toString();
                                ngaysinh.split( "/" );
                                String arr[] = ngaysinh.split( "/" );
//                                for (String w : arr) {
//                                   // Log.e( "day", w );
//                                }
                                member.setNgaysinh( arr[0] + "/" + arr[1] );
                                String sinhnhat = arr[0] + "/" + arr[1];
                                if (sinhnhat.equals( ngay )) {
                                    MyNotificationManager myNotificationManager= new MyNotificationManager( getApplicationContext() );
                                    myNotificationManager.getInstance(getApplicationContext()).displayNotification("Hôm nay "+ ngay, "Sinh nhật \n" + doc.get( "name" ).toString(),R.drawable.sinhnhat );
                                }
                                // member.setSdt( doc.get("sdt").toString() );
                              //  notesList.add( member );
                            }
                        }
                    }
                } );
    }
    public  void Notification_Lichhen(){
        firebaseFirestore.collection("user").document( userId ).collection( "Lichhen").addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e("aaa", "Listen failed!", e);
                    return;
                }
             //   List<com.example.myapplication.model.LichHen> notesList = new ArrayList<>();

                for (DocumentSnapshot doc : queryDocumentSnapshots) {
                    com.example.myapplication.model.LichHen lichHen = new com.example.myapplication.model.LichHen(  );
                    // Member member = doc.toObject(Member.class);
                    //  lichHen.setId(doc.getId().toString());
                    lichHen.setNoidung( doc.get("noidung").toString() );
                    lichHen.setNgayhen( doc.get("ngay").toString() );
                    String ngayhen=doc.get("ngay").toString();
                    lichHen.setGiohen( doc.get("gio").toString() );
                    ngayhen.split( "/" );
                    String arr[] = ngayhen.split( "/" );
//                                for (String w : arr) {
//                                   // Log.e( "day", w );
//                                }
                    lichHen.setNgayhen( arr[0] + "/" + arr[1] );
                    String ngayh = arr[0] + "/" + arr[1];
                    if (ngayh.equals( ngay )) {
                        MyNotificationManager myNotificationManager= new MyNotificationManager( getApplicationContext() );
                        myNotificationManager.getInstance(getApplicationContext()).displayNotification1("Lịch hẹn "+ ngay+" lúc "+doc.get("gio").toString() , " \n"+doc.get("noidung").toString() ,R.drawable.ic_october  );
                    }
                 //   notesList.add(lichHen);
                }
            }
        } );
    }
    public  void Notification_Ngayle(){

        firebaseFirestore.collection("user").document( userId ).collection( "Ngayle")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                NgayLe ngayLe = new NgayLe();
                                ngayLe.setNoidung( doc.get( "noidung" ).toString() );
                                ngayLe.setNgayle( doc.get( "ngay" ).toString() );
                                String ngayle = doc.get( "ngay" ).toString();
                                if (ngayle.equals( ngay )) {
                                    MyNotificationManager myNotificationManager = new MyNotificationManager( getApplicationContext() );
                                    myNotificationManager.getInstance( getApplicationContext() ).displayNotification2( "Ngày"+  ngay , " \n" + doc.get( "noidung" ).toString(), R.drawable.lehoi );
                                }
                            }
                        }
                    }
                });
    }
    public void startService() {
        Intent serviceIntent = new Intent(this, MyNotificationManager.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
       // notilichhen();
        Log.e( "dsa", "vao day" );
        ContextCompat.startForegroundService(this, serviceIntent);
    }
}
