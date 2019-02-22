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

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.InViewHolder> {
    public IngredientAdapter(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    private List<Ingredient> ingredientList;

    @NonNull
    @Override
    public IngredientAdapter.InViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_list_item,parent,false);
        return new InViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.InViewHolder holder, int position) {
     Ingredient ingredient=ingredientList.get(position);
     holder.inTv.setText(ingredient.getIngredient()+ingredient.getQuantity()+ ","+ingredient.getMeasure());
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class InViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvIngredient)
        TextView inTv;

        public InViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
