package com.nammahomestay.app.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.nammahomestay.app.data.model.User
import com.nammahomestay.app.util.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    val currentUser: FirebaseUser? get() = auth.currentUser

    suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Login failed")
        }
    }

    suspend fun signInWithGoogle(idToken: String): Resource<FirebaseUser> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = auth.signInWithCredential(credential).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Google Sign-in failed")
        }
    }

    suspend fun isUserRegistered(uid: String): Boolean {
        return try {
            val doc = firestore.collection("users").document(uid).get().await()
            doc.exists()
        } catch (e: Exception) {
            false
        }
    }

    suspend fun registerGoogleUser(
        uid: String,
        name: String,
        email: String,
        phone: String,
        role: String
    ): Resource<Unit> {
        return try {
            val userModel = User(
                uid = uid,
                name = name,
                email = email,
                phone = phone,
                role = role
            )
            firestore.collection("users").document(uid).set(userModel).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Registration failed")
        }
    }

    suspend fun register(
        name: String,
        email: String,
        phone: String,
        password: String,
        role: String
    ): Resource<FirebaseUser> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user!!
            val userModel = User(
                uid = user.uid,
                name = name,
                email = email,
                phone = phone,
                role = role
            )
            firestore.collection("users").document(user.uid).set(userModel).await()
            Resource.Success(user)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Registration failed")
        }
    }

    suspend fun getUserRole(uid: String): Resource<String> {
        return try {
            val doc = firestore.collection("users").document(uid).get().await()
            val role = doc.getString("role") ?: ""
            Resource.Success(role)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to fetch user role")
        }
    }

    suspend fun getUserProfile(uid: String): Resource<User> {
        return try {
            val doc = firestore.collection("users").document(uid).get().await()
            val user = doc.toObject(User::class.java) ?: User()
            Resource.Success(user)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to fetch profile")
        }
    }

    suspend fun updateUserProfile(uid: String, name: String, phone: String): Resource<Unit> {
        return try {
            firestore.collection("users").document(uid)
                .update("name", name, "phone", phone).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Update failed")
        }
    }

    suspend fun changePassword(newPassword: String): Resource<Unit> {
        return try {
            auth.currentUser?.updatePassword(newPassword)?.await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Password change failed")
        }
    }

    fun logout() = auth.signOut()
}
