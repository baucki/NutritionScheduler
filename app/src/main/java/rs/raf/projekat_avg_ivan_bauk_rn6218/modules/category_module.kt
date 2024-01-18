package rs.raf.projekat_avg_ivan_bauk_rn6218.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.datasources.remote.CategoryService
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.repositories.categories.CategoryRepository
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.repositories.categories.CategoryRepositoryImpl
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.viewmodel.CategoryViewModel

val categoryModule = module {

    viewModel { CategoryViewModel(categoryRepository = get()) }

    single<CategoryRepository> { CategoryRepositoryImpl(categoryDataSource = get(named("mealApi"))) }

    single<CategoryService>(named("mealApi")) { create(retrofit = get(named("mealApi"))) }

}