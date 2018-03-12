package com.shieh.rain.ftpserver;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.blankj.utilcode.util.NetworkUtils;

import org.apache.ftpserver.ftplet.FtpException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String ip = NetworkUtils.getIPAddress(true);
        ((TextView)findViewById(R.id.textView)).setText("ip:"+ip);
        if (Build.VERSION.SDK_INT >= 23) {
            if (PackageManager.PERMISSION_GRANTED != this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1001);
            }
        }else{
            onPermissionGranted();
        }

    }
    private void onPermissionGranted(){
        try {
            FtpServerlet.getInstance().start();
        } catch (FtpException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1001){
            if (permissions.length > 0 && grantResults.length > 0) {
                if(PackageManager.PERMISSION_GRANTED == grantResults[0] && TextUtils.equals(permissions[0], Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    onPermissionGranted();
                }
            }
        }

    }
}
