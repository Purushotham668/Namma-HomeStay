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
public final class ChatViewModel_Factory implements Factory<ChatViewModel> {
  private final Provider<InquiryRepository> inquiryRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<HomeStayRepository> homestayRepositoryProvider;

  public ChatViewModel_Factory(Provider<InquiryRepository> inquiryRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider,
      Provider<HomeStayRepository> homestayRepositoryProvider) {
    this.inquiryRepositoryProvider = inquiryRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
    this.homestayRepositoryProvider = homestayRepositoryProvider;
  }

  @Override
  public ChatViewModel get() {
    return newInstance(inquiryRepositoryProvider.get(), authRepositoryProvider.get(), homestayRepositoryProvider.get());
  }

  public static ChatViewModel_Factory create(Provider<InquiryRepository> inquiryRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider,
      Provider<HomeStayRepository> homestayRepositoryProvider) {
    return new ChatViewModel_Factory(inquiryRepositoryProvider, authRepositoryProvider, homestayRepositoryProvider);
  }

  public static ChatViewModel newInstance(InquiryRepository inquiryRepository,
      AuthRepository authRepository, HomeStayRepository homestayRepository) {
    return new ChatViewModel(inquiryRepository, authRepository, homestayRepository);
  }
}
