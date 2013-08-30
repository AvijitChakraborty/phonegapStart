/*
 * PhoneGap is available under *either* the terms of the modified BSD license *or* the
 * MIT License (2008). See http://opensource.org/licenses/alphabetical for full text.
 *
 * Copyright (c) 2005-2010, Nitobi Software Inc.
 * Copyright (c) 2011, IBM Corporation
 */

package com.phonegap.plugins.fileOpener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaPlugin;
import org.apache.cordova.api.PluginResult;

public class FileOpener extends CordovaPlugin {
	  private static final String YOU_TUBE = "youtube.com";
	    private static final String ASSETS = "file:///android_asset/";
	    
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
        PluginResult.Status status = PluginResult.Status.OK;
        String result = "";

        try {
            if (action.equals("openFile")) {
                openFile(args.getString(0));
            }
            else {
                status = PluginResult.Status.INVALID_ACTION;
            }
//            callbackContext.sendPluginResult(new PluginResult(status, result));
        } catch (JSONException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return true;
    }

    private void openFile(String url) throws IOException {
    	
    	if (url.contains("bit.ly/") || url.contains("goo.gl/") || url.contains("tinyurl.com/") || url.contains("youtu.be/") || url.contains("http://")) {
			//support for google / bitly / tinyurl / youtube shortens
			URLConnection con = new URL(url).openConnection();
			con.connect();
			InputStream is = con.getInputStream();
			//new redirected url
	        url = con.getURL().toString();
			is.close();
		}
        // Create URI
        Uri uri = Uri.parse(url);

        Intent intent = null;
        // Check what kind of file you are trying to open, by comparing the url with extensions.
        // When the if condition is matched, plugin sets the correct intent (mime) type, 
        // so Android knew what application to use to open the file
        if (url.contains(YOU_TUBE)) {
            // If we don't do it this way you don't have the option for youtube
            uri = Uri.parse("vnd.youtube:" + uri.getQueryParameter("v"));
            if (isYouTubeInstalled()) {
                intent = new Intent(Intent.ACTION_VIEW, uri);
            } else {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.google.android.youtube"));
            }
        } else if(url.contains(ASSETS)) {
        	 // get file path in assets folder
            String filepath = url.replace(ASSETS, "");
            // get actual filename from path as command to write to internal storage doesn't like folders
            String filename = filepath.substring(filepath.lastIndexOf("/")+1, filepath.length());

            // Don't copy the file if it already exists
            File fp = new File(this.cordova.getActivity().getFilesDir() + "/" + filename);
            if (!fp.exists()) {
                this.copy(filepath, filename);
            }
            // change uri to be to the new file in internal storage
            uri = Uri.parse("file://" + this.cordova.getActivity().getFilesDir() + "/" + filename);

            if (url.contains(".doc") || url.contains(".docx")) {
                // Word document
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "application/msword");
            } else if(url.contains(".pdf")) {
                // PDF file
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "application/pdf");
            } else if(url.contains(".ppt") || url.contains(".pptx")) {
                // Powerpoint file
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if(url.contains(".xls") || url.contains(".xlsx")) {
                // Excel file
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if(url.contains(".rtf")) {
                // RTF file
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "application/rtf");
            } else if(url.contains(".wav")) {
                // WAV audio file
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "audio/x-wav");
            } else if(url.contains(".gif")) {
                // GIF file
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "image/gif");
            } else if(url.contains(".jpg") || url.contains(".jpeg")) {
                // JPG file
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "image/jpeg");
            } else if(url.contains(".txt")) {
                // Text file
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "text/plain");
            } else if(url.contains(".mpg") || url.contains(".mpeg") || url.contains(".mpe") || url.contains(".mp4") || url.contains(".avi")|| url.contains(".mp3")) {
                // Video files
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "video/*");
            }         
                    
            //if you want you can also define the intent type for any other file
            
            //additionally use else clause below, to manage other unknown extensions
            //in this case, Android will show all applications installed on the device
            //so you can choose which application to use
            
            else {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "*/*");
            }
        	
        }else {           
        if (url.contains(".doc") || url.contains(".docx")) {
            // Word document
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/msword");
        } else if(url.contains(".pdf")) {
            // PDF file
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
        } else if(url.contains(".ppt") || url.contains(".pptx")) {
            // Powerpoint file
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } else if(url.contains(".xls") || url.contains(".xlsx")) {
            // Excel file
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        } else if(url.contains(".rtf")) {
            // RTF file
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/rtf");
        } else if(url.contains(".wav")) {
            // WAV audio file
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "audio/x-wav");
        } else if(url.contains(".gif")) {
            // GIF file
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "image/gif");
        } else if(url.contains(".jpg") || url.contains(".jpeg")) {
            // JPG file
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "image/jpeg");
        } else if(url.contains(".txt")) {
            // Text file
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "text/plain");
        } else if(url.contains(".mpg") || url.contains(".mpeg") || url.contains(".mpe") || url.contains(".mp4") || url.contains(".avi")|| url.contains(".mp3")) {
            // Video files
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "video/*");
        }         
                
        //if you want you can also define the intent type for any other file
        
        //additionally use else clause below, to manage other unknown extensions
        //in this case, Android will show all applications installed on the device
        //so you can choose which application to use
        
        else {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "*/*");
        }
        }

        this.cordova.getActivity().startActivity(intent);
    }

    
    private void copy(String fileFrom, String fileTo) throws IOException {
        // get file to be copied from assets
        InputStream in = this.cordova.getActivity().getAssets().open(fileFrom);
        // get file where copied too, in internal storage.
        // must be MODE_WORLD_READABLE or Android can't play it
        FileOutputStream out = this.cordova.getActivity().openFileOutput(fileTo, Context.MODE_WORLD_READABLE);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0)
            out.write(buf, 0, len);
        in.close();
        out.close();
    }
    
    private boolean isYouTubeInstalled() {
        PackageManager pm = this.cordova.getActivity().getPackageManager();
        try {
            pm.getPackageInfo("com.google.android.youtube", PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
