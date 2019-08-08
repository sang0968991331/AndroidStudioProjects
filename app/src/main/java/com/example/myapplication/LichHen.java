package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;

import com.example.myapplication.Adapter.Adapter_lichhen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import static com.example.myapplication.MainActivity.userId;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LichHen.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LichHen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LichHen extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private Adapter_lichhen adapter_lichhen;
    private FirebaseFirestore firebaseFirestore;
    private ListenerRegistration listenerRegistration;
    ArrayList<com.example.myapplication.model.LichHen> list;
    FloatingActionButton btn_addlichhen;
    private ImageView add_group;
    //Fragment_list_Group fragment_list_group;

    private OnFragmentInteractionListener mListener;
    private CalendarView calendarView;
    public LichHen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LichHen.
     */
    // TODO: Rename and change types and number of parameters
    public static LichHen newInstance(String param1, String param2) {
        LichHen fragment = new LichHen();
        Bundle args = new Bundle();
        args.putString( ARG_PARAM1, param1 );
        args.putString( ARG_PARAM2, param2 );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            mParam1 = getArguments().getString( ARG_PARAM1 );
            mParam2 = getArguments().getString( ARG_PARAM2 );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate( R.layout.fragment_lich_hen, container, false );
        firebaseFirestore= FirebaseFirestore.getInstance();
        recyclerView=view.findViewById( R.id.list_lichhen );
        btn_addlichhen=view.findViewById( R.id.add_lichhen );
//        final TextView tv_ngay=view.findViewById( R.id.tv_cate );
//        calendarView=view.findViewById( R.id.calendar );
//        calendarView.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//                String date =year+"/" +month+"/"+dayOfMonth;
//                tv_ngay.setText( date );
//                Log.e("date",date+" ");
//            }
//        } );
    loadLichhen();
        firebaseFirestore.collection("user").document( userId ).collection( "Lichhen").addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e("aaa", "Listen failed!", e);
                    return;
                }
                List<com.example.myapplication.model.LichHen> notesList = new ArrayList<>();

                for (DocumentSnapshot doc : queryDocumentSnapshots) {
                    com.example.myapplication.model.LichHen lichHen = new com.example.myapplication.model.LichHen(  );
                    // Member member = doc.toObject(Member.class);
                    //  lichHen.setId(doc.getId().toString());
                    lichHen.setNoidung( doc.get("noidung").toString() );
                    lichHen.setNgayhen( doc.get("ngay").toString() );
                    lichHen.setGiohen( doc.get("gio").toString() );
                    notesList.add(lichHen);
                }
                adapter_lichhen = new Adapter_lichhen(notesList, getContext(), firebaseFirestore);
                recyclerView.setAdapter(adapter_lichhen);
            }
        } );


        return view;
    }
    public void loadLichhen(){
        firebaseFirestore.collection("user").document( userId ).collection( "Lichhen")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<com.example.myapplication.model.LichHen> notesList = new ArrayList<>();

                            for (DocumentSnapshot doc : task.getResult()) {
                                com.example.myapplication.model.LichHen lichHen = new com.example.myapplication.model.LichHen(  );
                                // Member member = doc.toObject(Member.class);
                                //  lichHen.setId(doc.getId().toString());
                                lichHen.setNoidung( doc.get("noidung").toString() );
                                lichHen.setNgayhen( doc.get("ngay").toString() );
                                lichHen.setGiohen( doc.get("gio").toString() );
                                notesList.add(lichHen);
                            }

                            adapter_lichhen = new Adapter_lichhen(notesList, getContext(), firebaseFirestore);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(adapter_lichhen);
                        } else {
                            Log.d("aaa", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction( uri );
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach( context );

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
