package com.example.myapplication;


import com.example.myapplication.Adapter.Adapter_member;
import com.example.myapplication.model.Member;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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


import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
       // setContentView( R.layout.header );
       // layout=findViewById( R.layout.header );

        drawerLayout = findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener( (DrawerLayout.DrawerListener) actionBarDrawerToggle );
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nvDraver = findViewById(R.id.nav_view);
        setup(nvDraver);
        View head =nvDraver.getHeaderView( 0 );
        user_Name=head.findViewById( R.id.tv_userName );
        user_Email=head.findViewById( R.id.tv_userEmail );
        userId = getIntent().getStringExtra( "IdUser" );
        userName=getIntent().getStringExtra( "NameUser" );
        userEmail=getIntent().getStringExtra( "EmailUser" );
      //  Log.e("head",user_Name+" "+user_Email);
       user_Name.setText( userName.toString()+"");
       user_Email.setText( userEmail.toString()+"");
        Log.e("userid",userId+"   "+userName+"  "+userEmail);
        firebaseFirestore= FirebaseFirestore.getInstance();
        recyclerView=findViewById( R.id.recy_main );
        loadNotesList();
        add_member();
        listenerRegistration = firebaseFirestore.collection("user").document( userId ).collection( "Lismember" )
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.e("aaa", "Listen failed!", e);
                            return;
                        }

                        List<Member> notesList = new ArrayList<>();

                        for (DocumentSnapshot doc : documentSnapshots) {
                            Member note = doc.toObject(Member.class);
                          //  note.setEmail(doc.getId());
                            notesList.add(note);
                        }

                        adapter_member = new Adapter_member(notesList, getApplicationContext(), firebaseFirestore);
                        recyclerView.setAdapter(adapter_member);
                    }
                });
      //  text();
      //  text1();
    }
    private void loadNotesList() {
        firebaseFirestore.collection("user").document( userId ).collection( "Lismember")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Member> notesList = new ArrayList<>();

                            for (DocumentSnapshot doc : task.getResult()) {
                                Member member = new Member(  );
                               // Member member = doc.toObject(Member.class);
                                member.setId(doc.getId().toString());
                                member.setName( doc.get("name").toString() );
                                member.setEmail( doc.get("email").toString() );
                                notesList.add(member);
                            }

                            adapter_member = new Adapter_member(notesList, getApplicationContext(), firebaseFirestore);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(adapter_member);
                        } else {
                            Log.d("aaa", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public  void add_member(){
        btn_addmember=findViewById( R.id.add_member );
        btn_addmember.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, Add_member.class );
                intent.putExtra( "IdUser",userId );
                startActivity( intent );
             }
        } );


    }


    public void text(){
        DocumentReference documentReference = firebaseFirestore.collection( "user" ).document("sang@gmail.com");

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Log.e("AAA", "Listen failed.", e);
                    return;
                }
                String source = documentSnapshot != null && documentSnapshot.getMetadata().hasPendingWrites()
                        ? "Local" : "Server";
                if (documentSnapshot != null && documentSnapshot.exists()) {

                    Log.e("AAA", documentSnapshot.getData()+"");





                } else {
                    Log.d("AAA", source + " data: null");
                }
            }
        });
    }
    public void text1(){
        DocumentReference documentReference = firebaseFirestore.collection( "user" ).document("sang@gmail.com").collection( "Nhom" ).document("1nhe");

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Log.e("BBB", "Listen failed.", e);
                    return;
                }
                String source = documentSnapshot != null && documentSnapshot.getMetadata().hasPendingWrites()
                        ? "Local" : "Server";
                if (documentSnapshot != null && documentSnapshot.exists()) {
                  //  Log.e("BBB", documentSnapshot.get("Nhom")+"vao day nhe : ");
                    Log.e("BBB", documentSnapshot.get("Banbe")+"vao day nhe");





                } else {
                    Log.e("BBB", source + " data: null");
                }
            }
        });

    }

    @SuppressLint("RestrictedApi")
    public void selectIterDrawer(MenuItem menuItem){
      FrameLayout layout = (FrameLayout) findViewById(R.id.manhinhchinh);
        Fragment myFragment=null;
        Class fragmentClass;
        switch (menuItem.getItemId()){
            case R.id.home:
                fragmentClass=Home.class;
               // btn_addmember.setVisibility( View.VISIBLE );
                recyclerView.setVisibility( View.VISIBLE );
                break;
            case R.id.search:
               // btn_addmember.setVisibility( View.GONE );
                fragmentClass=Seach.class;
                recyclerView.setVisibility( View.GONE );
                break;
            case R.id.group:
                fragmentClass= f_Group.class;
                //btn_addmember.setVisibility( View.GONE );
                recyclerView.setVisibility( View.GONE );
                break;
            case R.id.event:
              //  btn_addmember.setVisibility( View.GONE );
                fragmentClass=LichHen.class;
                recyclerView.setVisibility( View.GONE );
                break;
            case R.id.logout:
                fragmentClass=LogOut.class;
                recyclerView.setVisibility( View.GONE );
                Intent intent = new Intent( MainActivity.this, Login.class );
                startActivity( intent );
                finish();

                break;
            default:
                fragmentClass=Home.class;
                recyclerView.setVisibility( View.VISIBLE );
        }try {
            myFragment=(Fragment) fragmentClass.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.manhinhchinh,myFragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }
    private void setup(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                selectIterDrawer(menuItem);
                return true;

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

