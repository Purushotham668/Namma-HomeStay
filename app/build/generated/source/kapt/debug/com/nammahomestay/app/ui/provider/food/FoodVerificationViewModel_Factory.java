package com.nammahomestay.app.ui.provider.food;

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
public final class FoodVerificationViewModel_Factory implements Factory<FoodVerificationViewModel> {
  private final Provider<FoodRepository> repositoryProvider;

  public FoodVerificationViewModel_Factory(Provider<FoodRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public FoodVerificationViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static FoodVerificationViewModel_Factory create(
      Provider<FoodRepository> repositoryProvider) {
    return new FoodVerificationViewModel_Factory(repositoryProvider);
  }

  public static FoodVerificationViewModel newInstance(FoodRepository repository) {
    return new FoodVerificationViewModel(repository);
  }
}
