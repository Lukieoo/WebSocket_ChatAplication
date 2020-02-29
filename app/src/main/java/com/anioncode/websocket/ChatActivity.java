package com.anioncode.websocket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.anioncode.websocket.ViewModel.MessageAdapter;
import com.anioncode.websocket.ViewModel.MessagesViewModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class ChatActivity extends AppCompatActivity {
    private Button start;

    private TextView output;
    private OkHttpClient client;
    EchoWebSocketListener listener;

    private final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            webSocket.send("first:" + nameUser + " join");
            output("Admin : " + "Hello " + nameUser);
            //  webSocket.send(ByteString.decodeHex("deadbeef"));
            //   webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            output(text);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            // output("Receiving bytes : " + bytes.hex());
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            output("Closing : " + code + " / " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            output("Error : " + t.getMessage());
        }
    }

    String nameUser;
    TextView nameField;

    RecyclerView MessagesRecycler;
    MessageAdapter adapter;
    MessagesViewModel model;
    List<String> data;
    ImageButton send;
    WebSocket ws;
    EditText messageadd;
    int sizeData = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        nameField = findViewById(R.id.userName);

        start = (Button) findViewById(R.id.start);

        output = (TextView) findViewById(R.id.output);

        send = (ImageButton) findViewById(R.id.send);

        messageadd = (EditText) findViewById(R.id.messageadd);

        MessagesRecycler = findViewById(R.id.MessagesRecycler);
        MessagesRecycler.setHasFixedSize(true);
        MessagesRecycler.setLayoutManager(new LinearLayoutManager(this));
        model = new ViewModelProvider(this).get(MessagesViewModel.class);

        data = new ArrayList<>();


        nameUser = getIntent().getStringExtra("Name");
        nameField.setText("Your nick : " + nameUser);

        adapter = new MessageAdapter(ChatActivity.this, data, nameUser);

        model.getHeroes().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                adapter.setData(strings);
                adapter.notifyDataSetChanged();
                sizeData = adapter.getData().size();

            }
        });

        MessagesRecycler.setAdapter(adapter);
        client = new OkHttpClient();
        start();


//                start();


    }

    private void start() {

        Request request = new Request.Builder().url("wss://connect.websocket.in/v2/1998?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjUyMmY0NGI3MWQzZmYzOTk5NGZmZjg3NDhjN2U3Yjk0NGMxOWE1NTkzMWRmOGFhZDk0N2EyMjYzYTc4NGYzMzA2MGI1Mzc5YmE1ZDlhMWU4In0.eyJhdWQiOiI4IiwianRpIjoiNTIyZjQ0YjcxZDNmZjM5OTk0ZmZmODc0OGM3ZTdiOTQ0YzE5YTU1OTMxZGY4YWFkOTQ3YTIyNjNhNzg0ZjMzMDYwYjUzNzliYTVkOWExZTgiLCJpYXQiOjE1ODI5Njk1MjEsIm5iZiI6MTU4Mjk2OTUyMSwiZXhwIjoxNjE0NTkxOTIxLCJzdWIiOiI2MDMiLCJzY29wZXMiOltdfQ.hxNCiERXZrDgPbN4GtPqpUAOeEKqdwIDaCkznEUDuLD2lChmBsFpqOYwIXUKexp262MsflCeJvZcV7n6iccE93emV-6b6YvFmwCkMzEd0ekHO-XgP0xIA7CTqxMh38GH-OowKer9APoynkMco1iEVADhiy7O5D-qhnxcTMRrr5Ui3uy36SbBRfkDS09Cmuo-pk4eRfMQtQkCGV7IQluv4wbxKlwCjRd_Xhx2sQ2g6PIJvSttuany8ewdYb4HKLnrPX3KcthlP3gdkrYOLIoMoeTXqaN-VO6Gwperkkxrnk8wYNEAZS0T5FJcnrqsMcV0flSj_mdwkAvLZ-__XjezJVpj2orykqaZTlua1sUHMPSEtHZnVhiN9nJlKHJ66l5cyBOzSHPgup-pUVRQcH89DkLpRDsn2pg5bJIk0goEZdrQRKzuaDAXuSsdQrRk2MwYR-hQG51cOqAnUyDhJMMBoEu2fJI_-dIKrMAnzjkhzqaOkzHenbF-vwbkKsIrq5GOkswR7a_sAidjBECYC_te63BXboLXJ8YvICBStkFy1Zwx6YAId9NhUXa05EoiZswELYSxQK7JEUI30tUltS2D5845rXeKOyREexBcnonS2z-5FT_uyA7R7L0BVToflF0hKfeY5NJY_FpEVYbpHZOaeF5OWhR2wKSWycDCYmnUUw4").build();
        listener = new EchoWebSocketListener();
        ws = client.newWebSocket(request, listener);

        send.setOnClickListener(view -> {
            if (messageadd.getText().toString().trim().equals("")) {

            } else {
                ws.send(nameUser + ":" + messageadd.getText().toString());
                output(nameUser + ":" + messageadd.getText().toString());
                messageadd.setText("");
            }
        });

        client.dispatcher().executorService().shutdown();

    }

    private void output(final String txt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (sizeData != 0) {
                    MessagesRecycler.smoothScrollToPosition(sizeData);
                }

                model.addItem(txt);
                model.setItemList();
            }
        });
    }
}