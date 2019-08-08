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
import com.example.myapplication.Adapter.Adapter_ngayle;
import com.example.myapplication.model.Member;
import com.example.myapplication.model.NgayLe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.MainActivity.userId;


public class Fragment_Sukien_le extends Fragment {
    FirebaseFirestore firebaseFirestore;
    Adapter_ngayle adapter_ngayle;
    private RecyclerView recyclerVieww;
    private ListenerRegistration listenerRegistration;
    View view;
    public Fragment_Sukien_le() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_sukien_le,container,false);
        firebaseFirestore=FirebaseFirestore.getInstance();
        recyclerVieww=view.findViewById( R.id.rc_sukien_le);
        firebaseFirestore.collection("user").document( userId ).collection( "Ngayle")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<NgayLe> notesList = new ArrayList<>();

                            for (DocumentSnapshot doc : task.getResult()) {
                                NgayLe ngayLe = new NgayLe(  );
                                // Member member = doc.toObject(Member.class);
                                //ngayLe.setId(doc.getId().toString());
                                ngayLe.setNoidung( doc.get("noidung").toString() );
                                ngayLe.setNgayle( doc.get("ngay").toString() );
                                // member.setSdt( doc.get("sdt").toString() );
                                notesList.add(ngayLe);
                            }

                            adapter_ngayle = new Adapter_ngayle(notesList, getContext(), firebaseFirestore);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            recyclerVieww.setLayoutManager(mLayoutManager);
                            recyclerVieww.setItemAnimator(new DefaultItemAnimator());
                            recyclerVieww.setAdapter(adapter_ngayle);
                        } else {
                            Log.d("aaa", "Error getting documents: ", task.getException());
                        }
                    }
                });

//        listenerRegistration = firebaseFirestore.collection("user").document( userId ).collection( "Ngayle" )
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
//                        if (e != null) {
//                            Log.e("aaa", "Listen failed!", e);
//                            return;
//                        }
//
//                        List<NgayLe> notesList = new ArrayList<>();
//
//                        for (DocumentSnapshot doc : documentSnapshots) {
//                            NgayLe ngayLe = new NgayLe(  );
//                            // Member member = doc.toObject(Member.class);
//                            //ngayLe.setId(doc.getId().toString());
//                            ngayLe.setNoidung( doc.get("noidung").toString() );
//                            ngayLe.setNgayle( doc.get("ngay").toString() );
//                            // member.setSdt( doc.get("sdt").toString() );
//                            notesList.add(ngayLe);
//
//                        }
//
//                        adapter_ngayle = new Adapter_ngayle(notesList, getContext(), firebaseFirestore);
//                        recyclerVieww.setAdapter(adapter_ngayle);
//                    }
//                });

        return view;
    }


}
