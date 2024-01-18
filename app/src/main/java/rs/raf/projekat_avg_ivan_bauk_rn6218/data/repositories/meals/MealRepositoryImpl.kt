package rs.raf.projekat_avg_ivan_bauk_rn6218.data.repositories.meals

import com.squareup.moshi.JsonAdapter
import io.reactivex.Observable
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.datasources.remote.MealService
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.Resource
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.meals.CategoryMeal
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.meals.Meal
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.meals.MealResponse

class MealRepositoryImpl(
    private val mealDataSource: MealService/*,
    private val mealAdapter: JsonAdapter<Meal>,
    private val mealResponseAdapter: JsonAdapter<MealResponse>*/
) : MealRepository {

    override fun fetchAllByCategory(category: String): Observable<Resource<List<CategoryMeal>>> {
        var meals: List<MealResponse> = emptyList()
        val entities: MutableList<CategoryMeal> = mutableListOf()
        return mealDataSource
            .getMealsByCategory(category)
            .doOnNext {
                if (it.meals != null)
                    meals = it.meals
                for (meal in meals) {
                    entities.add(
                        CategoryMeal(
                            meal.idMeal,
                            meal.strMeal,
                            meal.strMealThumb,
                            null,
                            "0"
                        )
                    )
                }
            }.map {
                Resource.Success(entities)
            }
    }

    override fun fetchAllByIngredient(ingredient: String): Observable<Resource<List<CategoryMeal>>> {
        var meals: List<MealResponse> = emptyList()
        val entities: MutableList<CategoryMeal> = mutableListOf()
        return mealDataSource
            .getMealsByIngredient(ingredient)
            .doOnNext {
                if (it.meals != null)
                    meals = it.meals
                for (meal in meals) {
                    entities.add(
                        CategoryMeal(
                            meal.idMeal,
                            meal.strMeal,
                            meal.strMealThumb,
                            null,
                            "0"
                        )
                    )
                }
            }.map {
                Resource.Success(entities)
            }
    }

    override fun fetchAllByArea(area: String): Observable<Resource<List<CategoryMeal>>> {
        var meals: List<MealResponse> = emptyList()
        val entities: MutableList<CategoryMeal> = mutableListOf()
        return mealDataSource
            .getMealsByArea(area)
            .doOnNext {
                if (it.meals != null)
                    meals = it.meals
                for (meal in meals) {
                    entities.add(
                        CategoryMeal(
                            meal.idMeal,
                            meal.strMeal,
                            meal.strMealThumb,
                            null,
                            "0"
                        )
                    )
                }
            }.map {
                Resource.Success(entities)
            }
    }

    override fun fetchAllByName(name: String): Observable<Resource<List<Meal>>> {
        var meals: List<MealResponse> = emptyList()
        val entities: MutableList<Meal> = mutableListOf()
        return mealDataSource
            .getMealsByName(name)
            .doOnNext {
                if (it.meals != null)
                    meals = it.meals
                for (meal in meals) {
//                    val json = mealResponseAdapter.toJson(meal)
//                    val entity = mealAdapter.fromJson(json)
//                    if (entity != null) {
//                        entities.add(entity)
//                    }
                    entities.add(
                        Meal(
                            meal.idMeal,
                            meal.strMeal,
                            meal.strDrinkAlternate,
                            meal.strCategory,
                            meal.strArea,
                            meal.strInstructions,
                            meal.strMealThumb,
                            meal.strTags,
                            meal.strYoutube,
                            meal.strIngredient1,
                            meal.strIngredient2,
                            meal.strIngredient3,
                            meal.strIngredient4,
                            meal.strIngredient5,
                            meal.strIngredient6,
                            meal.strIngredient7,
                            meal.strIngredient8,
                            meal.strIngredient9,
                            meal.strIngredient10,
                            meal.strIngredient11,
                            meal.strIngredient12,
                            meal.strIngredient13,
                            meal.strIngredient14,
                            meal.strIngredient15,
                            meal.strIngredient16,
                            meal.strIngredient17,
                            meal.strIngredient18,
                            meal.strIngredient19,
                            meal.strIngredient20,
                            meal.strMeasure1,
                            meal.strMeasure2,
                            meal.strMeasure3,
                            meal.strMeasure4,
                            meal.strMeasure5,
                            meal.strMeasure6,
                            meal.strMeasure7,
                            meal.strMeasure8,
                            meal.strMeasure9,
                            meal.strMeasure10,
                            meal.strMeasure11,
                            meal.strMeasure12,
                            meal.strMeasure13,
                            meal.strMeasure14,
                            meal.strMeasure15,
                            meal.strMeasure16,
                            meal.strMeasure17,
                            meal.strMeasure18,
                            meal.strMeasure19,
                            meal.strMeasure20,
                            meal.strSource,
                            meal.strImageSource,
                            meal.strCreativeCommonsConfirmed,
                            meal.dateModified
                        )
                    )
                }
            }.map {
                Resource.Success(entities)
            }
    }

}