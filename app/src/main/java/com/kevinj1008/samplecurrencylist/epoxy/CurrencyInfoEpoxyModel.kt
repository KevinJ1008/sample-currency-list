package com.kevinj1008.samplecurrencylist.epoxy

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.kevinj1008.basecore.base.KotlinEpoxyHolder
import com.kevinj1008.samplecurrencylist.R

@EpoxyModelClass
abstract class CurrencyInfoEpoxyModel : EpoxyModelWithHolder<CurrencyInfoEpoxyModel.ViewHolder>() {

    @EpoxyAttribute
    var name: String? = null

    @EpoxyAttribute
    var symbol: String? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var clickListener: (() -> Unit)? = null

    override fun getDefaultLayout(): Int {
        return R.layout.item_currency_info
    }

    override fun bind(holder: ViewHolder) {
        super.bind(holder)
        name?.takeIf { it.isNotEmpty() }?.apply {
            holder.textAvatar.text = this.first().toString()
        }
        holder.textName.text = name
        holder.textSymbol.text = symbol
        holder.view.setOnClickListener {
            clickListener?.invoke()
        }
    }

    override fun unbind(holder: ViewHolder) {
        super.unbind(holder)
        holder.view.setOnClickListener(null)
    }

    class ViewHolder : KotlinEpoxyHolder() {
        val textAvatar by bind<TextView>(R.id.text_avatar)
        val textName by bind<TextView>(R.id.text_name)
        val textSymbol by bind<TextView>(R.id.text_symbol)
    }
}