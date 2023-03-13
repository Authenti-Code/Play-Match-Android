// Generated by view binder compiler. Do not edit!
package com.playMatch.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.card.MaterialCardView;
import com.playMatch.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMatchDetailsBinding implements ViewBinding {
  @NonNull
  private final LinearLayoutCompat rootView_;

  @NonNull
  public final MaterialCardView accepted;

  @NonNull
  public final AppCompatTextView acceptedTv;

  @NonNull
  public final ImageView back;

  @NonNull
  public final TextView date;

  @NonNull
  public final ImageView edit;

  @NonNull
  public final AppCompatTextView fitnessLevel;

  @NonNull
  public final AppCompatTextView gender;

  @NonNull
  public final MaterialCardView invitedPlayers;

  @NonNull
  public final AppCompatTextView invitedPlayersTv;

  @NonNull
  public final TextView locationTv;

  @NonNull
  public final AppCompatImageView logo;

  @NonNull
  public final TextView name;

  @NonNull
  public final ProgressBar progressBar;

  @NonNull
  public final LinearLayoutCompat rootView;

  @NonNull
  public final RecyclerView rvAccepted;

  @NonNull
  public final RecyclerView rvInvitedPlayers;

  @NonNull
  public final RecyclerView rvSearchPlayers;

  @NonNull
  public final ImageView search;

  @NonNull
  public final MaterialCardView searchPlayers;

  @NonNull
  public final AppCompatTextView searchPlayersTv;

  @NonNull
  public final AppCompatTextView sportsName;

  @NonNull
  public final TextView time;

  private ActivityMatchDetailsBinding(@NonNull LinearLayoutCompat rootView_,
      @NonNull MaterialCardView accepted, @NonNull AppCompatTextView acceptedTv,
      @NonNull ImageView back, @NonNull TextView date, @NonNull ImageView edit,
      @NonNull AppCompatTextView fitnessLevel, @NonNull AppCompatTextView gender,
      @NonNull MaterialCardView invitedPlayers, @NonNull AppCompatTextView invitedPlayersTv,
      @NonNull TextView locationTv, @NonNull AppCompatImageView logo, @NonNull TextView name,
      @NonNull ProgressBar progressBar, @NonNull LinearLayoutCompat rootView,
      @NonNull RecyclerView rvAccepted, @NonNull RecyclerView rvInvitedPlayers,
      @NonNull RecyclerView rvSearchPlayers, @NonNull ImageView search,
      @NonNull MaterialCardView searchPlayers, @NonNull AppCompatTextView searchPlayersTv,
      @NonNull AppCompatTextView sportsName, @NonNull TextView time) {
    this.rootView_ = rootView_;
    this.accepted = accepted;
    this.acceptedTv = acceptedTv;
    this.back = back;
    this.date = date;
    this.edit = edit;
    this.fitnessLevel = fitnessLevel;
    this.gender = gender;
    this.invitedPlayers = invitedPlayers;
    this.invitedPlayersTv = invitedPlayersTv;
    this.locationTv = locationTv;
    this.logo = logo;
    this.name = name;
    this.progressBar = progressBar;
    this.rootView = rootView;
    this.rvAccepted = rvAccepted;
    this.rvInvitedPlayers = rvInvitedPlayers;
    this.rvSearchPlayers = rvSearchPlayers;
    this.search = search;
    this.searchPlayers = searchPlayers;
    this.searchPlayersTv = searchPlayersTv;
    this.sportsName = sportsName;
    this.time = time;
  }

  @Override
  @NonNull
  public LinearLayoutCompat getRoot() {
    return rootView_;
  }

  @NonNull
  public static ActivityMatchDetailsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMatchDetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_match_details, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMatchDetailsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.accepted;
      MaterialCardView accepted = ViewBindings.findChildViewById(rootView, id);
      if (accepted == null) {
        break missingId;
      }

      id = R.id.acceptedTv;
      AppCompatTextView acceptedTv = ViewBindings.findChildViewById(rootView, id);
      if (acceptedTv == null) {
        break missingId;
      }

      id = R.id.back;
      ImageView back = ViewBindings.findChildViewById(rootView, id);
      if (back == null) {
        break missingId;
      }

      id = R.id.date;
      TextView date = ViewBindings.findChildViewById(rootView, id);
      if (date == null) {
        break missingId;
      }

      id = R.id.edit;
      ImageView edit = ViewBindings.findChildViewById(rootView, id);
      if (edit == null) {
        break missingId;
      }

      id = R.id.fitnessLevel;
      AppCompatTextView fitnessLevel = ViewBindings.findChildViewById(rootView, id);
      if (fitnessLevel == null) {
        break missingId;
      }

      id = R.id.gender;
      AppCompatTextView gender = ViewBindings.findChildViewById(rootView, id);
      if (gender == null) {
        break missingId;
      }

      id = R.id.invitedPlayers;
      MaterialCardView invitedPlayers = ViewBindings.findChildViewById(rootView, id);
      if (invitedPlayers == null) {
        break missingId;
      }

      id = R.id.invitedPlayersTv;
      AppCompatTextView invitedPlayersTv = ViewBindings.findChildViewById(rootView, id);
      if (invitedPlayersTv == null) {
        break missingId;
      }

      id = R.id.locationTv;
      TextView locationTv = ViewBindings.findChildViewById(rootView, id);
      if (locationTv == null) {
        break missingId;
      }

      id = R.id.logo;
      AppCompatImageView logo = ViewBindings.findChildViewById(rootView, id);
      if (logo == null) {
        break missingId;
      }

      id = R.id.name;
      TextView name = ViewBindings.findChildViewById(rootView, id);
      if (name == null) {
        break missingId;
      }

      id = R.id.progressBar;
      ProgressBar progressBar = ViewBindings.findChildViewById(rootView, id);
      if (progressBar == null) {
        break missingId;
      }

      LinearLayoutCompat rootView_ = (LinearLayoutCompat) rootView;

      id = R.id.rvAccepted;
      RecyclerView rvAccepted = ViewBindings.findChildViewById(rootView, id);
      if (rvAccepted == null) {
        break missingId;
      }

      id = R.id.rvInvitedPlayers;
      RecyclerView rvInvitedPlayers = ViewBindings.findChildViewById(rootView, id);
      if (rvInvitedPlayers == null) {
        break missingId;
      }

      id = R.id.rv_searchPlayers;
      RecyclerView rvSearchPlayers = ViewBindings.findChildViewById(rootView, id);
      if (rvSearchPlayers == null) {
        break missingId;
      }

      id = R.id.search;
      ImageView search = ViewBindings.findChildViewById(rootView, id);
      if (search == null) {
        break missingId;
      }

      id = R.id.searchPlayers;
      MaterialCardView searchPlayers = ViewBindings.findChildViewById(rootView, id);
      if (searchPlayers == null) {
        break missingId;
      }

      id = R.id.searchPlayersTv;
      AppCompatTextView searchPlayersTv = ViewBindings.findChildViewById(rootView, id);
      if (searchPlayersTv == null) {
        break missingId;
      }

      id = R.id.sportsName;
      AppCompatTextView sportsName = ViewBindings.findChildViewById(rootView, id);
      if (sportsName == null) {
        break missingId;
      }

      id = R.id.time;
      TextView time = ViewBindings.findChildViewById(rootView, id);
      if (time == null) {
        break missingId;
      }

      return new ActivityMatchDetailsBinding((LinearLayoutCompat) rootView, accepted, acceptedTv,
          back, date, edit, fitnessLevel, gender, invitedPlayers, invitedPlayersTv, locationTv,
          logo, name, progressBar, rootView_, rvAccepted, rvInvitedPlayers, rvSearchPlayers, search,
          searchPlayers, searchPlayersTv, sportsName, time);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
