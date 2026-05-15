package com.nammahomestay.app.ui.traveler.food;

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
public final class FoodTicketViewModel_Factory implements Factory<FoodTicketViewModel> {
  private final Provider<FoodRepository> repositoryProvider;

  public FoodTicketViewModel_Factory(Provider<FoodRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public FoodTicketViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static FoodTicketViewModel_Factory create(Provider<FoodRepository> repositoryProvider) {
    return new FoodTicketViewModel_Factory(repositoryProvider);
  }

  public static FoodTicketViewModel newInstance(FoodRepository repository) {
    return new FoodTicketViewModel(repository);
  }
}
