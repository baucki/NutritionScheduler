package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.meals.CategoryMeal
import rs.raf.projekat_avg_ivan_bauk_rn6218.databinding.ActivityMealsListBinding
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.recycler.adapter.CategoryMealAdapter
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.states.meals.MealsState
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.viewmodel.MealViewModel

class MealsListActivity : AppCompatActivity(), CategoryMealAdapter.OnCategoryMealItemClickListener {

    companion object {
        const val EXTRA_CATEGORY_KEY = "categoryKey"
    }

    private val mealViewModel: MealViewModel by viewModel<MealViewModel>()

    private lateinit var binding: ActivityMealsListBinding
    private lateinit var category: String

    private lateinit var adapter: CategoryMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealsListBinding.inflate(layoutInflater)
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
            category = intent.getStringExtra(EXTRA_CATEGORY_KEY).toString()
        }
    }

    private fun initUi() {
        initRecycler()
        initListeners()
    }

    private fun initRecycler() {
        binding.listRv.layoutManager = LinearLayoutManager(this)
        adapter = CategoryMealAdapter()
        binding.listRv.adapter = adapter
        adapter.setOnCategoryMealItemClickListener(this)
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
                    "meals from menu" -> {
                        val intent = Intent(binding.root.context, MenuListActivity::class.java)

                        intent.putExtra(MenuListActivity.EXTRA_MENU_CATEGORY_KEY, category)
                        startActivity(intent)
                        finish()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun initObservers() {
        mealViewModel.mealsState.observe(this, Observer {
            renderState(it)
        })

        mealViewModel.fetchAllByCategory(category)

    }

    private fun renderState(state: MealsState) {
        when (state) {
            is MealsState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.meals)
            }
            is MealsState.Error -> {
                showLoadingState(false)
                Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
            }
            is MealsState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(this, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is MealsState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
//        binding.inputEt.isVisible = !loading
        binding.apiDatabaseSpinner.isVisible = !loading
        binding.listRv.isVisible = !loading
        binding.loadingPb.isVisible = loading
    }

    override fun onCategoryMealItemClick(categoryMeal: CategoryMeal) {
        val intent = Intent(this, MealDetailActivity::class.java)

        intent.putExtra(MealDetailActivity.EXTRA_CATEGORY_MEAL_KEY, mealViewModel.getMealDetail(categoryMeal.strMeal))
        startActivity(intent)
    }


}