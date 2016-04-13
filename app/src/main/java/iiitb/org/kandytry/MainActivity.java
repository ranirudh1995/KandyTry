package iiitb.org.kandytry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.genband.kandy.api.Kandy;
import com.genband.kandy.api.services.chats.KandySMSMessage;
import com.genband.kandy.api.services.common.KandyResponseListener;
import com.genband.kandy.api.utils.KandyIllegalArgumentException;

public class MainActivity extends AppCompatActivity {
    Button btnsend;
    EditText txtcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button VideoChat = (Button) findViewById(R.id.videochat);
        VideoChat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SubActivity.class));
            }
        });
        btnsend = (Button) findViewById(R.id.btnsend);
        txtcode = (EditText) findViewById(R.id.txtcode);

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String destination = "919945951154";

                KandySMSMessage message = null;
                try {
                    message = new KandySMSMessage(destination, "Kandy SMS", txtcode.getText().toString());
                } catch (KandyIllegalArgumentException e) {
                    //TODO insert your code here
                }

                // Sending message
                Kandy.getServices().getChatService().sendSMS(message, new KandyResponseListener() {
                    @Override
                    public void onRequestFailed(int responseCode, String err) {
                        //TODO insert your code here
                        Toast.makeText(MainActivity.this,"failure",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRequestSucceded() {
                        //TODO insert your code here
                        /*Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();*/
                    }
                });
            }


        });
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
