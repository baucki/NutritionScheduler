package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.states.categories.CategoriesState

interface CategoryContract {

    interface ViewModel {

        val categoriesState: LiveData<CategoriesState>

        fun fetchAllCategories()

        fun loadNextPage()
        fun loadPreviousPage()

    }

}