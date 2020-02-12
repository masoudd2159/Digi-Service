package ir.ac.sku.service.digiservice.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Objects;

import ir.ac.sku.service.digiservice.R;

public class SearchFragment extends Fragment {

    private View rootView;
    private EditText editTextSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        this.rootView = view;
        init();
        showInputMethod();
        editTextSearch.requestFocus();
        return view;
    }

    private void showInputMethod() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private void init() {
        editTextSearch = rootView.findViewById(R.id.fragmentSearch_EditTextSearch);
    }
}
