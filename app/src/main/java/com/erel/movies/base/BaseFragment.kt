package com.erel.movies.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.erel.movies.R
import com.erel.movies.domain.model.ConnectionFailure
import com.erel.movies.domain.model.Failure
import com.erel.movies.domain.model.ServerFailure
import com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment() {

    abstract val viewModel: BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        inflater.inflate(getLayoutResource(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        observeViewModel()
    }

    @LayoutRes
    abstract fun getLayoutResource(): Int

    abstract fun initView(view: View)

    open fun observeViewModel() = with(viewModel) {
        progressLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { isVisible ->
                (activity as? BaseActivity)?.apply {
                    setProgress(isVisible)
                }
            }
        })
        errorLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ServerFailure -> showSnackbar(it.message)
                is ConnectionFailure -> showSnackbar(getString(R.string.error_message_connectivity))
                else -> showSnackbar(it.message ?: getString(R.string.error_message_unknown))
            }
        })
    }

    private fun showSnackbar(message: String) {
        view?.let {
            Snackbar.make(it, message, LENGTH_SHORT).show()
        }
    }
}