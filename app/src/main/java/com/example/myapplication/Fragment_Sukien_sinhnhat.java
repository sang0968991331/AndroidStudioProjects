package com.example.myapplication;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.Adapter_Sinhnhat;
import com.example.myapplication.Adapter.Adapter_member;
import com.example.myapplication.Notificatuon.MyNotificationManager;
import com.example.myapplication.model.Member;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.myapplication.MainActivity.userId;


public class Fragment_Sukien_sinhnhat extends Fragment {
    FirebaseFirestore firebaseFirestore;
    Adapter_Sinhnhat adapter_sinhnhat;
    private RecyclerView recyclerViewe;
    String ngay;
    private ListenerRegistration listenerRegistration;
    View view;
    public Fragment_Sukien_sinhnhat() {
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view= inflater.inflate(R.layout.fragment_sukien_sinhnhat,container,false);
       firebaseFirestore=FirebaseFirestore.getInstance();
       Log.e("dada","vao day");
       recyclerViewe=view.findViewById( R.id. rc_sukien_sinhnhat);
        Calendar calendar = Calendar.getInstance();
                String date = calendar.get(Calendar.YEAR) +"/"+ (calendar.get(Calendar.MONTH)+1) +"/"+ calendar.get(Calendar.DATE);
        ngay=(calendar.get(Calendar.MONTH)+1) +"/"+ calendar.get(Calendar.DATE);
        String time = calendar.get(Calendar.HOUR) +"/"+ calendar.get(Calendar.MINUTE) +"/"+ calendar.get(Calendar.SECOND);
       loadsinhnhat();


//        listenerRegistration = firebaseFirestore.collection("user").document( userId ).collection( "Lismember" )
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
//                        if (e != null) {
//                            Log.e("aaa", "Listen failed!", e);
//                            return;
//                        }
//
//                        List<Member> notesList = new ArrayList<>();
//
//                        for (DocumentSnapshot doc : documentSnapshots) {
//                            Member member = new Member(  );
//                            // Member member = doc.toObject(Member.class);
//                            member.setId(doc.getId().toString());
//                            member.setName( doc.get("name").toString() );
//                            member.setNgaysinh( doc.get("ngay sinh").toString() );
//                           // member.setSdt( doc.get("sdt").toString() );
//                            notesList.add(member);
//                        }
//
//                        adapter_sinhnhat = new Adapter_Sinhnhat(notesList, getContext(), firebaseFirestore);
//                        recyclerViewe.setAdapter(adapter_sinhnhat);
//                    }
//                });
        return view;
    }
    private void loadsinhnhat() {
        firebaseFirestore.collection("user").document( userId ).collection( "Lismember")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Member> notesList = new ArrayList<>();
                            Log.e("dsa","vao day");
                            for (DocumentSnapshot doc : task.getResult()) {
                                Member member = new Member(  );
                                // Member member = doc.toObject(Member.class);
                                member.setId(doc.getId().toString());
                                member.setName( doc.get("name").toString() );
                                String ngaysinh=doc.get( "ngay sinh" ).toString();
                                ngaysinh.split( "/" );
                                String arr[] = ngaysinh.split( "/" );
                                for(String w: arr){
                                    Log.e( "day",w );
                                }
                                member.setNgaysinh( arr[0]+"/"+arr[1] );
                                String sinhnhat=arr[0]+"/"+arr[1];
                                if(sinhnhat.equals( ngay )){
                                   MyNotificationManager.getInstance(getActivity()).displayNotification(ngay, "Sinh nhat ban "+doc.get("name").toString(),R.drawable.sinhnhat);
                                }
                               // member.setSdt( doc.get("sdt").toString() );
                                notesList.add(member);
                            }

                            adapter_sinhnhat = new Adapter_Sinhnhat(notesList, getContext(), firebaseFirestore);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            recyclerViewe.setLayoutManager(mLayoutManager);
                            recyclerViewe.setItemAnimator(new DefaultItemAnimator());
                            recyclerViewe.setAdapter(adapter_sinhnhat);
                        } else {
                            Log.d("aaa", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


}
