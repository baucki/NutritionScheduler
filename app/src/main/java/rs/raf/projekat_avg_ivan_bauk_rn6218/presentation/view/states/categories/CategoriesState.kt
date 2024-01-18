package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.states.categories

import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.categories.Category

sealed class CategoriesState {
    object Loading: CategoriesState()
    object DataFetched: CategoriesState()
    data class Success(val categories: List<Category>): CategoriesState()
    data class Error(val message: String): CategoriesState()
}
