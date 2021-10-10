package com.astery.vtb.model

import com.astery.vtb.R
import com.astery.vtb.repository.preferences.TransportPreferences

abstract class LifeItemHolder(val titleId:Int) {
    val iconId:Int
        get() = selectedItem.iconI
    lateinit var selectedItem:LifeItem
    abstract fun getAvailableItems():List<LifeItem>
    abstract fun getInitialItem():LifeItem
}

/*
object class LifeItems{
    companion object{
        val list:List<LifeItemHolder>
        get() {
            val l = ArrayList<LifeItemHolder>()
            l.add(HouseHolder())
            l.add(FamilyHolder())
            return l
        }
    }
}

 */
/*

class HouseHolder:LifeItemHolder(R.string.life_item_category_house){
    override fun getAvailableItems(): List<LifeItem> {
        val list = ArrayList<LifeItem>()
        list.add(LifeItem(GARAGE_ID, R.string.life_item_house_0, R.drawable.life_item_house_0))
        list.add(LifeItem(ONE_ROOM_ID, R.string.life_item_house_1, R.drawable.life_item_house_1))
        list.add(LifeItem(TWO_ROOMS_ID, R.string.life_item_house_2, R.drawable.life_item_house_2))
        list.add(LifeItem(PENTHOUSE_ID, R.string.life_item_house_3, R.drawable.life_item_house_3))
        return list
    }

    override fun getInitialItem(): LifeItem {
        return LifeItem(GARAGE_ID, R.string.life_item_house_0, R.drawable.life_item_house_0)
    }

    companion object{
        const val GARAGE_ID = 1112121
        const val ONE_ROOM_ID = 2321312
        const val TWO_ROOMS_ID = 23232312
        const val PENTHOUSE_ID = 55332
    }
}

class FamilyHolder:LifeItemHolder(R.string.life_item_category_family){
    override fun getAvailableItems(): List<LifeItem> {
        val list = ArrayList<LifeItem>()
        list.add(LifeItem(ALONE_ID, R.string.life_item_family_0, R.drawable.life_item_family_0))
        list.add(LifeItem(GIRLFRIEND_ID, R.string.life_item_family_1, R.drawable.life_item_family_1))
        list.add(LifeItem(WIFE_ID, R.string.life_item_family_2, R.drawable.life_item_family_2))
        list.add(LifeItem(HAPPY_WIFE_ID, R.string.life_item_family_3, R.drawable.life_item_family_3))
        return list
    }

    override fun getInitialItem(): LifeItem {
        return LifeItem(ALONE_ID, R.string.life_item_family_0, R.drawable.life_item_house_0)
    }

    companion object{
        const val ALONE_ID = 333
        const val GIRLFRIEND_ID = 1111
        const val WIFE_ID = 444
        const val HAPPY_WIFE_ID = 55332
    }
}


class ASDASD{
    fun endIteration(){
        val lifeItems = LifeItems.list
        for (i in lifeItems){
            for (g in i.selectedItem.good) {
                if (g.gainType == GainTime.everyMonth)
                    addValue(g.valueType, g.value, true)
            }
            for (g in i.selectedItem.bad) {
                if (g.gainType == GainTime.everyMonth)
                    addValue(g.valueType, g.value, false)
            }
        }
    }

    fun addValue(value:GameValue, money:Int, add:Boolean){
        val actual = TransportPreferences.getValue(context, value)
        if (add)
            TransportPreferences.setValue(TODO(), value, money+actual )
        else
            TransportPreferences.setValue(TODO(), value, actual-money)
    }
}

 */

abstract class LiveAction{

}


data class GainType(
    val valueType:GameValue,
    val value:Int,
    val gainType:GainTime
)

enum class GameValue{
    money,
    hapiness,
    iteration,
    level
}


class GameItemHolder{
    companion object {
        fun actionTypes(difficult: Int): List<ActionType> {
            val list = ArrayList<ActionType>()
            when (difficult) {
                1 -> {
                    list.add(ActionType.Metal)
                }
                2 -> {
                    list.add(ActionType.Fond)
                    list.add(ActionType.Stocks)
                }
                3 -> {
                    list.add(ActionType.Stocks)
                    list.add(ActionType.Fuch)
                }
            }
            return list
        }
    }
}

enum class LifeValue{
    house,
    wife,
    food
}


enum class GainTime{
    now,
    everyMonth
}

const val salary = 100000

class LifeItem(val itemId:Int, val titleId:Int, val iconI:Int, val imageId:Int, val good:List<GainType>, val bad:List<GainType>)
