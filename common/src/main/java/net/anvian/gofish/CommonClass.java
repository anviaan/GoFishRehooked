package net.anvian.gofish;

import net.anvian.anvianslib.platform.Services;
import net.anvian.anvianslib.util.LibUtil;

public class CommonClass {
    public static void init() {
        Constants.LOG.info("Initializing {} v{} on {}", Constants.MOD_ID, Constants.MOD_VERSION, Services.PLATFORM.getPlatformName());
        LibUtil.setupTelemetry(Constants.MOD_ID, Constants.MOD_VERSION);
    }
}