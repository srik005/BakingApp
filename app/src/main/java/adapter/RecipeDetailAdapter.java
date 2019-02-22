package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.srikanth.bakingapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import model.Step;

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.DetailViewHolder> {
    public RecipeDetailAdapter(List<Step> stepList, Context context) {
        this.stepList = stepList;
        mContext = context;
    }

    List<Step> stepList;
    ItemClickListener itemClickListener;
    private String recipeName;
    private Context mContext;

    @NonNull
    @Override
    public RecipeDetailAdapter.DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View detailView = LayoutInflater.from(mContext).inflate(R.layout.recipe_detail_items, parent, false);
        return new DetailViewHolder(detailView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailAdapter.DetailViewHolder holder, int position) {
        holder.shortTextView.setText(stepList.get(position).getId() + "." + stepList.get(position).getShortDescription());

    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView shortTextView;

        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);
            shortTextView = itemView.findViewById(R.id.shortDescription);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
          /*  int clickedPosition = getAdapterPosition();
            itemClickListener.onItemClick(mContext, stepList.get(clickedPosition).getId(), stepList.get(clickedPosition).getDescription(),
                    stepList.get(clickedPosition).getVideoURL(), stepList.get(clickedPosition).getThumbnailURL());
*/
        }
    }

    public interface ItemClickListener {
        void onItemClick(Context context, Integer id, String description, String url, String thumbnailUrl);
    }
}
