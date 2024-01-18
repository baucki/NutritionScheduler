package rs.raf.projekat_avg_ivan_bauk_rn6218.data.repositories.categories

import io.reactivex.Observable
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.datasources.remote.CategoryService
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.Resource
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.categories.Category
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.categories.CategoryResponse

class CategoryRepositoryImpl(
    private val categoryDataSource: CategoryService,
) : CategoryRepository {
    override fun fetchAll(): Observable<Resource<List<Category>>> {
        lateinit var categories : List<CategoryResponse>
        val entities: MutableList<Category> = mutableListOf()
        return categoryDataSource
            .getAll()
            .doOnNext {
                categories = it.categories
                for (category in categories) {
                    entities.add(
                        Category(
                            category.idCategory,
                            category.strCategory,
                            category.strCategoryThumb,
                            category.strCategoryDescription
                        )
                    )
                }
            }.map {
                Resource.Success(entities)
            }
    }



}