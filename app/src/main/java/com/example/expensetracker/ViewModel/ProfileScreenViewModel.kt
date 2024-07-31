package com.example.expensetracker.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.Repository.ProfileRepository
import com.example.expensetracker.data.model.ProfileEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProfileScreenViewModel(private val repository: ProfileRepository): ViewModel() {

    val allProfile: Flow<List<ProfileEntity>> = repository.profileDetails

    suspend fun insertProfile(profileEntity: ProfileEntity){
        viewModelScope.launch {
            repository.insertProfile(profileEntity)
        }
    }
    suspend fun updateProfile(profileEntity: ProfileEntity){
        viewModelScope.launch {
            repository.updateProfile(profileEntity)
        }
    }
    suspend fun deleteProfile(profileEntity: ProfileEntity){
        viewModelScope.launch {
            repository.deleteProfile(profileEntity)
        }
    }
}
