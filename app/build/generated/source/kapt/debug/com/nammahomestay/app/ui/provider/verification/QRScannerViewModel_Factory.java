package com.nammahomestay.app.ui.provider.verification;

import com.nammahomestay.app.data.repository.BookingRepository;
import com.nammahomestay.app.data.repository.FoodRepository;
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
public final class QRScannerViewModel_Factory implements Factory<QRScannerViewModel> {
  private final Provider<BookingRepository> bookingRepositoryProvider;

  private final Provider<FoodRepository> foodRepositoryProvider;

  public QRScannerViewModel_Factory(Provider<BookingRepository> bookingRepositoryProvider,
      Provider<FoodRepository> foodRepositoryProvider) {
    this.bookingRepositoryProvider = bookingRepositoryProvider;
    this.foodRepositoryProvider = foodRepositoryProvider;
  }

  @Override
  public QRScannerViewModel get() {
    return newInstance(bookingRepositoryProvider.get(), foodRepositoryProvider.get());
  }

  public static QRScannerViewModel_Factory create(
      Provider<BookingRepository> bookingRepositoryProvider,
      Provider<FoodRepository> foodRepositoryProvider) {
    return new QRScannerViewModel_Factory(bookingRepositoryProvider, foodRepositoryProvider);
  }

  public static QRScannerViewModel newInstance(BookingRepository bookingRepository,
      FoodRepository foodRepository) {
    return new QRScannerViewModel(bookingRepository, foodRepository);
  }
}
