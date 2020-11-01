package ir.ac.sku.service.digiservice.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import butterknife.OnClick;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.base.BaseFragment;

@SuppressLint("NonConstantResourceId")
public class SignUpFragment extends BaseFragment {
    @Override protected int getLayoutResource() {
        return R.layout.fragment_sign_up;
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.nav_btn)
    public void next(View view) {
        Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_verificationFragment);
    }

    @OnClick(R.id.nav_btn_back)
    public void back(View view) {
        Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_signInFragment);
    }
}