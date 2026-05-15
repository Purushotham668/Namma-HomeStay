package com.nammahomestay.app.ui.provider.profile;

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
public final class HomeProfileViewModel_Factory implements Factory<HomeProfileViewModel> {
  private final Provider<HomeStayRepository> repositoryProvider;

  private final Provider<UserRepository> userRepositoryProvider;

  private final Provider<InquiryRepository> inquiryRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public HomeProfileViewModel_Factory(Provider<HomeStayRepository> repositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<InquiryRepository> inquiryRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.repositoryProvider = repositoryProvider;
    this.userRepositoryProvider = userRepositoryProvider;
    this.inquiryRepositoryProvider = inquiryRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public HomeProfileViewModel get() {
    return newInstance(repositoryProvider.get(), userRepositoryProvider.get(), inquiryRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static HomeProfileViewModel_Factory create(Provider<HomeStayRepository> repositoryProvider,
      Provider<UserRepository> userRepositoryProvider,
      Provider<InquiryRepository> inquiryRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new HomeProfileViewModel_Factory(repositoryProvider, userRepositoryProvider, inquiryRepositoryProvider, authRepositoryProvider);
  }

  public static HomeProfileViewModel newInstance(HomeStayRepository repository,
      UserRepository userRepository, InquiryRepository inquiryRepository,
      AuthRepository authRepository) {
    return new HomeProfileViewModel(repository, userRepository, inquiryRepository, authRepository);
  }
}
