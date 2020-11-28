package com.example.myapp;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class MailReceiver extends BroadcastReceiver {
    String email,subject,message;
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        email = bundle.getString("Usermail");
        subject = "Reminder";
        message = bundle.getString("Tiv")+" starts in an hour\n" + "Details of the event :" + bundle.getString("Cmntv") + "\n" + "Date: " + bundle.getString("datev") + "\nTime: " + bundle.getString("timev");
        new MyAsyncClass().execute();

    }
    GMailSender sender = new GMailSender("version20201@gmail.com", "Sricharan17#");
    class MyAsyncClass extends AsyncTask<Void, Void, Void> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
//        pDialog = new ProgressDialog();
//        pDialog.setMessage("Please wait...");
            // pDialog.show();

        }

        @Override

        protected Void doInBackground(Void... mApi) {
            try {
                sender.sendMail(subject, message, "version20201@gmail.com", email);

            }
            catch (Exception ex) {

            }
            return null;
        }

        @Override

        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            //pDialog.cancel();
            //  Toast.makeText(MainActivity.this, "mail send", Toast.LENGTH_SHORT).show();

        }
    }
}


