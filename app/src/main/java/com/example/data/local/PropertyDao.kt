package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PropertyDao {
    @Query("SELECT propertyId FROM favorites")
    fun getAllFavoriteIds(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE propertyId = :propertyId")
    suspend fun removeFavorite(propertyId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE propertyId = :propertyId)")
    suspend fun isFavorite(propertyId: String): Boolean

    @Query("SELECT * FROM saved_mortgages ORDER BY savedAt DESC")
    fun getAllSavedMortgages(): Flow<List<SavedMortgageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMortgage(entity: SavedMortgageEntity)

    @Query("DELETE FROM saved_mortgages WHERE id = :id")
    suspend fun deleteSavedMortgage(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInquiry(inquiry: PropertyInquiryEntity)

    @Query("SELECT * FROM property_inquiries ORDER BY timestamp DESC")
    fun getAllInquiries(): Flow<List<PropertyInquiryEntity>>
}
