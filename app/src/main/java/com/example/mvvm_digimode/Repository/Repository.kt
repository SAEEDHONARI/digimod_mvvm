package com.example.mvvm_digimode.Repository

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class Repository {

    object Customeresponse{
        fun <T:Any> Request (api:Single<T>,com:CompositeDisposable,call:(T)->Unit){
            com.add(api
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<T>(){
                    override fun onSuccess(t: T) {
                        call.invoke(t)
                    }

                    override fun onError(e: Throwable) {
                    }

                })
            )
        }
    }
}