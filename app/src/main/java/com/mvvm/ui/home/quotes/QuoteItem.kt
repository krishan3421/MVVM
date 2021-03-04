package com.mvvm.ui.home.quotes

import com.mvvm.R
import com.mvvm.data.db.entities.Quote
import com.mvvm.databinding.ItemQuoteBinding
import com.xwray.groupie.databinding.BindableItem

class QuoteItem(private val quote:Quote) : BindableItem<ItemQuoteBinding>(){
    override fun getLayout()= R.layout.item_quote

    override fun bind(viewBinding: ItemQuoteBinding, position: Int) {
        viewBinding.setQuote(quote)
    }


}