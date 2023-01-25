// Generated by view binder compiler. Do not edit!
package com.playMatch.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.playMatch.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentMatchesBinding implements ViewBinding {
  @NonNull
  private final LinearLayoutCompat rootView;

  @NonNull
  public final ImageView createMatch;

  @NonNull
  public final AppCompatTextView pastMatch;

  @NonNull
  public final RecyclerView rvPast;

  @NonNull
  public final RecyclerView rvUpcoming;

  @NonNull
  public final AppCompatTextView upcomingMatch;

  private FragmentMatchesBinding(@NonNull LinearLayoutCompat rootView,
      @NonNull ImageView createMatch, @NonNull AppCompatTextView pastMatch,
      @NonNull RecyclerView rvPast, @NonNull RecyclerView rvUpcoming,
      @NonNull AppCompatTextView upcomingMatch) {
    this.rootView = rootView;
    this.createMatch = createMatch;
    this.pastMatch = pastMatch;
    this.rvPast = rvPast;
    this.rvUpcoming = rvUpcoming;
    this.upcomingMatch = upcomingMatch;
  }

  @Override
  @NonNull
  public LinearLayoutCompat getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentMatchesBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentMatchesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_matches, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentMatchesBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.createMatch;
      ImageView createMatch = ViewBindings.findChildViewById(rootView, id);
      if (createMatch == null) {
        break missingId;
      }

      id = R.id.past_match;
      AppCompatTextView pastMatch = ViewBindings.findChildViewById(rootView, id);
      if (pastMatch == null) {
        break missingId;
      }

      id = R.id.rvPast;
      RecyclerView rvPast = ViewBindings.findChildViewById(rootView, id);
      if (rvPast == null) {
        break missingId;
      }

      id = R.id.rvUpcoming;
      RecyclerView rvUpcoming = ViewBindings.findChildViewById(rootView, id);
      if (rvUpcoming == null) {
        break missingId;
      }

      id = R.id.upcoming_match;
      AppCompatTextView upcomingMatch = ViewBindings.findChildViewById(rootView, id);
      if (upcomingMatch == null) {
        break missingId;
      }

      return new FragmentMatchesBinding((LinearLayoutCompat) rootView, createMatch, pastMatch,
          rvPast, rvUpcoming, upcomingMatch);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
