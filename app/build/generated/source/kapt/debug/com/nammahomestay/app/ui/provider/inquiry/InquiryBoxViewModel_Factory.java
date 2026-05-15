package com.nammahomestay.app.ui.provider.inquiry;

import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.HomeStayRepository;
import com.nammahomestay.app.data.repository.InquiryRepository;
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
public final class InquiryBoxViewModel_Factory implements Factory<InquiryBoxViewModel> {
  private final Provider<InquiryRepository> repositoryProvider;

  private final Provider<HomeStayRepository> homeStayRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public InquiryBoxViewModel_Factory(Provider<InquiryRepository> repositoryProvider,
      Provider<HomeStayRepository> homeStayRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.repositoryProvider = repositoryProvider;
    this.homeStayRepositoryProvider = homeStayRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public InquiryBoxViewModel get() {
    return newInstance(repositoryProvider.get(), homeStayRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static InquiryBoxViewModel_Factory create(Provider<InquiryRepository> repositoryProvider,
      Provider<HomeStayRepository> homeStayRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new InquiryBoxViewModel_Factory(repositoryProvider, homeStayRepositoryProvider, authRepositoryProvider);
  }

  public static InquiryBoxViewModel newInstance(InquiryRepository repository,
      HomeStayRepository homeStayRepository, AuthRepository authRepository) {
    return new InquiryBoxViewModel(repository, homeStayRepository, authRepository);
  }
}
