// Generated by view binder compiler. Do not edit!
package com.playMatch.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.playMatch.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class NearByMatchesBinding implements ViewBinding {
  @NonNull
  private final LinearLayoutCompat rootView;

  @NonNull
  public final ImageView back;

  @NonNull
  public final ImageView filter;

  @NonNull
  public final RecyclerView rvNearbyMatches;

  @NonNull
  public final ImageView search;

  @NonNull
  public final ImageView streetMap;

  private NearByMatchesBinding(@NonNull LinearLayoutCompat rootView, @NonNull ImageView back,
      @NonNull ImageView filter, @NonNull RecyclerView rvNearbyMatches, @NonNull ImageView search,
      @NonNull ImageView streetMap) {
    this.rootView = rootView;
    this.back = back;
    this.filter = filter;
    this.rvNearbyMatches = rvNearbyMatches;
    this.search = search;
    this.streetMap = streetMap;
  }

  @Override
  @NonNull
  public LinearLayoutCompat getRoot() {
    return rootView;
  }

  @NonNull
  public static NearByMatchesBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static NearByMatchesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.near_by_matches, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static NearByMatchesBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.back;
      ImageView back = ViewBindings.findChildViewById(rootView, id);
      if (back == null) {
        break missingId;
      }

      id = R.id.filter;
      ImageView filter = ViewBindings.findChildViewById(rootView, id);
      if (filter == null) {
        break missingId;
      }

      id = R.id.rv_nearby_matches;
      RecyclerView rvNearbyMatches = ViewBindings.findChildViewById(rootView, id);
      if (rvNearbyMatches == null) {
        break missingId;
      }

      id = R.id.search;
      ImageView search = ViewBindings.findChildViewById(rootView, id);
      if (search == null) {
        break missingId;
      }

      id = R.id.streetMap;
      ImageView streetMap = ViewBindings.findChildViewById(rootView, id);
      if (streetMap == null) {
        break missingId;
      }

      return new NearByMatchesBinding((LinearLayoutCompat) rootView, back, filter, rvNearbyMatches,
          search, streetMap);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
