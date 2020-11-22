package com.example.gfairproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class InfoFragment extends Fragment {
    ListView listInfo;
    ArrayList<String> strInfoArray = new ArrayList<String>();
    //String[] strInfoArray= {"안심식당이란?","안심식당앱정보"};
    InfoAdapter infoAdapter= new InfoAdapter();
    Intent intent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        strInfoArray.clear();
        strInfoArray.add("안심식당이란?");
        strInfoArray.add("안심식당앱정보");

        listInfo = view.findViewById(R.id.listInfo);
        listInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    //Toast.makeText(getContext(),"안심식당이란?",Toast.LENGTH_SHORT).show();
                    intent = new Intent(getContext(),ExplanationActivity.class);
                    startActivity(intent);

                }else if(position==1){
                    //Toast.makeText(getContext(),"안심식당앱정보",Toast.LENGTH_SHORT).show();
                    intent = new Intent(getContext(),AppInformationActivity.class);
                    startActivity(intent);
                }
            }
        });


        //infoAdapter = new InfoAdapter();
        listInfo.setAdapter(infoAdapter);
        return view;
    }

    class InfoAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return strInfoArray.size();
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
            convertView = getLayoutInflater().inflate(R.layout.item_info,null);
            TextView txtInfoName;
            txtInfoName = convertView.findViewById(R.id.txtInfoName);
            txtInfoName.setText(strInfoArray.get(position));
            return convertView;
        }
    }

}