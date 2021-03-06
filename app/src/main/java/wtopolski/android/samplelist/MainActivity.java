package wtopolski.android.samplelist;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity implements ElementListFragment.ListFragmentItemClickListener, ElementSingleFragment.SingleFragmentItemClickListener {
    private Toolbar mToolbar;
    private FragmentManager mFragmentManager;
    private CoordinatorLayout mCoordinatorContainer;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCoordinatorContainer = (CoordinatorLayout) findViewById(R.id.coordinator_container);

        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mToolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(mToolbar);

        mFragmentManager = getFragmentManager();

        if (mFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            ElementListFragment fragment = new ElementListFragment();
            fragmentTransaction.add(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onListFragmentItemClick(ElementSingleFragment.ARGUMENT_NONE);
            }
        });
    }

    @Override
    public void onListFragmentItemClick(long position) {
        Bundle bundle = new Bundle();
        bundle.putLong(ElementSingleFragment.ARGUMENT_ID, position);

        ElementSingleFragment fragment = new ElementSingleFragment();
        fragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void listFragmentUpdateToolbar(String value) {
        mToolbar.setSubtitle(value);
        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        mToolbar.setNavigationOnClickListener(null);
    }

    @Override
    public void showFAB(boolean value) {
        fab.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    @Override
    public void notifyUser(String value) {
        Snackbar.make(mCoordinatorContainer, "" + value, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void singleFragmentUpdateToolbar(String value) {
        mToolbar.setSubtitle(value);
        mToolbar.setNavigationIcon(R.drawable.ic_back_navigation);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!mFragmentManager.popBackStackImmediate()) {
            super.onBackPressed();
        }
    }
}
