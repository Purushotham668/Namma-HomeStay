package com.nammahomestay.app.ui.traveler.booking;

import androidx.lifecycle.SavedStateHandle;
import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.BookingRepository;
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
public final class ReviewViewModel_Factory implements Factory<ReviewViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<BookingRepository> bookingRepositoryProvider;

  private final Provider<HomeStayRepository> homeStayRepositoryProvider;

  public ReviewViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<AuthRepository> authRepositoryProvider,
      Provider<BookingRepository> bookingRepositoryProvider,
      Provider<HomeStayRepository> homeStayRepositoryProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.authRepositoryProvider = authRepositoryProvider;
    this.bookingRepositoryProvider = bookingRepositoryProvider;
    this.homeStayRepositoryProvider = homeStayRepositoryProvider;
  }

  @Override
  public ReviewViewModel get() {
    return newInstance(savedStateHandleProvider.get(), authRepositoryProvider.get(), bookingRepositoryProvider.get(), homeStayRepositoryProvider.get());
  }

  public static ReviewViewModel_Factory create(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<AuthRepository> authRepositoryProvider,
      Provider<BookingRepository> bookingRepositoryProvider,
      Provider<HomeStayRepository> homeStayRepositoryProvider) {
    return new ReviewViewModel_Factory(savedStateHandleProvider, authRepositoryProvider, bookingRepositoryProvider, homeStayRepositoryProvider);
  }

  public static ReviewViewModel newInstance(SavedStateHandle savedStateHandle,
      AuthRepository authRepository, BookingRepository bookingRepository,
      HomeStayRepository homeStayRepository) {
    return new ReviewViewModel(savedStateHandle, authRepository, bookingRepository, homeStayRepository);
  }
}
