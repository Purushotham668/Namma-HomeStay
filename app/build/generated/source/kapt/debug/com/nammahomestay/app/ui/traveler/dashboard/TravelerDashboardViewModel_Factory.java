package com.nammahomestay.app.ui.traveler.dashboard;

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
public final class TravelerDashboardViewModel_Factory implements Factory<TravelerDashboardViewModel> {
  private final Provider<HomeStayRepository> repositoryProvider;

  private final Provider<BookingRepository> bookingRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public TravelerDashboardViewModel_Factory(Provider<HomeStayRepository> repositoryProvider,
      Provider<BookingRepository> bookingRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.repositoryProvider = repositoryProvider;
    this.bookingRepositoryProvider = bookingRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public TravelerDashboardViewModel get() {
    return newInstance(repositoryProvider.get(), bookingRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static TravelerDashboardViewModel_Factory create(
      Provider<HomeStayRepository> repositoryProvider,
      Provider<BookingRepository> bookingRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new TravelerDashboardViewModel_Factory(repositoryProvider, bookingRepositoryProvider, authRepositoryProvider);
  }

  public static TravelerDashboardViewModel newInstance(HomeStayRepository repository,
      BookingRepository bookingRepository, AuthRepository authRepository) {
    return new TravelerDashboardViewModel(repository, bookingRepository, authRepository);
  }
}
