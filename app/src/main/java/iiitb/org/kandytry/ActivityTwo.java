package iiitb.org.kandytry;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.genband.kandy.api.Kandy;
import com.genband.kandy.api.services.calls.IKandyCall;
import com.genband.kandy.api.services.calls.IKandyIncomingCall;
import com.genband.kandy.api.services.calls.KandyCallResponseListener;
import com.genband.kandy.api.services.calls.KandyCallServiceNotificationListener;
import com.genband.kandy.api.services.calls.KandyCallState;
import com.genband.kandy.api.services.calls.KandyCallTerminationReason;
import com.genband.kandy.api.services.calls.KandyRecord;
import com.genband.kandy.api.services.calls.KandyRecordType;
import com.genband.kandy.api.services.calls.KandyView;
import com.genband.kandy.api.services.chats.IKandyMessage;
import com.genband.kandy.api.services.chats.IKandyTransferProgress;
import com.genband.kandy.api.services.chats.KandyChatMessage;
import com.genband.kandy.api.services.chats.KandyChatServiceNotificationListener;
import com.genband.kandy.api.services.chats.KandyDeliveryAck;
import com.genband.kandy.api.services.chats.KandyMessageMediaItemType;
import com.genband.kandy.api.services.common.KandyMissedCallMessage;
import com.genband.kandy.api.services.common.KandyResponseListener;
import com.genband.kandy.api.services.common.KandyWaitingVoiceMailMessage;
import com.genband.kandy.api.utils.KandyIllegalArgumentException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class ActivityTwo extends AppCompatActivity implements KandyCallServiceNotificationListener {
    private static final String TAG = ActivityTwo.class.getSimpleName();
    Button access,deny;
    KandyView local,remote;
    int OTP_length=5,mobile_length=12;
    private IKandyCall mCurrentCall;
    private boolean mIsCreateVideoCall = true;
    String data = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        access=(Button)findViewById(R.id.access);
        deny=(Button)findViewById(R.id.deny);
        local = (KandyView) findViewById(R.id.kandy_local_video_view);
        remote = (KandyView) findViewById(R.id.kandy_remote_video_view);


        registerCallListener();
        registerChatsNotifications();


        access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (!data.isEmpty()) {*/
                Toast.makeText(ActivityTwo.this, "Call finished", Toast.LENGTH_SHORT).show();
                   /* if (data.length() == mobile_length) {
                        Intent i = new Intent(getApplicationContext(), ActivityThree.class);
                        startActivity(i);
                    } else if (data.length() == OTP_length) {
                        //send 1 to server
                        String destination = "pi@10j01.iiitb.org";
                        String text = "true";
                        JSONObject additionalData = createJSONStub(text);

                        // Set the recipient
                        KandyRecord recipient = getRecipient(destination);

                        if (recipient == null)
                            return;

                        // creating message to be sent
                        final KandyChatMessage message = new KandyChatMessage(recipient, text);

                        // set custome additional data to be sent with the message
                        message.getMediaItem().setAdditionalData(additionalData);

                        // Sending message
                        Kandy.getServices().getChatService().sendChat(message, new KandyResponseListener() {

                            @Override
                            public void onRequestFailed(int responseCode, String err) {
                                //Log.e(TAG, "Kandy.getChatService().sendMessage:onRequestFailed - Error: " + err + "\nResponse code: " + responseCode);
                                //UIUtils.handleResultOnUiThread(ChatServiceActivity.this, true, err);
                            }

                            @Override
                            public void onRequestSucceded() {
                                //Log.i(TAG, "Kandy.getChatService().sendMessage:onRequestSucceded - Message sent");
                                //UIUtils.handleResultOnUiThread(ChatServiceActivity.this, false, getString(R.string.activity_cahts_message_sent_label));
                                //addMessageOnUI(message);
                            }


                        });
                    }

                } else if (data.isEmpty()) {
                    Intent i = new Intent(getApplicationContext(), ActivityThree.class);
                    startActivity(i);
                }*/

                //send callee's mobile no.

                String destination = "pi@10j01.iiitb.org";
                String mobno = "9900203514";
                JSONObject additionalData = createJSONStub(mobno);

                // Set the recipient
                KandyRecord recipient = getRecipient(destination);

                if (recipient == null)
                    return;

                // creating message to be sent
                final KandyChatMessage message = new KandyChatMessage(recipient, mobno);


                // set custome additional data to be sent with the message
                message.getMediaItem().setAdditionalData(additionalData);

                // Sending message
                Kandy.getServices().getChatService().sendChat(message, new KandyResponseListener() {

                    @Override
                    public void onRequestFailed(int responseCode, String err) {
                        //Log.e(TAG, "Kandy.getChatService().sendMessage:onRequestFailed - Error: " + err + "\nResponse code: " + responseCode);
                        //UIUtils.handleResultOnUiThread(ChatServiceActivity.this, true, err);
                    }

                    @Override
                    public void onRequestSucceded() {
                        //Log.i(TAG, "Kandy.getChatService().sendMessage:onRequestSucceded - Message sent");
                        //UIUtils.handleResultOnUiThread(ChatServiceActivity.this, false, getString(R.string.activity_cahts_message_sent_label));
                        //addMessageOnUI(message);

                    }
                });

                Toast.makeText(ActivityTwo.this, "Mobile no. sent", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), ActivityThree.class);
                startActivity(i);

            }
        });

        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ActivityTwo.this, "Call is rejected", Toast.LENGTH_SHORT).show();


                String destination = "pi@10j01.iiitb.org";
                String text = "false";
                JSONObject additionalData = createJSONStub(text);

                // Set the recipient
                KandyRecord recipient = getRecipient(destination);

                if (recipient == null)
                    return;

                // creating message to be sent
                final KandyChatMessage message = new KandyChatMessage(recipient, text);


                // set custome additional data to be sent with the message
                message.getMediaItem().setAdditionalData(additionalData);

                // Sending message
                Kandy.getServices().getChatService().sendChat(message, new KandyResponseListener() {

                    @Override
                    public void onRequestFailed(int responseCode, String err) {
                        //Log.e(TAG, "Kandy.getChatService().sendMessage:onRequestFailed - Error: " + err + "\nResponse code: " + responseCode);
                        //UIUtils.handleResultOnUiThread(ChatServiceActivity.this, true, err);
                    }

                    @Override
                    public void onRequestSucceded() {
                        //Log.i(TAG, "Kandy.getChatService().sendMessage:onRequestSucceded - Message sent");
                        //UIUtils.handleResultOnUiThread(ChatServiceActivity.this, false, getString(R.string.activity_cahts_message_sent_label));
                        //addMessageOnUI(message);
                    }
                });


                //code for video call disconnect
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

    private void registerCallListener() {
        Log.d(TAG, "registerCallListener()");
        Kandy.getServices().getCallService().registerNotificationListener(this);
    }

    private void unregisterCallListener() {
        Log.d(TAG, "unregisterCallListener()");
        Kandy.getServices().getCallService().unregisterNotificationListener(this);
    }

    private void registerChatsNotifications() {
        Timer chatTimer = new Timer();
        chatTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Kandy.getServices().getChatService().pullEvents(new KandyResponseListener() {
                    @Override
                    public void onRequestSucceded() {
                        Log.i(getClass().getName(), "Pull success");
                    }

                    @Override
                    public void onRequestFailed(int i, String s) {
                        Log.i(getClass().getName(), "Code:" + i + "  msg:" + s);
                    }
                });
            }
        }, 0, 5000);

        Log.d(TAG, "registerNotifications");
        Kandy.getServices().getChatService().registerNotificationListener(new KandyChatServiceNotificationListener() {
            @Override
            public void onChatReceived(IKandyMessage iKandyMessage, KandyRecordType kandyRecordType) {
                Log.d("Test", "onChatReceived");
            }

            @Override
            public void onChatDelivered(KandyDeliveryAck kandyDeliveryAck) {
                Log.d("Test", "onChatDelivered");
            }

            @Override
            public void onChatMediaAutoDownloadProgress(IKandyMessage iKandyMessage, IKandyTransferProgress iKandyTransferProgress) {

            }

            @Override
            public void onChatMediaAutoDownloadFailed(IKandyMessage iKandyMessage, int i, String s) {

            }

            @Override
            public void onChatMediaAutoDownloadSucceded(IKandyMessage iKandyMessage, Uri uri) {

            }
        });
    }

    private void unRegisterChatsNotifications() {
        Log.d(TAG, "unRegisterNotifications");
//        Kandy.getServices().getChatService().unregisterNotificationListener(this);
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
