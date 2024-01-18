package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat_avg_ivan_bauk_rn6218.R
import rs.raf.projekat_avg_ivan_bauk_rn6218.databinding.FragmentSearchBinding
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.contract.MealContract
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.recycler.adapter.CategoryMealAdapter
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.viewmodel.MealViewModel
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.meals.CategoryMeal
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.activities.MealDetailActivity
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.states.meals.MealsState

class SearchFragment : Fragment(R.layout.fragment_search), CategoryMealAdapter.OnCategoryMealItemClickListener {

    private val mealViewModel: MealContract.ViewModel by sharedViewModel<MealViewModel>()

    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: CategoryMealAdapter

    private var category: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstaceState: Bundle?) {
        super.onViewCreated(view, savedInstaceState)
        init()
    }

    private fun init() {
        initUi()
        initObservers()
    }

    private fun initUi() {

        initRecycler()
        initListeners()
    }

    private fun initObservers() {
        mealViewModel.mealsState.observe(this, Observer {
            renderState(it)
        })
    }

    private fun initRecycler() {
        binding.listRv.layoutManager = LinearLayoutManager(context)
        adapter = CategoryMealAdapter()
        binding.listRv.adapter = adapter
        adapter.setOnCategoryMealItemClickListener(this)
    }

    private fun initListeners() {
        binding.searchBtn.setOnClickListener {
            if (binding.searchSpinner.selectedItem.toString() == "Category") {
                mealViewModel.fetchAllByCategory(binding.searchInputEt.text.toString())
            } else if (binding.searchSpinner.selectedItem.toString() == "Ingredient") {
                mealViewModel.fetchAllByIngredient(binding.searchInputEt.text.toString())
            } else if (binding.searchSpinner.selectedItem.toString() == "Area") {
                mealViewModel.fetchAllByArea(binding.searchInputEt.text.toString())
            }
        }
        binding.filterInputEt.doAfterTextChanged {
            val filter = it.toString()
            if (binding.filterSpinner.selectedItem.toString() == "Name") {
                mealViewModel.filterByName(filter)
            } else if (binding.filterSpinner.selectedItem.toString() == "Tags") {
                mealViewModel.filterByTags(filter)
            }
        }

        binding.rangeSlider.addOnChangeListener { slider, _, _ ->
            val min = slider.values[0]
            val max = slider.values[1]

            mealViewModel.filterByCalories(min.toInt(), max.toInt())
        }

        binding.sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                when (parent?.getItemAtPosition(position).toString()) {
                    "A-Z" -> {
                        mealViewModel.sortNameAsc()
                    }
                    "Z-A" -> {
                        mealViewModel.sortNameDesc()
                    }
                    "Calories low to high" -> {
                        mealViewModel.sortCaloriesAsc()
                    }
                    "Calories high to low" -> {
                        mealViewModel.sortCaloriesDesc()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun renderState(state: MealsState) {
        when (state) {
            is MealsState.Success -> {
                showLoadingState(false)
                updateSlider(state.meals)
                adapter.submitList(state.meals)
            }
            is MealsState.Error -> {
                showLoadingState(false)
//                Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
            }
            is MealsState.DataFetched -> {
                showLoadingState(false)
//                Toast.makeText(this, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is MealsState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        binding.searchInputEt.isVisible = !loading
        binding.searchSpinner.isVisible = !loading
        binding.filterInputEt.isVisible = !loading
        binding.filterSpinner.isVisible = !loading
        binding.rangeSlider.isVisible = !loading
        binding.searchBtn.isVisible = !loading
        binding.sortSpinner.isVisible = !loading
        binding.listRv.isVisible = !loading
        binding.loadingPb.isVisible = loading

    }

    override fun onCategoryMealItemClick(categoryMeal: CategoryMeal) {

        val intent = Intent(requireContext(), MealDetailActivity::class.java)

        intent.putExtra(MealDetailActivity.EXTRA_CATEGORY_MEAL_KEY, mealViewModel.getMealDetail(categoryMeal.strMeal))
        startActivity(intent)

    }

    private fun updateSlider(meals: List<CategoryMeal>) {
        if (meals.isEmpty() && binding.filterInputEt.text.toString() == "") {
            binding.rangeSlider.values[0] = 0.0f
            binding.rangeSlider.values[1] = 100.0f
            binding.rangeSlider.valueTo = 100.0f
        }
        if (binding.rangeSlider.valueTo == 100.0f){
            var max = binding.rangeSlider.values[1].toInt()
            for (meal in meals) {
                if (meal.strCalories.toInt() > max) {
                    max = meal.strCalories.toInt()
                }
                binding.rangeSlider.valueTo = max.toFloat()
            }
            binding.rangeSlider.values[1] = max.toFloat()
        }



    }
}