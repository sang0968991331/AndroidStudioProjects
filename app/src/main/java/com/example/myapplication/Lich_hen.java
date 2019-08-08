package com.example.myapplication;

import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;


import androidx.viewpager.widget.ViewPager;



public class Lich_hen extends AppCompatActivity {



        private TabLayout tabLayout;
        private AppBarLayout appBarLayout;
        private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_hen);
        tabLayout= findViewById(R.id.tabLayout) ;
        appBarLayout=findViewById(R.id.appbar);
        viewPager=findViewById(R.id.viewpager);

        ViewPagerAdapter adapter= new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new Fragment_Sukien_sinhnhat(), "Cá nhân");
        adapter.AddFragment(new Fragment_Sukien_le(),"Nhóm");


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        //===============================

    }
    }
