package com.nammahomestay.app.ui.provider.pricing;

import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.HomeStayRepository;
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
public final class PricingViewModel_Factory implements Factory<PricingViewModel> {
  private final Provider<HomeStayRepository> repositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public PricingViewModel_Factory(Provider<HomeStayRepository> repositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.repositoryProvider = repositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public PricingViewModel get() {
    return newInstance(repositoryProvider.get(), authRepositoryProvider.get());
  }

  public static PricingViewModel_Factory create(Provider<HomeStayRepository> repositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new PricingViewModel_Factory(repositoryProvider, authRepositoryProvider);
  }

  public static PricingViewModel newInstance(HomeStayRepository repository,
      AuthRepository authRepository) {
    return new PricingViewModel(repository, authRepository);
  }
}
