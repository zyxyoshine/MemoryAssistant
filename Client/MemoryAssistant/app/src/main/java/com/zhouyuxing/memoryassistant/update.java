package com.zhouyuxing.memoryassistant;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;


public class update{
    private Context context;
    private String url;
    private String notificationTitle;
    private String notificationDescription;
    private DownloadManager downLoadManager;

    public static final String DOWNLOAD_FOLDER_NAME = "app/apk/download";
    public static final String DOWNLOAD_FILE_NAME   = "newver.apk";


    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationDescription() {
        return notificationDescription;
    }

    public void setNotificationDescription(String notificationDescription) {
        this.notificationDescription = notificationDescription;
    }

    public update(Context context) {
        this.context = context;
        downLoadManager = (DownloadManager) this.context
                .getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public int getVersionName() throws Exception {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),
                0);
        return packInfo.versionCode;
    }

    public boolean canUpdate(int localVersion,int serverVersion){
        if(localVersion <=0 || serverVersion<=0)
            return false;
        if(localVersion>=serverVersion){
            return false;
        }
        return true;
    }

    public void downLoad(String url){
        Request request=new Request(Uri.parse(url));
        request.setNotificationVisibility(Request.VISIBILITY_HIDDEN);
        if(!TextUtils.isEmpty(getNotificationTitle())){
            request.setTitle(getNotificationTitle());
        }
        if(!TextUtils.isEmpty(getNotificationDescription())){
            request.setDescription(getNotificationDescription());
        }
        request.setAllowedNetworkTypes(Request.NETWORK_MOBILE  | Request.NETWORK_WIFI);
        request.setVisibleInDownloadsUi(false);
        File folder = Environment.getExternalStoragePublicDirectory(DOWNLOAD_FOLDER_NAME);
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdirs();
        }
        request.setDestinationInExternalPublicDir(DOWNLOAD_FOLDER_NAME, DOWNLOAD_FILE_NAME);
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url));
        request.setMimeType(mimeString);
        downLoadManager.enqueue(request);
    }

    public static boolean install(Context context, String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File file = new File(filePath);
        Log.v("fp",filePath);
        if (file != null && file.length() > 0 && file.exists() && file.isFile()) {
            intent.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        }
        return false;
    }
}
