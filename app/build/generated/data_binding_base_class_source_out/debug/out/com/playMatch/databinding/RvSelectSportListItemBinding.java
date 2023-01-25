// Generated by view binder compiler. Do not edit!
package com.playMatch.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.viewbinding.ViewBinding;
import com.playMatch.R;
import java.lang.NullPointerException;
import java.lang.Override;

public final class RvSelectSportListItemBinding implements ViewBinding {
  @NonNull
  private final LinearLayoutCompat rootView;

  @NonNull
  public final LinearLayoutCompat cardView;

  private RvSelectSportListItemBinding(@NonNull LinearLayoutCompat rootView,
      @NonNull LinearLayoutCompat cardView) {
    this.rootView = rootView;
    this.cardView = cardView;
  }

  @Override
  @NonNull
  public LinearLayoutCompat getRoot() {
    return rootView;
  }

  @NonNull
  public static RvSelectSportListItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static RvSelectSportListItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.rv_select_sport_list_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static RvSelectSportListItemBinding bind(@NonNull View rootView) {
    if (rootView == null) {
      throw new NullPointerException("rootView");
    }

    LinearLayoutCompat cardView = (LinearLayoutCompat) rootView;

    return new RvSelectSportListItemBinding((LinearLayoutCompat) rootView, cardView);
  }
}
