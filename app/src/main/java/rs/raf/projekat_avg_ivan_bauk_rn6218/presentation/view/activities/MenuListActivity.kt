package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.menu.Menu
import rs.raf.projekat_avg_ivan_bauk_rn6218.databinding.ActivityMenuListBinding
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.contract.MenuContract
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.recycler.adapter.CategoryMenuAdapter
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.states.menu.DeleteMenuState
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.states.menu.MenuState
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.viewmodel.MenuViewModel
import timber.log.Timber

class MenuListActivity : AppCompatActivity(), CategoryMenuAdapter.OnCategoryMenuItemClickListener,
CategoryMenuAdapter.OnCategoryMenuItemElementClickListener {

    companion object {
        const val EXTRA_MENU_CATEGORY_KEY = "menuCategoryKey"
    }

    private val menuViewModel: MenuContract.ViewModel by viewModel<MenuViewModel>()

    private lateinit var binding: ActivityMenuListBinding
    private lateinit var category: String

    private lateinit var adapter: CategoryMenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        loadIntent()
        initUi()
        initObservers()
    }

    private fun loadIntent() {
        if (intent.extras != null) {
            category = intent.getStringExtra(EXTRA_MENU_CATEGORY_KEY).toString()
        }
    }

    private fun initUi() {
        initRecycler()
        initListeners()
    }

    private fun initRecycler() {
        binding.listRv.layoutManager = LinearLayoutManager(this)
        adapter = CategoryMenuAdapter()
        binding.listRv.adapter = adapter
        adapter.setOnCategoryMenuItemClickListener(this)
        adapter.setOnCategoryMenuItemElementClickListener(this)

    }

    private fun initListeners() {
        binding.apiDatabaseSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (parent?.getItemAtPosition(position).toString()) {
                    "meals from server" -> {
                        val intent = Intent(binding.root.context, MealsListActivity::class.java)

                        intent.putExtra(MealsListActivity.EXTRA_CATEGORY_KEY, category)
                        startActivity(intent)
                        finish()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun initObservers() {
        menuViewModel.menuState.observe(this, Observer {
            renderState(it)
        })

        menuViewModel.deleteDone.observe(this, Observer {
            displayDeleteMessage(it)
        })

        menuViewModel.getByCategory(category)
    }

    private fun renderState(state: MenuState) {
        when (state) {
            is MenuState.Success -> {
                adapter.submitList(state.menus)
            }
            is MenuState.Error -> {
                Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
            }
            is MenuState.DataFetched -> {
                Toast.makeText(this, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is MenuState.Loading -> {
            }
        }
    }


    private fun displayDeleteMessage(state: DeleteMenuState) {
        when (state) {
            is DeleteMenuState.Success ->
                Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
            is DeleteMenuState.Error ->
                Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCategoryMenuItemClick(menu: Menu) {
        Timber.e("poruka")
    }

    override fun onCategoryMenuEditClick(menu: Menu) {
        val intent = Intent(this, EditMenuActivity::class.java)

        intent.putExtra(EditMenuActivity.EXTRA_EDIT_MENU_KEY, menu)
        startActivity(intent)
    }

    override fun onCategoryMenuDeleteClick(menu: Menu) {
        menuViewModel.deleteMenu(menu.idMenu)
    }


}