package com.pliseproject.testbaas;

import android.app.AlertDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nifty.cloud.mb.*;

import java.util.List;
import java.util.Random;


public class MainActivity extends ActionBarActivity {
    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button) findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NCMBQuery<NCMBObject> query = NCMBQuery.getQuery("TestClass");
//                query.whereEqualTo("message", "Hello, NCMB!");
                try {
                    query.setSkip((int) (Math.random() * query.count()));
                } catch (Exception e) {
                }
                query.findInBackground(new FindCallback<NCMBObject>() {
                    @Override
                    public void done(List<NCMBObject> result, NCMBException e) {
//                        if (result.isEmpty() != true) {
                            ((TextView) findViewById(R.id.text)).setText(result.get(0).getString("message"));
//                            dispMessage(result.get(0));
//                        } else {
//                            PostData();
//                        }
                    }
                });

            }
        });

        ((Button) findViewById(R.id.submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostData();
            }
        });

        NCMB.initialize(this, "d5f48e68422ad7c65227ff2444fb327e5956fef5dabd239183df985f180adaa9",
                "e86658f1c52fdabe3722911706e653f38b3f669e14144ebdc2e8fd30fe3f1ae3");


    }

    private void PostData() {
        NCMBObject TestClass = new NCMBObject("TestClass");
        TestClass.put("message", ((EditText)findViewById(R.id.edit)).getText().toString());
        TestClass.saveInBackground();
    }

    private void dispMessage(NCMBObject message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("データ取得");
        alertDialogBuilder.setMessage(message.getString("message"));
        alertDialogBuilder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
