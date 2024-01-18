package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.states.menu

sealed class DeleteMenuState {
    data class Success(val message: String): DeleteMenuState()
    data class Error(val message: String): DeleteMenuState()
}