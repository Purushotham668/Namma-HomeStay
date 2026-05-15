package com.nammahomestay.app.ui.provider.food;

import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.FoodRepository;
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
public final class FoodAnalyticsViewModel_Factory implements Factory<FoodAnalyticsViewModel> {
  private final Provider<FoodRepository> repositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public FoodAnalyticsViewModel_Factory(Provider<FoodRepository> repositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.repositoryProvider = repositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public FoodAnalyticsViewModel get() {
    return newInstance(repositoryProvider.get(), authRepositoryProvider.get());
  }

  public static FoodAnalyticsViewModel_Factory create(Provider<FoodRepository> repositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new FoodAnalyticsViewModel_Factory(repositoryProvider, authRepositoryProvider);
  }

  public static FoodAnalyticsViewModel newInstance(FoodRepository repository,
      AuthRepository authRepository) {
    return new FoodAnalyticsViewModel(repository, authRepository);
  }
}
