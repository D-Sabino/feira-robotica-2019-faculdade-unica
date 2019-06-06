package br.com.luminaspargere.mazerunner.domain

import br.com.luminaspargere.mazerunner.domain.extensions.save
import com.github.icarohs7.unoxcore.extensions.coroutines.onBackground
import com.github.icarohs7.unoxcore.extensions.toIntOr
import javafx.beans.property.IntegerProperty
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.inject
import tornadofx.*
import java.util.Properties
import kotlin.reflect.KProperty

object Config {
    private val props: Properties by Injector.inject()

    val srcTipHueStart = intProperty(0)
    val srcTipHueEnd = intProperty(0)
    val srcTipSatStart = intProperty(0)
    val srcTipSatEnd = intProperty(0)
    val srcTipValueStart = intProperty(0)
    val srcTipValueEnd = intProperty(0)

    val srcHueStart = intProperty(0)
    val srcHueEnd = intProperty(0)
    val srcSatStart = intProperty(0)
    val srcSatEnd = intProperty(0)
    val srcValueStart = intProperty(0)
    val srcValueEnd = intProperty(0)

    val dstHueStart = intProperty(0)
    val dstHueEnd = intProperty(0)
    val dstSatStart = intProperty(0)
    val dstSatEnd = intProperty(0)
    val dstValueStart = intProperty(0)
    val dstValueEnd = intProperty(0)

    suspend fun init() {
        onBackground {
            srcTipHueStart.setup(::srcTipHueStart)
            srcTipHueEnd.setup(::srcTipHueEnd)
            srcTipSatStart.setup(::srcTipSatStart)
            srcTipSatEnd.setup(::srcTipSatEnd)
            srcTipValueStart.setup(::srcTipValueStart)
            srcTipValueEnd.setup(::srcTipValueEnd)

            srcHueStart.setup(::srcHueStart)
            srcHueEnd.setup(::srcHueEnd)
            srcSatStart.setup(::srcSatStart)
            srcSatEnd.setup(::srcSatEnd)
            srcValueStart.setup(::srcValueStart)
            srcValueEnd.setup(::srcValueEnd)

            dstHueStart.setup(::dstHueStart)
            dstHueEnd.setup(::dstHueEnd)
            dstSatStart.setup(::dstSatStart)
            dstSatEnd.setup(::dstSatEnd)
            dstValueStart.setup(::dstValueStart)
            dstValueEnd.setup(::dstValueEnd)
        }
    }

    private fun IntegerProperty.setup(prop: KProperty<*>) {
        val name = prop.name
        set(props[name]?.toString()?.toIntOr(0) ?: 0)
        addListener { _, _, v ->
            GlobalScope.launch {
                props.setProperty(name, "$v")
                props.save()
            }
        }
    }
}