package com.newgbaxl.blastmaze;
/*import com.shephertz.app42.paas.sdk.java.App42API;
import com.shephertz.app42.paas.sdk.java.App42Response;
import com.shephertz.app42.paas.sdk.java.App42Exception;
import com.shephertz.app42.paas.sdk.java.App42BadParameterException;
import com.shephertz.app42.paas.sdk.java.App42NotFoundException;
import com.shephertz.app42.paas.sdk.java.user.User;
import com.shephertz.app42.paas.sdk.java.user.User.Profile;
import com.shephertz.app42.paas.sdk.java.user.User.UserGender;
import com.shephertz.app42.paas.sdk.java.user.UserService;*/

public class OnlineAdapter {

    //experimental
    public static String AppKey = "7c38bb21176fa67fc33c4771d99d8552d2b4c69c4d4481659ad444ec99afd2ee";
    public static String SecretKey = "5176e35f4ffbcc7eca80fb1b9ddf462c66a60a144e3acd1849443e385757c4e9";

    /*public OnlineAdapter {
        initAppwarp();
        warpClient.addConnectionRequestListener(new ConnectionListener(this));
        warpClient.addChatRequestListener(new ChatListener(this));
        warpClient.addZoneRequestListener(new ZoneListener(this));
        warpClient.addRoomRequestListener(new RoomListener(this));
        warpClient.addNotificationListener(new NotificationListener(this));
    }
    //start the game: WarpClient.initialize(AppKey, SecretKey);

    private void initAppwarp() {
        try {
            WarpClient.initialize(AppKey, SecretKey);
            warpClient = WarpClient.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onConnectDone(ConnectEvent e) {
        if(e.getResult()==WarpResponseResultCode.SUCCESS){
            callBack.onConnectDone(true);
        }else{
            callBack.onConnectDone(false);
        }
    }

    public void onConnectDone(boolean status){
        if(status){
            warpClient.initUDP();
            warpClient.joinRoomInRange(1, 1, false);
        }else{
            isConnected = false;
            handleError();
        }
    }

    public void onJoinRoomDone(RoomEvent event){
        if(event.getResult()==WarpResponseResultCode.SUCCESS){// success case
            this.roomId = event.getData().getId();
            warpClient.subscribeRoom(roomId);
        }else if(event.getResult()==WarpResponseResultCode.RESOURCE_NOT_FOUND){// no such room found
            HashMap<string, object=""> data = new HashMap<string, object="">();
            data.put("result", "");
            warpClient.createRoom("superjumper", "shephertz", 2, data);
        }else{
            warpClient.disconnect();
            handleError();
        }
    }

    public void onGetLiveRoomInfo(String[] liveUsers){
        if(liveUsers!=null){
            if(liveUsers.length==2){
                startGame();
            }else{
                waitForOtherUser();
            }
        }else{
            warpClient.disconnect();
            handleError();
        }
    }

    public void onUpdatePeersReceived(UpdateEvent event) {
        callBack.onGameUpdateReceived(new String(event.getUpdate()));
    }

    private void renderBob () {
        if (side < 0){
            batch.draw(keyFrame, world.local_bob.position.x + 0.5f, world.local_bob.position.y - 0.5f, side * 1, 1);
            sendLocation(world.local_bob.position.x + 0.5f, world.local_bob.position.y - 0.5f, side * 1, 1);
        }else{
            batch.draw(keyFrame, world.local_bob.position.x - 0.5f, world.local_bob.position.y - 0.5f, side * 1, 1);
            sendLocation(world.local_bob.position.x - 0.5f, world.local_bob.position.y - 0.5f, side * 1, 1);
        }
    }

    private void sendLocation(float x, float y, float width, float height){
        try {
            JSONObject data = new JSONObject();
            data.put("x", x);
            data.put("y", y);
            data.put("width", width);
            data.put("height", height);
            WarpController.getInstance().sendGameUpdate(data.toString());
        } catch (Exception e) {
            // exception in sendLocation
        }
    }

    public void updateResult(int code, String msg) {
        if (isConnected) {
            STATE = COMPLETED;
            HashMap<string, object="" > properties = new HashMap<string, object = "" > ();
            properties.put("result", code);
            warpClient.lockProperties(properties);
        }
    }

    public void onGameFinished (int code) {
        if(code==WarpController.GAME_WIN){
            this.msg = game_loose;
        }else if(code==WarpController.GAME_LOOSE){
            this.msg = game_win;
        }else if(code==WarpController.ENEMY_LEFT){
            this.msg = enemy_left;
        }
        update();
        game.setScreen(this);
    }

    public void handleLeave(){
        if(isConnected){
            warpClient.unsubscribeRoom(roomId);
            warpClient.leaveRoom(roomId);
            if(STATE!=STARTED){
                warpClient.deleteRoom(roomId);
            }
            warpClient.disconnect();
        }
    }

    private void disconnect(){
        warpClient.removeConnectionRequestListener(new ConnectionListener(this));
        warpClient.removeChatRequestListener(new ChatListener(this));
        warpClient.removeZoneRequestListener(new ZoneListener(this));
        warpClient.removeRoomRequestListener(new RoomListener(this));
        warpClient.removeNotificationListener(new NotificationListener(this));
        warpClient.disconnect();
    }*/

}