package com.nammahomestay.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.nammahomestay.app.data.model.Inquiry
import com.nammahomestay.app.util.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InquiryRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val collection = firestore.collection("inquiries")
    
    fun observeInquiryCount(homestayId: String) = callbackFlow {
        val listener = collection.whereEqualTo("homestayId", homestayId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(0)
                    return@addSnapshotListener
                }
                trySend(snapshot?.size() ?: 0)
            }
        awaitClose { listener.remove() }
    }

    fun observeChatMessages(travelerId: String, homestayId: String) = callbackFlow {
        val sevenDaysAgo = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000L)
        val listener = collection
            .whereEqualTo("travelerId", travelerId)
            .whereEqualTo("homestayId", homestayId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val messages = snapshot?.documents?.mapNotNull { it.toObject(Inquiry::class.java) }
                    ?.filter { it.timestamp > sevenDaysAgo }
                    ?.sortedBy { it.timestamp } ?: emptyList()
                trySend(messages)
            }
        awaitClose { listener.remove() }
    }

    suspend fun getConversationsForProvider(providerId: String): Resource<List<Inquiry>> {
        return try {
            val sevenDaysAgo = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000L)
            val snapshot = collection.whereEqualTo("providerId", providerId).get().await()
            val allMessages = snapshot.documents.mapNotNull { it.toObject(Inquiry::class.java) }
                .filter { it.timestamp > sevenDaysAgo }
            
            // Group by travelerId + homestayId to show latest message per conversation
            val latestMessages = allMessages.groupBy { it.travelerId + it.homestayId }
                .map { it.value.maxBy { msg -> msg.timestamp } }
                .sortedByDescending { it.timestamp }
                
            Resource.Success(latestMessages)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to load conversations")
        }
    }

    suspend fun getConversationsForTraveler(travelerId: String): Resource<List<Inquiry>> {
        return try {
            val sevenDaysAgo = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000L)
            val snapshot = collection.whereEqualTo("travelerId", travelerId).get().await()
            val allMessages = snapshot.documents.mapNotNull { it.toObject(Inquiry::class.java) }
                .filter { it.timestamp > sevenDaysAgo }
            
            // Group by homestayId to show latest message per homestay
            val latestMessages = allMessages.groupBy { it.homestayId }
                .map { it.value.maxBy { msg -> msg.timestamp } }
                .sortedByDescending { it.timestamp }
                
            Resource.Success(latestMessages)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to load conversations")
        }
    }

    suspend fun sendMessage(message: Inquiry): Resource<Unit> {
        return try {
            val docRef = collection.document()
            collection.document(docRef.id).set(message.copy(id = docRef.id)).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to send message")
        }
    }

    suspend fun markMessagesAsSeen(travelerId: String, homestayId: String, currentUserId: String) {
        try {
            // Find messages in this chat that were NOT sent by me and are NOT seen yet
            val snapshot = collection
                .whereEqualTo("travelerId", travelerId)
                .whereEqualTo("homestayId", homestayId)
                .whereEqualTo("seen", false)
                .get().await()

            val unreadDocs = snapshot.documents.filter { 
                it.getString("senderId") != currentUserId && it.getString("senderId") != null 
            }

            if (unreadDocs.isNotEmpty()) {
                val batch = firestore.batch()
                for (doc in unreadDocs) {
                    batch.update(doc.reference, "seen", true)
                }
                batch.commit().await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun observeTypingStatus(travelerId: String, homestayId: String) = callbackFlow {
        val docId = "${travelerId}_${homestayId}"
        val listener = firestore.collection("typing_status").document(docId)
            .addSnapshotListener { snapshot, _ ->
                val typingId = snapshot?.getString("typingId")
                trySend(typingId)
            }
        awaitClose { listener.remove() }
    }

    suspend fun setTypingStatus(travelerId: String, homestayId: String, userId: String, isTyping: Boolean) {
        val docId = "${travelerId}_${homestayId}"
        try {
            if (isTyping) {
                firestore.collection("typing_status").document(docId)
                    .set(mapOf("typingId" to userId)).await()
            } else {
                firestore.collection("typing_status").document(docId)
                    .set(mapOf("typingId" to null)).await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun cleanupOldInquiries(): Resource<Unit> {
        return try {
            val sevenDaysAgo = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000L)
            val oldInquiries = collection.whereLessThan("timestamp", sevenDaysAgo).get().await()
            val batch = collection.firestore.batch()
            oldInquiries.documents.forEach { doc -> batch.delete(doc.reference) }
            batch.commit().await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Cleanup failed")
        }
    }
}
