package br.com.luminaspargere.remotino.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.toPublisher
import arrow.core.Try
import base.corelibrary.domain.extensions.textOrEmpty
import base.corelibrary.domain.logExecution
import br.com.luminaspargere.remotino.R
import br.com.luminaspargere.remotino.data.api.ApiService
import br.com.luminaspargere.remotino.databinding.ActivityMainBinding
import br.com.luminaspargere.remotino.domain.Config
import com.github.icarohs7.unoxandroidarch.extensions.createRetrofitService
import com.github.icarohs7.unoxandroidarch.extensions.setupAndroidSchedulers
import com.github.icarohs7.unoxandroidarch.presentation.activities.BaseBindingActivity
import com.github.icarohs7.unoxcore.extensions.valueOr
import io.reactivex.Flowable
import io.sellmair.disposer.disposeBy
import io.sellmair.disposer.onDestroy
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {
    private val viewmodel: MainViewModel by viewModels()

    private val service: ApiService
        get() {
            val address = "http://${viewmodel.addressLiveData.valueOr("")}"
            return createRetrofitService(address)
        }

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)
        Flowable.fromPublisher(viewmodel.addressLiveData.toPublisher(this))
                .setupAndroidSchedulers()
                .debounce(2, TimeUnit.SECONDS)
                .subscribe { Config.address = it }
                .disposeBy(onDestroy)
        setupBinding()
    }

    private fun setupBinding(): Unit = with(binding) {
        address = viewmodel.addressLiveData
        setForwardHandler { launch { forward() } }
        setRightHandler { launch { right() } }
        setBackwardHandler { launch { backward() } }
        setLeftHandler { launch { left() } }
        setStopHandler { launch { stop() } }
    }

    private suspend fun forward() = Try { service.forward() }.let(::logExecution)
    private suspend fun right() = Try { service.right() }.let(::logExecution)
    private suspend fun backward() = Try { service.backward() }.let(::logExecution)
    private suspend fun left() = Try { service.left() }.let(::logExecution)
    private suspend fun stop() = Try { service.stop() }.let(::logExecution)

    override fun getLayout(): Int {
        return R.layout.activity_main
    }
}