package com.example.anirview;


import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class sesion {

    private static final String jsonFile = "session.json";
    private File sessionFile;
    private boolean open;
    private String user;

    public sesion(Context context) {
        sessionFile = new File(context.getFilesDir(), jsonFile);
        loadSesion();
    }

    private void loadSesion() {
        if (sessionFile.exists()) {
            try (FileReader reader = new FileReader(sessionFile)) {
                char[] buffer = new char[(int) sessionFile.length()];
                reader.read(buffer);
                String json = new String(buffer);
                JSONObject jsonObject = new JSONObject(json);
                this.open = jsonObject.getBoolean("open");
                this.user = jsonObject.getString("user");
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                resetSesion();
            }
        } else {
            resetSesion();
        }
    }

    private void saveSesion() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("open", open);
            jsonObject.put("user", user);
            try (FileWriter writer = new FileWriter(sessionFile)) {
                writer.write(jsonObject.toString());
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    public void resetSesion() {
        this.open = false;
        this.user = "";
        saveSesion();
    }


    public boolean getOpen(){
        return open;
    }

    public String getUser(){
        return user;
    }

    public void setUser(String user){
        this.user=user;
        saveSesion();
    }

    public void open(){
        open=true;
        saveSesion();
    }
    public void close(){
        open=false;
        saveSesion();
    }
}
