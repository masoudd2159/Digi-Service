package ir.ac.sku.service.digiservice.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;

import butterknife.BindView;
import butterknife.OnClick;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.base.BaseFragment;
import ir.ac.sku.service.digiservice.circularprogressbutton.CircularProgressButton;
import ir.ac.sku.service.digiservice.config.MyAPI;
import ir.ac.sku.service.digiservice.util.Tools;

@SuppressLint("NonConstantResourceId")
public class SignUpFragment extends BaseFragment {

    @BindView(R.id.cardView) CardView cardView;
    @BindView(R.id.button_continue) CircularProgressButton sendBtn;

    @Override protected int getLayoutResource() {
        return R.layout.fragment_sign_up;
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sendBtn.setIndeterminateProgressMode(true);
        sendBtn.setCardView(getContext(), cardView);
    }

    @OnClick(R.id.button_continue)
    public void onClickItemContinue(View view) {
        sendBtn.setProgress(CircularProgressButton.INDETERMINATE_STATE_PROGRESS);
        if (sendBtn.getProgress() == CircularProgressButton.ERROR_STATE_PROGRESS) {
            sendBtn.setProgress(CircularProgressButton.IDLE_STATE_PROGRESS);
        } else {
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                sendBtn.setProgress(CircularProgressButton.SUCCESS_STATE_PROGRESS);
                sendBtn.setEnabled(false);
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_verificationFragment);
                }, 1024);
            }, 1024);
        }
    }

    @OnClick(R.id.layout_icon)
    public void onClickItemViewWebSite(View v) {
        Tools.openWebViewActivity(getContext(), MyAPI.DIGI_SERVICE);
    }

    @OnClick(R.id.layout_register)
    public void onClickItemSignIn(View view) {
        Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_signInFragment);
    }
}