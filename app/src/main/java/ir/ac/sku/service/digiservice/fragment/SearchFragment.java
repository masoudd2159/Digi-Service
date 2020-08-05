package ir.ac.sku.service.digiservice.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.adapter.SearchAdapter;
import ir.ac.sku.service.digiservice.model.SearchModel;
import ir.ac.sku.service.digiservice.util.MyHandler;

public class SearchFragment extends Fragment {
    //* Views
    @BindView(R.id.fragmentSearch_EditTextSearch) EditText editTextSearch;
    @BindView(R.id.fragmentSearch_SearchImageViewButton) ImageView searchButton;
    @BindView(R.id.fragmentSearch_SearchList) RecyclerView searchList;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, rootView);
        showInputMethod();
        editTextSearch.requestFocus();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextSearch.length() > 0) {
                    setUpSearchData(editTextSearch.getText().toString().trim());
                } else {
                    Toast.makeText(rootView.getContext(), "لطفا عبارت مورد نظر را وارد کنید!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }

    private void setUpSearchData(String query) {
        HashMap<String, String> params = new HashMap<>();

        params.put("filter", query);
        params.put("eventId", "0");

        SearchModel.fetchFromWeb(rootView.getContext(), params, new MyHandler() {
            @Override
            public void onResponse(boolean ok, Object obj) {
                if (ok) {
                    showData((SearchModel) obj);
                }
            }
        });
    }

    private void showData(SearchModel model) {
        searchList.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(rootView.getContext(), R.anim.layout_animation_from_right));
        searchList.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));
        searchList.setAdapter(new SearchAdapter(rootView.getContext(), model.getData()));
    }

    private void showInputMethod() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
}
