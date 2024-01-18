package rs.raf.projekat_avg_ivan_bauk_rn6218.modules

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.datasources.remote.MealService
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.datasources.remote.NutritionService
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.meals.Meal
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.meals.MealResponse
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.repositories.meals.MealRepository
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.repositories.meals.MealRepositoryImpl
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.repositories.nutrition.NutritionRepository
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.repositories.nutrition.NutritionRepositoryImpl
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.viewmodel.MealViewModel

val mealsModule = module {

    viewModel { MealViewModel(mealRepository = get(), nutritionRepository = get()) }

    single<MealRepository> { MealRepositoryImpl(mealDataSource = get(named("mealApi"))/*, mealAdapter = get(), mealResponseAdapter =  get()*/) }
    single<NutritionRepository> { NutritionRepositoryImpl(nutritionDataSource = get(named("nutritionApi"))) }

    single<MealService>(named("mealApi")) {create(retrofit = get(named("mealApi")))}
    single<NutritionService>(named("nutritionApi")) {create(retrofit = get(named("nutritionApi")))}

//    single(named("mealAdapter")) { createMealMoshiAdapter(get()) }
//    single(named("mealResponseAdapter")) { createMealResponseMoshiAdapter(get()) }

}
//    // Function to create Moshi adapter for Meal
//    fun createMealMoshiAdapter(moshi: Moshi): JsonAdapter<Meal> {
//        return moshi.adapter(Meal::class.java)
//    }
//
//    // Function to create Moshi adapter for MealResponse
//    fun createMealResponseMoshiAdapter(moshi: Moshi): JsonAdapter<MealResponse> {
//        return moshi.adapter(MealResponse::class.java)
//    }

