package com.anysoftkeyboard;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transfermanager.*;
import com.amazonaws.regions.Regions;

/**
 * Created by Hooligan on 7/15/2015.
 */
public class DataToFileWriter {

    private File mFile;
    private FileWriter mFileWriter;
    private static final String mLogTag = "DataToFileWriter";

    /*
    private static CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
            myActivity.getContext(), // Context
            "us-east-1:10cf1912-ef4c-4fef-b054-0398cd94814c", // Identity Pool ID
            Regions.US_EAST_1 // Region
    );
    */

    public DataToFileWriter(String fileName) {
        try {

            if (isExternalStorageWritable()) {
                File sdCard = android.os.Environment.getExternalStorageDirectory();
                File dir = new File(sdCard, "ActiveAuthKeyboard");
                dir.mkdir();
                mFile = new File(dir, fileName);
                mFile.createNewFile();

                if (!mFile.canWrite()) {
                    mFile.setWritable(true);
                }
                mFileWriter = new FileWriter(mFile, true);
                Log.i(mLogTag, mFile.getPath());
            } else {
                throw new IOException("External not writable");
            }

        } catch (IOException | NullPointerException | SecurityException e) {
            e.printStackTrace();
        }
    }

    public boolean writeToFile(String toWrite, Boolean timestamp) {
        try {
            Date date = new Date();
            if (mFileWriter == null) {
                mFileWriter = new FileWriter(mFile, true);
            }
            if (timestamp) {
                mFileWriter.append(new Timestamp(date.getTime()).getTime() + ", " + toWrite + "\r\n");
            } else {
                mFileWriter.append(toWrite + "\r\n");
            }
            mFileWriter.flush();
        } catch (IOException | NullPointerException e) {
            Log.i(mLogTag, "Error writing to file");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean writeToFile(String toWrite) {
        try {
            Date date = new Date();
            if (mFileWriter == null) {
                mFileWriter = new FileWriter(mFile, true);
            }
            mFileWriter.append(new Timestamp(date.getTime()).getTime() + ", " + toWrite + "\r\n");
            mFileWriter.flush();
        } catch (IOException | NullPointerException e) {
            Log.i(mLogTag, "Error writing to file");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean closeFile() {
        try {
            mFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public void uploadToAws() {

    }

    public boolean deleteFile() {
        return mFile.delete();
    }

}
