package com.nammahomestay.app.ui.traveler.settings;

import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.UserRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class ProfileSettingsViewModel_Factory implements Factory<ProfileSettingsViewModel> {
  private final Provider<UserRepository> userRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public ProfileSettingsViewModel_Factory(Provider<UserRepository> userRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.userRepositoryProvider = userRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public ProfileSettingsViewModel get() {
    return newInstance(userRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static ProfileSettingsViewModel_Factory create(
      Provider<UserRepository> userRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new ProfileSettingsViewModel_Factory(userRepositoryProvider, authRepositoryProvider);
  }

  public static ProfileSettingsViewModel newInstance(UserRepository userRepository,
      AuthRepository authRepository) {
    return new ProfileSettingsViewModel(userRepository, authRepository);
  }
}
