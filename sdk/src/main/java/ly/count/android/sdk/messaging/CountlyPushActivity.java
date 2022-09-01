package ly.count.android.sdk.messaging;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import ly.count.android.sdk.Countly;

import static ly.count.android.sdk.messaging.CountlyPush.COUNTLY_CONFIG_PUSH;
import static ly.count.android.sdk.messaging.CountlyPush.EXTRA_ACTION_INDEX;
import static ly.count.android.sdk.messaging.CountlyPush.EXTRA_INTENT;
import static ly.count.android.sdk.messaging.CountlyPush.EXTRA_MESSAGE;
import static ly.count.android.sdk.messaging.CountlyPush.useAdditionalIntentRedirectionChecks;

public class CountlyPushActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performPushAction(getIntent());
        finish();
    }

    private void performPushAction(Intent activityIntent) {
        Context context = this;
        Countly.sharedInstance().L.d("[CountlyPush, CountlyPushActivity] Push activity receiver receiving message");

        activityIntent.setExtrasClassLoader(CountlyPush.class.getClassLoader());

        Intent intent = activityIntent.getParcelableExtra(EXTRA_INTENT);

        if (intent == null) {
            Countly.sharedInstance().L.e("[CountlyPush, CountlyPushActivity] Received a null Intent, stopping execution");
            return;
        }

        int flags = intent.getFlags();
        if (((flags & Intent.FLAG_GRANT_READ_URI_PERMISSION) != 0) || ((flags & Intent.FLAG_GRANT_WRITE_URI_PERMISSION) != 0)) {
            Countly.sharedInstance().L.w("[CountlyPush, CountlyPushActivity] Attempt to get URI permissions");
            return;
        }

        if (useAdditionalIntentRedirectionChecks) {
            ComponentName componentName = intent.getComponent();
            String intentPackageName = componentName.getPackageName();
            String intentClassName = componentName.getClassName();
            String contextPackageName = context.getPackageName();

            CountlyConfigPush countlyConfigPush = (CountlyConfigPush) intent.getSerializableExtra(COUNTLY_CONFIG_PUSH);

            countlyConfigPush.whiteListIntentClassNames.add(intentClassName);
            countlyConfigPush.whiteListIntentPackageNames.add(contextPackageName);

            if(intentPackageName != null) {
                boolean isTrustedPackage = false;
                for (String packageName : countlyConfigPush.whiteListIntentPackageNames) {
                    if(intentPackageName.equals(packageName)) {
                        isTrustedPackage = true;
                        break;
                    }
                }
                if (!isTrustedPackage) {
                    Countly.sharedInstance().L.w("[CountlyPush, CountlyPushActivity] Untrusted intent package");
                    return;
                }
            }


            if (intentPackageName == null || !intentClassName.startsWith(intentPackageName)) {
                Countly.sharedInstance().L.w("[CountlyPush, CountlyPushActivity] intent class name and intent package names do not match");
                return;
            }
        }

        Countly.sharedInstance().L.d("[CountlyPush, CountlyPushActivity] Push activity, after filtering");

        intent.setExtrasClassLoader(CountlyPush.class.getClassLoader());

        int index = intent.getIntExtra(EXTRA_ACTION_INDEX, 0);
        Bundle bundle = intent.getParcelableExtra(EXTRA_MESSAGE);
        if (bundle == null) {
            Countly.sharedInstance().L.e("[CountlyPush, CountlyPushActivity] Received a null Intent bundle, stopping execution");
            return;
        }

        CountlyPush.Message message = bundle.getParcelable(EXTRA_MESSAGE);
        if (message == null) {
            Countly.sharedInstance().L.e("[CountlyPush, CountlyPushActivity] Received a null Intent bundle message, stopping execution");
            return;
        }

        message.recordAction(context, index);

        final NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.cancel(message.hashCode());
        }

        try {
            //try/catch required due to Android 12
            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                //this needs to be called before Android 12
                Intent closeNotificationsPanel = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
                context.sendBroadcast(closeNotificationsPanel);
            }
        } catch (Exception ex) {
            Countly.sharedInstance().L.e("[CountlyPush, CountlyPushActivity] Encountered issue while trying to send the on click broadcast. [" + ex.toString() + "]");
        }

        if (index == 0) {
            try {
                if (message.link() != null) {
                    Countly.sharedInstance().L.d("[CountlyPush, CountlyPushActivity] Starting activity with given link. Push body. [" + message.link() + "]");
                    Intent i = new Intent(Intent.ACTION_VIEW, message.link());
                    i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra(EXTRA_MESSAGE, bundle);
                    i.putExtra(EXTRA_ACTION_INDEX, index);
                    context.startActivity(i);
                } else {
                    Countly.sharedInstance().L.d("[CountlyPush, CountlyPushActivity] Starting activity without a link. Push body");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            } catch (Exception ex) {
                Countly.sharedInstance().L.e("[CountlyPush, displayDialog] Encountered issue while clicking on notification body [" + ex.toString() + "]");
            }
        } else {
            try {
                Countly.sharedInstance().L.d("[CountlyPush, CountlyPushActivity] Starting activity with given button link. [" + (index - 1) + "] [" + message.buttons().get(index - 1).link() + "]");
                Intent i = new Intent(Intent.ACTION_VIEW, message.buttons().get(index - 1).link());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(EXTRA_MESSAGE, bundle);
                i.putExtra(EXTRA_ACTION_INDEX, index);
                context.startActivity(i);
            } catch (Exception ex) {
                Countly.sharedInstance().L.e("[CountlyPush, displayDialog] Encountered issue while clicking on notification button [" + ex.toString() + "]");
            }
        }
    }
}