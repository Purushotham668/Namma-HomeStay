package com.nammahomestay.app.data.repository;

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
public final class LocalSpotRepository_Factory implements Factory<LocalSpotRepository> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  public LocalSpotRepository_Factory(Provider<FirebaseFirestore> firestoreProvider) {
    this.firestoreProvider = firestoreProvider;
  }

  @Override
  public LocalSpotRepository get() {
    return newInstance(firestoreProvider.get());
  }

  public static LocalSpotRepository_Factory create(Provider<FirebaseFirestore> firestoreProvider) {
    return new LocalSpotRepository_Factory(firestoreProvider);
  }

  public static LocalSpotRepository newInstance(FirebaseFirestore firestore) {
    return new LocalSpotRepository(firestore);
  }
}
