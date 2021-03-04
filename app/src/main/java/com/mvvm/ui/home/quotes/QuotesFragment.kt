package com.mvvm.ui.home.quotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.R
import com.mvvm.data.db.entities.Quote
import com.mvvm.util.Coroutines
import com.mvvm.util.hide
import com.mvvm.util.show
import com.mvvm.util.toast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_quotes.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class QuotesFragment : Fragment(), KodeinAware {
    override val kodein by kodein()
    private lateinit var quotesViewModel: QuotesViewModel
    private val factory: QuotesViewModelFactory by instance ()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       // val binding:FragmentQuotesBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_quotes, container, false)
       val root = inflater.inflate(R.layout.fragment_quotes, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        quotesViewModel =
                ViewModelProvider(this,factory).get(QuotesViewModel::class.java)
        bindUI()


    }

    private fun bindUI() =Coroutines.main {
        progress_bar.show()
        quotesViewModel.quotes.await().observe(this, Observer {
            progress_bar.hide()
            initRecycerView(it.toQuoteItem())
        })
    }


    private fun initRecycerView(quoteItem: List<QuoteItem>) {
       val groupAdapter= GroupAdapter<ViewHolder>().apply {
           addAll(quoteItem)
       }
        recyclerview.apply {
            layoutManager=LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter=groupAdapter
        }
    }

    private fun List<Quote>.toQuoteItem() : List<QuoteItem>{
        return this.map {
            QuoteItem(it)
        }
    }
}