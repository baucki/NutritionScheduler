package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.menu.Menu
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.repositories.menus.MenuRepository
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.contract.MenuContract
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.states.menu.AddMenuState
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.states.menu.DeleteMenuState
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.states.menu.MenuState
import timber.log.Timber

class MenuViewModel(
    private val menuRepository: MenuRepository
) : ViewModel(), MenuContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val menuState: MutableLiveData<MenuState> = MutableLiveData()
    override val addDone: MutableLiveData<AddMenuState> = MutableLiveData()
    override val deleteDone: MutableLiveData<DeleteMenuState> = MutableLiveData()

    override fun getAllMenus() {
        val subscription = menuRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    menuState.value = MenuState.Success(it)
                },
                {

                }
            )
        subscriptions.add(subscription)
    }

    override fun insertMenu(menu: Menu) {
        val subscription = menuRepository
            .insert(menu)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    addDone.value = AddMenuState.Success("Meal has been successfully added to menu")
                },
                {
                    addDone.value = AddMenuState.Error("Error happened while adding meal in menu")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun deleteMenu(id: String) {
        val subscription = menuRepository
            .delete(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    deleteDone.value = DeleteMenuState.Success("Meal has been successfully deleted from menu")
                },
                {
                    deleteDone.value = DeleteMenuState.Error("Meal has not been successfully deleted from menu")
                }
            )
        subscriptions.add(subscription)
    }

    override fun getByCategory(category: String) {
        val subscription = menuRepository
            .getAllMenusByCategory(category)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    menuState.value = MenuState.Success(it)
                },
                {
                    menuState.value = MenuState.Error("Error while getting meals from menu by category")
                }
            )
        subscriptions.add(subscription)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}