package retrofit;

import model.Recipes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Bakeinterface {
    @GET("baking.json")
    Call<ArrayList<Recipes>> getRecipes();
}
