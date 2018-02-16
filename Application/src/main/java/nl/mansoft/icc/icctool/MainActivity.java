package nl.mansoft.icc.icctool;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ActionBar.TabListener {
    private final String TAG = MainActivity.class.getName();

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    public static int parseHex(EditText number) {
        String s = number.getText().toString();
        return s.isEmpty() ? 0 : Integer.parseInt(s, 16);
    }

    /**
     * Create the activity. Sets up an {@link android.app.ActionBar} with tabs, and then configures the
     * {@link ViewPager} contained inside R.layout.activity_main.
     *
     * <p>A {@link SectionsPagerAdapter} will be instantiated to hold the different pages of
     * fragments that are to be displayed. A
     * {@link android.support.v4.view.ViewPager.SimpleOnPageChangeListener} will also be configured
     * to receive callbacks when the user swipes between pages in the ViewPager.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the UI from res/layout/activity_main.xml
        setContentView(R.layout.sample_main);

        // Set up the action bar. The navigation mode is set to NAVIGATION_MODE_TABS, which will
        // cause the ActionBar to render a set of tabs. Note that these tabs are *not* rendered
        // by the ViewPager; additional logic is lower in this file to synchronize the ViewPager
        // state with the tab state. (See mViewPager.setOnPageChangeListener() and onTabSelected().)
        // BEGIN_INCLUDE (set_navigation_mode)
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // END_INCLUDE (set_navigation_mode)

        // BEGIN_INCLUDE (setup_view_pager)
        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        // END_INCLUDE (setup_view_pager)

        // When swiping between different sections, select the corresponding tab. We can also use
        // ActionBar.Tab#select() to do this if we have a reference to the Tab.
        // BEGIN_INCLUDE (page_change_listener)
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
        // END_INCLUDE (page_change_listener)

        // BEGIN_INCLUDE (add_tabs)
        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter. Also
            // specify this Activity object, which implements the TabListener interface, as the
            // callback (listener) for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
        // END_INCLUDE (add_tabs)
    }

    /**
     * Update {@link ViewPager} after a tab has been selected in the ActionBar.
     *
     * @param tab Tab that was selected.
     * @param fragmentTransaction A {@link android.app.FragmentTransaction} for queuing fragment operations to
     *                            execute once this method returns. This FragmentTransaction does
     *                            not support being added to the back stack.
     */
    // BEGIN_INCLUDE (on_tab_selected)
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, tell the ViewPager to switch to the corresponding page.
        mViewPager.setCurrentItem(tab.getPosition());
    }
    // END_INCLUDE (on_tab_selected)

    /**
     * Unused. Required for {@link android.app.ActionBar.TabListener}.
     */
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * Unused. Required for {@link android.app.ActionBar.TabListener}.
     */
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    // BEGIN_INCLUDE (fragment_pager_adapter)
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages. This provides the data for the {@link ViewPager}.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
    // END_INCLUDE (fragment_pager_adapter)

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // BEGIN_INCLUDE (fragment_pager_adapter_getitem)
        /**
         * Get fragment corresponding to a specific position. This will be used to populate the
         * contents of the {@link ViewPager}.
         *
         * @param position Position to fetch fragment for.
         * @return Fragment for specified position.
         */
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            Fragment fragment;
            if (position == 0) {
                fragment = new BasicSectionFragment();

            } else {
                fragment = new SimIoSectionFragment();
            }
            //Bundle args = new Bundle();
            //args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
            //fragment.setArguments(args);
            return fragment;
        }
        // END_INCLUDE (fragment_pager_adapter_getitem)

        // BEGIN_INCLUDE (fragment_pager_adapter_getcount)
        /**
         * Get number of pages the {@link ViewPager} should render.
         *
         * @return Number of fragments to be rendered as pages.
         */
        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
        // END_INCLUDE (fragment_pager_adapter_getcount)

        // BEGIN_INCLUDE (fragment_pager_adapter_getpagetitle)
        /**
         * Get title for each of the pages. This will be displayed on each of the tabs.
         *
         * @param position Page to fetch title for.
         * @return Title for specified page.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
            }
            return null;
        }
        // END_INCLUDE (fragment_pager_adapter_getpagetitle)
    }

    public static class BasicSectionFragment extends Fragment {
        private final String TAG = MainActivity.class.getName();
        private EditText mCla;
        private EditText mInstruction;
        private EditText mP1;
        private EditText mP2;
        private EditText mP3;
        private EditText mData;
        private TextView mResponse;

        public BasicSectionFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_basic, container, false);
            FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.basic_fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int cla = parseHex(mCla);
                    Log.d(TAG, "cla: " + cla);
                    int instruction = parseHex(mInstruction);
                    Log.d(TAG, "instruction: " + instruction);
                    int p1 = parseHex(mP1);
                    Log.d(TAG, "p1: " + p1);
                    int p2 = parseHex(mP2);
                    Log.d(TAG, "p2: " + p2);
                    int p3 = parseHex(mP3);
                    Log.d(TAG, "p3: " + p3);
                    String data = mData.getText().toString();
                    Log.d(TAG, "data: " + data);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                    String response = "No MODIFY_PHONE_STATE permission";

                    if (getActivity().checkSelfPermission(Manifest.permission.MODIFY_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                        response = telephonyManager.iccTransmitApduBasicChannel(cla, instruction, p1, p2, p3, data);
                    }
                    Log.d(TAG, "iccTransmitApduBasicChannel response: " + response);
                    mResponse.setText(response);
                }
            });
            mCla = (EditText) rootView.findViewById(R.id.cla);
            mInstruction = (EditText) rootView.findViewById(R.id.instruction);
            mP1 = (EditText) rootView.findViewById(R.id.basic_p1);
            mP2 = (EditText) rootView.findViewById(R.id.basic_p2);
            mP3 = (EditText) rootView.findViewById(R.id.basic_p3);
            mData = (EditText) rootView.findViewById(R.id.data);
            mResponse = (TextView) rootView.findViewById(R.id.basic_response);
            return rootView;
        }
    }

    public static class SimIoSectionFragment extends Fragment {
        private final String TAG = MainActivity.class.getName();
        private EditText mFileId;
        private EditText mCommand;
        private EditText mP1;
        private EditText mP2;
        private EditText mP3;
        private EditText mFilePath;
        private TextView mResponse;
        private final static char[] hexArray = "0123456789ABCDEF".toCharArray();


        public SimIoSectionFragment() {
        }

        public static String bytesToHex(byte[] bytes) {
            char[] hexChars = new char[bytes.length * 2];
            for ( int j = 0; j < bytes.length; j++ ) {
                int v = bytes[j] & 0xFF;
                hexChars[j * 2] = hexArray[v >>> 4];
                hexChars[j * 2 + 1] = hexArray[v & 0x0F];
            }
            return new String(hexChars);
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_simio, container, false);
            FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.simio_fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int fileid = parseHex(mFileId);
                    Log.d(TAG, "file id: " + fileid);
                    int command = parseHex(mCommand);
                    Log.d(TAG, "command: " + command);
                    int p1 = parseHex(mP1);
                    Log.d(TAG, "p1: " + p1);
                    int p2 = parseHex(mP2);
                    Log.d(TAG, "p2: " + p2);
                    int p3 = parseHex(mP3);
                    Log.d(TAG, "p3: " + p3);
                    String filepath = mFilePath.getText().toString();
                    Log.d(TAG, "data: " + filepath);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                    String response = "No MODIFY_PHONE_STATE permission";

                    if (getActivity().checkSelfPermission(Manifest.permission.MODIFY_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                        byte[] b = telephonyManager.iccExchangeSimIO(fileid, command, p1, p2, p3, filepath);
                        response = bytesToHex(b);
                    }
                    Log.d(TAG, "iccExchangeSimIO response: " + response);
                    mResponse.setText(response);
                }
            });
            mFileId = (EditText) rootView.findViewById(R.id.fileid);
            mCommand = (EditText) rootView.findViewById(R.id.command);
            mP1 = (EditText) rootView.findViewById(R.id.simio_p1);
            mP2 = (EditText) rootView.findViewById(R.id.simio_p2);
            mP3 = (EditText) rootView.findViewById(R.id.simio_p3);
            mFilePath = (EditText) rootView.findViewById(R.id.filepath);
            mResponse = (TextView) rootView.findViewById(R.id.simio_response);
            return rootView;
        }
    }

}
