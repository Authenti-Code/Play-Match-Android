// Generated by view binder compiler. Do not edit!
package com.playMatch.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.playMatch.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityLoginBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final AppCompatTextView forgotPassTv;

  @NonNull
  public final CardView login;

  @NonNull
  public final RelativeLayout relative4;

  @NonNull
  public final AppCompatTextView signupTv;

  @NonNull
  public final AppCompatTextView textSign;

  private ActivityLoginBinding(@NonNull FrameLayout rootView,
      @NonNull AppCompatTextView forgotPassTv, @NonNull CardView login,
      @NonNull RelativeLayout relative4, @NonNull AppCompatTextView signupTv,
      @NonNull AppCompatTextView textSign) {
    this.rootView = rootView;
    this.forgotPassTv = forgotPassTv;
    this.login = login;
    this.relative4 = relative4;
    this.signupTv = signupTv;
    this.textSign = textSign;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_login, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLoginBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.forgotPassTv;
      AppCompatTextView forgotPassTv = ViewBindings.findChildViewById(rootView, id);
      if (forgotPassTv == null) {
        break missingId;
      }

      id = R.id.login;
      CardView login = ViewBindings.findChildViewById(rootView, id);
      if (login == null) {
        break missingId;
      }

      id = R.id.relative4;
      RelativeLayout relative4 = ViewBindings.findChildViewById(rootView, id);
      if (relative4 == null) {
        break missingId;
      }

      id = R.id.signupTv;
      AppCompatTextView signupTv = ViewBindings.findChildViewById(rootView, id);
      if (signupTv == null) {
        break missingId;
      }

      id = R.id.text_sign;
      AppCompatTextView textSign = ViewBindings.findChildViewById(rootView, id);
      if (textSign == null) {
        break missingId;
      }

      return new ActivityLoginBinding((FrameLayout) rootView, forgotPassTv, login, relative4,
          signupTv, textSign);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
