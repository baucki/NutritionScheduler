package rs.raf.projekat_avg_ivan_bauk_rn6218.data.repositories.nutrition

import io.reactivex.Observable
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.Resource
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.nutrition.Nutrition

interface NutritionRepository {

    fun fetchAllNutritionFactsForMeal(query: String): Observable<Resource<List<Nutrition>>>

}