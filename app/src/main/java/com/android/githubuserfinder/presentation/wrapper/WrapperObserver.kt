package com.android.githubuserfinder.presentation.wrapper

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class WrapperObserver<T> : Observer<T> {

    override fun onSubscribe(d: Disposable) {
        // default implementation ignored
    }

    override fun onError(e: Throwable) {
        // default implementation ignored
    }

    override fun onComplete() {
        // default implementation ignored
    }

}