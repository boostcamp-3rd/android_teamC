package kr.co.connect.boostcamp.livewhere.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import kr.co.connect.boostcamp.livewhere.data.entity.RecentSearchEntity

@Dao
interface RecentSearchDAO {
    @Query("SELECT * FROM recent_search")
    fun getAll(): Single<List<RecentSearchEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecentSearch(recentSearch:RecentSearchEntity)
}