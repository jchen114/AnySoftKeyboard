/*
 * Copyright (c) 2013 Menny Even-Danan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.menny.android.anysoftkeyboard;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;

import com.anysoftkeyboard.AskPrefs;
import com.anysoftkeyboard.AskPrefsImpl;
import com.anysoftkeyboard.backup.CloudBackupRequester;
import com.anysoftkeyboard.backup.CloudBackupRequesterDiagram;
import com.anysoftkeyboard.devicespecific.DeviceSpecific;
import com.anysoftkeyboard.devicespecific.StrictModeAble;
import com.anysoftkeyboard.ui.tutorials.TutorialsProvider;
import com.anysoftkeyboard.utils.Log;

import net.evendanan.frankenrobot.FrankenRobot;
import net.evendanan.frankenrobot.Lab;

import java.util.LinkedList;
import java.util.List;


public class AnyApplication extends Application implements OnSharedPreferenceChangeListener {

    private static final String TAG = "ASK_APP";
    private static AskPrefs msConfig;
    private static FrankenRobot msFrank;
    private static DeviceSpecific msDeviceSpecific;
    private static CloudBackupRequester msCloudBackupRequester;
    public static AnyApplication sharedApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedApplication = this;
        Thread.setDefaultUncaughtExceptionHandler(new ChewbaccaUncaughtExceptionHandler(getBaseContext(), null));
        Log.d(TAG, "** Starting application in DEBUG mode.");
        msFrank = Lab.build(getApplicationContext(), R.array.frankenrobot_interfaces_mapping);
        if (BuildConfig.DEBUG) {
            StrictModeAble strictMode = msFrank.embody(StrictModeAble.class);
            if (strictMode != null)//it should be created only in the API18.
                strictMode.setupStrictMode();
        }

        msConfig = new AskPrefsImpl(this);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sp.registerOnSharedPreferenceChangeListener(this);


        msDeviceSpecific = msFrank.embody(DeviceSpecific.class);
        Log.i(TAG, "Loaded DeviceSpecific " + msDeviceSpecific.getApiLevel() + " concrete class " + msDeviceSpecific.getClass().getName());

        msCloudBackupRequester = msFrank.embody(new CloudBackupRequesterDiagram(getApplicationContext()));

        TutorialsProvider.showDragonsIfNeeded(getApplicationContext());
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        ((AskPrefsImpl) msConfig).onSharedPreferenceChanged(sharedPreferences, key);
        //should we disable the Settings App? com.menny.android.anysoftkeyboard.LauncherSettingsActivity
        if (key.equals(getString(R.string.settings_key_show_settings_app))) {
            PackageManager pm = getPackageManager();
            boolean showApp = sharedPreferences.getBoolean(key, getResources().getBoolean(R.bool.settings_default_show_settings_app));
            pm.setComponentEnabledSetting(new ComponentName(getApplicationContext(), com.menny.android.anysoftkeyboard.LauncherSettingsActivity.class),
                    showApp ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
        }
    }


    public static AskPrefs getConfig() {
        return msConfig;
    }

    public static DeviceSpecific getDeviceSpecific() {
        return msDeviceSpecific;
    }

    public static void requestBackupToCloud() {
        if (msCloudBackupRequester != null)
            msCloudBackupRequester.notifyBackupManager();
    }

    public static FrankenRobot getFrankenRobot() {
        return msFrank;
    }

    public String getUsername() {
        AccountManager manager = AccountManager.get(this);
        Account[] accounts = manager.getAccountsByType("com.google");
        List<String> possibleEmails = new LinkedList<String>();

        for (Account account : accounts) {
            // TODO: Check possibleEmail against an email regex or treat
            // account.name as an email address only for certain account.type values.
            possibleEmails.add(account.name);
        }

        if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
            String email = possibleEmails.get(0);
            String[] parts = email.split("@");

            if (parts.length > 1)
                return parts[0];
        }
        return null;
    }

}
