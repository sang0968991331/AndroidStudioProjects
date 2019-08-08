package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;



import com.example.myapplication.Adapter.Adapter_Group;
import com.example.myapplication.model.Group;
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

import static android.content.Intent.getIntent;

import static com.example.myapplication.MainActivity.userId;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link f_Group#newInstance} factory method to
 * create an instance of this fragment.
 */
public class f_Group extends Fragment implements CallBackGroup {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private Adapter_Group adapter_group;
    private FirebaseFirestore firebaseFirestore;
    private ListenerRegistration listenerRegistration;
    ArrayList<Group> list;
    private ImageView add_group;
    Fragment_list_Group fragment_list_group;
    Bundle bundle;



    private OnFragmentInteractionListener mListener;

    public f_Group() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment f_Group.
     */
    // TODO: Rename and change types and number of parameters
    public static f_Group newInstance(String param1, String param2) {
        f_Group fragment = new f_Group();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_group, container, false);
        Log.e("iddd", "idddd"+userId);
        firebaseFirestore=FirebaseFirestore.getInstance();
        recyclerView=view.findViewById( R.id.recy_group );
        fragment_list_group = new Fragment_list_Group();
        list=new ArrayList<>(  );
        bundle = new Bundle(  );
        loadlistGroup(userId);
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.replace( R.id.fl_change,new Fragment_list_Group() ).addToBackStack( "asdasd" );
//        fragmentTransaction.commit();
//        add_group=view.findViewById( R.id.add_group );
//        add_group.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent =new Intent( MainActivity.this, Add_group.class );
//                startActivity( intent );
//            }
//        } );

//        listenerRegistration = firebaseFirestore.collection("user").document( userId ).collection( "Group" )
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
//                        if (e != null) {
//                            Log.e("aaa", "Listen failed!", e);
//                            return;
//                        }
//
//                        List<Group> notesList = new ArrayList<>();
//
//                        for (DocumentSnapshot doc : documentSnapshots) {
//                            Group note = doc.toObject( Group.class);
//                            Log.e("group", doc.getData().toString()+"sss");
//                              note.setName_group(doc.getId());
//                            notesList.add(note);
//                        }
//
//                        adapter_group = new Adapter_Group(notesList, getContext(), firebaseFirestore);
//                        recyclerView.setAdapter(adapter_group);
//                    }
//                });
        return view;
        //  text();
        //  text1();
    }
    private void loadlistGroup(String id) {
        firebaseFirestore.collection( "user" ).document( id ).collection( "Group" )
                .get()
                .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                list.add( new Group( documentSnapshot.getId()+"",documentSnapshot.get( "name" )+"" ) );
                            }
                          adapter_group = new Adapter_Group( list, getContext(), firebaseFirestore,f_Group.this );
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager( getContext() );
                            recyclerView.setLayoutManager( mLayoutManager );
                            recyclerView.setItemAnimator( new DefaultItemAnimator() );
                            recyclerView.setAdapter( adapter_group );
                        } else {
                            Log.d( "aaa", "Error getting documents: ", task.getException() );
                        }
                    }
                } );



    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void NameGroup(String name) {
        Log.d( "IDGR",name );
        bundle.putString( "IDGroup",name );
        fragment_list_group.setArguments( bundle );
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace( R.id.fl_change,fragment_list_group ).addToBackStack( "asdasd" );
        fragmentTransaction.commit();
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
