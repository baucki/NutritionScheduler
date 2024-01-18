package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.meals.MealDetail
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.states.meals.MealsState
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.states.nutrition.NutritionState

interface MealContract {

    interface ViewModel {

        val mealsState: LiveData<MealsState>
        val nutritionState: LiveData<NutritionState>

        fun fetchAllByCategory(category: String)
        fun fetchAllByIngredient(ingredient: String)
        fun fetchAllByArea(area: String)
        fun fetchAllByName(name: String, callback: (Boolean) -> Unit)

        fun fetchAllNutritionFactsForMeal(query: String, meal: String, callback: (Boolean) -> Unit)

        fun getMealDetail(name: String): MealDetail?
        fun filterByTags(tags: String)
        fun filterByName(name: String)
        fun filterByCalories(low: Int, high: Int)

        fun sortNameAsc()
        fun sortNameDesc()
        fun sortCaloriesAsc()
        fun sortCaloriesDesc()
    }

}