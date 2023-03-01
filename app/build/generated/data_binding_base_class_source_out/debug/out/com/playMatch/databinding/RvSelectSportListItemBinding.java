// Generated by view binder compiler. Do not edit!
package com.playMatch.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.playMatch.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class RvSelectSportListItemBinding implements ViewBinding {
  @NonNull
  private final LinearLayoutCompat rootView;

  @NonNull
  public final LinearLayoutCompat cardView;

  @NonNull
  public final AppCompatTextView sportName;

  private RvSelectSportListItemBinding(@NonNull LinearLayoutCompat rootView,
      @NonNull LinearLayoutCompat cardView, @NonNull AppCompatTextView sportName) {
    this.rootView = rootView;
    this.cardView = cardView;
    this.sportName = sportName;
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
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      LinearLayoutCompat cardView = (LinearLayoutCompat) rootView;

      id = R.id.sportName;
      AppCompatTextView sportName = ViewBindings.findChildViewById(rootView, id);
      if (sportName == null) {
        break missingId;
      }

      return new RvSelectSportListItemBinding((LinearLayoutCompat) rootView, cardView, sportName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
