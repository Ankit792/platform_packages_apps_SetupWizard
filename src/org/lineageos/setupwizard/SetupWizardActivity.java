/*
 * Copyright (C) 2016 The CyanogenMod Project
 * Copyright (C) 2017,2019 The LineageOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.lineageos.setupwizard;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;

import static org.lineageos.setupwizard.SetupWizardApp.ACTION_LOAD;
import static org.lineageos.setupwizard.SetupWizardApp.EXTRA_SCRIPT_URI;
import static org.lineageos.setupwizard.SetupWizardApp.LOGV;

import android.annotation.Nullable;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.setupcompat.util.WizardManagerHelper;

import org.lineageos.setupwizard.util.SetupWizardUtils;
import org.lineageos.setupwizard.wizardmanager.WizardManager;

import android.provider.Settings;

public class SetupWizardActivity extends BaseSetupWizardActivity {
    private static final String TAG = SetupWizardActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Settings.Global.putInt(getContentResolver(), Settings.Global.DEVICE_PROVISIONED, 0);
        // Settings.Secure.putInt(getContentResolver(), Settings.Secure.USER_SETUP_COMPLETE, 0);
        
        Log.d(TAG, "Ankit SetupWizardActivity onCreate called.");

        if (LOGV) {
            Log.v(TAG, "onCreate savedInstanceState=" + savedInstanceState);
        }

         // Log whether the user setup is complete
        boolean isSetupComplete = WizardManagerHelper.isUserSetupComplete(this);
        Log.d(TAG, "Ankit Is user setup complete? " + isSetupComplete);

        if (WizardManagerHelper.isUserSetupComplete(this)) {
            Log.d(TAG, "Ankit User setup is complete. Finishing SetupWizardActivity.");
            SetupWizardUtils.finishSetupWizard(this);
            finish();
        } else {
            Log.d(TAG, "Ankit User setup is not complete. Starting setup process.");
            Settings.Global.putInt(getContentResolver(), Settings.Global.DEVICE_PROVISIONED, 0);
            Settings.Secure.putInt(getContentResolver(), Settings.Secure.USER_SETUP_COMPLETE, 0);
        
            onSetupStart();
            Log.d(TAG, "Ankit Enabling WizardManager.");
            SetupWizardUtils.enableComponent(this, WizardManager.class);
            Intent intent = new Intent(ACTION_LOAD);
            if (isPrimaryUser()) {
                intent.putExtra(EXTRA_SCRIPT_URI, getString(R.string.lineage_wizard_script_uri));
                Log.d(TAG, "Ankit Primary user detected. Using script URI: ");
            } else {
                intent.putExtra(EXTRA_SCRIPT_URI,
                        getString(R.string.lineage_wizard_script_user_uri));
                Log.d(TAG, "Ankit Non-primary user detected. Using script user URI: ");
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
            Log.d(TAG, "Ankit WizardManager started. Finishing SetupWizardActivity.");
            finish();
        }
        Log.d(TAG, "Ankit SetupWizardActivity onCreate finished.");
    }
}
