package rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat_avg_ivan_bauk_rn6218.R
import rs.raf.projekat_avg_ivan_bauk_rn6218.data.models.categories.Category
import rs.raf.projekat_avg_ivan_bauk_rn6218.databinding.FragmentHomeBinding
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.contract.CategoryContract
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.activities.MealsListActivity
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.view.states.categories.CategoriesState
import rs.raf.projekat_avg_ivan_bauk_rn6218.presentation.viewmodel.CategoryViewModel

class HomeFragment : Fragment(R.layout.fragment_home), CategoryAdapter.OnCategoryItemClickListener {

    private val categoryViewModel: CategoryContract.ViewModel by sharedViewModel<CategoryViewModel>()

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    private fun initRecycler() {
        binding.listRv.layoutManager = LinearLayoutManager(context)
        adapter = CategoryAdapter()
        binding.listRv.adapter = adapter
        adapter.setOnCategoryItemClickListener(this)
    }

    private fun initListeners() {
        binding.prevPageButton.setOnClickListener {
            categoryViewModel.loadPreviousPage()
        }

        binding.nextPageButton.setOnClickListener {
            categoryViewModel.loadNextPage()
        }
    }

    private fun initObservers() {
        categoryViewModel.categoriesState.observe(viewLifecycleOwner, Observer {
            renderState(it)
        })
        categoryViewModel.fetchAllCategories()
    }

    private fun renderState(state: CategoriesState) {
        when (state) {
            is CategoriesState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.categories)
            }
            is CategoriesState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is CategoriesState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is CategoriesState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
//        binding.inputEt.isVisible = !loading
        binding.listRv.isVisible = !loading
        binding.loadingPb.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCategoryItemClick(category: Category) {

        val intent = Intent(requireContext(), MealsListActivity::class.java)

        intent.putExtra(MealsListActivity.EXTRA_CATEGORY_KEY, category.strCategory)
        startActivity(intent)

    }

}