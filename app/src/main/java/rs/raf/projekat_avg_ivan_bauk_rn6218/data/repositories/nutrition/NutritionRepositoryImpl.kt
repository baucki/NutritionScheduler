package rs.raf.projekat_avg_ivan_bauk_rn6218.data.repositories.nutrition

import io.reactivex.Observable
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.datasources.remote.NutritionService
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.Resource
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.nutrition.Nutrition
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.nutrition.NutritionResponse

class NutritionRepositoryImpl(
    private val nutritionDataSource: NutritionService
) : NutritionRepository{

    override fun fetchAllNutritionFactsForMeal(
        query: String
    ): Observable<Resource<List<Nutrition>>> {
        lateinit var allNutritionFacts: List<NutritionResponse>
        val entities: MutableList<Nutrition> = mutableListOf()
        return nutritionDataSource
            .getNutritionFactsForMeal(query)
            .doOnNext {
                allNutritionFacts = it
                for (nutrition in allNutritionFacts) {
                    entities.add(
                        Nutrition(
                            nutrition.name,
                            nutrition.calories,
                            nutrition.serving_size_g,
                            nutrition.fat_total_g,
                            nutrition.fat_saturated_g,
                            nutrition.protein_g,
                            nutrition.sodium_mg,
                            nutrition.potassium_mg,
                            nutrition.cholesterol_mg,
                            nutrition.carbohydrates_total_g,
                            nutrition.fiber_g,
                            nutrition.sugar_g
                        )
                    )
                }
            }.map { Resource.Success(entities) }
    }


}