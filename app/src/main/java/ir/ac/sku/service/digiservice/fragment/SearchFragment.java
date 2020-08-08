package ir.ac.sku.service.digiservice.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

import butterknife.BindView;
import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.adapter.SearchAdapter;
import ir.ac.sku.service.digiservice.api.search.SearchModel;
import ir.ac.sku.service.digiservice.base.BaseFragment;
import ir.ac.sku.service.digiservice.config.MyLog;
import ir.ac.sku.service.digiservice.util.MyHandler;

@SuppressLint("LongLogTag")
public class SearchFragment extends BaseFragment {
    //* Views
    @BindView(R.id.fragmentSearch_SearchList) RecyclerView searchList;
    @BindView(R.id.fragmentSearch_EditTextSearch) EditText editTextSearch;
    @BindView(R.id.fragmentSearch_SearchImageViewButton) ImageView searchButton;

    //* View Inflater
    @Override protected int getLayoutResource() {
        return R.layout.fragment_search;
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showInputMethod();
        editTextSearch.requestFocus();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextSearch.length() > 0) {
                    setUpSearchData(editTextSearch.getText().toString().trim());
                } else {
                    Toast.makeText(getContext(), "لطفا عبارت مورد نظر را وارد کنید!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setUpSearchData(String query) {
        HashMap<String, String> params = new HashMap<>();

        params.put("filter", query);
        params.put("eventId", "0");

        Log.i(MyLog.SEARCH, "Query Search : " + params.get("filter"));
        Log.i(MyLog.SEARCH, "Event Id : " + params.get("eventId"));

        SearchModel.fetchFromWeb(getContext(), params, new MyHandler() {
            @Override
            public void onResponse(boolean ok, Object obj) {
                if (ok) {
                    showData((SearchModel) obj);
                }
            }
        });
    }

    private void showData(SearchModel model) {
        searchList.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_from_right));
        searchList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        searchList.setAdapter(new SearchAdapter(getContext(), model.getData()));
    }

    private void showInputMethod() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
}
