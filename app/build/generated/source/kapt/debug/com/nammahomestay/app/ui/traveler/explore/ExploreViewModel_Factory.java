package com.nammahomestay.app.ui.traveler.explore;

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
public final class ExploreViewModel_Factory implements Factory<ExploreViewModel> {
  private final Provider<HomeStayRepository> repositoryProvider;

  private final Provider<BookingRepository> bookingRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public ExploreViewModel_Factory(Provider<HomeStayRepository> repositoryProvider,
      Provider<BookingRepository> bookingRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.repositoryProvider = repositoryProvider;
    this.bookingRepositoryProvider = bookingRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public ExploreViewModel get() {
    return newInstance(repositoryProvider.get(), bookingRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static ExploreViewModel_Factory create(Provider<HomeStayRepository> repositoryProvider,
      Provider<BookingRepository> bookingRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new ExploreViewModel_Factory(repositoryProvider, bookingRepositoryProvider, authRepositoryProvider);
  }

  public static ExploreViewModel newInstance(HomeStayRepository repository,
      BookingRepository bookingRepository, AuthRepository authRepository) {
    return new ExploreViewModel(repository, bookingRepository, authRepository);
  }
}
