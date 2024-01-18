package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.contract

import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.menu.Menu
import androidx.lifecycle.LiveData
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.states.menu.AddMenuState
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.states.menu.DeleteMenuState
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.states.menu.MenuState

interface MenuContract {

    interface ViewModel {

        val menuState: LiveData<MenuState>
        val addDone: LiveData<AddMenuState>
        val deleteDone: LiveData<DeleteMenuState>

        fun getAllMenus()
        fun insertMenu(menu: Menu)
        fun deleteMenu(id: String)
        fun getByCategory(category: String)

    }

}