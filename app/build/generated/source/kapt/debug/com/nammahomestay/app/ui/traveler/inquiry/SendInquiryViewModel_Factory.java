package com.nammahomestay.app.ui.traveler.inquiry;

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
public final class SendInquiryViewModel_Factory implements Factory<SendInquiryViewModel> {
  private final Provider<InquiryRepository> inquiryRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<HomeStayRepository> homeStayRepositoryProvider;

  public SendInquiryViewModel_Factory(Provider<InquiryRepository> inquiryRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider,
      Provider<HomeStayRepository> homeStayRepositoryProvider) {
    this.inquiryRepositoryProvider = inquiryRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
    this.homeStayRepositoryProvider = homeStayRepositoryProvider;
  }

  @Override
  public SendInquiryViewModel get() {
    return newInstance(inquiryRepositoryProvider.get(), authRepositoryProvider.get(), homeStayRepositoryProvider.get());
  }

  public static SendInquiryViewModel_Factory create(
      Provider<InquiryRepository> inquiryRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider,
      Provider<HomeStayRepository> homeStayRepositoryProvider) {
    return new SendInquiryViewModel_Factory(inquiryRepositoryProvider, authRepositoryProvider, homeStayRepositoryProvider);
  }

  public static SendInquiryViewModel newInstance(InquiryRepository inquiryRepository,
      AuthRepository authRepository, HomeStayRepository homeStayRepository) {
    return new SendInquiryViewModel(inquiryRepository, authRepository, homeStayRepository);
  }
}
