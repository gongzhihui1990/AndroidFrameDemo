package com.starkrak.framedemo.logboy;

import android.provider.Settings;

import com.starkrak.framedemo.App;

import net.gtr.framework.util.Loger;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class LogBoy {
    private static LogBoy instance;
    private OkHttpClient client = new OkHttpClient();
    private EchoWebSocketListener listener = new EchoWebSocketListener();
    private WebSocket ws;

    public static LogBoy getInstance() {
        if (instance == null) {
            instance = new LogBoy();
        }
        return instance;
    }

    public WebSocket getWs() {
        return ws;
    }

    public boolean initialized() {
        return ws != null;
    }

    public void init(Request request) {
        ws = client.newWebSocket(request, listener);
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
        Loger.setNetProxy(new Loger.Proxy() {
            private void send(String string) {
                LogBoy.getInstance().getWs().send(format.format(new Date()) + "-" + string);
            }

            @Override
            public void v(String tag, String msg) { send("V " + tag + "-" + msg); }

            @Override
            public void i(String tag, String msg) {
                send("I " + tag + "-" + msg);
            }

            @Override
            public void d(String tag, String msg) {
                send("D " + tag + "-" + msg);
            }

            @Override
            public void w(String tag, String msg) {
                send("W " + tag + "-" + msg);
            }

            @Override
            public void e(String tag, String msg) {
                send("E " + tag + "-" + msg);
            }
        });
    }

    public void fini() {
        ws.close(1, "手动关闭");
        ws = null;
        Loger.setNetProxy(null);
    }

    public void attach(Outer outer) {
        listener.setOuter(outer);
    }

    public void detach() {
        listener.setOuter(null);
    }

    interface Outer {
        void outPut(String out);
    }

    private static final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;
        private Outer outer;

        public void setOuter(Outer outer) {
            this.outer = outer;
        }

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            webSocket.send("android device" + Settings.Secure.getString(App.getContext().getContentResolver(), Settings.Secure.ANDROID_ID) + " connected");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            if (outer == null) {
                return;
            }
            outer.outPut("Receiving : " + text);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            if (outer == null) {
                return;
            }
            outer.outPut("Receiving bytes : " + bytes.hex());
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            if (outer == null) {
                return;
            }
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            outer.outPut("Closing : " + code + " / " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            if (outer == null) {
                return;
            }
            outer.outPut("Error : " + t.getMessage());
        }
    }
}
