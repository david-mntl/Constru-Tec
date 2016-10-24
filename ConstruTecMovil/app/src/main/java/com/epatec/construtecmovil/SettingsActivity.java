
package com.epatec.construtecmovil;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;


public class SettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final EditText settingsIP = (EditText)findViewById(R.id.settingsIP);
        final EditText settingsPort = (EditText)findViewById(R.id.settingsPort);


        final Button settingsButton = (Button) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ConnectionDataHolder connClass = ConnectionDataHolder.getInstance();
                connClass.ipConnection = settingsIP.getText().toString();
                connClass.portConnection = settingsPort.getText().toString();
             }
        });


    }
}
