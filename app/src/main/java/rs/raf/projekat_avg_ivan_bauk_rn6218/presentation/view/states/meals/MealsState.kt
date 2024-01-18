package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.states.meals

import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.meals.CategoryMeal

sealed class MealsState {
    object Loading: MealsState()
    object DataFetched: MealsState()
    data class Success(val meals: List<CategoryMeal>): MealsState()
    data class Error(val message: String): MealsState()
}