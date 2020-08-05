package ir.ac.sku.service.digiservice.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.util.ManagerHelper;

public class ItemSelectedTabFragment extends Fragment {

    //* Views
    @BindView(R.id.fragmentWebView_WebView) WebView webView;
    private View rootView;
    private String htmlCode;

    public static ItemSelectedTabFragment newInstance(String htmlCode) {
        ItemSelectedTabFragment itemSelectedTabFragment = new ItemSelectedTabFragment();
        Bundle args = new Bundle();
        args.putString("htmlCode", htmlCode);
        itemSelectedTabFragment.setArguments(args);
        return itemSelectedTabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        htmlCode = getArguments().getString("htmlCode");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_web_view, container, false);
        ButterKnife.bind(this, rootView);
        setUpView(htmlCode);
        return rootView;
    }

    private void setUpView(String htmlCode) {
        webView.loadDataWithBaseURL(
                "file:///android_asset/images/",
                ManagerHelper.getStyle(
                        "fonts/IRANSansMobile(FaNum)_Light.ttf",
                        "18",
                        "justify",
                        "#2C2C2C")
                        + htmlCode,
                "text/html",
                "utf-8",
                "");
    }
}
