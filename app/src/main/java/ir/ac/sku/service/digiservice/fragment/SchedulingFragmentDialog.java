package ir.ac.sku.service.digiservice.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.model.SchedulingModel;
import ir.ac.sku.service.digiservice.util.ManagerHelper;
import ir.ac.sku.service.digiservice.util.MyHandler;

public class SchedulingFragmentDialog extends DialogFragment {

    //* Views
    @BindView(R.id.dialogFragmentScheduling_WebView) WebView webView;
    @BindView(R.id.dialogFragmentScheduling_ButtonClose) Button close;
    private View rootView;
    //* Dependence
    private int id;
    private String schTable;

    public SchedulingFragmentDialog(int id, String schTable) {
        this.id = id;
        this.schTable = schTable;
    }

    @Override
    public void onResume() {
        super.onResume();
        changeDialogSize();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_fragment_scheduling, container, false);
        ButterKnife.bind(this, rootView);

        if (getDialog() != null && getDialog().isShowing()) {
            dismiss();
        }

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
                dismiss();
            }
        });

        return rootView;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setUpData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        SchedulingModel.fetchFromWeb(rootView.getContext(), params, new MyHandler() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(boolean ok, Object obj) {
                if (ok) {
                    SchedulingModel schedulingModel = (SchedulingModel) obj;
                    if (schedulingModel.isOk()) {
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
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setLayout(
                (int) (displayMetrics.widthPixels * 0.9),
                (int) (displayMetrics.heightPixels * 0.9));
    }
}
