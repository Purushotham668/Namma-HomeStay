package com.nammahomestay.app.ui.traveler.booking;

import android.content.Context;
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
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class BookingViewModel_Factory implements Factory<BookingViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  private final Provider<HomeStayRepository> homeStayRepositoryProvider;

  private final Provider<BookingRepository> bookingRepositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public BookingViewModel_Factory(Provider<Context> contextProvider,
      Provider<AuthRepository> authRepositoryProvider,
      Provider<HomeStayRepository> homeStayRepositoryProvider,
      Provider<BookingRepository> bookingRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.contextProvider = contextProvider;
    this.authRepositoryProvider = authRepositoryProvider;
    this.homeStayRepositoryProvider = homeStayRepositoryProvider;
    this.bookingRepositoryProvider = bookingRepositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public BookingViewModel get() {
    return newInstance(contextProvider.get(), authRepositoryProvider.get(), homeStayRepositoryProvider.get(), bookingRepositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static BookingViewModel_Factory create(Provider<Context> contextProvider,
      Provider<AuthRepository> authRepositoryProvider,
      Provider<HomeStayRepository> homeStayRepositoryProvider,
      Provider<BookingRepository> bookingRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new BookingViewModel_Factory(contextProvider, authRepositoryProvider, homeStayRepositoryProvider, bookingRepositoryProvider, savedStateHandleProvider);
  }

  public static BookingViewModel newInstance(Context context, AuthRepository authRepository,
      HomeStayRepository homeStayRepository, BookingRepository bookingRepository,
      SavedStateHandle savedStateHandle) {
    return new BookingViewModel(context, authRepository, homeStayRepository, bookingRepository, savedStateHandle);
  }
}
