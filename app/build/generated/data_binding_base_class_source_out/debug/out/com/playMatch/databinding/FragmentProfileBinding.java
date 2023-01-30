// Generated by view binder compiler. Do not edit!
package com.playMatch.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public final class FragmentProfileBinding implements ViewBinding {
  @NonNull
  private final LinearLayoutCompat rootView;

  @NonNull
  public final MaterialCardView Fcv;

  @NonNull
  public final AppCompatTextView Ftv;

  @NonNull
  public final MaterialCardView Mcv;

  @NonNull
  public final AppCompatTextView Mtv;

  @NonNull
  public final MaterialCardView Sacv;

  @NonNull
  public final AppCompatTextView Satv;

  @NonNull
  public final MaterialCardView Scv;

  @NonNull
  public final AppCompatTextView Stv;

  @NonNull
  public final MaterialCardView Tcv;

  @NonNull
  public final MaterialCardView Thcv;

  @NonNull
  public final AppCompatTextView Thtv;

  @NonNull
  public final AppCompatTextView Ttv;

  @NonNull
  public final MaterialCardView Wcv;

  @NonNull
  public final AppCompatTextView Wtv;

  @NonNull
  public final TextView date;

  @NonNull
  public final ImageView editProfile;

  @NonNull
  public final TextView name;

  @NonNull
  public final RecyclerView rvSports;

  @NonNull
  public final RecyclerView rvStatistics;

  @NonNull
  public final ImageView setting;

  @NonNull
  public final TextView time;

  private FragmentProfileBinding(@NonNull LinearLayoutCompat rootView,
      @NonNull MaterialCardView Fcv, @NonNull AppCompatTextView Ftv, @NonNull MaterialCardView Mcv,
      @NonNull AppCompatTextView Mtv, @NonNull MaterialCardView Sacv,
      @NonNull AppCompatTextView Satv, @NonNull MaterialCardView Scv,
      @NonNull AppCompatTextView Stv, @NonNull MaterialCardView Tcv, @NonNull MaterialCardView Thcv,
      @NonNull AppCompatTextView Thtv, @NonNull AppCompatTextView Ttv,
      @NonNull MaterialCardView Wcv, @NonNull AppCompatTextView Wtv, @NonNull TextView date,
      @NonNull ImageView editProfile, @NonNull TextView name, @NonNull RecyclerView rvSports,
      @NonNull RecyclerView rvStatistics, @NonNull ImageView setting, @NonNull TextView time) {
    this.rootView = rootView;
    this.Fcv = Fcv;
    this.Ftv = Ftv;
    this.Mcv = Mcv;
    this.Mtv = Mtv;
    this.Sacv = Sacv;
    this.Satv = Satv;
    this.Scv = Scv;
    this.Stv = Stv;
    this.Tcv = Tcv;
    this.Thcv = Thcv;
    this.Thtv = Thtv;
    this.Ttv = Ttv;
    this.Wcv = Wcv;
    this.Wtv = Wtv;
    this.date = date;
    this.editProfile = editProfile;
    this.name = name;
    this.rvSports = rvSports;
    this.rvStatistics = rvStatistics;
    this.setting = setting;
    this.time = time;
  }

  @Override
  @NonNull
  public LinearLayoutCompat getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_profile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentProfileBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Fcv;
      MaterialCardView Fcv = ViewBindings.findChildViewById(rootView, id);
      if (Fcv == null) {
        break missingId;
      }

      id = R.id.Ftv;
      AppCompatTextView Ftv = ViewBindings.findChildViewById(rootView, id);
      if (Ftv == null) {
        break missingId;
      }

      id = R.id.Mcv;
      MaterialCardView Mcv = ViewBindings.findChildViewById(rootView, id);
      if (Mcv == null) {
        break missingId;
      }

      id = R.id.Mtv;
      AppCompatTextView Mtv = ViewBindings.findChildViewById(rootView, id);
      if (Mtv == null) {
        break missingId;
      }

      id = R.id.Sacv;
      MaterialCardView Sacv = ViewBindings.findChildViewById(rootView, id);
      if (Sacv == null) {
        break missingId;
      }

      id = R.id.Satv;
      AppCompatTextView Satv = ViewBindings.findChildViewById(rootView, id);
      if (Satv == null) {
        break missingId;
      }

      id = R.id.Scv;
      MaterialCardView Scv = ViewBindings.findChildViewById(rootView, id);
      if (Scv == null) {
        break missingId;
      }

      id = R.id.Stv;
      AppCompatTextView Stv = ViewBindings.findChildViewById(rootView, id);
      if (Stv == null) {
        break missingId;
      }

      id = R.id.Tcv;
      MaterialCardView Tcv = ViewBindings.findChildViewById(rootView, id);
      if (Tcv == null) {
        break missingId;
      }

      id = R.id.Thcv;
      MaterialCardView Thcv = ViewBindings.findChildViewById(rootView, id);
      if (Thcv == null) {
        break missingId;
      }

      id = R.id.Thtv;
      AppCompatTextView Thtv = ViewBindings.findChildViewById(rootView, id);
      if (Thtv == null) {
        break missingId;
      }

      id = R.id.Ttv;
      AppCompatTextView Ttv = ViewBindings.findChildViewById(rootView, id);
      if (Ttv == null) {
        break missingId;
      }

      id = R.id.Wcv;
      MaterialCardView Wcv = ViewBindings.findChildViewById(rootView, id);
      if (Wcv == null) {
        break missingId;
      }

      id = R.id.Wtv;
      AppCompatTextView Wtv = ViewBindings.findChildViewById(rootView, id);
      if (Wtv == null) {
        break missingId;
      }

      id = R.id.date;
      TextView date = ViewBindings.findChildViewById(rootView, id);
      if (date == null) {
        break missingId;
      }

      id = R.id.editProfile;
      ImageView editProfile = ViewBindings.findChildViewById(rootView, id);
      if (editProfile == null) {
        break missingId;
      }

      id = R.id.name;
      TextView name = ViewBindings.findChildViewById(rootView, id);
      if (name == null) {
        break missingId;
      }

      id = R.id.rv_sports;
      RecyclerView rvSports = ViewBindings.findChildViewById(rootView, id);
      if (rvSports == null) {
        break missingId;
      }

      id = R.id.rv_statistics;
      RecyclerView rvStatistics = ViewBindings.findChildViewById(rootView, id);
      if (rvStatistics == null) {
        break missingId;
      }

      id = R.id.setting;
      ImageView setting = ViewBindings.findChildViewById(rootView, id);
      if (setting == null) {
        break missingId;
      }

      id = R.id.time;
      TextView time = ViewBindings.findChildViewById(rootView, id);
      if (time == null) {
        break missingId;
      }

      return new FragmentProfileBinding((LinearLayoutCompat) rootView, Fcv, Ftv, Mcv, Mtv, Sacv,
          Satv, Scv, Stv, Tcv, Thcv, Thtv, Ttv, Wcv, Wtv, date, editProfile, name, rvSports,
          rvStatistics, setting, time);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
