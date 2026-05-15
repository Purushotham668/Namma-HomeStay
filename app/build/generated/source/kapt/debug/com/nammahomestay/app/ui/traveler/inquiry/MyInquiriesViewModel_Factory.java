package com.nammahomestay.app.ui.traveler.inquiry;

import com.nammahomestay.app.data.repository.AuthRepository;
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
public final class MyInquiriesViewModel_Factory implements Factory<MyInquiriesViewModel> {
  private final Provider<InquiryRepository> repositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public MyInquiriesViewModel_Factory(Provider<InquiryRepository> repositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.repositoryProvider = repositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public MyInquiriesViewModel get() {
    return newInstance(repositoryProvider.get(), authRepositoryProvider.get());
  }

  public static MyInquiriesViewModel_Factory create(Provider<InquiryRepository> repositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new MyInquiriesViewModel_Factory(repositoryProvider, authRepositoryProvider);
  }

  public static MyInquiriesViewModel newInstance(InquiryRepository repository,
      AuthRepository authRepository) {
    return new MyInquiriesViewModel(repository, authRepository);
  }
}
