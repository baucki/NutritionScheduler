package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.Resource
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.categories.Category
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.repositories.categories.CategoryRepository
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.contract.CategoryContract
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.states.categories.CategoriesState
import timber.log.Timber

class CategoryViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel(), CategoryContract.ViewModel {

    private val pageSize = 10 // Number of items per page
    private var currentPage = 1 // Current page

    private val subscriptions = CompositeDisposable()
    override val categoriesState: MutableLiveData<CategoriesState> = MutableLiveData()

    var allCategories: List<Category> = emptyList()
    var currentPageCategories: List<Category> = emptyList()

    init {
        fetchAllCategories()
    }

    override fun fetchAllCategories() {
        val subscription = categoryRepository
            .fetchAll()
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when (it) {
                        is Resource.Loading -> categoriesState.value = CategoriesState.Loading
                        is Resource.Success ->  {
                            categoriesState.value = CategoriesState.Success(it.data)

                            allCategories = it.data

                            updateCategoriesPage(currentPage)

                        }
                        is Resource.Error -> categoriesState.value = CategoriesState.Error("Error happened while fetching data from api")
                    }
                },
                {
                    categoriesState.value = CategoriesState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }

            )
        subscriptions.add(subscription)
    }

    private fun updateCategoriesPage(page: Int) {
        val startIndex = (page - 1) * pageSize
        val endIndex = minOf(page * pageSize, allCategories.size)
        val categoriesForPage = allCategories.subList(startIndex, endIndex)
        currentPageCategories = categoriesForPage
        categoriesState.value = CategoriesState.Success(currentPageCategories)
    }

    // Public function to request the next page of categories
    override fun loadNextPage() {
        currentPage++
        updateCategoriesPage(currentPage)
    }

    override fun loadPreviousPage() {
        currentPage--
        updateCategoriesPage(currentPage)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }

}