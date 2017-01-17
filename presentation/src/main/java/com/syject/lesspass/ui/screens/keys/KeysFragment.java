package com.syject.lesspass.ui.screens.keys;

import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.syject.data.entities.Options;
import com.syject.lesspass.R;
import com.syject.lesspass.ui.screens.lesspass.KeysAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@OptionsMenu(R.menu.fragment_keys)
@EFragment(R.layout.fragment_keys)
public class KeysFragment extends Fragment implements SearchView.OnQueryTextListener, IKeysView {

    @ViewById
    RecyclerView keysRecyclerView;

    @ViewById
    LinearLayout contentLinearLayout;

    @ViewById
    LinearLayout loading;

    @ViewById
    RelativeLayout nothingToShow;

    @Bean
    KeysPresenter presenter;

    KeysAdapter adapter;

    RecyclerView.LayoutManager layoutManager;

    List<Options> optionsList;

    private static final Comparator<Options> ALPHABETICAL_COMPARATOR = (a, b) -> a.getSite().compareTo(b.getSite());

    private Subscription subscription;

    @AfterViews
    protected void initViews() {
        presenter.setView(this);

        loading.setVisibility(View.VISIBLE);
        subscription = presenter.getOptions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(throwable -> {
                    Log.w("", throwable);
                    return null;
                })
                .subscribe(this::showContent);
    }

    private void showContent(List<Options> opts) {
        loading.setVisibility(View.GONE);
        if (opts == null || opts.isEmpty()) {
            nothingToShow.setVisibility(View.VISIBLE);
        } else {
            contentLinearLayout.setVisibility(View.VISIBLE);
            optionsList = opts;
            layoutManager = new LinearLayoutManager(getActivity());
            adapter = new KeysAdapter(opts, ALPHABETICAL_COMPARATOR);
            adapter.setCallBack(new KeysAdapter.KeysCallBack() {
                @Override
                public void onRemoveButtonClick(Options options) {
                    presenter.removeOptions(options)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(n -> {
                                adapter.remove(options);
                                onOptionRemove(options);
                            });
                }

                @Override
                public void onKeysClick(Options options) {
                    OnActionSelectedListener callback = (OnActionSelectedListener) getActivity();
                    callback.onKeyClicked(options);
                }
            });

            keysRecyclerView.setLayoutManager(layoutManager);
            keysRecyclerView.setAdapter(adapter);
        }
    }

    private void onOptionRemove(Options options) {
        optionsList.remove(options);
        if (optionsList.isEmpty()) {
            contentLinearLayout.setVisibility(View.GONE);
            nothingToShow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Options> filteredModelList = filter(optionsList, newText);
        adapter.replaceAll(filteredModelList);
        keysRecyclerView.scrollToPosition(0);
        return true;
    }

    private static List<Options> filter(List<Options> models, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<Options> filteredModelList = new ArrayList<>();
        for (Options model : models) {
            final String text = model.getSite().toLowerCase();
            if (text.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
        subscription.unsubscribe();
    }

    public interface OnActionSelectedListener {
        void onKeyClicked(Options options);
    }
}
