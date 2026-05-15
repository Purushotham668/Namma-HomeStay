package com.nammahomestay.app.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u001c\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u008b\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\u0002\u0010\u0011J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u00c6\u0003J\t\u0010\"\u001a\u00020\u0010H\u00c6\u0003J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\t\u0010%\u001a\u00020\u0003H\u00c6\u0003J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u00c6\u0003J\u000f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u00c6\u0003J\u000f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u00c6\u0003J\u008f\u0001\u0010+\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u00c6\u0001J\u0013\u0010,\u001a\u00020-2\b\u0010.\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010/\u001a\u000200H\u00d6\u0001J\t\u00101\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0013R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0013R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0013R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0013R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0015R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0013R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0015R\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001f\u00a8\u00062"}, d2 = {"Lcom/nammahomestay/app/data/model/DailyMenu;", "", "breakfast", "", "breakfastPrice", "lunch", "lunchPrice", "dinner", "dinnerPrice", "breakfastItems", "", "Lcom/nammahomestay/app/data/model/FoodItem;", "lunchItems", "dinnerItems", "specials", "updatedAt", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;J)V", "getBreakfast", "()Ljava/lang/String;", "getBreakfastItems", "()Ljava/util/List;", "getBreakfastPrice", "getDinner", "getDinnerItems", "getDinnerPrice", "getLunch", "getLunchItems", "getLunchPrice", "getSpecials", "getUpdatedAt", "()J", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class DailyMenu {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String breakfast = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String breakfastPrice = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String lunch = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String lunchPrice = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String dinner = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String dinnerPrice = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.nammahomestay.app.data.model.FoodItem> breakfastItems = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.nammahomestay.app.data.model.FoodItem> lunchItems = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.nammahomestay.app.data.model.FoodItem> dinnerItems = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.nammahomestay.app.data.model.FoodItem> specials = null;
    private final long updatedAt = 0L;
    
    public DailyMenu(@org.jetbrains.annotations.NotNull()
    java.lang.String breakfast, @org.jetbrains.annotations.NotNull()
    java.lang.String breakfastPrice, @org.jetbrains.annotations.NotNull()
    java.lang.String lunch, @org.jetbrains.annotations.NotNull()
    java.lang.String lunchPrice, @org.jetbrains.annotations.NotNull()
    java.lang.String dinner, @org.jetbrains.annotations.NotNull()
    java.lang.String dinnerPrice, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.FoodItem> breakfastItems, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.FoodItem> lunchItems, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.FoodItem> dinnerItems, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.FoodItem> specials, long updatedAt) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBreakfast() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBreakfastPrice() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLunch() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLunchPrice() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDinner() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDinnerPrice() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.nammahomestay.app.data.model.FoodItem> getBreakfastItems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.nammahomestay.app.data.model.FoodItem> getLunchItems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.nammahomestay.app.data.model.FoodItem> getDinnerItems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.nammahomestay.app.data.model.FoodItem> getSpecials() {
        return null;
    }
    
    public final long getUpdatedAt() {
        return 0L;
    }
    
    public DailyMenu() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.nammahomestay.app.data.model.FoodItem> component10() {
        return null;
    }
    
    public final long component11() {
        return 0L;
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
    public final java.util.List<com.nammahomestay.app.data.model.FoodItem> component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.nammahomestay.app.data.model.FoodItem> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.nammahomestay.app.data.model.FoodItem> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.nammahomestay.app.data.model.DailyMenu copy(@org.jetbrains.annotations.NotNull()
    java.lang.String breakfast, @org.jetbrains.annotations.NotNull()
    java.lang.String breakfastPrice, @org.jetbrains.annotations.NotNull()
    java.lang.String lunch, @org.jetbrains.annotations.NotNull()
    java.lang.String lunchPrice, @org.jetbrains.annotations.NotNull()
    java.lang.String dinner, @org.jetbrains.annotations.NotNull()
    java.lang.String dinnerPrice, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.FoodItem> breakfastItems, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.FoodItem> lunchItems, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.FoodItem> dinnerItems, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.FoodItem> specials, long updatedAt) {
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