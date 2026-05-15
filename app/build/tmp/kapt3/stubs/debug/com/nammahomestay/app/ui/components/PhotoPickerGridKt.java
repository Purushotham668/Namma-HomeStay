package com.nammahomestay.app.ui.components;

import android.net.Uri;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.lazy.grid.GridCells;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.text.font.FontWeight;
import com.nammahomestay.app.ui.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0088\u0001\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u00052\u0018\u0010\b\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0005\u0012\u0004\u0012\u00020\u00010\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\t2\u0014\b\u0002\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\t2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0007\u001a\u001a\u0010\u0010\u001a\u00020\u0001*\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u0013H\u0003\u00a8\u0006\u0014"}, d2 = {"PhotoPickerGrid", "", "label", "", "existingUrls", "", "selectedPhotos", "Landroid/net/Uri;", "onImagesSelected", "Lkotlin/Function1;", "onRemoveSelected", "onRemoveExisting", "modifier", "Landroidx/compose/ui/Modifier;", "maxImages", "", "RemoveIcon", "Landroidx/compose/foundation/layout/BoxScope;", "onClick", "Lkotlin/Function0;", "app_debug"})
public final class PhotoPickerGridKt {
    
    @androidx.compose.runtime.Composable()
    public static final void PhotoPickerGrid(@org.jetbrains.annotations.NotNull()
    java.lang.String label, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> existingUrls, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends android.net.Uri> selectedPhotos, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.util.List<? extends android.net.Uri>, kotlin.Unit> onImagesSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super android.net.Uri, kotlin.Unit> onRemoveSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onRemoveExisting, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, int maxImages) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void RemoveIcon(androidx.compose.foundation.layout.BoxScope $this$RemoveIcon, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
}