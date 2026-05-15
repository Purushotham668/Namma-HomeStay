package com.nammahomestay.app.ui.provider.booking;

import android.content.Context;
import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.BookingRepository;
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
public final class ProviderBookingsViewModel_Factory implements Factory<ProviderBookingsViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<BookingRepository> bookingRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public ProviderBookingsViewModel_Factory(Provider<Context> contextProvider,
      Provider<BookingRepository> bookingRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.contextProvider = contextProvider;
    this.bookingRepositoryProvider = bookingRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public ProviderBookingsViewModel get() {
    return newInstance(contextProvider.get(), bookingRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static ProviderBookingsViewModel_Factory create(Provider<Context> contextProvider,
      Provider<BookingRepository> bookingRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new ProviderBookingsViewModel_Factory(contextProvider, bookingRepositoryProvider, authRepositoryProvider);
  }

  public static ProviderBookingsViewModel newInstance(Context context,
      BookingRepository bookingRepository, AuthRepository authRepository) {
    return new ProviderBookingsViewModel(context, bookingRepository, authRepository);
  }
}
