package com.nammahomestay.app.data.model;

import com.google.firebase.firestore.PropertyName;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010 \n\u0000\n\u0002\u0010$\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b6\b\u0087\b\u0018\u00002\u00020\u0001B\u00e7\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b\u0012\u0014\b\u0002\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u000e0\r\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u0012\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000b\u0012\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b\u0012\u000e\b\u0002\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u000b\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0019\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0019\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u001c\u0012\b\b\u0002\u0010\u001d\u001a\u00020\u001e\u0012\b\b\u0002\u0010\u001f\u001a\u00020\u000e\u00a2\u0006\u0002\u0010 J\t\u0010<\u001a\u00020\u0003H\u00c6\u0003J\t\u0010=\u001a\u00020\u0010H\u00c6\u0003J\t\u0010>\u001a\u00020\u0012H\u00c6\u0003J\u000f\u0010?\u001a\b\u0012\u0004\u0012\u00020\u00140\u000bH\u00c6\u0003J\u000f\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00030\u000bH\u00c6\u0003J\u000f\u0010A\u001a\b\u0012\u0004\u0012\u00020\u00170\u000bH\u00c6\u0003J\t\u0010B\u001a\u00020\u0019H\u00c6\u0003J\t\u0010C\u001a\u00020\u0019H\u00c6\u0003J\t\u0010D\u001a\u00020\u001cH\u00c6\u0003J\t\u0010E\u001a\u00020\u001eH\u00c6\u0003J\t\u0010F\u001a\u00020\u000eH\u00c6\u0003J\t\u0010G\u001a\u00020\u0003H\u00c6\u0003J\t\u0010H\u001a\u00020\u0003H\u00c6\u0003J\t\u0010I\u001a\u00020\u0003H\u00c6\u0003J\t\u0010J\u001a\u00020\u0003H\u00c6\u0003J\t\u0010K\u001a\u00020\u0003H\u00c6\u0003J\t\u0010L\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010M\u001a\b\u0012\u0004\u0012\u00020\u00030\u000bH\u00c6\u0003J\u0015\u0010N\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u000e0\rH\u00c6\u0003J\u00eb\u0001\u0010O\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b2\u0014\b\u0002\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u000e0\r2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00122\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000b2\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b2\u000e\b\u0002\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u000b2\b\b\u0002\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u00192\b\b\u0002\u0010\u001b\u001a\u00020\u001c2\b\b\u0002\u0010\u001d\u001a\u00020\u001e2\b\b\u0002\u0010\u001f\u001a\u00020\u000eH\u00c6\u0001J\u0013\u0010P\u001a\u00020\u000e2\b\u0010Q\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010R\u001a\u00020\u001eH\u00d6\u0001J\t\u0010S\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u001d\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u000e0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\"R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\"R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\"R\u0014\u0010\u001f\u001a\u00020\u000eX\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010*R\u0011\u0010\u0018\u001a\u00020\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010$R\u0011\u0010\u001a\u001a\u00020\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010,R\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u00100R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010\"R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u0010\"R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u0010$R\u0011\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u00105R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u0010\"R\u0011\u0010\u001b\u001a\u00020\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u00108R\u0011\u0010\u001d\u001a\u00020\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b9\u0010:R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b;\u0010$\u00a8\u0006T"}, d2 = {"Lcom/nammahomestay/app/data/model/HomeStay;", "", "id", "", "providerId", "name", "ownerName", "contact", "address", "description", "photoUrls", "", "checklist", "", "", "menu", "Lcom/nammahomestay/app/data/model/DailyMenu;", "pricing", "Lcom/nammahomestay/app/data/model/Pricing;", "localSpots", "Lcom/nammahomestay/app/data/model/LocalSpot;", "categories", "rooms", "Lcom/nammahomestay/app/data/model/Room;", "latitude", "", "longitude", "rating", "", "reviewCount", "", "isOccupied", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/Map;Lcom/nammahomestay/app/data/model/DailyMenu;Lcom/nammahomestay/app/data/model/Pricing;Ljava/util/List;Ljava/util/List;Ljava/util/List;DDFIZ)V", "getAddress", "()Ljava/lang/String;", "getCategories", "()Ljava/util/List;", "getChecklist", "()Ljava/util/Map;", "getContact", "getDescription", "getId", "()Z", "getLatitude", "()D", "getLocalSpots", "getLongitude", "getMenu", "()Lcom/nammahomestay/app/data/model/DailyMenu;", "getName", "getOwnerName", "getPhotoUrls", "getPricing", "()Lcom/nammahomestay/app/data/model/Pricing;", "getProviderId", "getRating", "()F", "getReviewCount", "()I", "getRooms", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class HomeStay {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String providerId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String ownerName = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String contact = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String address = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String description = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> photoUrls = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.lang.Boolean> checklist = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.model.DailyMenu menu = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.model.Pricing pricing = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.nammahomestay.app.data.model.LocalSpot> localSpots = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> categories = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.nammahomestay.app.data.model.Room> rooms = null;
    private final double latitude = 0.0;
    private final double longitude = 0.0;
    private final float rating = 0.0F;
    private final int reviewCount = 0;
    @com.google.firebase.firestore.PropertyName(value = "isOccupied")
    private final boolean isOccupied = false;
    
    public HomeStay(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String providerId, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String ownerName, @org.jetbrains.annotations.NotNull()
    java.lang.String contact, @org.jetbrains.annotations.NotNull()
    java.lang.String address, @org.jetbrains.annotations.NotNull()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> photoUrls, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.Boolean> checklist, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.model.DailyMenu menu, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.model.Pricing pricing, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.LocalSpot> localSpots, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> categories, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.Room> rooms, double latitude, double longitude, float rating, int reviewCount, boolean isOccupied) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getProviderId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getOwnerName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getContact() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAddress() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDescription() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getPhotoUrls() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.Boolean> getChecklist() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.nammahomestay.app.data.model.DailyMenu getMenu() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.nammahomestay.app.data.model.Pricing getPricing() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.nammahomestay.app.data.model.LocalSpot> getLocalSpots() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getCategories() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.nammahomestay.app.data.model.Room> getRooms() {
        return null;
    }
    
    public final double getLatitude() {
        return 0.0;
    }
    
    public final double getLongitude() {
        return 0.0;
    }
    
    public final float getRating() {
        return 0.0F;
    }
    
    public final int getReviewCount() {
        return 0;
    }
    
    @com.google.firebase.firestore.PropertyName(value = "isOccupied")
    public final boolean isOccupied() {
        return false;
    }
    
    public HomeStay() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.nammahomestay.app.data.model.DailyMenu component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.nammahomestay.app.data.model.Pricing component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.nammahomestay.app.data.model.LocalSpot> component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component13() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.nammahomestay.app.data.model.Room> component14() {
        return null;
    }
    
    public final double component15() {
        return 0.0;
    }
    
    public final double component16() {
        return 0.0;
    }
    
    public final float component17() {
        return 0.0F;
    }
    
    public final int component18() {
        return 0;
    }
    
    public final boolean component19() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.Boolean> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.nammahomestay.app.data.model.HomeStay copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String providerId, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String ownerName, @org.jetbrains.annotations.NotNull()
    java.lang.String contact, @org.jetbrains.annotations.NotNull()
    java.lang.String address, @org.jetbrains.annotations.NotNull()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> photoUrls, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.Boolean> checklist, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.model.DailyMenu menu, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.model.Pricing pricing, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.LocalSpot> localSpots, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> categories, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.Room> rooms, double latitude, double longitude, float rating, int reviewCount, boolean isOccupied) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}