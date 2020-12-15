package ir.ac.sku.service.digiservice.fragment.dialogfragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;

import java.util.HashMap;

import butterknife.BindView;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.api.scheduling.SchedulingModel;
import ir.ac.sku.service.digiservice.base.BaseDialogFragment;
import ir.ac.sku.service.digiservice.util.ManagerHelper;


@SuppressLint("NonConstantResourceId")
public class SchedulingFragmentDialog extends BaseDialogFragment {

    //* Dependence
    private final int id;
    private final String schTable;
    //* Views
    @BindView(R.id.dialogFragmentScheduling_WebView) WebView webView;
    @BindView(R.id.dialogFragmentScheduling_ButtonClose) Button close;
    @BindView(R.id.dialogFragmentScheduling_AnimationView) LottieAnimationView loadingAnimationView;

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
        setUpData();
        close.setOnClickListener(view1 -> {
            Log.i(getTagLog(), "Dismiss Scheduling Dialog Fragment");
            dismiss();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        changeDialogSize((int) (getDisplayMetrics().widthPixels * 0.9), ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setUpData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        Log.i(getTagLog(), "Scheduling ID : " + id);

        SchedulingModel.fetchFromWeb(getContext(), params, (ok, obj) -> {
            if (ok) {
                SchedulingModel schedulingModel = (SchedulingModel) obj;
                if (schedulingModel.isOk()) {
                    loadingAnimationView.setVisibility(View.INVISIBLE);
                    showWebView(schedulingModel.getData().get(0).getSchTable());
                }
            } else {
                showWebView(schTable);
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
}
