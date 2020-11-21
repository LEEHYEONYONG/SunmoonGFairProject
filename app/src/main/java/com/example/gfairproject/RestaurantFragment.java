package com.example.gfairproject;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class RestaurantFragment extends Fragment {
    Spinner spinSearchSiDo;
    Spinner spinSearchSiGunGu;
    Button btnSearchRestaurant;
    FloatingActionButton btnRestaurantMore;

    RecyclerView listRestaurant;
    //RestaurantAdapter restaurantAdapter = new RestaurantAdapter();
    RestaurantAdapter restaurantAdapter = new RestaurantAdapter();

    //ArrayList<HashMap<String,String>> arrayRestaurant = new ArrayList<>();
    ArrayList<HashMap<String,String>> arrayRestaurant = new ArrayList<>();
    String result;

    String url = "http://211.237.50.150:7080/openapi/"+ApiKey.ApiFoodKey+"/json/Grid_20200713000000000605_1/1/";
    int page = 10;
    String strSelectedSido;//선택한 시도
    String strSelectedSiGunGu;//선택한 시군구
    int check=1;//경우선택

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_restaurant,container,false);
        spinSearchSiDo = view.findViewById(R.id.spinSearchSiDo);
        spinSearchSiGunGu = view.findViewById(R.id.spinSearchSiGunGu);
        btnSearchRestaurant = view.findViewById(R.id.btnSearchRestaurant);
        btnRestaurantMore = view.findViewById(R.id.btnRestaurantMore);

        listRestaurant = view.findViewById(R.id.listRestaurant);
        listRestaurant.setLayoutManager(new LinearLayoutManager(getActivity()));
        listRestaurant.setAdapter(restaurantAdapter);



        final ArrayAdapter adapterSido = ArrayAdapter.createFromResource(getContext(),R.array.SiDo,R.layout.support_simple_spinner_dropdown_item);
        adapterSido.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinSearchSiDo.setAdapter(adapterSido);

        final ArrayAdapter adapterSiGunGu = ArrayAdapter.createFromResource(getContext(),R.array.SiGunGu,R.layout.support_simple_spinner_dropdown_item);
        adapterSido.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinSearchSiGunGu.setAdapter(adapterSiGunGu);

        spinSearchSiDo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getContext(),adapterSido.getItem(position).toString(),Toast.LENGTH_SHORT).show();
                String strSidoCheck = adapterSido.getItem(position).toString();
                CheckSido(strSidoCheck);//시도에 따라 시군구선택함.

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSearchRestaurant.setOnClickListener(new View.OnClickListener() {//안심식당검색버튼누를시
            @Override
            public void onClick(View v) {
                arrayRestaurant.clear();
                page=10;
                strSelectedSido = spinSearchSiDo.getSelectedItem().toString();//시도확인
                strSelectedSiGunGu = spinSearchSiGunGu.getSelectedItem().toString();//시군구확인

                if(strSelectedSido.equals("전체") && strSelectedSiGunGu.equals("전체")){//두개다 전체일때
                    check=1;
                }else if(strSelectedSiGunGu.equals("전체")){//도전체를 검색하고 싶을때
                    check=2;
                }else{//시군구까지 검색하고 싶을때
                    check=3;
                }
                new APIThread().execute();
                new Handler().postDelayed(new Runnable() {//position 문제가 발생해 핸들러를 사용함.
                    @Override
                    public void run() {
                        listRestaurant.scrollToPosition(page-10);
                    }
                },200);
            }
        });

        btnRestaurantMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(page>=1000){
                    Toast.makeText(getContext(),"1000건 이상을 검색할 수 없습니다.",Toast.LENGTH_SHORT).show();
                }else{
                    page = page + 10;
                    new APIThread().execute();
                    //listRestaurant.scrollToPosition(page-1);
                    new Handler().postDelayed(new Runnable() {//position 문제가 발생해 핸들러를 사용함.
                        @Override
                        public void run() {
                            listRestaurant.scrollToPosition(page-1);
                        }
                    },200);
                }
            }
        });

        new APIThread().execute();


        return view;
    }

    public void CheckSido(String strSidoCheck){//시도마다 시군구 선택할 환경제공하는 메소드

        switch(strSidoCheck){
            case "전체" :
                final ArrayAdapter adapterSiGunGu = ArrayAdapter.createFromResource(getContext(),R.array.SiGunGu,R.layout.support_simple_spinner_dropdown_item);
                spinSearchSiGunGu.setAdapter(adapterSiGunGu);
                break;
            case "서울특별시" :
                final ArrayAdapter adapterSeoul = ArrayAdapter.createFromResource(getContext(),R.array.Seoul,R.layout.support_simple_spinner_dropdown_item);
                spinSearchSiGunGu.setAdapter(adapterSeoul);
                break;
            case "부산광역시" :
                final ArrayAdapter adapterBusan = ArrayAdapter.createFromResource(getContext(),R.array.Busan,R.layout.support_simple_spinner_dropdown_item);
                spinSearchSiGunGu.setAdapter(adapterBusan);
                break;
            case "대구광역시" :
                final ArrayAdapter adapterDaegu = ArrayAdapter.createFromResource(getContext(),R.array.Daegu,R.layout.support_simple_spinner_dropdown_item);
                spinSearchSiGunGu.setAdapter(adapterDaegu);
                break;
            case "인천광역시" :
                final ArrayAdapter adapterInCheon = ArrayAdapter.createFromResource(getContext(),R.array.Incheon,R.layout.support_simple_spinner_dropdown_item);
                spinSearchSiGunGu.setAdapter(adapterInCheon);
                break;
            case "광주광역시" :
                final ArrayAdapter adapterGwangju = ArrayAdapter.createFromResource(getContext(),R.array.GwangJu,R.layout.support_simple_spinner_dropdown_item);
                spinSearchSiGunGu.setAdapter(adapterGwangju);
                break;
            case "대전광역시" :
                final ArrayAdapter adapterDaeJeon = ArrayAdapter.createFromResource(getContext(),R.array.DaeJeon,R.layout.support_simple_spinner_dropdown_item);
                spinSearchSiGunGu.setAdapter(adapterDaeJeon);
                break;
            case "울산광역시" :
                final ArrayAdapter adapterUlsan = ArrayAdapter.createFromResource(getContext(),R.array.Ulsan,R.layout.support_simple_spinner_dropdown_item);
                spinSearchSiGunGu.setAdapter(adapterUlsan);
                break;
            case "세종특별자치시" :
                final ArrayAdapter adapterSejong = ArrayAdapter.createFromResource(getContext(),R.array.SeJong,R.layout.support_simple_spinner_dropdown_item);
                spinSearchSiGunGu.setAdapter(adapterSejong);
                break;
            case "경기도" :
                final ArrayAdapter adapterGyeongGi = ArrayAdapter.createFromResource(getContext(),R.array.GyeongGi,R.layout.support_simple_spinner_dropdown_item);
                spinSearchSiGunGu.setAdapter(adapterGyeongGi);
                break;
            case "강원도" :
                final ArrayAdapter adapterGangWon = ArrayAdapter.createFromResource(getContext(),R.array.GangWon,R.layout.support_simple_spinner_dropdown_item);
                spinSearchSiGunGu.setAdapter(adapterGangWon);
                break;
            case "충청북도" :
                final ArrayAdapter adapterChungcheongbukdo = ArrayAdapter.createFromResource(getContext(),R.array.Chungcheongbukdo,R.layout.support_simple_spinner_dropdown_item);
                spinSearchSiGunGu.setAdapter(adapterChungcheongbukdo);
                break;
            case "충청남도" :
                final ArrayAdapter adapterChungcheongnamdo = ArrayAdapter.createFromResource(getContext(),R.array.Chungcheongnamdo,R.layout.support_simple_spinner_dropdown_item);
                spinSearchSiGunGu.setAdapter(adapterChungcheongnamdo);
                break;
            case "전라북도" :
                final ArrayAdapter adapterJeollabukdo = ArrayAdapter.createFromResource(getContext(),R.array.Jeollabukdo,R.layout.support_simple_spinner_dropdown_item);
                spinSearchSiGunGu.setAdapter(adapterJeollabukdo);
                break;
            case "전라남도" :
                final ArrayAdapter adapterJeollanamdo = ArrayAdapter.createFromResource(getContext(),R.array.Jeollanamdo,R.layout.support_simple_spinner_dropdown_item);
                spinSearchSiGunGu.setAdapter(adapterJeollanamdo);
                break;
            case "경상북도" :
                final ArrayAdapter adapterGyeonsangbukdo = ArrayAdapter.createFromResource(getContext(),R.array.Gyeonsangbukdo,R.layout.support_simple_spinner_dropdown_item);
                spinSearchSiGunGu.setAdapter(adapterGyeonsangbukdo);
                break;
            case "경상남도" :
                final ArrayAdapter adapterGyeongsangnamdo = ArrayAdapter.createFromResource(getContext(),R.array.Gyeongsangnamdo,R.layout.support_simple_spinner_dropdown_item);
                spinSearchSiGunGu.setAdapter(adapterGyeongsangnamdo);
                break;
            case "제주특별자치도" :
                final ArrayAdapter adapterJejudo = ArrayAdapter.createFromResource(getContext(),R.array.Jejudo,R.layout.support_simple_spinner_dropdown_item);
                spinSearchSiGunGu.setAdapter(adapterJejudo);
                break;
        }

    }

    class APIThread extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            if(check==1){//두개다 전체일때
                result = RestaurantApi.connect(url + page);
            }else if(check==2){//도전체를 검색하고 싶을때
                result = RestaurantApi.connect(url + page + "?RELAX_SI_NM="+strSelectedSido);
            }else if(check==3){//시군구까지 검색하고 싶을때
                result = RestaurantApi.connect(url + page + "?RELAX_SI_NM="+strSelectedSido+"&RELAX_SIDO_NM="+strSelectedSiGunGu);
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            parser(s);
            System.out.println("음식점데이터갯수 : "+ arrayRestaurant.size());
            //restaurantAdapter = new RestaurantAdapter();
            restaurantAdapter.notifyDataSetChanged();
            //listRestaurant.scrollToPosition(page-1);
            //listRestaurant.scrollToPosition(page-1);


        }
    }

    public void parser(String result){
        arrayRestaurant = new ArrayList<>();
        try{
            System.out.println("결과는 "+ result);
            JSONObject jsonObject = new JSONObject(result);
            //System.out.println("????결과는" + jsonObject);
            JSONObject jsonObject1 = jsonObject.getJSONObject("Grid_20200713000000000605_1");
            JSONArray jArray = jsonObject1.getJSONArray("row");
            //JSONArray jArray2 = jArray.getJSONArray(5);
            //JSONArray jArray2 = jsonObject1.getJSONArray("row");
            for(int i=0;i<jArray.length();i++){
                JSONObject obj = jArray.getJSONObject(i);

                HashMap<String,String> map = new HashMap<>();
                map.put("RELAX_SEQ",obj.getString("RELAX_SEQ"));//안심식당SEQ
                map.put("RELAX_ZIPCODE",obj.getString("RELAX_ZIPCODE"));//시도코드
                map.put("RELAX_SI_NM",obj.getString("RELAX_SI_NM"));//시도명
                map.put("RELAX_SIDO_NM",obj.getString("RELAX_SIDO_NM"));//시군구명
                map.put("RELAX_RSTRNT_NM",obj.getString("RELAX_RSTRNT_NM"));//사업자명
                map.put("RELAX_RSTRNT_REPRESENT",obj.getString("RELAX_RSTRNT_REPRESENT"));//대표자명
                //map.put("RELAX_RSTRNT_REG_DT",obj.getString("RELAX_RSTRNT_REG_DT"));//안심식당지정일
                map.put("RELAX_ADD1",obj.getString("RELAX_ADD1"));//주소1
                map.put("RELAX_ADD2",obj.getString("RELAX_ADD2"));//주소2
                map.put("RELAX_GUBUN",obj.getString("RELAX_GUBUN"));//업종
                map.put("RELAX_GUBUN_DETAIL",obj.getString("RELAX_GUBUN_DETAIL"));//업종상세
                map.put("RELAX_RSTRNT_TEL",obj.getString("RELAX_RSTRNT_TEL"));//전화번호
                map.put("RELAX_RSTRNT_ETC",obj.getString("RELAX_RSTRNT_ETC"));//비고(웹사이트등)
                map.put("RELAX_USE_YN",obj.getString("RELAX_USE_YN"));//선정여부
                //map.put("RELAX_RSTRNT_CNCL_DT",obj.getString("RELAX_RSTRNT_CNCL_DT"));//안심식당취소일
                //map.put("RELAX_UPDATE_DT",obj.getString("RELAX_UPDATE_DT"));//수정일

                arrayRestaurant.add(map);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder>{

        @NonNull
        @Override
        public RestaurantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.item_restaurant,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RestaurantAdapter.ViewHolder holder, int position) {
            HashMap<String,String> map = arrayRestaurant.get(position);
            holder.txtRestaurantName.setText(map.get("RELAX_RSTRNT_NM"));
            holder.txtRestaurantAddress.setText(map.get("RELAX_ADD1"));
            holder.txtRestaurantPhoneNumber.setText(map.get("RELAX_RSTRNT_TEL"));
            holder.linearRestaurant.setOnClickListener(new View.OnClickListener() {//각 아이템을 클릭했을때 안심식당 상세정보로 이동.
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"확인",Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return arrayRestaurant.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView txtRestaurantName, txtRestaurantPhoneNumber, txtRestaurantAddress;
            LinearLayout linearRestaurant;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtRestaurantName = itemView.findViewById(R.id.txtRestaurantName);
                txtRestaurantPhoneNumber = itemView.findViewById(R.id.txtRestaurantPhoneNumber);
                txtRestaurantAddress = itemView.findViewById(R.id.txtRestaurantAddress);
                linearRestaurant = itemView.findViewById(R.id.linearRestaurant);

            }
        }
    }

}