package com.example.gfairproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class NewsFragment extends Fragment {
    ArrayList<HashMap<String,String>> arrayNews = new ArrayList<>();
    int display=5;
    String query="안심식당";
    String url="https://openapi.naver.com/v1/search/news.json";
    String sort="sim";//유사도순 : sim(유사도순), date(날짜순)

    RecyclerView listNews;
    FloatingActionButton btnNewsMore;
    NewsAdapter newsAdapter = new NewsAdapter();
    Button btnSearchNews;

    ProgressBar progressBarNews;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        listNews = view.findViewById(R.id.listNews);
        listNews.setLayoutManager(new LinearLayoutManager(getActivity()));
        listNews.setAdapter(newsAdapter);

        progressBarNews =view.findViewById(R.id.progressBarNews);

        btnSearchNews = view.findViewById(R.id.btnSearchNews);
        btnSearchNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display=5;
                new APIThreadNews().execute();
                new APIThreadNewsSub().execute();
                /*
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listNews.scrollToPosition(0);
                    }
                },1000);
                */
            }
        });


        btnNewsMore = view.findViewById(R.id.btnNewsMore);
        btnNewsMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display>=100){
                    Toast.makeText(getContext(),"100건 이상을 검색할 수 없습니다.",Toast.LENGTH_SHORT).show();
                }else{
                    display = display + 5;
                    new APIThreadNews().execute();
                    new APIThreadNewsSub2().execute();
                    /*
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listNews.scrollToPosition(display-1);
                        }
                    },1000);
                     */


                }

            }
        });


        new APIThreadNews().execute();
        return view;
    }

    //서브스레드1
    class APIThreadNews extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            progressBarNews.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = NaverApi.connect(display,query,url,sort);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            parser(s);
            System.out.println("음식점데이터갯수 : "+ arrayNews.size());
            newsAdapter.notifyDataSetChanged();
            progressBarNews.setVisibility(View.INVISIBLE);
        }
    }

    //서브스레드2
    class APIThreadNewsSub extends AsyncTask<String,String,String>{


        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            listNews.scrollToPosition(0);
        }
    }

    //서브스레드3
    class APIThreadNewsSub2 extends AsyncTask<String,String,String>{
        

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            listNews.scrollToPosition(display-1);
        }
    }


    public void parser(String result){
        arrayNews = new ArrayList<>();
        System.out.println("뉴스결과: "+ result);
        try{
            JSONArray jsonArray = new JSONObject(result).getJSONArray("items");
            for(int i=0; i<jsonArray.length();i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                String title = obj.getString("title");//타이틀(제목)
                String link = obj.getString("link");//링크
                String description = obj.getString("description");//설명(기사내용)

                HashMap<String,String> map = new HashMap<>();
                map.put("title",title);
                map.put("link",link);
                map.put("description",description);
                arrayNews.add(map);
            }
        }catch (JSONException e){

        }

    }


    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

        @NonNull
        @Override
        public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.item_news,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
            HashMap<String,String> map = arrayNews.get(position);
            holder.txtNewsTitle.setText(Html.fromHtml(map.get("title")));
            holder.txtNewsDescription.setText(Html.fromHtml(map.get("description")));
            holder.linearNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), NewsWebActivity.class);
                    intent.putExtra("link",map.get("link"));
                    intent.putExtra("title",map.get("title"));
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return arrayNews.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView txtNewsTitle,txtNewsDescription;
            LinearLayout linearNews;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtNewsTitle = itemView.findViewById(R.id.txtNewsTitle);
                txtNewsDescription = itemView.findViewById(R.id.txtNewsDescription);
                linearNews = itemView.findViewById(R.id.linearNews);
            }
        }
    }
}