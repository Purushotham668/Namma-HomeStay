package com.nammahomestay.app.ui.provider.menu;

import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.HomeStayRepository;
import com.nammahomestay.app.data.repository.MenuRepository;
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
public final class DailyMenuViewModel_Factory implements Factory<DailyMenuViewModel> {
  private final Provider<MenuRepository> repositoryProvider;

  private final Provider<HomeStayRepository> homeStayRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public DailyMenuViewModel_Factory(Provider<MenuRepository> repositoryProvider,
      Provider<HomeStayRepository> homeStayRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.repositoryProvider = repositoryProvider;
    this.homeStayRepositoryProvider = homeStayRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public DailyMenuViewModel get() {
    return newInstance(repositoryProvider.get(), homeStayRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static DailyMenuViewModel_Factory create(Provider<MenuRepository> repositoryProvider,
      Provider<HomeStayRepository> homeStayRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new DailyMenuViewModel_Factory(repositoryProvider, homeStayRepositoryProvider, authRepositoryProvider);
  }

  public static DailyMenuViewModel newInstance(MenuRepository repository,
      HomeStayRepository homeStayRepository, AuthRepository authRepository) {
    return new DailyMenuViewModel(repository, homeStayRepository, authRepository);
  }
}
