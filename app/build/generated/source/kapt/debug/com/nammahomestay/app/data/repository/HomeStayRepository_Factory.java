package com.nammahomestay.app.data.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
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
public final class HomeStayRepository_Factory implements Factory<HomeStayRepository> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  private final Provider<FirebaseStorage> storageProvider;

  public HomeStayRepository_Factory(Provider<FirebaseFirestore> firestoreProvider,
      Provider<FirebaseStorage> storageProvider) {
    this.firestoreProvider = firestoreProvider;
    this.storageProvider = storageProvider;
  }

  @Override
  public HomeStayRepository get() {
    return newInstance(firestoreProvider.get(), storageProvider.get());
  }

  public static HomeStayRepository_Factory create(Provider<FirebaseFirestore> firestoreProvider,
      Provider<FirebaseStorage> storageProvider) {
    return new HomeStayRepository_Factory(firestoreProvider, storageProvider);
  }

  public static HomeStayRepository newInstance(FirebaseFirestore firestore,
      FirebaseStorage storage) {
    return new HomeStayRepository(firestore, storage);
  }
}
