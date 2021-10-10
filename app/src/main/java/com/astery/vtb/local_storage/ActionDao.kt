package com.astery.vtb.local_storage

import androidx.room.*
import com.astery.vtb.model.ActionEntity
import com.astery.vtb.model.ActionType
import com.astery.vtb.model.ChoresHistory
import com.astery.vtb.model.InvestHistory
import io.reactivex.Single

@Dao
interface ActionDao {

    @Query("SELECT * FROM actionentity WHERE actionType in (:type)")
    fun getActionsWithType(type:List<ActionType>): Single<List<ActionEntity>>

    @Query("SELECT * FROM actionentity WHERE purchasedCount>0")
    fun getSelectedActions(): Single<List<ActionEntity>>


    @Query("SELECT * FROM actionentity WHERE id=:id")
    fun getAction(id:Int): Single<List<ActionEntity>>

    @Query("SELECT * FROM actionentity")
    fun getActions(): Single<List<ActionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItem(item: ActionEntity?)


    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateActions(item: ActionEntity?)


    @Query("SELECT * FROM investhistory ORDER BY iteration")
    fun getInvestHistory(): Single<List<InvestHistory>>
    @Query("SELECT * FROM choreshistory ORDER BY iteration")
    fun getChoresHistory(): Single<List<ChoresHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItem(item: InvestHistory?)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItem(item: ChoresHistory?)

    @Delete
    fun deleteItem(item:InvestHistory)

    @Delete
    fun deleteItem(item:ChoresHistory)
}
