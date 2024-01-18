package rs.raf.projekat_avg_ivan_bauk_rn6218.data.repositories.meals

import io.reactivex.Observable
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.Resource
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.meals.CategoryMeal
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.meals.Meal


interface MealRepository {

    fun fetchAllByCategory(category: String): Observable<Resource<List<CategoryMeal>>>
    fun fetchAllByIngredient(ingredient: String): Observable<Resource<List<CategoryMeal>>>
    fun fetchAllByArea(area: String): Observable<Resource<List<CategoryMeal>>>
    fun fetchAllByName(name: String): Observable<Resource<List<Meal>>>

}