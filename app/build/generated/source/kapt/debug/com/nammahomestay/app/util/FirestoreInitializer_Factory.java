package com.nammahomestay.app.util;

import com.google.firebase.firestore.FirebaseFirestore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class FirestoreInitializer_Factory implements Factory<FirestoreInitializer> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  public FirestoreInitializer_Factory(Provider<FirebaseFirestore> firestoreProvider) {
    this.firestoreProvider = firestoreProvider;
  }

  @Override
  public FirestoreInitializer get() {
    return newInstance(firestoreProvider.get());
  }

  public static FirestoreInitializer_Factory create(Provider<FirebaseFirestore> firestoreProvider) {
    return new FirestoreInitializer_Factory(firestoreProvider);
  }

  public static FirestoreInitializer newInstance(FirebaseFirestore firestore) {
    return new FirestoreInitializer(firestore);
  }
}
