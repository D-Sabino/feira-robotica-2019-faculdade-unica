package br.com.luminaspargere.remotino

import base.corelibrary.BaseApplication
import base.corelibrary.presentation.AppView
import br.com.luminaspargere.remotino.presentation.MainActivity
import com.github.icarohs7.unoxandroidarch.UnoxAndroidArch
import org.koin.core.module.Module
import org.koin.dsl.module

@Suppress("unused")
class AppMain : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        AppView.MAIN = MainActivity::class
        UnoxAndroidArch.isDebug = BuildConfig.DEBUG
    }

    override fun onCreateKoinModules(): List<Module> {
        // val database = buildDatabase<AppDatabase>().build()

        return listOf(module {
            //single { database }
        })
    }
}
