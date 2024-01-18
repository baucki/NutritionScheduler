package rs.raf.projekat_avg_ivan_bauk_rn6218.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.datasources.local.DataBase
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.repositories.menus.MenuRepository
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.repositories.menus.MenuRepositoryImpl
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.viewmodel.MenuViewModel


val menuModule = module {

    viewModel { MenuViewModel(menuRepository = get()) }

    single<MenuRepository> { MenuRepositoryImpl(localDataSource = get()) }
    single { get<DataBase>().getMenuDao() }

}