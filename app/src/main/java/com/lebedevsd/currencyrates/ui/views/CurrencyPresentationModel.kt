package com.lebedevsd.currencyrates.ui.views

data class CurrencyPresentationModel(
    val title: String,
    val description: String,
    val value: Double,
    val exchangeRate: Double
) : ListItem {



    override val listId: String
        get() = this.title

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CurrencyPresentationModel

        if (title != other.title) return false
        if (description != other.description) return false
        if (exchangeRate != other.exchangeRate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + value.hashCode()
        result = 31 * result + exchangeRate.hashCode()
        return result
    }
}