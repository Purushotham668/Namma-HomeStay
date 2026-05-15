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
public final class FoodRepository_Factory implements Factory<FoodRepository> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  public FoodRepository_Factory(Provider<FirebaseFirestore> firestoreProvider) {
    this.firestoreProvider = firestoreProvider;
  }

  @Override
  public FoodRepository get() {
    return newInstance(firestoreProvider.get());
  }

  public static FoodRepository_Factory create(Provider<FirebaseFirestore> firestoreProvider) {
    return new FoodRepository_Factory(firestoreProvider);
  }

  public static FoodRepository newInstance(FirebaseFirestore firestore) {
    return new FoodRepository(firestore);
  }
}
