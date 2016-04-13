package iiitb.org.kandytry;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.genband.kandy.api.Kandy;
import com.genband.kandy.api.access.KandyLoginResponseListener;
import com.genband.kandy.api.services.calls.KandyRecord;
import com.genband.kandy.api.utils.KandyIllegalArgumentException;

/**
 * Created by SandyB on 19-Mar-16.
 */
public class KandyTry extends Application {
    private static final String TAG = "KandyTry";
//
//    static KandyRecord kandyRecord;

    @Override
    public void onCreate() {
        super.onCreate();

        // Init Kandy SDK
        Kandy.initialize(getApplicationContext(), "DAK59d2c0a84dda4e949be8e944a901ded8", "DAS63da421f86b24fd084c7a331b9d377fd");
        Log.d(TAG, "Login Succeeded! 123");
//        kandyRecord = null;
//        try {
//            kandyRecord = new KandyRecord("user1@sample.iiitb.org");
//        } catch (KandyIllegalArgumentException e) {
//            //TODO insert your code here
//            return;
//        }
//        String password = "1similiquetotamau1";
//        //String password = "thanks123";
//
//        Kandy.getAccess().login(kandyRecord, password, new KandyLoginResponseListener() {
//            @Override
//            public void onRequestFailed(int responseCode, String err) {
//                //TODO insert your code here
//            }
//
//            @Override
//            public void onLoginSucceeded() {
//                //TODO insert your code here
//                Log.d(TAG, "Login Succeeded!");
//            }
//        });
    }
}
