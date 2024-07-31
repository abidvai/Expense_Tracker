package com.example.expensetracker.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel: ViewModel() {
    val auth = FirebaseAuth.getInstance()

    private val _user = MutableLiveData<AuthStatus>()
    val user: MutableLiveData<AuthStatus> = _user

    init {
        checkUser()
    }
    private fun checkUser(){
        if(auth.currentUser != null){
            _user.value = AuthStatus.Authenticated
        }else{
            _user.value = AuthStatus.Unauthenticated
        }
    }

    fun logIn(email: String, password: String){
        if(email.isEmpty()){
            _user.value = AuthStatus.Error("email is in correct")
            return
        }else if(password.isEmpty()){
            _user.value = AuthStatus.Error("Password is in correct")
        }
        _user.value = AuthStatus.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{task ->
                if(task.isSuccessful){
                    _user.value = AuthStatus.Authenticated
                }else{
                    _user.value = AuthStatus.Error(task.exception?.message ?: "Login error")
                }
            }
    }
    fun signUp(email: String, password: String, name: String, phone: String, city: String){
        if(email.isEmpty()){
            _user.value = AuthStatus.Error("email is require")
            return
        }else if(password.isEmpty()){
            _user.value = AuthStatus.Error("Password is require")
        }
        _user.value = AuthStatus.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{task ->
                if(task.isSuccessful){
                    _user.value = AuthStatus.Authenticated
                }else{
                    _user.value = AuthStatus.Error(task.exception?.message ?: "Signup error")
                }
            }
    }
    fun signOut(){
        auth.signOut()
        _user.value = AuthStatus.Unauthenticated
    }
}

sealed class AuthStatus{
    object Authenticated: AuthStatus()
    object Unauthenticated: AuthStatus()
    object Loading: AuthStatus()
    data class Error(val message: String): AuthStatus()
}