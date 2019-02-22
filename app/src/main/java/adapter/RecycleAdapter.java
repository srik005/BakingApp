package adapter;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.srikanth.bakingapp.R;
import com.srikanth.bakingapp.RecipeListActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import model.Ingredient;
import model.Recipes;
import model.Step;


public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private Context mContext;
    private List<Ingredient> ingredientList;
    private List<Step> stepList;
    private Gson gson;
    private SharedPreferences sharedPreferences;
    public RecycleAdapter(List<Recipes> recipeArrList, Context context) {
        this.recipeList = recipeArrList;
        this.mContext = context;
        sharedPreferences = context.getSharedPreferences("BakingPreference",
                Context.MODE_PRIVATE);

    }

    List<Recipes> recipeList;

    public void setRecipeData(List<Recipes> recipeArrList, Context context) {
        recipeList = recipeArrList;
        mContext = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_recycle_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipes recipe = recipeList.get(position);
        holder.recipeName.setText(recipe.getName());
    }

    @Override
    public int getItemCount() {
        Log.d("getSize", "" + recipeList.size());
        return recipeList != null ? recipeList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtRecipeName)
        TextView recipeName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.txtRecipeName)
        public void onClick(View view) {
            ingredientList = recipeList.get(getAdapterPosition()).getIngredients();
            stepList = recipeList.get(getAdapterPosition()).getSteps();
            Intent recipeDetail = new Intent(view.getContext(), RecipeListActivity.class);
            gson = new Gson();
            recipeDetail.putExtra("recipe", recipeList.get(getAdapterPosition()));
            String ingredientJson = gson.toJson(ingredientList);
            String stepJson = gson.toJson(stepList);
            recipeDetail.putExtra("ingredients_key", ingredientJson);
            recipeDetail.putExtra("steps_key", stepJson);
            String resultJson = gson.toJson(recipeList.get(getAdapterPosition()));
            sharedPreferences.edit().putString("widget_result", resultJson).apply();
            view.getContext().startActivity(recipeDetail);
        }
    }

    public interface listItemclickListener {
        void onListItemClick(Recipes clickedRecipe);

    }

}
