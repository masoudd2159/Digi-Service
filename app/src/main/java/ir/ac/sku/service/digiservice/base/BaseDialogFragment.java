package ir.ac.sku.service.digiservice.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import butterknife.ButterKnife;

public abstract class BaseDialogFragment extends AppCompatDialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResource(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public abstract int getLayoutResource();
}
