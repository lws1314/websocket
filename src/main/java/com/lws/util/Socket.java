package com.lws.util;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.lws.controller.WebSocketController;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint("/websocket/{username}")
public class Socket {
    private Logger logger = LoggerFactory.getLogger(WebSocketController.class);
    private static int onlineCount = 0;
    private static Map<String, Socket> clients = new ConcurrentHashMap<String, Socket>();
    private Session session;
    private String username;

    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session) throws IOException {

        this.username = "lws"+session.getId();
        this.session = session;
        addOnlineCount();
        clients.put("lws"+session.getId(), this);
        logger.info("链接成功");
    }

    @OnClose
    public void onClose() throws IOException {
        clients.remove(username);
        subOnlineCount();
    }

    @OnMessage
    public void onMessage(String message) throws IOException {

        JSONObject jsonTo = JSONObject.fromObject(message);

        if (!jsonTo.get("To").equals("All")){
            sendMessageTo(jsonTo.get("message").toString(), jsonTo.get("To").toString());
        }else{
            sendMessageAll(jsonTo.get("message").toString());
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessageTo(String message, String To) throws IOException {
        // session.getBasicRemote().sendText(message);
        //session.getAsyncRemote().sendText(message);
        for (Socket item : clients.values()) {
            if (item.username.equals(To) )
                item.session.getAsyncRemote().sendText(message);
        }
    }

    public void sendMessageAll(String message) throws IOException {
        for (Socket item : clients.values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }



    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        Socket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        Socket.onlineCount--;
    }

    public static synchronized Map<String, Socket> getClients() {
        return clients;
    }
}
