package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.srikanth.bakingapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import model.Ingredient;
import model.Step;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.InViewHolder> {
    public RecipeStepAdapter(List<Step> ingredientList) {
        this.recipeStepList = ingredientList;
    }

    private List<Step> recipeStepList;

    @NonNull
    @Override
    public RecipeStepAdapter.InViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_detail_items, parent, false);
        return new RecipeStepAdapter.InViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepAdapter.InViewHolder holder, int position) {
        Step recipeSteps = recipeStepList.get(position);
        holder.stepTv.setText(recipeSteps.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return recipeStepList.size();
    }

    public class InViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shortDescription)
        TextView stepTv;

        public InViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
