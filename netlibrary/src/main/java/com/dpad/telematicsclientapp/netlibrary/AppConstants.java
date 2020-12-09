package com.dpad.telematicsclientapp.netlibrary;


import com.dpad.telematicsclientapp.mvp.kit.Kits;
import com.dpad.telematicsclientapp.netlibrary.base.basecopy.AppManager;
import com.dpad.telematicsclientapp.util.PreferenceUtils;
import com.dpad.telematicsclientapp.util.utils.Constant;
import com.dpad.telematicsclientapp.util.utils.SystemUtil;
import com.socks.library.KLog;

import java.util.UUID;



/**
 * Created by vigss on 2018/3/13.
 */

public class AppConstants {
    public static final String BRANDCODE = "DPAD";
    public static String APPID = "0";
    public static final String APPVERSIONNO = Kits.Package.getVersionCode(MainApplicaton.getContext()) + "";
    public static final String DEVICENO = "869718026776358";
    public static final String DEVICETYPE = "00";


    public static String getMyUUID() {
        String uniqueId;
        uniqueId = SystemUtil.getIMEI(AppManager.getInstance().getCurrentActivity());
        if (Kits.Empty.check(uniqueId)) {
            if (Kits.Empty.check(PreferenceUtils.getInstance(MainApplicaton.getContext()).getString(Constant.DEVICEID_KEY))) {
                UUID uuid = UUID.randomUUID();
                uniqueId = uuid.toString();
                PreferenceUtils.getInstance(MainApplicaton.getContext()).setString(Constant.DEVICEID_KEY, uniqueId);
            } else {
                uniqueId = PreferenceUtils.getInstance(MainApplicaton.getContext()).getString(Constant.DEVICEID_KEY);
            }
        }else {
            PreferenceUtils.getInstance(MainApplicaton.getContext()).setString(Constant.DEVICEID_KEY, uniqueId);
        }
        KLog.e(uniqueId + "UUID_________________");
        return uniqueId;
    }
}
