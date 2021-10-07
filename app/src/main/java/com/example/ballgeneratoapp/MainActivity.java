package com.example.ballgeneratoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private EditText editGroupName;
    private EditText editAmount;
    private EditText editX;
    private EditText editY;
    private Switch switchRed;
    private Switch switchGreen;
    private Switch switchBlue;
    private Button buttonCreate;
    private Button buttonDelete;

    private Socket socket;
    private BufferedWriter bw;
    private BufferedReader br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editGroupName = findViewById(R.id.editGroupName);
        editAmount = findViewById(R.id.editAmount);
        editX = findViewById(R.id.editX);
        editY = findViewById(R.id.editY);
        switchRed = findViewById(R.id.switchRed);
        switchGreen = findViewById(R.id.switchGreen);
        switchBlue = findViewById(R.id.switchBlue);
        switchRed = findViewById(R.id.switchRed);
        buttonCreate = findViewById(R.id.buttonCreate);
        buttonDelete = findViewById(R.id.buttonDelete);

        initClient();


        buttonCreate.setOnClickListener((v) -> {

            if (editGroupName.getText().toString().isEmpty() || editAmount.getText().toString().isEmpty() || editX.getText().toString().isEmpty() || editY.getText().toString().isEmpty()) {
                Toast.makeText(this, "Por favor no dejar campos vacios", Toast.LENGTH_SHORT).show();
            } else if (!switchBlue.isChecked() && !switchGreen.isChecked() && !switchRed.isChecked()) {
                Toast.makeText(this, "Por favor seleccione un tipo", Toast.LENGTH_SHORT).show();
            } else if (Integer.parseInt(editAmount.getText().toString()) <= 0) {
                Toast.makeText(this, "La cantidad debe ser mayor a 0", Toast.LENGTH_SHORT).show();
            } else if (Integer.parseInt(editX.getText().toString()) > 750 || Integer.parseInt(editX.getText().toString()) < 50 || Integer.parseInt(editY.getText().toString()) < 50 || Integer.parseInt(editY.getText().toString()) > 549) {
                Toast.makeText(this, "Las coordenadas estan fuera de sus rangos, para x (0-749), para y (50-549)", Toast.LENGTH_SHORT).show();
            } else {
                int tipo = 0;
                if (switchRed.isChecked()) {
                    tipo = 1;
                }
                if (switchGreen.isChecked()) {
                    tipo = 2;
                }
                if (switchBlue.isChecked()) {
                    tipo = 3;
                }

                for (int i = 0; i < Integer.parseInt(editAmount.getText().toString()); i++) {
                    String grupo = editGroupName.getText().toString();
                    int x = Integer.parseInt(editX.getText().toString());
                    int y = Integer.parseInt(editY.getText().toString());
                    int velx = (int) (-10 + Math.floor(Math.random() * 15));
                    int vely = (int) (-5 + Math.floor(Math.random() * 15));
                    sendMessage(grupo, x, y, tipo, velx, vely);
                }


            }

        });

        buttonDelete.setOnClickListener((v) -> {
            sendMessageDelete();
        });

        switchRed.setOnClickListener((v) -> {


            if (switchBlue.isChecked()) {
                switchBlue.setChecked(false);
            }
            if (switchGreen.isChecked()) {
                switchGreen.setChecked(false);
            }
        });

        switchGreen.setOnClickListener((v) -> {
            switchGreen.setEnabled(true);

            if (switchBlue.isChecked()) {
                switchBlue.setChecked(false);
            }
            if (switchRed.isChecked()) {
                switchRed.setChecked(false);
            }
        });

        switchBlue.setOnClickListener((v) -> {
            switchBlue.setEnabled(true);

            if (switchGreen.isChecked()) {
                switchGreen.setChecked(false);
            }
            if (switchRed.isChecked()) {
                switchRed.setChecked(false);
            }
        });


    }

    public void initClient() {
        new Thread(
                () -> {
                    try {
                        //10.0.2.2 Emulator
                        //IP 192.168.0.5
                        socket = new Socket("10.0.2.2", 5000);

                        InputStream is = socket.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);
                        br = new BufferedReader(isr);

                        OutputStream os = socket.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os);
                        bw = new BufferedWriter(osw);

                        while (true) {
                            System.out.println("Waiting");
                            String line = br.readLine();
                            System.out.println("Recieved");
                            System.out.println("Msg: " + line);
                        }


                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }).start();

    }


    //Gson
    public void sendMessage(String grupo, int x, int y, int tipo, int velx, int vely) {

        Gson gson = new Gson();
        Bolita b = new Bolita(grupo, x, y, tipo, velx, vely);

        //Serializacion

        String bolitaString = gson.toJson(b);
        new Thread(
                () -> {

                    try {

                        bw.write(bolitaString + "\n");
                        bw.flush();

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }).start();
    }

    public void sendMessageDelete() {

        new Thread(
                () -> {

                    try {

                        bw.write("delete" + "\n");
                        bw.flush();

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }).start();
    }

}