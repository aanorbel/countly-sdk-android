package ly.count.android.sdk;

import android.app.Activity;
import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static androidx.test.InstrumentationRegistry.getContext;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class CountlyConfigTests {

    @Before
    public void setUp(){
        Countly.sharedInstance().setLoggingEnabled(true);
    }

    @Test
    public void constructor(){
        CountlyConfig config = new CountlyConfig(getContext(), "Som345345", "fsdf7349374");

        assertDefaultValues(config, false);
    }

    @Test
    public void settingAllValues() {
        String[] s = new String[]{"4234234234ff", "sssa2323", "sds", "sdfsdf232", "aa22", "xvcx", "hghn", "0gifg", "kfkfdd"};
        Context c = getContext();
        CountlyConfig config = new CountlyConfig();
        CountlyStore cs = new CountlyStore(c);

        StarRatingCallback rc = new StarRatingCallback() {
            @Override
            public void onRate(int rating) {

            }

            @Override
            public void onDismiss() {

            }
        };

        RemoteConfig.RemoteConfigCallback rcc = new RemoteConfig.RemoteConfigCallback() {
            @Override
            public void callback(String error) {

            }
        };

        Map<String, String> hv = new HashMap<>();
        hv.put("11", "22");
        hv.put("1331", "2332");

        String[] fn = new String[]{"ds dsd", "434f", "ngfhg"};

        CrashFilterCallback callback = new CrashFilterCallback() {
            @Override
            public boolean filterCrash(String crash) {
                return false;
            }
        };

        Map<String, Object> vs = new HashMap<>();
        vs.put("ss", "fdf");
        vs.put("s22s", 2323);
        vs.put("s44s", 33434.33d);
        vs.put("dds44s", true);

        Class[] act = new Class[]{Activity.class};

        String[] appCrawlerNames = new String[] {"Some", "Crazy", "name"};

        String[] publicKeyCerts = new String[] { "ddd", "111", "ffd" };
        String[] certificateCerts = new String[] { "ddsd", "vvcv", "mbnb" };

        Map<String, Object> crashSegments = new HashMap<>();
        crashSegments.put("s2s", "fdf");
        crashSegments.put("s224s", 2323);
        crashSegments.put("s434s", 33434.33d);
        crashSegments.put("ddsa44s", true);


        assertDefaultValues(config, true);


        config.setServerURL(s[0]);
        config.setContext(c);
        config.setAppKey(s[1]);
        config.setCountlyStore(cs);
        config.checkForNativeCrashDumps(false);
        config.setDeviceId(s[2]);
        config.setIdMode(DeviceId.Type.ADVERTISING_ID);
        config.setStarRatingSessionLimit(1335);
        config.setStarRatingCallback(rc);
        config.setStarRatingTextDismiss(s[3]);
        config.setStarRatingTextMessage(s[4]);
        config.setStarRatingTextTitle(s[5]);
        config.setLoggingEnabled(true);
        config.enableCrashReporting();
        config.setViewTracking(true);
        config.setAutoTrackingUseShortName(true);
        config.addCustomNetworkRequestHeaders(hv);
        config.setPushIntentAddMetadata(true);
        config.setRemoteConfigAutomaticDownload(true, rcc);
        config.setRequiresConsent(true);
        config.setConsentEnabled(fn);
        config.setHttpPostForced(true);
        config.enableTemporaryDeviceIdMode();
        config.setCrashFilterCallback(callback);
        config.setParameterTamperingProtectionSalt(s[6]);
        config.setAutomaticViewSegmentation(vs);
        config.setAutoTrackingExceptions(act);
        config.setTrackOrientationChanges(true);
        config.setEventQueueSizeToSend(1337);
        config.enableManualSessionControl();
        config.setRecordAllThreadsWithCrash();
        config.setDisableUpdateSessionRequests(true);
        config.setShouldIgnoreAppCrawlers(true);
        config.setAppCrawlerNames(appCrawlerNames);
        config.enableCertificatePinning(certificateCerts);
        config.enablePublicKeyPinning(publicKeyCerts);
        config.setEnableAttribution(true);
        config.setCustomCrashSegment(crashSegments);
        config.setUpdateSessionTimerDelay(137);
        config.setIfStarRatingDialogIsCancellable(true);
        config.setIfStarRatingShownAutomatically(true);
        config.setStarRatingDisableAskingForEachAppVersion(true);



        Assert.assertEquals(s[0], config.serverURL);
        Assert.assertEquals(c, config.context);
        Assert.assertEquals(s[1], config.appKey);
        Assert.assertEquals(cs, config.countlyStore);
        Assert.assertFalse(config.checkForNativeCrashDumps);
        Assert.assertEquals(s[2], config.deviceID);
        Assert.assertEquals(DeviceId.Type.ADVERTISING_ID, config.idMode);
        Assert.assertEquals(1335, config.starRatingSessionLimit);
        Assert.assertEquals(rc, config.starRatingCallback);
        Assert.assertEquals(s[3], config.starRatingTextDismiss);
        Assert.assertEquals(s[4], config.starRatingTextMessage);
        Assert.assertEquals(s[5], config.starRatingTextTitle);
        Assert.assertTrue(config.loggingEnabled);
        Assert.assertTrue(config.enableUnhandledCrashReporting);
        Assert.assertTrue(config.enableViewTracking);
        Assert.assertTrue(config.autoTrackingUseShortName);
        Assert.assertEquals(hv, config.customNetworkRequestHeaders);
        Assert.assertTrue(config.pushIntentAddMetadata);
        Assert.assertTrue(config.enableRemoteConfigAutomaticDownload);
        Assert.assertEquals(rcc, config.remoteConfigCallback);
        Assert.assertTrue(config.shouldRequireConsent);
        Assert.assertArrayEquals(fn, config.enabledFeatureNames);
        Assert.assertTrue(config.httpPostForced);
        Assert.assertTrue(config.temporaryDeviceIdEnabled);
        Assert.assertEquals(callback, config.crashFilterCallback);
        Assert.assertEquals(s[6], config.tamperingProtectionSalt);
        Assert.assertEquals(vs, config.automaticViewSegmentation);
        Assert.assertArrayEquals(act, config.autoTrackingExceptions);
        Assert.assertTrue(config.trackOrientationChange);
        Assert.assertEquals(1337, config.eventQueueSizeThreshold.intValue());
        Assert.assertTrue(config.manualSessionControlEnabled);
        Assert.assertTrue(config.recordAllThreadsWithCrash);
        Assert.assertTrue(config.disableUpdateSessionRequests);
        Assert.assertTrue(config.shouldIgnoreAppCrawlers);
        Assert.assertArrayEquals(appCrawlerNames, config.appCrawlerNames);
        Assert.assertArrayEquals(certificateCerts, config.certificatePinningCertificates);
        Assert.assertArrayEquals(publicKeyCerts, config.publicKeyPinningCertificates);
        Assert.assertTrue(config.enableAttribution);
        Assert.assertEquals(crashSegments, config.customCrashSegment);
        Assert.assertEquals(137, config.sessionUpdateTimerDelay.intValue());
        Assert.assertTrue(config.starRatingDialogIsCancellable);
        Assert.assertTrue(config.starRatingShownAutomatically);
        Assert.assertTrue(config.starRatingDisableAskingForEachAppVersion);

    }

    @Test
    public void defaultValues(){
        CountlyConfig config = new CountlyConfig();

        assertDefaultValues(config, true);
    }

    @Test (expected = IllegalArgumentException.class)
    public void autoTrackingExceptionNull() {
        CountlyConfig config = new CountlyConfig();
        config.setAutoTrackingExceptions(new Class[]{null});
    }

    void assertDefaultValues(CountlyConfig config, boolean includeConstructorValues){
        if(includeConstructorValues){
            Assert.assertNull(config.context);
            Assert.assertNull(config.serverURL);
            Assert.assertNull(config.appKey);
        }

        Assert.assertNull(config.countlyStore);
        Assert.assertTrue(config.checkForNativeCrashDumps);
        Assert.assertNull(config.deviceID);
        Assert.assertNull(config.idMode);
        Assert.assertEquals(5, config.starRatingSessionLimit);
        Assert.assertNull(config.starRatingCallback);
        Assert.assertNull(config.starRatingTextDismiss);
        Assert.assertNull(config.starRatingTextMessage);
        Assert.assertNull(config.starRatingTextTitle);
        Assert.assertFalse(config.loggingEnabled);
        Assert.assertFalse(config.enableUnhandledCrashReporting);
        Assert.assertFalse(config.enableViewTracking);
        Assert.assertFalse(config.autoTrackingUseShortName);
        Assert.assertNull(config.customNetworkRequestHeaders);
        Assert.assertFalse(config.pushIntentAddMetadata);
        Assert.assertFalse(config.enableRemoteConfigAutomaticDownload);
        Assert.assertNull(config.remoteConfigCallback);
        Assert.assertFalse(config.shouldRequireConsent);
        Assert.assertNull(config.enabledFeatureNames);
        Assert.assertFalse(config.httpPostForced);
        Assert.assertFalse(config.temporaryDeviceIdEnabled);
        Assert.assertNull(config.crashFilterCallback);
        Assert.assertNull(config.tamperingProtectionSalt);
        Assert.assertNull(config.automaticViewSegmentation);
        Assert.assertNull(config.eventQueueSizeThreshold);
        Assert.assertFalse(config.trackOrientationChange);
        Assert.assertFalse(config.manualSessionControlEnabled);
        Assert.assertFalse(config.recordAllThreadsWithCrash);
        Assert.assertFalse(config.disableUpdateSessionRequests);
        Assert.assertFalse(config.shouldIgnoreAppCrawlers);
        Assert.assertNull(config.appCrawlerNames);
        Assert.assertNull(config.publicKeyPinningCertificates);
        Assert.assertNull(config.certificatePinningCertificates);
        Assert.assertNull(config.enableAttribution);
        Assert.assertNull(config.customCrashSegment);
        Assert.assertNull(config.sessionUpdateTimerDelay);
        Assert.assertFalse(config.starRatingDialogIsCancellable);
        Assert.assertFalse(config.starRatingShownAutomatically);
        Assert.assertFalse(config.starRatingDisableAskingForEachAppVersion);
    }
}
