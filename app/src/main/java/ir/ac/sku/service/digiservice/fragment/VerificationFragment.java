package ir.ac.sku.service.digiservice.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import butterknife.OnClick;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.activity.main.MainActivity;
import ir.ac.sku.service.digiservice.base.BaseFragment;

@SuppressLint("NonConstantResourceId")
public class VerificationFragment extends BaseFragment {
    @Override protected int getLayoutResource() {
        return R.layout.fragment_verification;
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.nav_btn)
    public void next(View view) {
        getContext().startActivity(new Intent(getContext(), MainActivity.class));
    }

   @OnClick(R.id.nav_btn_back)
    public void back(View view) {
        Navigation.findNavController(view).navigate(R.id.action_verificationFragment_to_signUpFragment);
    }

}