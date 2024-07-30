package com.example.expensetracker.Repository

import com.example.expensetracker.data.Dao.ProfileDao
import com.example.expensetracker.data.model.ProfileEntity
import kotlinx.coroutines.flow.Flow

class ProfileRepository(private val profileRepo: ProfileDao) {

    val profileDetails: Flow<List<ProfileEntity>> = profileRepo.getProfile()

    suspend fun insertProfile(profileEntity: ProfileEntity){
        profileRepo.insertProfile(profileEntity)
    }
    suspend fun updateProfile(profileEntity: ProfileEntity){
        profileRepo.updateProfile(profileEntity)
    }
    suspend fun deleteProfile(profileEntity: ProfileEntity){
        profileRepo.deleteProfile(profileEntity)
    }

}