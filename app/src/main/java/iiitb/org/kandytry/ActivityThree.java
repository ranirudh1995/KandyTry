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
import com.genband.kandy.api.services.calls.KandyRecord;
import com.genband.kandy.api.services.calls.KandyView;
import com.genband.kandy.api.services.chats.KandyChatMessage;
import com.genband.kandy.api.services.chats.KandySMSMessage;
import com.genband.kandy.api.services.common.KandyResponseListener;
import com.genband.kandy.api.utils.KandyIllegalArgumentException;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityThree extends AppCompatActivity {
    private static final String TAG = ActivityTwo.class.getSimpleName();
    Button btnOTP;
    EditText txtmobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        btnOTP = (Button) findViewById(R.id.btnOTP);
        txtmobile = (EditText) findViewById(R.id.txtmobile);


        btnOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String destination_code = txtmobile.getText().toString();
                String otp_code = "Your code is: 12345";
                KandySMSMessage message = null;
                try {
                    message = new KandySMSMessage(destination_code, "Kandy SMS", otp_code);
                } catch (KandyIllegalArgumentException e) {
                    //TODO insert your code here
                }

                // Sending message
                Kandy.getServices().getChatService().sendSMS(message, new KandyResponseListener() {
                    @Override
                    public void onRequestFailed(final int responseCode, final String err) {
                        //TODO insert your code here
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "failure: " + (String.valueOf(responseCode)) + " " + err, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onRequestSucceded() {
                        //TODO insert your code here
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(getApplicationContext(), ActivityTwo.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                            }
                        });

                    }
                });



                // Toast.makeText(activity2.this, "OPT Sent", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private KandyRecord getRecipient(String destination) {
        KandyRecord recipient;
        try{
            recipient = new KandyRecord(destination);
        }
        catch(KandyIllegalArgumentException ex){
            return null;
        }



        return recipient;
    }
    private JSONObject createJSONStub(String val)
    {
        JSONObject additionalData = new JSONObject();
        try
        {

            additionalData.put("value", val);
            //additionalData.put("type", "youtubeVideo");
            //additionalData.put("url", url);

        }
        catch (JSONException e)
        {
            Log.w(TAG, "createJSONStub: " + e.getLocalizedMessage());
        }

        return additionalData;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_two, menu);
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
