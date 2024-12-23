// Generated by view binder compiler. Do not edit!
package com.example.sleepwell.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.sleepwell.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentProfileBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button btnLogout;

  @NonNull
  public final Button btnReport;

  @NonNull
  public final Button btnSwitchUser;

  @NonNull
  public final ImageView userAvatar;

  @NonNull
  public final TextView userEmail;

  @NonNull
  public final TextView userName;

  private FragmentProfileBinding(@NonNull LinearLayout rootView, @NonNull Button btnLogout,
      @NonNull Button btnReport, @NonNull Button btnSwitchUser, @NonNull ImageView userAvatar,
      @NonNull TextView userEmail, @NonNull TextView userName) {
    this.rootView = rootView;
    this.btnLogout = btnLogout;
    this.btnReport = btnReport;
    this.btnSwitchUser = btnSwitchUser;
    this.userAvatar = userAvatar;
    this.userEmail = userEmail;
    this.userName = userName;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
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
      id = R.id.btn_logout;
      Button btnLogout = ViewBindings.findChildViewById(rootView, id);
      if (btnLogout == null) {
        break missingId;
      }

      id = R.id.btn_report;
      Button btnReport = ViewBindings.findChildViewById(rootView, id);
      if (btnReport == null) {
        break missingId;
      }

      id = R.id.btn_switch_user;
      Button btnSwitchUser = ViewBindings.findChildViewById(rootView, id);
      if (btnSwitchUser == null) {
        break missingId;
      }

      id = R.id.user_avatar;
      ImageView userAvatar = ViewBindings.findChildViewById(rootView, id);
      if (userAvatar == null) {
        break missingId;
      }

      id = R.id.user_email;
      TextView userEmail = ViewBindings.findChildViewById(rootView, id);
      if (userEmail == null) {
        break missingId;
      }

      id = R.id.user_name;
      TextView userName = ViewBindings.findChildViewById(rootView, id);
      if (userName == null) {
        break missingId;
      }

      return new FragmentProfileBinding((LinearLayout) rootView, btnLogout, btnReport,
          btnSwitchUser, userAvatar, userEmail, userName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
