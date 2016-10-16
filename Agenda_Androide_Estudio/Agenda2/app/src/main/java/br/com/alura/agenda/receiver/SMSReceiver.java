package br.com.alura.agenda.receiver;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsMessage;
import android.widget.MediaController;
import android.widget.Toast;

import br.com.alura.agenda.R;
import br.com.alura.agenda.dao.AlunoDAO;

/**
 * Created by Furmiga on 24/05/2016.
 */
public class SMSReceiver extends BroadcastReceiver {

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {

        Object[]pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];

        String formato = (String) intent.getSerializableExtra("format");

        SmsMessage sms = SmsMessage.createFromPdu(pdu, formato);

        String telefone = sms.getDisplayOriginatingAddress();
        AlunoDAO dao = new AlunoDAO(context);
        if(dao.ehAluno(telefone)) {
            Toast.makeText(context, "Chegou um SMS!", Toast.LENGTH_SHORT).show();
            MediaPlayer m = MediaPlayer.create(context , R.raw.msg);
            m.start();
        }
    }

}
