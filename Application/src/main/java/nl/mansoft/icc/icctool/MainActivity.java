package nl.mansoft.icc.icctool;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.telephony.IccOpenLogicalChannelResponse;
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
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new AdpuSectionFragment();
                    break;
                case 1:
                    fragment = new SimIoSectionFragment();
                    break;
                case 2:
                    fragment = new JavaSectionFragment();
                    break;
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
            return 3;
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
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
        // END_INCLUDE (fragment_pager_adapter_getpagetitle)
    }

    public static class AdpuSectionFragment extends Fragment {
        private final String TAG = MainActivity.class.getName();
        private EditText mChannel;
        private EditText mCla;
        private EditText mInstruction;
        private EditText mP1;
        private EditText mP2;
        private EditText mData;
        private TextView mResponse;

        public AdpuSectionFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_apdu, container, false);
            FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int channel = mChannel.getText().toString().isEmpty() ? -1 : parseHex(mChannel);
                    Log.d(TAG, "channel: " + channel);
                    int cla = parseHex(mCla);
                    Log.d(TAG, "cla: " + cla);
                    int instruction = parseHex(mInstruction);
                    Log.d(TAG, "instruction: " + instruction);
                    int p1 = parseHex(mP1);
                    Log.d(TAG, "p1: " + p1);
                    int p2 = parseHex(mP2);
                    Log.d(TAG, "p2: " + p2);
                    String data = mData.getText().toString();
                    Log.d(TAG, "data: " + data);
                    int p3 = data.length() / 2;
                    String response;
                    try {
                        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                        response = channel == -1 ? telephonyManager.iccTransmitApduBasicChannel(cla, instruction, p1, p2, p3, data) : telephonyManager.iccTransmitApduLogicalChannel(channel, cla, instruction, p1, p2, p3, data);
                    } catch (Exception e) {
                        response = e.getMessage();
                    }
                    Log.d(TAG, "iccTransmitApduBasicChannel response: " + response);
                    mResponse.setText(response);
                }
            });
            mChannel = (EditText) rootView.findViewById(R.id.channel);
            mCla = (EditText) rootView.findViewById(R.id.cla);
            mInstruction = (EditText) rootView.findViewById(R.id.instruction);
            mP1 = (EditText) rootView.findViewById(R.id.p1);
            mP2 = (EditText) rootView.findViewById(R.id.p2);
            mData = (EditText) rootView.findViewById(R.id.data);
            mResponse = (TextView) rootView.findViewById(R.id.response);
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
            FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
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
                    String response;
                    try {
                        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                        byte[] b = telephonyManager.iccExchangeSimIO(fileid, command, p1, p2, p3, filepath);
                        response = bytesToHex(b);
                    } catch (Exception e) {
                        response = e.getMessage();
                    }
                    Log.d(TAG, "iccExchangeSimIO response: " + response);
                    mResponse.setText(response);
                }
            });
            mFileId = (EditText) rootView.findViewById(R.id.fileid);
            mCommand = (EditText) rootView.findViewById(R.id.command);
            mP1 = (EditText) rootView.findViewById(R.id.p1);
            mP2 = (EditText) rootView.findViewById(R.id.p2);
            mP3 = (EditText) rootView.findViewById(R.id.p3);
            mFilePath = (EditText) rootView.findViewById(R.id.filepath);
            mResponse = (TextView) rootView.findViewById(R.id.response);
            return rootView;
        }
    }

    public static class JavaSectionFragment extends Fragment {
        private final String TAG = MainActivity.class.getName();
        private EditText mAID;
        private EditText mCla;
        private EditText mInstruction;
        private EditText mP1;
        private EditText mP2;
        private EditText mData;
        private TextView mResponse;
        private final static char[] hexArray = "0123456789ABCDEF".toCharArray();


        public JavaSectionFragment() {
        }

        public static byte[] hexStringToByteArray(String s) {
            int len = s.length();
            byte[] data = new byte[len / 2];
            for (int i = 0; i < len; i += 2) {
                data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                        + Character.digit(s.charAt(i+1), 16));
            }
            return data;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_java, container, false);
            FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String aid = mAID.getText().toString();;
                    Log.d(TAG, "AID: " + aid);
                    int cla = parseHex(mCla);
                    Log.d(TAG, "cla: " + cla);
                    int instruction = parseHex(mInstruction);
                    Log.d(TAG, "instruction: " + instruction);
                    int p1 = parseHex(mP1);
                    Log.d(TAG, "p1: " + p1);
                    int p2 = parseHex(mP2);
                    Log.d(TAG, "p2: " + p2);
                    String data = mData.getText().toString();
                    Log.d(TAG, "data: " + data);
                    int p3 = data.length() / 2;
                    String response;
                    try {
                        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                        // Open channel and select aid
                        IccOpenLogicalChannelResponse iccOpenLogicalChannelResponse = telephonyManager.iccOpenLogicalChannel(aid);
                        int status = iccOpenLogicalChannelResponse.getStatus();
                        if (status == IccOpenLogicalChannelResponse.STATUS_NO_ERROR) {
                            int channel = iccOpenLogicalChannelResponse.getChannel();
                            Log.d(TAG, "channel: " + channel);
                            response = telephonyManager.iccTransmitApduLogicalChannel(channel, cla, instruction, p1, p2, p3, data);
                            Log.d(TAG, "response1: " + response);
                            if (response.endsWith("9000")) {
                                // convert response data to ASCII
                                response = new String(hexStringToByteArray(response.substring(0, response.length() - 4)));
                                Log.d(TAG, "response2: " + response);
                            }
                            if (!telephonyManager.iccCloseLogicalChannel(channel)) {
                                Log.e(TAG, "Error closing logical channel");
                            }
                        } else {
                            response = "Error opening logical channel, status: " + status;
                            Log.e(TAG, response);
                        }
                    } catch (Exception e) {
                        response = "Exception: " + e.getMessage();
                        Log.e(TAG, response);
                    }
                    mResponse.setText(response);
                }
            });
            mAID = (EditText) rootView.findViewById(R.id.aid);
            mCla = (EditText) rootView.findViewById(R.id.cla);
            mInstruction = (EditText) rootView.findViewById(R.id.instruction);
            mP1 = (EditText) rootView.findViewById(R.id.p1);
            mP2 = (EditText) rootView.findViewById(R.id.p2);
            mData = (EditText) rootView.findViewById(R.id.data);
            mResponse = (TextView) rootView.findViewById(R.id.response);
            return rootView;
        }
    }
}
