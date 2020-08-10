package ir.ac.sku.service.digiservice.fragment.dialogfragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;

import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.api.scheduling.SchedulingModel;
import ir.ac.sku.service.digiservice.base.BaseDialogFragment;
import ir.ac.sku.service.digiservice.config.MyLog;
import ir.ac.sku.service.digiservice.util.ManagerHelper;
import ir.ac.sku.service.digiservice.util.MyHandler;

@SuppressLint("LongLogTag")
public class SchedulingFragmentDialog extends BaseDialogFragment {

    //* Views
    @BindView(R.id.dialogFragmentScheduling_WebView) WebView webView;
    @BindView(R.id.dialogFragmentScheduling_ButtonClose) Button close;
    @BindView(R.id.dialogFragmentScheduling_AnimationView) LottieAnimationView loadingAnimationView;

    //* Dependence
    private int id;
    private String schTable;

    //* Constructor
    public SchedulingFragmentDialog(int id, String schTable) {
        this.id = id;
        this.schTable = schTable;
    }

    //* View Inflater
    @Override
    public int getLayoutResource() {
        return R.layout.dialog_fragment_scheduling;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadingAnimationView.setVisibility(View.VISIBLE);

        if (getDialog() != null && getDialog().isShowing()) {
            Log.i(MyLog.SCHEDULING, "Dismiss Scheduling Dialog Fragment");
            dismiss();
        }

        Log.i(MyLog.SCHEDULING, "Open Scheduling Dialog Fragment");
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.dimAmount = 0.7f;
        getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getDialog().setCancelable(false);

        setUpData();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(MyLog.SCHEDULING, "Dismiss Scheduling Dialog Fragment");
                dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        changeDialogSize();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setUpData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        Log.i(MyLog.SCHEDULING, "Scheduling ID : " + id);

        SchedulingModel.fetchFromWeb(getContext(), params, new MyHandler() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(boolean ok, Object obj) {
                if (ok) {
                    SchedulingModel schedulingModel = (SchedulingModel) obj;
                    if (schedulingModel.isOk()) {
                        loadingAnimationView.setVisibility(View.INVISIBLE);
                        showWebView(schedulingModel.getData().get(0).getSchTable());
                    }
                } else {
                    showWebView(schTable);
                }
            }
        });
    }

    private void showWebView(String htmlCode) {
        webView.loadDataWithBaseURL(
                "file:///android_asset/images/",
                ManagerHelper.getStyle(
                        "fonts/IRANSansMobile(FaNum)_Light.ttf",
                        "19",
                        "justify",
                        "#2C2C2C")
                        + htmlCode,
                "text/html",
                "utf-8",
                "");
    }

    private void changeDialogSize() {
        Log.i(MyLog.SCHEDULING, "Change Dialog Display Metrics");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setLayout(
                (int) (displayMetrics.widthPixels * 0.9),
                (int) (displayMetrics.heightPixels * 0.9));
    }
}
