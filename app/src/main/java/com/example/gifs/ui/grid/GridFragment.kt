package com.example.gifs.ui.grid


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.gifs.R
import com.example.gifs.data.Gif
import kotlinx.android.synthetic.main.fragment_grid.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val IDLE_INDEX = 0
private const val SUCCESS_INDEX = 1

class GridFragment : Fragment(R.layout.fragment_grid) {
    private val gridViewModel: GridViewModel by viewModel()
    private val gridAdapter = GridAdapter(this::gifClicked)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gifsList.adapter = gridAdapter
        configureSearch()
    }

    private fun configureSearch() {
        search.setOnClickListener { performSearch() }
        searchInput.editText?.apply {
            setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch()
                    return@OnEditorActionListener true
                }
                false
            })
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    gridAdapter.submitList(null)
                    gridAnimator.displayedChild = IDLE_INDEX
                }
            }
        }
    }

    private fun performSearch() {
        searchInput.editText?.let {
            gridViewModel.search(it.text.toString())
            it.clearFocus()
        }
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onResume() {
        super.onResume()
        addObserver()
        recoverQueryString()
    }

    private fun addObserver() {
        gridViewModel.gridViewState.observe(this, Observer {
            when (it) {
                is GridViewState.Success -> {
                    gridAnimator.displayedChild = SUCCESS_INDEX
                    gridAdapter.submitList(it.gifs)
                }
            }
        })
    }

    private fun recoverQueryString() {
        gridViewModel.getSearchQuery()?.let {
            searchInput.editText?.text =
                Editable.Factory.getInstance().newEditable(it)
        }
    }

    private fun gifClicked(gif: Gif) {
        findNavController().navigate(GridFragmentDirections.actionGridFragmentToDetailsFragment(gif))
    }
}
