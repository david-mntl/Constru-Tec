package com.epatec.construtecmovil;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        final TextView usertxt = (TextView)findViewById(R.id.usertxtview);
        UserDataHolder x = UserDataHolder.getInstance();
        usertxt.setText(x.user);

        final ImageButton logoutButton = (ImageButton) findViewById(R.id.logoutbutton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final UserDataHolder x = UserDataHolder.getInstance();
                if (x.user != "") {
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("")
                            .setContentText("¿Está seguro que desea salir?")
                            .setConfirmText("Salir")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    TextView usertxt = (TextView) findViewById(R.id.usertxtview);

                                    x.user = "";
                                    x.userROLE = "";
                                    x.userID = "";
                                    x.userType="";
                                    usertxt.setText(x.user);
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                } else {
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("¡Atención!")
                            .setContentText("No ha ingresado al sistema")
                            .show();
                }
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        final TextView usertxt = (TextView)findViewById(R.id.usertxtview);
        UserDataHolder x = UserDataHolder.getInstance();
        usertxt.setText(x.user);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        UserDataHolder userInfo = UserDataHolder.getInstance();
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);

                break;

            case 2:
                mTitle = getString(R.string.title_section2);
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                Intent register = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(register);
                break;

            case 4:
                if(userInfo.user != "")
                {
                    mTitle = getString(R.string.title_section4);
                    Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(profileIntent);
                    break;
                }
                else
                {
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops")
                            .setContentText("Porfavor debe registrarse")
                            .show();
                };
                break;

            case 5:
                mTitle = getString(R.string.title_section5);
                Intent projectIntent = new Intent(MainActivity.this, ProjectsActivity.class);
                startActivity(projectIntent);
                break;

            case 6:
                mTitle = getString(R.string.title_section6);
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case 7:
                if (userInfo.userType == 1+"")
                {
                    mTitle = getString(R.string.title_section7);
                    Intent managerIntent = new Intent(MainActivity.this, addProjectActivity.class);
                    startActivity(managerIntent);
                    break;
                }

        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
