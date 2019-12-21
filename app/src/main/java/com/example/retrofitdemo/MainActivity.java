package com.example.retrofitdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<Hero>> call = api.getHeroes();
        call.enqueue(new Callback<List<Hero>>() {
            @Override
            public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {
                List<Hero> heroList = response.body();
                String[] heroes = new String[heroList.size()];

                //looping through all the heroes and inserting the names inside the string array
                for (int i = 0; i < heroList.size(); i++) {
                    heroes[i] = heroList.get(i).getName();
                    Log.d("NAMES", heroList.get(i).getBio());
                }

                TextView textView = findViewById(R.id.tv);
                textView.setText(heroList.get(0).getName());

            }

            @Override
            public void onFailure(Call<List<Hero>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(PagesApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PagesApi pagesApi = retrofit1.create(PagesApi.class);
        Call<Page> call1 = pagesApi.getPages();
        call1.enqueue(new Callback<Page>() {
            @Override
            public void onResponse(Call<Page> call, Response<Page> response) {
                Page page = response.body();
                TextView textView = findViewById(R.id.tv);
                textView.setText(page.getTotal());
                List<Data> listData = page.getData();
                textView.setText(listData.get(0).getEmail());
                for(int i = 0;  i < listData.size(); i++) {
                    Log.d("DATA", listData.get(i).getId());
                    Log.d("DATA", listData.get(i).getEmail());
                    Log.d("DATA", listData.get(i).getFirst_name());
                    Log.d("DATA", listData.get(i).getLast_name());
                    Log.d("DATA", listData.get(i).getAvatar());
                }
            }

            @Override
            public void onFailure(Call<Page> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
