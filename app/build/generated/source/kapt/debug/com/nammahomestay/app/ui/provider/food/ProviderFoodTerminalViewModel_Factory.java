package com.nammahomestay.app.ui.provider.food;

import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.FoodRepository;
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
public final class ProviderFoodTerminalViewModel_Factory implements Factory<ProviderFoodTerminalViewModel> {
  private final Provider<HomeStayRepository> homestayRepositoryProvider;

  private final Provider<FoodRepository> foodRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public ProviderFoodTerminalViewModel_Factory(
      Provider<HomeStayRepository> homestayRepositoryProvider,
      Provider<FoodRepository> foodRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.homestayRepositoryProvider = homestayRepositoryProvider;
    this.foodRepositoryProvider = foodRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public ProviderFoodTerminalViewModel get() {
    return newInstance(homestayRepositoryProvider.get(), foodRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static ProviderFoodTerminalViewModel_Factory create(
      Provider<HomeStayRepository> homestayRepositoryProvider,
      Provider<FoodRepository> foodRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new ProviderFoodTerminalViewModel_Factory(homestayRepositoryProvider, foodRepositoryProvider, authRepositoryProvider);
  }

  public static ProviderFoodTerminalViewModel newInstance(HomeStayRepository homestayRepository,
      FoodRepository foodRepository, AuthRepository authRepository) {
    return new ProviderFoodTerminalViewModel(homestayRepository, foodRepository, authRepository);
  }
}
