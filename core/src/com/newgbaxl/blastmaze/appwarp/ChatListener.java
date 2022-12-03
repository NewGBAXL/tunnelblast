package com.newgbaxl.blastmaze.appwarp;

import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ChatEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ChatRequestListener;

import java.util.ArrayList;

public class ChatListener implements ChatRequestListener{

    WarpController callBack;

    public ChatListener(WarpController callBack) {
        this.callBack = callBack;
    }

    public void onSendChatDone(byte result) {
        if(result==WarpResponseResultCode.SUCCESS){
            callBack.onSendChatDone(true);
        }else{
            callBack.onSendChatDone(false);
        }
    }

    @Override
    public void onSendPrivateChatDone (byte arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onGetChatHistoryDone(byte b, ArrayList<ChatEvent> arrayList) {

    }

}