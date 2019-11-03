package com.lebedevsd.currencyrates.ui.currency

import com.lebedevsd.currencyrates.ui.base.Image
import com.lebedevsd.currencyrates.ui.base.ListItem
import java.math.BigDecimal
import java.math.RoundingMode

data class CurrencyPresentationModel(
    val title: String,
    val description: String,
    val value: Double,
    val exchangeRate: Double,
    val flagImage: Image
) : ListItem {

    override fun calculatePayload(oldItem: ListItem): Any? {
        return value
    }

    override val listId: String
        get() = this.title

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CurrencyPresentationModel

        if (title != other.title) return false
        if (description != other.description) return false
        if (exchangeRate != other.exchangeRate) return false
        if (flagImage != other.flagImage) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + value.hashCode()
        result = 31 * result + exchangeRate.hashCode()
        result = 31 * result + flagImage.hashCode()
        return result
    }
}

inline fun CurrencyPresentationModel.calculateNewValue(newBaseValue: Double): Double {
    return BigDecimal(newBaseValue * this.exchangeRate).setScale(
        2,
        RoundingMode.HALF_UP
    ).toDouble()
}
