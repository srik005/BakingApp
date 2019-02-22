package com.srikanth.bakingapp;


import android.appwidget.AppWidgetManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import model.Recipes;
import widget.RecipeWidgetProvider;

public class RecipeListActivity extends AppCompatActivity {
    private Recipes recipe;
    private Gson detailGson;
    private String stepJson, ingredientJson;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        detailGson=new Gson();
        recipe = getIntent().getParcelableExtra("recipe");
        setTitle(recipe.getName());
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        stepJson=getIntent().getStringExtra("steps_key");
        ingredientJson=getIntent().getStringExtra("ingredients_key");
        Bundle b=new Bundle();
        b.putString("steps_key",stepJson);
        b.putString("ingredients_key",ingredientJson);
        RecipeDetailFragment detailFragment=new RecipeDetailFragment();
        detailFragment.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.recipe_detail_layout,detailFragment).commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addToWidget:
                SharedPreferences sharedePreferences = this.getSharedPreferences("BakingPreference", MODE_PRIVATE);
                Recipes recipe=detailGson.fromJson(sharedePreferences.getString("widget_result",null),Recipes.class);
                AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(getApplicationContext());
                Bundle b=new Bundle();
                int appWidgetId=b.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
                RecipeWidgetProvider.updateWidget(this,appWidgetManager,appWidgetId,recipe.getName(),recipe.getIngredients());
                Toast.makeText(this, "Added " + recipe.getName() + " to Widget.", Toast.LENGTH_SHORT).show();
                return  true;
        }
                return super.onOptionsItemSelected(item);
    }
}