package com.nammahomestay.app.ui.provider.localguide;

import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.LocalSpotRepository;
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
public final class LocalGuideViewModel_Factory implements Factory<LocalGuideViewModel> {
  private final Provider<LocalSpotRepository> repositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public LocalGuideViewModel_Factory(Provider<LocalSpotRepository> repositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.repositoryProvider = repositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public LocalGuideViewModel get() {
    return newInstance(repositoryProvider.get(), authRepositoryProvider.get());
  }

  public static LocalGuideViewModel_Factory create(Provider<LocalSpotRepository> repositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new LocalGuideViewModel_Factory(repositoryProvider, authRepositoryProvider);
  }

  public static LocalGuideViewModel newInstance(LocalSpotRepository repository,
      AuthRepository authRepository) {
    return new LocalGuideViewModel(repository, authRepository);
  }
}
