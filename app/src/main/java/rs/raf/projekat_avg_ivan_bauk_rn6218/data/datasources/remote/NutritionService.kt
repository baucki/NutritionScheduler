package rs.raf.projekat_avg_ivan_bauk_rn6218.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.nutrition.NutritionResponse

interface NutritionService {

    @GET("nutrition")
    fun getNutritionFactsForMeal(
        @Query("query") meal: String,
    ): Observable<List<NutritionResponse>>

}