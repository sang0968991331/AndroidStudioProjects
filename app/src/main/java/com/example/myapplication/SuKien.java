package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;




/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SuKien#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuKien extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    Fragment_Sukien_sinhnhat fragment_sukien_sinhnhat=new Fragment_Sukien_sinhnhat();
    Fragment_Sukien_le fragment_sukien_le= new Fragment_Sukien_le();
    private OnFragmentInteractionListener mListener;

    public SuKien() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SuKien.
     */
    // TODO: Rename and change types and number of parameters
    public static SuKien newInstance(String param1, String param2) {
        SuKien fragment = new SuKien();
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
        Log.e("sasa","vao sukien");
        View view= inflater.inflate(R.layout.fragment_su_kien, container, false);
        tabLayout= view.findViewById(R.id.tabLayout) ;
        appBarLayout=view.findViewById(R.id.appbar);
        viewPager=view.findViewById(R.id.viewpager);
        ViewPagerAdapter adapter= new ViewPagerAdapter(getChildFragmentManager());
        adapter.AddFragment(fragment_sukien_sinhnhat, "Sinh nhật");
        adapter.AddFragment(fragment_sukien_le,"Ngày lễ");
      //  Log.e( "fafa","vaoday" );
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        Log.e( "fafa","vaoday" );
        return  view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
