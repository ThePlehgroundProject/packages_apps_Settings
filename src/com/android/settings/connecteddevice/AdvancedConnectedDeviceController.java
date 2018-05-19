/*
 * Copyright 2018 The Android Open Source Project
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
 * limitations under the License
 */
package com.android.settings.connecteddevice;

import android.content.Context;
import androidx.annotation.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.nfc.NfcPreferenceController;

/**
 * Controller that used to show which component is available
 */
public class AdvancedConnectedDeviceController extends BasePreferenceController {

    public AdvancedConnectedDeviceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

    @Override
    public CharSequence getSummary() {
        return mContext.getText(getConnectedDevicesSummaryResourceId(mContext));
    }

    /**
     * Get Connected Devices summary that depend on {@link NfcPreferenceController} or
     * diving mode are available
     */
    public static int getConnectedDevicesSummaryResourceId(Context context) {
        final NfcPreferenceController nfcPreferenceController =
                new NfcPreferenceController(context);
        final boolean isDrivingModeAvailable = false;

        return getConnectedDevicesSummaryResourceId(nfcPreferenceController,
                isDrivingModeAvailable);
    }

    @VisibleForTesting
    static int getConnectedDevicesSummaryResourceId(NfcPreferenceController
            nfcPreferenceController, boolean isDrivingModeAvailable) {
        final int resId;

        if (nfcPreferenceController.isAvailable()) {
            if (isDrivingModeAvailable) {
                // NFC available, driving mode available
                resId = R.string.connected_devices_dashboard_summary;
            } else {
                // NFC available, driving mode not available
                resId = R.string.connected_devices_dashboard_no_driving_mode_summary;
            }
        } else {
            if (isDrivingModeAvailable) {
                // NFC not available, driving mode available
                resId = R.string.connected_devices_dashboard_no_nfc_summary;
            } else {
                // NFC not available, driving mode not available
                resId = R.string.connected_devices_dashboard_no_driving_mode_no_nfc_summary;
            }
        }

        return resId;
    }
}