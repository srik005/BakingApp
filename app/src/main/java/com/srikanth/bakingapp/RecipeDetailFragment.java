package com.srikanth.bakingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import adapter.RecipeDetailAdapter;
import adapter.RecycleAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import model.Ingredient;
import model.Step;

public class RecipeDetailFragment extends Fragment {
    @BindView(R.id.ingredients_rv)
    RecyclerView ingredients_rv;

    @BindView(R.id.steps_rv)
    RecyclerView steps_rv;

    List<Ingredient> ingredients;
    RecycleAdapter ingredientAdapter;

    ArrayList<Step> steps;
    RecipeDetailAdapter stepAdapter;
    public RecipeDetailFragment() {
    }

    String s;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_recipe_detail, parent, false);
        // recipesArrayList=new ArrayList<>();

        return rootView;
    }
}
