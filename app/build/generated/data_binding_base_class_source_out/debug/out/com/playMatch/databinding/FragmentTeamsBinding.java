// Generated by view binder compiler. Do not edit!
package com.playMatch.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.playMatch.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentTeamsBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView_;

  @NonNull
  public final ImageView addTeam;

  @NonNull
  public final LinearLayout rootView;

  @NonNull
  public final RecyclerView rvTeams;

  @NonNull
  public final SwipeRefreshLayout swipeRefreshLay;

  private FragmentTeamsBinding(@NonNull LinearLayout rootView_, @NonNull ImageView addTeam,
      @NonNull LinearLayout rootView, @NonNull RecyclerView rvTeams,
      @NonNull SwipeRefreshLayout swipeRefreshLay) {
    this.rootView_ = rootView_;
    this.addTeam = addTeam;
    this.rootView = rootView;
    this.rvTeams = rvTeams;
    this.swipeRefreshLay = swipeRefreshLay;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView_;
  }

  @NonNull
  public static FragmentTeamsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentTeamsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_teams, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentTeamsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.addTeam;
      ImageView addTeam = ViewBindings.findChildViewById(rootView, id);
      if (addTeam == null) {
        break missingId;
      }

      LinearLayout rootView_ = (LinearLayout) rootView;

      id = R.id.rvTeams;
      RecyclerView rvTeams = ViewBindings.findChildViewById(rootView, id);
      if (rvTeams == null) {
        break missingId;
      }

      id = R.id.swipeRefreshLay;
      SwipeRefreshLayout swipeRefreshLay = ViewBindings.findChildViewById(rootView, id);
      if (swipeRefreshLay == null) {
        break missingId;
      }

      return new FragmentTeamsBinding((LinearLayout) rootView, addTeam, rootView_, rvTeams,
          swipeRefreshLay);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
