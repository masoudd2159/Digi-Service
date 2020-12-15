package ir.ac.sku.service.digiservice.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import ir.ac.sku.service.digiservice.config.MyLog;
import ir.ac.sku.service.digiservice.util.SharedPreferencesUtils;

public abstract class BaseFragment extends Fragment {

    private SharedPreferencesUtils preferencesUtils;
    private View rootView;

    public SharedPreferencesUtils getPreferencesUtils() {
        return preferencesUtils;
    }

    public String getTagLog() {
        return MyLog.DIGI_SERVICE + getClass().getSimpleName();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(getLayoutResource(), container, false);
        preferencesUtils = new SharedPreferencesUtils(rootView.getContext());
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void startActivity(Bundle bundle, Class<?> cls) {
        Intent intent = new Intent(rootView.getContext(), cls);
        if (bundle != null)
            intent.putExtras(bundle);
        rootView.getContext().startActivity(intent);
    }

    protected abstract int getLayoutResource();
}
