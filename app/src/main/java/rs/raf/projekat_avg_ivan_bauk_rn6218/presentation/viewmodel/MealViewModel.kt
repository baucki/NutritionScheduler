package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.Resource
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.meals.CategoryMeal
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.meals.Meal
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.meals.MealDetail
import kotlin.reflect.full.memberProperties
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.repositories.meals.MealRepository
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.repositories.nutrition.NutritionRepository
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.contract.MealContract
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.states.meals.MealsState
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.states.nutrition.NutritionState
import timber.log.Timber
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger


class MealViewModel(
    private val mealRepository: MealRepository,
    private val nutritionRepository: NutritionRepository
) : ViewModel(), MealContract.ViewModel {

    private val subscriptions = CompositeDisposable()

    override val mealsState: MutableLiveData<MealsState> = MutableLiveData()
    override val nutritionState: MutableLiveData<NutritionState> = MutableLiveData()

    private val filterNamesPublishSubject: PublishSubject<String> = PublishSubject.create()
    private val filterTagsPublishSubject: PublishSubject<String> = PublishSubject.create()

    var meals: List<CategoryMeal> = emptyList()
    var displayedMeals: List<CategoryMeal> = emptyList()
    var mealsDetails: MutableList<MealDetail> = mutableListOf()

    init {
        val subscription = filterNamesPublishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { name ->
                    displayedMeals = meals.filter { it.strMeal.contains(name) }
                    if (name != "") {
                        mealsState.value = MealsState.Success(displayedMeals)
                    } else {
                        mealsState.value = MealsState.Success(meals)
                    }
                },
                {

                }
            )
        subscriptions.add(subscription)
    }

    init {
        val subscription = filterTagsPublishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { tags ->
                    displayedMeals = meals.filter { it.strTags!!.contains(tags) }
                    if (tags != "") {
                        mealsState.value = MealsState.Success(displayedMeals)
                    } else {
                        mealsState.value = MealsState.Success(meals)
                    }
                },
                {

                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllByCategory(category: String) {
        val subscription = mealRepository
            .fetchAllByCategory(category)
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when (it) {
                        is Resource.Loading -> mealsState.value = MealsState.Loading
                        is Resource.Success -> {

                            if (it.data.isEmpty())  {
                                meals = emptyList()
                                mealsState.value = MealsState.Success(meals)
                            } else {
                                meals = it.data
                            }

                            val completionCounter = AtomicInteger(0)

                            for (meal in meals) {
                                fetchAllByName(meal.strMeal) { success ->
                                    if (success) {
                                        completionCounter.incrementAndGet()
                                    } else {
                                        completionCounter.incrementAndGet()
                                    }
                                    if (completionCounter.get() == meals.size) {
                                        displayedMeals = meals
                                        mealsState.value = MealsState.Success(meals)
                                    }
                                }
                            }

                        }
                        is Resource.Error -> mealsState.value = MealsState.Error("Error happened while fetching data from api")
                    }

                },
                {
                    mealsState.value = MealsState.Error("Error happened while fetching data from api")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllByIngredient(ingredient: String) {
        val subscription = mealRepository
            .fetchAllByIngredient(ingredient)
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when (it) {
                        is Resource.Loading -> mealsState.value = MealsState.Loading
                        is Resource.Success -> {

                            if (it.data.isEmpty())  {
                                meals = emptyList()
                                mealsState.value = MealsState.Success(meals)
                            } else {
                                meals = it.data
                            }

                            val completionCounter = AtomicInteger(0)

                            for (meal in meals) {
                                fetchAllByName(meal.strMeal) { success ->
                                    if (success) {
                                        completionCounter.incrementAndGet()
                                    } else {
                                        completionCounter.incrementAndGet()
                                    }
                                    if (completionCounter.get() == meals.size) {
                                        mealsState.value = MealsState.Success(meals)
                                    }
                                }
                            }

                        }
                        is Resource.Error -> mealsState.value = MealsState.Error("Error happened while fetching data from api")
                    }

                },
                {
                    mealsState.value = MealsState.Error("Error happened while fetching data from api")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllByArea(area: String) {
        val subscription = mealRepository
            .fetchAllByArea(area)
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when (it) {
                        is Resource.Loading -> mealsState.value = MealsState.Loading
                        is Resource.Success -> {

                            if (it.data.isEmpty())  {
                                meals = emptyList()
                                mealsState.value = MealsState.Success(meals)
                            } else {
                                meals = it.data
                            }

                            val completionCounter = AtomicInteger(0)

                            for (meal in meals) {
                                fetchAllByName(meal.strMeal) { success ->
                                    if (success) {
                                        completionCounter.incrementAndGet()
                                    } else {
                                        completionCounter.incrementAndGet()
                                    }
                                    if (completionCounter.get() == meals.size) {
                                        mealsState.value = MealsState.Success(meals)
                                    }
                                }
                            }

                        }
                        is Resource.Error -> mealsState.value = MealsState.Error("Error happened while fetching data from api")
                    }

                },
                {
                    mealsState.value = MealsState.Error("Error happened while fetching data from api")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllByName(name: String, callback: (success: Boolean) -> Unit) {
        val subscription = mealRepository
            .fetchAllByName(name)
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when (it) {
                        is Resource.Success -> {
                            val fieldMeasure = "strMeasure"
                            val fieldIngredient = "strIngredient"
                            for (meal in it.data) {
                                var query = ""
                                addMealDetail(meal)
                                updateMeals(meal)
                                for (i in 1..20) {
                                    val currFieldMeasure = fieldMeasure + i
                                    val currFieldIngredient = fieldIngredient + i
                                    val propertyMeasure = Meal::class.memberProperties.find { it.name == currFieldMeasure }
                                    val propertyIngredient = Meal::class.memberProperties.find { it.name == currFieldIngredient }

                                    val measure: String
                                    val ingredient: String


                                    if (propertyMeasure != null && propertyIngredient != null) {
                                        measure = propertyMeasure.get(meal) as String
                                        ingredient = propertyIngredient.get(meal) as String
                                        if (measure != "" && ingredient != "") {
                                             if (i != 1) {
                                                 query += " and "
                                             }
                                            query += "$measure $ingredient"
                                        }
                                    }
                                }
                                fetchAllNutritionFactsForMeal(query, meal.strMeal) { success ->
                                    if (success) {
                                        callback(true)
                                    } else {
                                        callback(false)
                                    }
                                }
                            }
                        }
                    }
                },
                {
                    callback(false)
                }
            )
        subscriptions.add(subscription)
    }
    override fun fetchAllNutritionFactsForMeal(query: String, meal: String, callback: (success: Boolean) -> Unit) {
        val subscription = nutritionRepository
            .fetchAllNutritionFactsForMeal(query)
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when (it) {
                        is Resource.Success -> {
                            for (categoryMeal in meals) {
                                for (nutrition in it.data) {
                                    if (categoryMeal.strMeal == meal) {
                                        var calories: Int = categoryMeal.strCalories.toInt()
                                        calories += nutrition.calories.toInt()
                                        categoryMeal.strCalories = calories.toString()
                                        var mealDetail = getMealDetail(meal)
                                        mealDetail!!.strCalories = categoryMeal.strCalories
                                        updateIngredientCalories(nutrition.name, nutrition.calories.toInt().toString(), mealDetail)
                                    }
                                }
                            }
                        }
                    }
                },
                {
                    callback(false)
                },
                {
                    callback(true)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getMealDetail(name: String): MealDetail? {
        for (meal in mealsDetails) {
            if (meal.strMeal == name) return meal
        }
        return null
    }

    fun updateIngredientCalories(ingredient: String, calories: String, mealDetail: MealDetail) {
        val fieldIngredient = "strIngredient"
        val fieldCalories = "strCalories"

        for (i in 1 .. 20) {
            val currFieldIngredient = fieldIngredient + i
            val currFieldCalories = fieldCalories + i
            val propertyIngredient = MealDetail::class.memberProperties.find { it.name == currFieldIngredient }
            val propertyCalories = MealDetail::class.memberProperties.find { it.name == currFieldCalories }

            val oldIngredient = propertyIngredient!!.get(mealDetail) as String
            val oldCalories = propertyCalories!!.get(mealDetail) as String

            if (propertyIngredient != null && propertyCalories != null) {
                if (oldIngredient != "" && oldCalories != "") {
                    if (oldIngredient.equals(ingredient, ignoreCase = true)) {
                        mealDetail.javaClass.getDeclaredField(currFieldCalories).apply {
                            isAccessible = true
                            set(mealDetail, calories)
                        }
                    }
                }
            }
        }
    }

    fun updateMeals(meal: Meal) {
        for (categoryMeal in meals) {
            if (categoryMeal.strMeal == meal.strMeal) {
                categoryMeal.strTags = meal.strTags
            }
        }
    }

    fun addMealDetail(meal: Meal) {
        mealsDetails.add(
            MealDetail(
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
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                "0",
                meal.strSource,
                meal.strImageSource,
                meal.strCreativeCommonsConfirmed,
                meal.dateModified
            )
        )
    }

    override fun filterByName(name: String) {
        filterNamesPublishSubject.onNext(name)
    }

    override fun filterByTags(tags: String) {
        filterTagsPublishSubject.onNext(tags)
    }

    override fun filterByCalories(low: Int, high: Int) {
        var list: List<CategoryMeal> = displayedMeals.filter { it.strCalories.toInt() in low..high }
        mealsState.value = MealsState.Success(list)
    }

    override fun sortNameAsc() {
        displayedMeals = displayedMeals.sortedBy { it.strMeal }
        mealsState.value = MealsState.Success(displayedMeals)
    }
    override fun sortNameDesc() {
        displayedMeals = displayedMeals.sortedByDescending { it.strMeal }
        mealsState.value = MealsState.Success(displayedMeals)
    }

    override fun sortCaloriesAsc() {
        displayedMeals = displayedMeals.sortedBy { it.strCalories.toInt() }
        mealsState.value = MealsState.Success(displayedMeals)
    }

    override fun sortCaloriesDesc() {
        displayedMeals = displayedMeals.sortedByDescending { it.strCalories.toInt() }
        mealsState.value = MealsState.Success(displayedMeals)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }

}