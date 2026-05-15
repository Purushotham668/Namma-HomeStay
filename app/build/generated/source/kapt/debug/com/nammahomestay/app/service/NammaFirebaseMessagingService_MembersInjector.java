package com.nammahomestay.app.service;

import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.UserRepository;
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
public final class NammaFirebaseMessagingService_MembersInjector implements MembersInjector<NammaFirebaseMessagingService> {
  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  public NammaFirebaseMessagingService_MembersInjector(
      Provider<AuthRepository> authRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider) {
    this.authRepositoryProvider = authRepositoryProvider;
    this.userRepositoryProvider = userRepositoryProvider;
  }

  public static MembersInjector<NammaFirebaseMessagingService> create(
      Provider<AuthRepository> authRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider) {
    return new NammaFirebaseMessagingService_MembersInjector(authRepositoryProvider, userRepositoryProvider);
  }

  @Override
  public void injectMembers(NammaFirebaseMessagingService instance) {
    injectAuthRepository(instance, authRepositoryProvider.get());
    injectUserRepository(instance, userRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.nammahomestay.app.service.NammaFirebaseMessagingService.authRepository")
  public static void injectAuthRepository(NammaFirebaseMessagingService instance,
      AuthRepository authRepository) {
    instance.authRepository = authRepository;
  }

  @InjectedFieldSignature("com.nammahomestay.app.service.NammaFirebaseMessagingService.userRepository")
  public static void injectUserRepository(NammaFirebaseMessagingService instance,
      UserRepository userRepository) {
    instance.userRepository = userRepository;
  }
}
