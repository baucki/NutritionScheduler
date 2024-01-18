package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.states.menu

import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.menu.Menu

sealed class MenuState {
    object Loading: MenuState()
    object DataFetched: MenuState()
    data class Success(val menus: List<Menu>): MenuState()
    data class Error(val message: String): MenuState()
}