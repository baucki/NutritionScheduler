package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.states.menu

sealed class AddMenuState {
    data class Success(val message: String): AddMenuState()
    data class Error(val message: String): AddMenuState()
}