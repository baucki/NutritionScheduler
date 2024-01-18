package rs.raf.projekat_avg_ivan_bauk_rn6218.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.meals.AllMealsResponse

interface MealService {

    @GET("lookup.php?i={id}")
    fun getMealById(
        @Path("id") id: String,
    ): Observable<AllMealsResponse>

    @GET("search.php")
    fun getMealsByName(
        @Query("s") name: String,
    ): Observable<AllMealsResponse>

    @GET("filter.php")
    fun getMealsByIngredient(
        @Query("i") ingredient: String,
    ): Observable<AllMealsResponse>

    @GET("filter.php")
    fun getMealsByCategory(
        @Query("c") category: String,
    ): Observable<AllMealsResponse>

    @GET("filter.php")
    fun getMealsByArea(
        @Query("a") area: String,
    ): Observable<AllMealsResponse>

}