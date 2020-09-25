package com.android.githubuserfinder.presentation.main

import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.githubuserfinder.Constants
import com.android.githubuserfinder.R
import com.android.githubuserfinder.databinding.ActivityMainBinding
import com.android.githubuserfinder.ext.addTo
import com.android.githubuserfinder.presentation.BaseActivity
import com.android.githubuserfinder.presentation.adapter.UserListAdapter
import com.android.githubuserfinder.presentation.listeners.InfiniteScrollListener
import com.android.githubuserfinder.presentation.wrapper.WrapperObserver
import com.android.githubuserfinder.presentation.wrapper.WrapperTextWatcher
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: MainViewModel

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        screenComponent.inject(this)
        binding.viewModel = viewModel

        initSubject()
    }

    override fun onResume() {
        super.onResume()
        viewModel.showErrorGettingUser.observe()
            .subscribe { showMessage(it) }
            .addTo(disposables)
    }

    override fun onPause() {
        disposables.clear()
        super.onPause()
    }

    override fun onDestroy() {
        viewModel.clearDisposable()
        super.onDestroy()
    }

    private fun showMessage(errorState: ErrorState) {
        var message = ""
        when (errorState) {
            ErrorState.DATA_NOT_FOUND -> {
                message = getString(R.string.label_data_not_found)
            }
            ErrorState.UNEXPECTED_ERROR -> {
                message = getString(R.string.label_error_message)
            }
        }
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.label_dialog_title))
            .setMessage(message)
            .setNeutralButton(getString(R.string.label_positive_button)) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun initSubject() {
        subject = PublishSubject.create()
        subject.debounce(Constants.DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                object : WrapperObserver<String>() {
                    override fun onNext(searchKeywords: String) {
                        viewModel.searchUsername(searchKeywords)
                    }
                }
            )
    }

    companion object {
        /**
         * bindRecyclerView
         * this is referenced in activity_main.xml as 'app:adapter={@viewModel}'
         */
        @JvmStatic
        @BindingAdapter("adapter")
        fun bindRecyclerView(recyclerView: RecyclerView, viewModel: MainViewModel) {

            val layoutManager = LinearLayoutManager(recyclerView.context)
            val adapter = UserListAdapter(viewModel.userList)

            recyclerView.layoutManager = layoutManager
            recyclerView.addItemDecoration(
                DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
            )
            recyclerView.addOnScrollListener(
                InfiniteScrollListener(
                    { viewModel.searchUsername() },
                    layoutManager
                )
            )
            recyclerView.adapter = adapter

        }


        private lateinit var subject: PublishSubject<String>

        /**
         * bindOnTextChanged
         * this is referenced in activity_main.xml as 'app:onTextChanged={@viewModel}
         */
        @JvmStatic
        @BindingAdapter("onTextChanged")
        fun bindOnTextChanged(editText: EditText, viewModel: MainViewModel) {

            editText.addTextChangedListener(
                object : WrapperTextWatcher(editText) {
                    override fun onTextChanged(s: CharSequence?, start: Int,
                        before: Int, count: Int
                    ) {
                        viewModel.clearDisposable()
                        viewModel.clearUserList()
                        viewModel.hideProgressBar()
                        viewModel.reInitPageValue()
                    }

                    override fun afterTextChanged(editable: Editable?) {
                        if (editable.isNullOrEmpty()) return
                        if (editable.length < 3) return
                        subject.onNext(editable.toString())
                    }

                }
            )

        }

    }

}