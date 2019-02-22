package com.srikanth.bakingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import IdlingResources.SimpleIdlingResource;
import adapter.RecycleAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import model.Recipes;

import java.util.ArrayList;

import androidx.annotation.VisibleForTesting;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingResource;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit.Bakeinterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecycleAdapter recycleAdapter;
    private RecyclerView recyclerView;
    private boolean mTwoPane;
    @Nullable
    private SimpleIdlingResource simpleIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        if (simpleIdlingResource != null) {
            simpleIdlingResource.setIdleState(true);
        }

       /* Toolbar toolbar = null;
        setSupportActionBar(toolbar);*/
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("Baking Time");
        }
        getIdlingResource();

        String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(logging);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build()).build();
        Bakeinterface bakeinterface = retrofit.create(Bakeinterface.class);

        LinearLayoutManager lm = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(lm);

        if (checkConnection(this)) {
            Call<ArrayList<Recipes>> mIngredients = bakeinterface.getRecipes();
            mIngredients.enqueue(new Callback<ArrayList<Recipes>>() {
                @Override
                public void onResponse(@NonNull Call<ArrayList<Recipes>> call, @NonNull Response<ArrayList<Recipes>> response) {
                    if (response.body() != null) {
                        Log.d("Response", "" + response.body());
                        ArrayList<Recipes> mList = response.body();
                        recycleAdapter = new RecycleAdapter(mList, MainActivity.this);
                        Bundle recipeData = new Bundle();
                        recipeData.putParcelableArrayList("RECIPES", mList);
                        //recycleAdapter.setRecipeData(mList, MainActivity.this);
                        recyclerView.setAdapter(recycleAdapter);
                        if (simpleIdlingResource != null) {
                            simpleIdlingResource.setIdleState(true);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Recipes>> call, Throwable t) {

                }
            });
        }
    }

    @NonNull
    @VisibleForTesting
    private IdlingResource getIdlingResource() {
        if (simpleIdlingResource == null) {
            simpleIdlingResource = new SimpleIdlingResource();
        }
        return simpleIdlingResource;

    }

    private boolean checkConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}