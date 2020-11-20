package com.example.gfairproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ImportantActivity extends AppCompatActivity {

    ViewPager pager;
    TabLayout tab;
    PageAdapter ad;
    ArrayList<Fragment> array = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important);

        getSupportActionBar().setTitle("안심식당정보");

        tab = findViewById(R.id.tab);
        pager = findViewById(R.id.pager);

        tab.addTab(tab.newTab().setText("안심식당검색"));
        tab.getTabAt(0).setIcon(R.drawable.ic_baseline_restaurant_24);
        tab.addTab(tab.newTab().setText("안심식당뉴스"));
        tab.getTabAt(1).setIcon(R.drawable.ic_baseline_article_24);
        tab.addTab(tab.newTab().setText("안심식당정보"));
        tab.getTabAt(2).setIcon(R.drawable.ic_baseline_info_24);
        tab.getTabAt(0).getIcon().setColorFilter(getResources().getColor(android.R.color.white),PorterDuff.Mode.SRC_IN);

        array.add(new RestaurantFragment());
        array.add(new NewsFragment());
        array.add(new InfoFragment());

        ad = new PageAdapter(getSupportFragmentManager());
        pager.setAdapter(ad);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
                tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.BLACK,PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });





    }

    class PageAdapter extends FragmentStatePagerAdapter{

        public PageAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return array.get(position);
        }

        @Override
        public int getCount() {
            return array.size();
        }
    }
}