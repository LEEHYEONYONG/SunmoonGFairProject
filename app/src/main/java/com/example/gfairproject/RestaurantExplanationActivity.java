package com.example.gfairproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class RestaurantExplanationActivity extends AppCompatActivity {
    ArrayList<RestaurantExplanation> restaurantExplanationArray = new ArrayList<>();
    Intent intent;
    RestaurantExplanationAdapter restaurantExplanationAdapter;
    ListView listRestaurantExplanation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_explanation);
        getSupportActionBar().setTitle("음식점 상세정보");

        listRestaurantExplanation = findViewById(R.id.listRestaurantExplanation);

        intent = getIntent();
        datainput(intent);//다른 액티비티에서 데이터 받아옴.

        restaurantExplanationAdapter = new RestaurantExplanationAdapter();
        listRestaurantExplanation.setAdapter(restaurantExplanationAdapter);
    }

    public void datainput(Intent intent){//다른 액티비티에서 데이터 받아옴.
        intent = getIntent();

        restaurantExplanationArray.clear();

        RestaurantExplanation restaurantExplanation = new RestaurantExplanation("* 안심식당SEQ",intent.getStringExtra("RELAX_SEQ"));//안심식당SEQ
        restaurantExplanationArray.add(restaurantExplanation);
        /*
        restaurantExplanation = new RestaurantExplanation("* 시도코드",intent.getStringExtra("RELAX_ZIPCODE"));//시도코드
        restaurantExplanationArray.add(restaurantExplanation);
        restaurantExplanation = new RestaurantExplanation("* 시도명",intent.getStringExtra("RELAX_SI_NM"));//시도명
        restaurantExplanationArray.add(restaurantExplanation);
        restaurantExplanation = new RestaurantExplanation("* 시군구명",intent.getStringExtra("RELAX_SIDO_NM"));//시군구명
        restaurantExplanationArray.add(restaurantExplanation);
        */
        restaurantExplanation = new RestaurantExplanation("* 사업자명",intent.getStringExtra("RELAX_RSTRNT_NM"));//사업자명
        restaurantExplanationArray.add(restaurantExplanation);
        restaurantExplanation = new RestaurantExplanation("* 대표자명",intent.getStringExtra("RELAX_RSTRNT_REPRESENT"));//대표자명
        restaurantExplanationArray.add(restaurantExplanation);
        restaurantExplanation = new RestaurantExplanation("* 주소",intent.getStringExtra("RELAX_ADD1") +" "+intent.getStringExtra("RELAX_ADD2"));//주소1
        restaurantExplanationArray.add(restaurantExplanation);
        /*
        restaurantExplanation = new RestaurantExplanation("* 기타주소",intent.getStringExtra("RELAX_ADD2"));//주소2
        restaurantExplanationArray.add(restaurantExplanation);
         */
        restaurantExplanation = new RestaurantExplanation("* 업종",intent.getStringExtra("RELAX_GUBUN"));//업종
        restaurantExplanationArray.add(restaurantExplanation);
        restaurantExplanation = new RestaurantExplanation("* 업종상세",intent.getStringExtra("RELAX_GUBUN_DETAIL"));//업종상세
        restaurantExplanationArray.add(restaurantExplanation);
        restaurantExplanation = new RestaurantExplanation("* 전화번호",intent.getStringExtra("RELAX_RSTRNT_TEL"));//전화번호
        restaurantExplanationArray.add(restaurantExplanation);
        restaurantExplanation = new RestaurantExplanation("* 비고",intent.getStringExtra("RELAX_RSTRNT_ETC"));//비고
        restaurantExplanationArray.add(restaurantExplanation);
        restaurantExplanation = new RestaurantExplanation("* 안심식당선정여부",intent.getStringExtra("RELAX_USE_YN"));//안심식당선정여부
        restaurantExplanationArray.add(restaurantExplanation);
        /*

        restaurantExplanationArray.add(intent.getStringExtra("RELAX_SEQ"));
        restaurantExplanationArray.add(intent.getStringExtra("RELAX_ZIPCODE"));
        restaurantExplanationArray.add(intent.getStringExtra("RELAX_SI_NM"));
        restaurantExplanationArray.add(intent.getStringExtra("RELAX_SIDO_NM"));
        restaurantExplanationArray.add(intent.getStringExtra("RELAX_RSTRNT_NM"));
        restaurantExplanationArray.add(intent.getStringExtra("RELAX_RSTRNT_REPRESENT"));
        restaurantExplanationArray.add(intent.getStringExtra("RELAX_ADD1"));
        restaurantExplanationArray.add(intent.getStringExtra("RELAX_ADD2"));
        restaurantExplanationArray.add(intent.getStringExtra("RELAX_GUBUN"));
        restaurantExplanationArray.add(intent.getStringExtra("RELAX_GUBUN_DETAIL"));
        restaurantExplanationArray.add(intent.getStringExtra("RELAX_RSTRNT_TEL"));
        */
    }

    class RestaurantExplanationAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return restaurantExplanationArray.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.item_restaurant_explanation,null);
            TextView txtRestaurantExplanationTitle = convertView.findViewById(R.id.txtRestaurantExplanationTitle);
            TextView txtRestaurantExplanationDescription = convertView.findViewById(R.id.txtRestaurantExplanationDescription);
            ImageView imgRestaurantExplanation = convertView.findViewById(R.id.imgRestaurantExplanation);

            if(position==3){//지도
                imgRestaurantExplanation.setImageResource(R.drawable.ic_baseline_map_24);
            }else if(position==6){//지도
                imgRestaurantExplanation.setImageResource(R.drawable.ic_baseline_local_phone_24);
            }

            RestaurantExplanation restaurantExplanation = restaurantExplanationArray.get(position);
            txtRestaurantExplanationTitle.setText(restaurantExplanation.getTitle());
            txtRestaurantExplanationDescription.setText(restaurantExplanation.getDescription());
            return convertView;
        }
    }
}