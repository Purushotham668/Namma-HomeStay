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
public final class MenuRepository_Factory implements Factory<MenuRepository> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  public MenuRepository_Factory(Provider<FirebaseFirestore> firestoreProvider) {
    this.firestoreProvider = firestoreProvider;
  }

  @Override
  public MenuRepository get() {
    return newInstance(firestoreProvider.get());
  }

  public static MenuRepository_Factory create(Provider<FirebaseFirestore> firestoreProvider) {
    return new MenuRepository_Factory(firestoreProvider);
  }

  public static MenuRepository newInstance(FirebaseFirestore firestore) {
    return new MenuRepository(firestore);
  }
}
