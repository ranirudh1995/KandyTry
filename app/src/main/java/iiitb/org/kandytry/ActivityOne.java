package iiitb.org.kandytry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.genband.kandy.api.Kandy;
import com.genband.kandy.api.access.KandyLoginResponseListener;
import com.genband.kandy.api.services.calls.KandyRecord;
import com.genband.kandy.api.utils.KandyIllegalArgumentException;

public class ActivityOne extends AppCompatActivity {

    private static final String TAG = "KandyTry";

    static KandyRecord kandyRecord;
    EditText email;
    EditText password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        email = (EditText) findViewById(R.id.editText);
        password2 = (EditText) findViewById(R.id.editText2);

        Button btn = (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                kandyRecord = null;
                try {
                    kandyRecord = new KandyRecord(email.getText().toString());
                } catch (KandyIllegalArgumentException e) {
                    //TODO insert your code here
                    return;
                }
                String password = password2.getText().toString();
                //String password = "thanks123";

                Kandy.getAccess().login(kandyRecord, password, new KandyLoginResponseListener() {
                    @Override
                    public void onRequestFailed(int responseCode, String err) {
                        //TODO insert your code here
                        //Toast.makeText(activity1.this, "Login Unsucessfull", Toast.LENGTH_SHORT).show();
                        email.setText("");
                        password2.setText("");
                    }

                    @Override
                    public void onLoginSucceeded() {
                        //TODO insert your code here
                        Log.d(TAG, "Login Succeeded!");
                        Toast.makeText(ActivityOne.this, "Login Sucessfull", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), ActivityTwo.class));
                    }
                });

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity1, menu);
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
