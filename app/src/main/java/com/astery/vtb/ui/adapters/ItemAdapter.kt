package com.astery.vtb.ui.adapters

import android.graphics.Bitmap
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.transition.TransitionManager
import com.astery.copying.forms_adapter.utils.JobListener
import com.astery.copying.forms_adapter.utils.SD
import com.astery.vtb.R
import com.astery.vtb.databinding.UnitProductBinding
import com.astery.vtb.model.ActionEntity
import com.astery.vtb.ui.utils.VS
import com.google.android.material.internal.VisibilityAwareImageButton
import com.google.android.material.transition.MaterialFadeThrough
import java.text.DecimalFormat
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.properties.Delegates


class ItemsAdapter(private val parent: Fragment, private val layout:ViewGroup,
                   private val listener:BlockListener?, private val style:ItemStyle) {
    private val form:LinearLayout = parent.layoutInflater.inflate(R.layout.form, null) as LinearLayout
    private var simpleItems: ArrayList<ItemElement> = ArrayList()
    private var elements: ArrayList<Presentable> = ArrayList()
    private var styles: MutableMap <String, Int> = HashMap()
    private var actions:List<BlockListener> = ArrayList()
    fun setActions(actions:List<BlockListener>){
        this.actions = actions
    }

    init{
        layout.addView(form)
    }

    fun addItem(elementSimple: Presentable):ItemsAdapter{
        elements.add(elementSimple)
        return this
    }

    fun clear(){
        elements.clear()
    }

    fun addItems(elements:List<Presentable>):ItemsAdapter{
        this.elements.addAll(elements)
        return this
    }


    fun draw(){
        layout.removeAllViews()
        form.removeAllViews()
        for (i in elements.indices){
            if (simpleItems.size <= i){
                simpleItems.add(getItem(elements[i], style, parent))
                simpleItems[i].pos = i
                simpleItems[i].draw()
                if (!simpleItems[i].view.hasOnClickListeners())
                    simpleItems[i].view.setOnClickListener { listener?.click(i) }
                if (actions.isNotEmpty())
                    simpleItems[i].actions = actions
            }
            form.addView(simpleItems[i].view)
        }
        layout.addView(form)
    }

    private fun getItem(simpleItem: Presentable, style:ItemStyle, parent:Fragment):ItemElement{
        return when {
            (simpleItem as? TextElementSimple.TextPresentable) != null ->
                TextElementSimple(parent, simpleItem.getText(), (simpleItem as? TextElementSimple.TextPresentable)!!.getDescription(), style)
            (simpleItem as? CheckElementSimple.CheckPresentable) != null ->
                CheckElementSimple(parent, simpleItem.getText(), (simpleItem as? CheckElementSimple.CheckPresentable)!!.getChecked(), style)
            (simpleItem as? IconElementSimple.IconPresentable) != null ->
                IconElementSimple(parent, simpleItem.getText(), (simpleItem as? IconElementSimple.IconPresentable)!!.getIconId(), style)
            (simpleItem as? ImageElementSimple.ImagePresentable) != null ->
                ImageElementSimple(parent, simpleItem.getText(), (simpleItem as? ImageElementSimple.ImagePresentable)!!.getImage(), style)
            else ->
                ActionElementSimple(parent, simpleItem as ActionEntity)

        }
    }

    interface BlockListener{
        fun click(pos:Int)
    }

}


/**
 * {params: helperOnTop (position of the second element. True - on top. False - center)
 * isTextOnLeft - true - left, false - right
 * }
 * */
class ItemStyle(val helperOnTop:Boolean=false, val isTextOnLeft:Boolean=true, val style:Int=-1)



interface Presentable{
    fun getText():String
}
abstract class ItemElement(protected val parent:Fragment, protected val title:String){
    var pos by Delegates.notNull<Int>()
    abstract val view:View
    protected val textView:TextView = TextView(ContextThemeWrapper(parent.requireContext(), R.style.text), null, 0)
    abstract fun draw()
    var actions:List<ItemsAdapter.BlockListener> = ArrayList()
    fun setAction(actions: List<ItemsAdapter.BlockListener>){
        this.actions = actions
    }
}

abstract class SimpleItemElement(parent:Fragment, title:String, protected val style:ItemStyle):ItemElement(parent, title){
    final override val view:View = parent.layoutInflater.inflate(R.layout.unit_item_layout, null)
    protected val content:LinearLayout = view.findViewById(R.id.content)
    protected abstract var helper:View

    init{
        textView.text = title
    }

    override fun draw(){
        if (style.isTextOnLeft) {
            content.addView(textView)
            content.addView(helper)
            setForRight(helper)
            setForLeft(textView)
        }
        if (!style.isTextOnLeft) {
            content.addView(helper)
            content.addView(textView)
            setForRight(textView)
            setForLeft(helper)
        }

        textView.layoutParams.width = 0
        (textView.layoutParams as LinearLayout.LayoutParams).weight = 1F

        if (!style.helperOnTop){
            (helper.layoutParams as LinearLayout.LayoutParams).gravity = Gravity.CENTER_VERTICAL
            (textView.layoutParams as LinearLayout.LayoutParams).gravity = Gravity.CENTER_VERTICAL
        } else{
            textView.setPadding(0,parent.requireContext().resources.getDimension(R.dimen.icon_padding).toInt(),0,0)
        }
    }

    private fun setForRight(view:View){
        (view.layoutParams as ViewGroup.MarginLayoutParams).setMargins(
            parent.requireContext().resources.getDimension(R.dimen.item_content_align).toInt(), 0,
            parent.requireContext().resources.getDimension(R.dimen.item_content_padding).toInt(),0)
        (view.layoutParams as LinearLayout.LayoutParams).gravity = Gravity.END
        //(view.layoutParams as LinearLayout.LayoutParams).weight = 1F
    }
    private fun setForLeft(view:View){
        (view.layoutParams as LinearLayout.LayoutParams).gravity = Gravity.START
        (view.layoutParams as LinearLayout.LayoutParams).weight = 0F
        (view.layoutParams as ViewGroup.MarginLayoutParams).setMargins(
            parent.requireContext().resources.getDimension(R.dimen.item_content_padding).toInt(), 0,0,0)
    }


}

class TextElementSimple(parent:Fragment, title:String, description:String, style:ItemStyle):SimpleItemElement(parent,title,style){
    override var helper: View = TextView(ContextThemeWrapper(parent.requireContext(), R.style.text), null, 0)

    init{
        (helper as TextView).text = description
        helper.setPadding(0,parent.requireContext().resources.getDimension(R.dimen.icon_padding).toInt(),0,0)
    }

    interface TextPresentable:Presentable{
        fun getDescription():String
    }
}

class IconElementSimple(parent:Fragment, title:String, icon:Int, style:ItemStyle):SimpleItemElement(parent,title,style){
    //override var helper: View = ImageView(ContextThemeWrapper(parent.requireContext(), R.style.item_unit_icon), null, 0)
    override var helper:View = parent.layoutInflater.inflate(R.layout.icon, null)


    init{
        (helper as ImageView).setImageDrawable(SD.getDr(icon, parent.requireContext()))
    }

    interface IconPresentable:Presentable{
        fun getIconId():Int
    }

    override fun draw() {
        super.draw()
        helper.layoutParams.height = parent.requireContext().resources.getDimension(R.dimen.icon_unit_size).toInt()
        helper.layoutParams.width = parent.requireContext().resources.getDimension(R.dimen.icon_unit_size).toInt()
    }
}

class CheckElementSimple(parent:Fragment, title:String, isChecked:Boolean, style:ItemStyle):SimpleItemElement(parent,title,style){
    override var helper:View = parent.layoutInflater.inflate(R.layout.checkbox, null)

    init{
        (helper as CheckBox).isChecked = isChecked
        helper.isClickable = false
        helper.isEnabled = false
    }

    interface CheckPresentable:Presentable{
        fun getChecked():Boolean
    }
}

class ImageElementSimple(parent:Fragment, title:String, image: Bitmap, style:ItemStyle):SimpleItemElement(parent,title,style){
    override var helper: View = ImageView(ContextThemeWrapper(parent.requireContext(), R.style.item_unit_image), null, 0)

    init{
        (helper as ImageView).setImageBitmap(image)
    }

    interface ImagePresentable:Presentable{
        fun getImage():Bitmap
    }
}


class ActionElementSimple(parent: Fragment, private val item: ActionEntity):ItemElement(parent,item.getText()) {
    private val binding = UnitProductBinding.inflate(parent.layoutInflater)
    override val view: View = binding.root

    private var opened = false

    override fun draw() {
        binding.text.text = item.name
        binding.price.text = item.actualPrice.toInt().toString()
        binding.old.text = item.oldPrice.toInt().toString()
        binding.difference.text = getDifference()

        binding.opened.text.text = item.name
        binding.opened.description.text = item.description

        binding.difference.setTextColor(getColor())
        binding.old.setTextColor(getColor())
        binding.price.setTextColor(getColor())

        if (item.purchasedCount > 0){
            binding.opened.send.visibility = VISIBLE
        }

        if (isProfit())
            SD.setDrawable(binding.indicator, R.drawable.ic_indicator_high, parent.requireContext())
        else
            SD.setDrawable(binding.indicator, R.drawable.ic_indicator_low, parent.requireContext())



        val itemAdapter = ItemsAdapter(parent, binding.opened.comments, null, ItemStyle())
        itemAdapter.addItem(ActionItem("Текущая цена", item.actualPrice.toInt().toString()))
            .addItem(ActionItem("Старая цена", item.oldPrice.toInt().toString()))
            .addItem(ActionItem("Изменение", getDifference()))

        if (item.purchasedCount > 0)
            itemAdapter.addItem(ActionItem("В наличии", item.purchasedCount.toString()))

        itemAdapter.draw()


        binding.opened.buy.setOnClickListener{
            actions[0].click(pos)
        }
        binding.opened.send.setOnClickListener{
            actions[1].click(pos)
        }



        view.setOnClickListener {
            val fadeThrough = MaterialFadeThrough()
            TransitionManager.beginDelayedTransition(binding.root, fadeThrough)
            opened = !opened
            binding.closed.visibility = VS.get(!opened)
            binding.opened.opened.visibility = VS.get(opened)
        }

    }

    private fun getDifference():String{
        val int = item.actualPrice / (item.oldPrice / 100)

        Log.i("main", "here $int")

        return if (isProfit())
            "+${DecimalFormat( "#.###" ).format(int-100)}%"
        else
            "-${DecimalFormat( "#.###" ).format(int)}%"
    }


    private fun isProfit():Boolean{
        return (item.oldPrice / (item.actualPrice / 100)) <= 100
    }

    private fun getColor(): Int {
        if (isProfit()) return parent.requireContext().getColor(R.color.good_dynamic)
        return parent.requireContext().getColor(R.color.bad_dynamic)
    }

    class ActionItem(private val title: String, private val desc: String) :
        TextElementSimple.TextPresentable {
        override fun getText(): String {
            return title
        }

        override fun getDescription(): String {
            return desc
        }
    }
}