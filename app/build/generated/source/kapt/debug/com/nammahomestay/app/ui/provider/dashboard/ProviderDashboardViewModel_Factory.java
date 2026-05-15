package com.nammahomestay.app.ui.provider.dashboard;

import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.HomeStayRepository;
import com.nammahomestay.app.data.repository.InquiryRepository;
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
public final class ProviderDashboardViewModel_Factory implements Factory<ProviderDashboardViewModel> {
  private final Provider<HomeStayRepository> homeStayRepositoryProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  private final Provider<InquiryRepository> inquiryRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public ProviderDashboardViewModel_Factory(Provider<HomeStayRepository> homeStayRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<InquiryRepository> inquiryRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.homeStayRepositoryProvider = homeStayRepositoryProvider;
    this.userRepositoryProvider = userRepositoryProvider;
    this.inquiryRepositoryProvider = inquiryRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public ProviderDashboardViewModel get() {
    return newInstance(homeStayRepositoryProvider.get(), userRepositoryProvider.get(), inquiryRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static ProviderDashboardViewModel_Factory create(
      Provider<HomeStayRepository> homeStayRepositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<InquiryRepository> inquiryRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new ProviderDashboardViewModel_Factory(homeStayRepositoryProvider, userRepositoryProvider, inquiryRepositoryProvider, authRepositoryProvider);
  }

  public static ProviderDashboardViewModel newInstance(HomeStayRepository homeStayRepository,
      UserRepository userRepository, InquiryRepository inquiryRepository,
      AuthRepository authRepository) {
    return new ProviderDashboardViewModel(homeStayRepository, userRepository, inquiryRepository, authRepository);
  }
}
