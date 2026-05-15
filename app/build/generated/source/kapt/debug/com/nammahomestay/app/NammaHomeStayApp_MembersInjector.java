package com.nammahomestay.app;

import com.nammahomestay.app.util.FirestoreInitializer;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class NammaHomeStayApp_MembersInjector implements MembersInjector<NammaHomeStayApp> {
  private final Provider<FirestoreInitializer> firestoreInitializerProvider;

  public NammaHomeStayApp_MembersInjector(
      Provider<FirestoreInitializer> firestoreInitializerProvider) {
    this.firestoreInitializerProvider = firestoreInitializerProvider;
  }

  public static MembersInjector<NammaHomeStayApp> create(
      Provider<FirestoreInitializer> firestoreInitializerProvider) {
    return new NammaHomeStayApp_MembersInjector(firestoreInitializerProvider);
  }

  @Override
  public void injectMembers(NammaHomeStayApp instance) {
    injectFirestoreInitializer(instance, firestoreInitializerProvider.get());
  }

  @InjectedFieldSignature("com.nammahomestay.app.NammaHomeStayApp.firestoreInitializer")
  public static void injectFirestoreInitializer(NammaHomeStayApp instance,
      FirestoreInitializer firestoreInitializer) {
    instance.firestoreInitializer = firestoreInitializer;
  }
}
