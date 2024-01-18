package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.states.nutrition

sealed class NutritionState {
    object Loading: NutritionState()
    object DataFetched: NutritionState()
//    data class Success(val nutritionFacts: List<CategoryMeal>): NutritionState()
    data class Error(val message: String): NutritionState()

}