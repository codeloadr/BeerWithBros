package com.graviton.beerwithbros

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.graviton.beerwithbros.databinding.FragmentBeerListBinding
import com.graviton.beerwithbros.ui.paging.BeerPagingAdapter
import com.graviton.beerwithbros.util.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BeerListFragment : Fragment(R.layout.fragment_beer_list) {

    private val viewModel by viewModels<BeerListViewModel>()
    private var binding: FragmentBeerListBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentBeerListBinding.bind(view)
        val beerAdapter = BeerPagingAdapter()
        binding?.apply {
            list.apply {
                itemAnimator = null
                setHasFixedSize(true)
                adapter = beerAdapter
            }

            swipeRefresh.setOnRefreshListener {
                beerAdapter.refresh()
                swipeRefresh.isRefreshing = false
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.beersStateFlow.collectLatest { result ->
                when(result) {
                    is Result.Success -> {
                        binding?.progressBar?.isVisible = false
                        beerAdapter.submitData(result.data)
                    }
                    is Result.Loading -> {
                        binding?.progressBar?.isVisible = true
                    }
                    is Result.Error -> {
                        binding?.progressBar?.isVisible = false
                        Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onRefresh()
    }
}