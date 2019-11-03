package com.lebedevsd.currencyrates.ui.views

data class CurrencyPresentationModel(
    val title: String,
    val description: String,
    val value: Double,
    val exchangeRate: Double,
    val flagString: String
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
        if (flagString != other.flagString) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + value.hashCode()
        result = 31 * result + exchangeRate.hashCode()
        result = 31 * result + flagString.hashCode()
        return result
    }
}