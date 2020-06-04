package ir.ac.sku.service.digiservice.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Objects;

import ir.ac.sku.service.digiservice.R;
import ir.ac.sku.service.digiservice.activity.main.AreaSelectedActivity;
import ir.ac.sku.service.digiservice.activity.main.ItemSelectedActivity;
import ir.ac.sku.service.digiservice.adapter.AreaSelectedAdapter;
import ir.ac.sku.service.digiservice.adapter.SearchAdapter;
import ir.ac.sku.service.digiservice.model.SearchModel;
import ir.ac.sku.service.digiservice.model.SelectedResourceModel;
import ir.ac.sku.service.digiservice.util.MyHandler;

public class SearchFragment extends Fragment {

    private View rootView;
    private EditText editTextSearch;
    private ImageView searchButton;
    private RecyclerView searchList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        this.rootView = view;
        init();

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
        return view;
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

    private void init() {
        editTextSearch = rootView.findViewById(R.id.fragmentSearch_EditTextSearch);
        searchButton = rootView.findViewById(R.id.fragmentSearch_SearchImageViewButton);
        searchList = rootView.findViewById(R.id.fragmentSearch_SearchList);
    }
}
