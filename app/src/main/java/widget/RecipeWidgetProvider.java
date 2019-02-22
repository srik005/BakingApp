package widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.srikanth.bakingapp.R;

import java.util.List;

import model.Ingredient;
import model.Recipes;

public class RecipeWidgetProvider extends AppWidgetProvider {
    public static void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, String recipeName,
                                    List<Ingredient> ingredientList) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        views.setTextViewText(R.id.tvRecipeName, recipeName);
        views.removeAllViews(R.id.ingredientsWidget);
        for (Ingredient ingredient : ingredientList) {
            RemoteViews ingredientView = new RemoteViews(context.getPackageName(),
                    R.layout.recipe_list_item);
            ingredientView.setTextViewText(R.id.ingredient_txt_view,
                    String.valueOf(ingredient.getIngredient()) + " " +
                            String.valueOf(ingredient.getMeasure()));
            views.addView(R.id.ingredientsWidget, ingredientView);
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BakingPreference", Context.MODE_PRIVATE);
        String widgetResult = sharedPreferences.getString("widget_result", null);
        Gson widgetGson = new Gson();
        Recipes widgetRecipe = widgetGson.fromJson(widgetResult, Recipes.class);
        String widgetRecipeName = widgetRecipe.getName();
        for (int appWidgetId : appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId, widgetRecipeName, widgetRecipe.getIngredients());
        }
    }
}
