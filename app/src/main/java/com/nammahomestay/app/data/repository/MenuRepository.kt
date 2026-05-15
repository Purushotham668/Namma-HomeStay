package com.nammahomestay.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.nammahomestay.app.data.model.DailyMenu
import com.nammahomestay.app.util.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val collection = firestore.collection("homestays")

    suspend fun updateMenu(homeStayId: String, menu: DailyMenu): Resource<Unit> {
        return try {
            if (homeStayId.isEmpty()) throw Exception("Invalid property ID")
            collection.document(homeStayId)
                .update("menu", menu)
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to update menu")
        }
    }

    suspend fun getMenu(homeStayId: String): Resource<DailyMenu> {
        return try {
            if (homeStayId.isEmpty()) throw Exception("Invalid property ID")
            val doc = collection.document(homeStayId).get().await()
            val stay = doc.toObject(com.nammahomestay.app.data.model.HomeStay::class.java)
            Resource.Success(stay?.menu ?: DailyMenu())
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to load menu")
        }
    }
}
