// Generated by view binder compiler. Do not edit!
package com.playMatch.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.card.MaterialCardView;
import com.playMatch.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityPaymentBinding implements ViewBinding {
  @NonNull
  private final LinearLayoutCompat rootView;

  @NonNull
  public final ImageView back;

  @NonNull
  public final MaterialCardView beginner;

  @NonNull
  public final AppCompatTextView beginnerTv;

  @NonNull
  public final AppCompatTextView cardNumber;

  @NonNull
  public final AppCompatTextView cardNumber2;

  @NonNull
  public final CheckBox checkbox;

  @NonNull
  public final CheckBox checkbox2;

  @NonNull
  public final AppCompatTextView hideNumber;

  @NonNull
  public final AppCompatTextView hideNumber2;

  @NonNull
  public final CardView pay;

  @NonNull
  public final AppCompatImageView visa;

  @NonNull
  public final AppCompatImageView visa2;

  private ActivityPaymentBinding(@NonNull LinearLayoutCompat rootView, @NonNull ImageView back,
      @NonNull MaterialCardView beginner, @NonNull AppCompatTextView beginnerTv,
      @NonNull AppCompatTextView cardNumber, @NonNull AppCompatTextView cardNumber2,
      @NonNull CheckBox checkbox, @NonNull CheckBox checkbox2,
      @NonNull AppCompatTextView hideNumber, @NonNull AppCompatTextView hideNumber2,
      @NonNull CardView pay, @NonNull AppCompatImageView visa, @NonNull AppCompatImageView visa2) {
    this.rootView = rootView;
    this.back = back;
    this.beginner = beginner;
    this.beginnerTv = beginnerTv;
    this.cardNumber = cardNumber;
    this.cardNumber2 = cardNumber2;
    this.checkbox = checkbox;
    this.checkbox2 = checkbox2;
    this.hideNumber = hideNumber;
    this.hideNumber2 = hideNumber2;
    this.pay = pay;
    this.visa = visa;
    this.visa2 = visa2;
  }

  @Override
  @NonNull
  public LinearLayoutCompat getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityPaymentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityPaymentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_payment, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityPaymentBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.back;
      ImageView back = ViewBindings.findChildViewById(rootView, id);
      if (back == null) {
        break missingId;
      }

      id = R.id.beginner;
      MaterialCardView beginner = ViewBindings.findChildViewById(rootView, id);
      if (beginner == null) {
        break missingId;
      }

      id = R.id.beginnerTv;
      AppCompatTextView beginnerTv = ViewBindings.findChildViewById(rootView, id);
      if (beginnerTv == null) {
        break missingId;
      }

      id = R.id.cardNumber;
      AppCompatTextView cardNumber = ViewBindings.findChildViewById(rootView, id);
      if (cardNumber == null) {
        break missingId;
      }

      id = R.id.cardNumber2;
      AppCompatTextView cardNumber2 = ViewBindings.findChildViewById(rootView, id);
      if (cardNumber2 == null) {
        break missingId;
      }

      id = R.id.checkbox;
      CheckBox checkbox = ViewBindings.findChildViewById(rootView, id);
      if (checkbox == null) {
        break missingId;
      }

      id = R.id.checkbox2;
      CheckBox checkbox2 = ViewBindings.findChildViewById(rootView, id);
      if (checkbox2 == null) {
        break missingId;
      }

      id = R.id.hideNumber;
      AppCompatTextView hideNumber = ViewBindings.findChildViewById(rootView, id);
      if (hideNumber == null) {
        break missingId;
      }

      id = R.id.hideNumber2;
      AppCompatTextView hideNumber2 = ViewBindings.findChildViewById(rootView, id);
      if (hideNumber2 == null) {
        break missingId;
      }

      id = R.id.pay;
      CardView pay = ViewBindings.findChildViewById(rootView, id);
      if (pay == null) {
        break missingId;
      }

      id = R.id.visa;
      AppCompatImageView visa = ViewBindings.findChildViewById(rootView, id);
      if (visa == null) {
        break missingId;
      }

      id = R.id.visa2;
      AppCompatImageView visa2 = ViewBindings.findChildViewById(rootView, id);
      if (visa2 == null) {
        break missingId;
      }

      return new ActivityPaymentBinding((LinearLayoutCompat) rootView, back, beginner, beginnerTv,
          cardNumber, cardNumber2, checkbox, checkbox2, hideNumber, hideNumber2, pay, visa, visa2);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
