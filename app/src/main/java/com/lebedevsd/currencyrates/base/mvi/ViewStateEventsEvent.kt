package com.lebedevsd.currencyrates.base.mvi

import android.os.Parcelable
import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ViewStateErrorEvent(
    val payload: Throwable,
    override val isConsumed: AtomicBoolean = AtomicBoolean(false)
) : SingleEvent<Throwable>(payload), Parcelable {

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = payload.hashCode()
        result = 31 * result + isConsumed.hashCode()
        return result
    }
}

@Parcelize
data class ViewStateOfflineEvent(
    val payload: String,
    override val isConsumed: AtomicBoolean = AtomicBoolean(false)
) : SingleEvent<String>(payload), Parcelable {

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = payload.hashCode()
        result = 31 * result + isConsumed.hashCode()
        return result
    }
}

abstract class SingleEvent<T>(
    private val argument: T,
    protected open val isConsumed: AtomicBoolean = AtomicBoolean(false)
) {

    private fun isConsumed(setAsConsumed: Boolean = false) = isConsumed.getAndSet(setAsConsumed)

    fun consume(action: (T) -> Unit) {
        if (!isConsumed(true)) {
            action.invoke(argument)
        }
    }
}
