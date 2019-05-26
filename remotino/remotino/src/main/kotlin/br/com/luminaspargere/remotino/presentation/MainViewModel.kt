package br.com.luminaspargere.remotino.presentation

import androidx.lifecycle.MutableLiveData
import br.com.luminaspargere.remotino.domain.Config
import com.github.icarohs7.unoxandroidarch.presentation.viewmodel.BaseScopedViewModel

class MainViewModel : BaseScopedViewModel() {
    val addressLiveData: MutableLiveData<String> = MutableLiveData(Config.address)
}