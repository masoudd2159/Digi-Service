package ir.ac.sku.service.digiservice.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.activity.main.MainActivity;
import ir.ac.sku.service.digiservice.api.enter.LoginModel;
import ir.ac.sku.service.digiservice.base.BaseFragment;
import ir.ac.sku.service.digiservice.circularprogressbutton.CircularProgressButton;
import ir.ac.sku.service.digiservice.config.MyAPI;
import ir.ac.sku.service.digiservice.util.ManagerHelper;
import ir.ac.sku.service.digiservice.util.Tools;

import static ir.ac.sku.service.digiservice.util.MyApplication.API_KEY;
import static ir.ac.sku.service.digiservice.util.MyApplication.APPLICATION_ID;

@SuppressLint("NonConstantResourceId")
public class SignInFragment extends BaseFragment {

    @BindView(R.id.cardView) CardView cardView;
    @BindView(R.id.button_continue) CircularProgressButton sendBtn;
    @BindView(R.id.editText_Email) EditText editTextEmail;
    @BindView(R.id.editText_password) EditText editTextPassword;

    @Override protected int getLayoutResource() {
        return R.layout.fragment_sign_in;
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sendBtn.setIndeterminateProgressMode(true);
        sendBtn.setCardView(getContext(), cardView);
    }

    @OnClick(R.id.layout_icon)
    public void onClickItemViewWebSite(View v) {
        Tools.openWebViewActivity(getContext(), MyAPI.DIGI_SERVICE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick(R.id.button_continue)
    public void onClickItemSignIn(View view) {
        String username = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (username.equals("")) {
            editTextEmail.setError("لطفا پست الکترونیک خود را وارد کنید!");
        } else if (password.equals("")) {
            editTextPassword.setError("لطفا رمز عبور خود را وارد کنید!");
        } else {
            if (ManagerHelper.checkInternetServices(getContext())) {
                if (sendBtn.getProgress() == CircularProgressButton.ERROR_STATE_PROGRESS) {
                    sendBtn.setProgress(CircularProgressButton.IDLE_STATE_PROGRESS);
                } else {
                    getPasswordSalt(username, password);
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getPasswordSalt(String username, String password) {
        String checksum = Tools.encryptString("username=" + username.toLowerCase().trim() + "&applicationId=" + APPLICATION_ID + API_KEY);
        sendBtn.setProgress(CircularProgressButton.INDETERMINATE_STATE_PROGRESS);
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("applicationId", APPLICATION_ID);
        params.put("checksum", checksum.trim().toLowerCase());

        LoginModel.fetchUserKey(getContext(), params, (ok, obj) -> {
            LoginModel response = (LoginModel) obj;
            if (ok) {
                String hashedPassword = Tools.encryptBase64String(password, response.getData().get(0).getPasswordSalt());
                checkPassword(username, hashedPassword);
            } else {
                sendBtn.setProgress(CircularProgressButton.ERROR_STATE_PROGRESS);
            }
        });
    }

    private void checkPassword(String username, String hashedPassword) {
        String checksum = Tools.encryptString("username=" + username.toLowerCase().trim() + "&password=" + hashedPassword.toLowerCase().trim() + "&applicationId=" + APPLICATION_ID + API_KEY);
        String params = "enter?username=" + username + "&password=" + hashedPassword + "&applicationId=" + APPLICATION_ID + "&checksum=" + checksum;
        Log.i(getTagLog(), params);
        LoginModel.fetchFromWeb(getContext(), params, (ok, obj) -> {
            LoginModel response = (LoginModel) obj;
            if (ok) {
                sendBtn.setProgress(CircularProgressButton.SUCCESS_STATE_PROGRESS);
                getPreferencesUtils().setStartSigningKey(false);
                new Handler(Looper.getMainLooper()).postDelayed(() -> startActivity(null, MainActivity.class), 512);
            } else {
                Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
                sendBtn.setProgress(CircularProgressButton.ERROR_STATE_PROGRESS);
            }
        });
    }

    @OnClick(R.id.layout_register)
    public void onClickItemSignUp(View view) {
        Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_signUpFragment);
    }


}