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
public final class BookingRepository_Factory implements Factory<BookingRepository> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  public BookingRepository_Factory(Provider<FirebaseFirestore> firestoreProvider) {
    this.firestoreProvider = firestoreProvider;
  }

  @Override
  public BookingRepository get() {
    return newInstance(firestoreProvider.get());
  }

  public static BookingRepository_Factory create(Provider<FirebaseFirestore> firestoreProvider) {
    return new BookingRepository_Factory(firestoreProvider);
  }

  public static BookingRepository newInstance(FirebaseFirestore firestore) {
    return new BookingRepository(firestore);
  }
}
