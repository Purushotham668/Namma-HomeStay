package com.nammahomestay.app.ui.traveler.food;

import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.BookingRepository;
import com.nammahomestay.app.data.repository.FoodRepository;
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
public final class MealBookingViewModel_Factory implements Factory<MealBookingViewModel> {
  private final Provider<FoodRepository> foodRepositoryProvider;

  private final Provider<HomeStayRepository> homestayRepositoryProvider;

  private final Provider<BookingRepository> bookingRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public MealBookingViewModel_Factory(Provider<FoodRepository> foodRepositoryProvider,
      Provider<HomeStayRepository> homestayRepositoryProvider,
      Provider<BookingRepository> bookingRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.foodRepositoryProvider = foodRepositoryProvider;
    this.homestayRepositoryProvider = homestayRepositoryProvider;
    this.bookingRepositoryProvider = bookingRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public MealBookingViewModel get() {
    return newInstance(foodRepositoryProvider.get(), homestayRepositoryProvider.get(), bookingRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static MealBookingViewModel_Factory create(Provider<FoodRepository> foodRepositoryProvider,
      Provider<HomeStayRepository> homestayRepositoryProvider,
      Provider<BookingRepository> bookingRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new MealBookingViewModel_Factory(foodRepositoryProvider, homestayRepositoryProvider, bookingRepositoryProvider, authRepositoryProvider);
  }

  public static MealBookingViewModel newInstance(FoodRepository foodRepository,
      HomeStayRepository homestayRepository, BookingRepository bookingRepository,
      AuthRepository authRepository) {
    return new MealBookingViewModel(foodRepository, homestayRepository, bookingRepository, authRepository);
  }
}
