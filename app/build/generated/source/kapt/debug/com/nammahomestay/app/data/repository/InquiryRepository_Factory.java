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
public final class InquiryRepository_Factory implements Factory<InquiryRepository> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  public InquiryRepository_Factory(Provider<FirebaseFirestore> firestoreProvider) {
    this.firestoreProvider = firestoreProvider;
  }

  @Override
  public InquiryRepository get() {
    return newInstance(firestoreProvider.get());
  }

  public static InquiryRepository_Factory create(Provider<FirebaseFirestore> firestoreProvider) {
    return new InquiryRepository_Factory(firestoreProvider);
  }

  public static InquiryRepository newInstance(FirebaseFirestore firestore) {
    return new InquiryRepository(firestore);
  }
}
