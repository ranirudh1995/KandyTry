package iiitb.org.kandytry;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.genband.kandy.api.Kandy;
import com.genband.kandy.api.services.calls.IKandyCall;
import com.genband.kandy.api.services.calls.IKandyIncomingCall;
import com.genband.kandy.api.services.calls.IKandyOutgoingCall;
import com.genband.kandy.api.services.calls.KandyCallResponseListener;
import com.genband.kandy.api.services.calls.KandyCallServiceNotificationListener;
import com.genband.kandy.api.services.calls.KandyCallState;
import com.genband.kandy.api.services.calls.KandyCallTerminationReason;
import com.genband.kandy.api.services.calls.KandyOutgingVoipCallOptions;
import com.genband.kandy.api.services.calls.KandyRecord;
import com.genband.kandy.api.services.calls.KandyView;
import com.genband.kandy.api.services.common.KandyMissedCallMessage;
import com.genband.kandy.api.services.common.KandyWaitingVoiceMailMessage;
import com.genband.kandy.api.utils.KandyIllegalArgumentException;

public class activity3 extends AppCompatActivity implements KandyCallServiceNotificationListener {
    private static final String TAG = activity3.class.getSimpleName();
    private IKandyCall mCurrentCall;
    private boolean mIsCreateVideoCall = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity3);
        registerCallListener();
        Button initiate = (Button) findViewById(R.id.initiate);
        initiate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               // KandyRecord caller = KandyTry.kandyRecord;
                KandyRecord callee = null;
                try {
                    callee = new KandyRecord("anirudh@sample.iiitb.org");
                } catch (KandyIllegalArgumentException e) {
                    //TODO insert your code here
                    return;
                }

                Log.d("TEST: ", callee.getUri());
//                Log.d("TEST: ", caller.getUri());

                KandyView localVideoView = (KandyView) findViewById(R.id.kandy_local_video_view);
                KandyView remoteVideoView = (KandyView) findViewById(R.id.kandy_remote_video_view);
                IKandyOutgoingCall currentCall = Kandy.getServices().getCallService().createVoipCall(null, callee, KandyOutgingVoipCallOptions.START_CALL_WITH_VIDEO);

//YOU MUST TO PASS THE NON NULL VALUE OF LOCAL VIEW TO THE KandyOutgoingCall or/and KandyIncomingCall
                currentCall.setLocalVideoView(localVideoView);
//YOU MUST TO PASS THE NON NULL VALUE OF REMOTE VIEW TO THE KandyOutgoingCall or/and KandyIncomingCall
                currentCall.setRemoteVideoView(remoteVideoView);
                currentCall.establish(new KandyCallResponseListener() {

                    @Override
                    public void onRequestSucceeded(IKandyCall call) {
                        //TODO insert your code here
                    }

                    @Override
                    public void onRequestFailed(IKandyCall call, int arg1, String arg2) {
                        //TODO insert your code here
                    }
                });

            }
        });
    }

    private void registerCallListener() {
        Log.d(TAG, "registerCallListener()");
        Kandy.getServices().getCallService().registerNotificationListener(this);
    }

    private void unregisterCallListener() {
        Log.d(TAG, "unregisterCallListener()");
        Kandy.getServices().getCallService().unregisterNotificationListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity3, menu);
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


    public void accept(IKandyIncomingCall mCurrentCall) {
        if(mCurrentCall.canReceiveVideo()){
            //Call has video m line
            //so this call can be answered with
            //acceptCall(true) if one wants to answer with video
            // or
            //acceptCall(false) if one wants to answer with audio only

            ((IKandyIncomingCall)mCurrentCall).accept(mIsCreateVideoCall, new KandyCallResponseListener() {

                @Override
                public void onRequestSucceeded(IKandyCall call) {
                    Log.i(TAG, "mCurrentIncomingCall.accept succeed");
                }

                @Override
                public void onRequestFailed(IKandyCall call, int responseCode, String err) {
                    Log.i(TAG, "mCurrentIncomingCall.accept. Error: " + err + "\nResponse code: " + responseCode);
                }
            });
        }
        else
        {
            //Call has only one m line, so this call will be answered
            //with only audio
            //acceptCall(false)

            ((IKandyIncomingCall)mCurrentCall).accept(false, new KandyCallResponseListener() {

                @Override
                public void onRequestSucceeded(IKandyCall call) {
                    Log.i(TAG, "mCurrentIncomingCall.accept succeed" );
                }

                @Override
                public void onRequestFailed(IKandyCall call, int responseCode, String err) {
                    Log.i(TAG, "mCurrentIncomingCall.accept. Error: " + err + "\nResponse code: " + responseCode);
                }
            });
        }
    }

    private void answerCall(final IKandyIncomingCall pCall) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                accept(pCall);
            }
        });
    }
    private void setAudioState(boolean pState) {

           /*final String state = String.format("%s %s",getString(R.string.activity_calls_audio_state),
                   String.valueOf(pState));
           setStateForTextViewOnUIThread(uiAudioStateTextView, state);*/
    }

    private void setVideoState(boolean isReceiving, boolean isSending) {
   /*
           final String state = String.format("%s %s, %s %s", getString(R.string.activity_calls_receiving_video_state),
                   String.valueOf(isReceiving), getString(R.string.activity_calls_sending_video_state), String.valueOf(isSending));
           setStateForTextViewOnUIThread(uiVideoStateTextView, state);*/
    }
    @Override
    public void onIncomingCall(IKandyIncomingCall iKandyIncomingCall) {
        Log.i(TAG, "onIncomingCall: " + iKandyIncomingCall.getCallId());
        KandyView localVideoView = (KandyView) findViewById(R.id.kandy_local_video_view);
        KandyView remoteVideoView = (KandyView) findViewById(R.id.kandy_remote_video_view);

        iKandyIncomingCall.setLocalVideoView(localVideoView);
        iKandyIncomingCall.setRemoteVideoView(remoteVideoView);
        answerCall(iKandyIncomingCall);

    }

    @Override
    public void onMissedCall(KandyMissedCallMessage kandyMissedCallMessage) {

    }

    @Override
    public void onWaitingVoiceMailCall(KandyWaitingVoiceMailMessage kandyWaitingVoiceMailMessage) {

    }

    @Override
    public void onCallStateChanged(KandyCallState state, IKandyCall iKandyCall) {

        Log.i(TAG, "onCallStateChanged: " + state);
        //Here while state is KandyCallState.DIALING you can play a ringback
        Log.i(TAG, "onCallStatusChanged: " + state.name());

        String reason = "";
        if(state == KandyCallState.TALKING) {
//            KandyView localVideoView = (KandyView) findViewById(R.id.kandy_local_video_view);
//            KandyView remoteVideoView = (KandyView) findViewById(R.id.kandy_remote_video_view);
//
//            mCurrentCall.setLocalVideoView(localVideoView);
//            mCurrentCall.setRemoteVideoView(remoteVideoView);
        }else if(state == KandyCallState.TERMINATED) {
            KandyCallTerminationReason callTerminationReason = iKandyCall.getTerminationReason();
            Log.d(TAG, "registerCallListener: " + "call TERMINATED reason: " + callTerminationReason);
            reason = "("+callTerminationReason.getReason()+")";
            mCurrentCall = null;
        }
    }

    @Override
    public void onVideoStateChanged(IKandyCall call, boolean isReceivingVideo,
                                    boolean isSendingVideo) {
/*
        Log.i(TAG, "onVideoStateChanged: Receiving: " + isReceivingVideo+ " Sending: " + isSendingVideo);
        setVideoState(isReceivingVideo, isSendingVideo);
        setAudioState(call.isMute());
*/
    }
    @Override
    public void onGSMCallIncoming(IKandyCall call, String incomingNumber) {
        //Here you can implement the GSMCallIncoming behavior/actions
      /*  Log.i(TAG, "onGSMCallIncoming");*/
    }

    @Override
    public void onGSMCallConnected(IKandyCall call, String incomingNumber) {
        //Here you can implement the GSMCallConnected behavior/actions
/*
        Log.i(TAG, "onGSMCallConnected");
        doHold(mCurrentCall);
*/
    }

    @Override
    public void onGSMCallDisconnected(IKandyCall call, String incomingNumber) {
        //Here you can implement the GSMCallDisconnection behavior/actions
/*
        Log.i(TAG, "onGSMCallDisconnected");
        doUnHold(mCurrentCall);
*/
    }
}
