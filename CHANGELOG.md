## 23.8.1
* Adding an additional testing call for retrieving AB test experiment information

## 23.8.0
* ! Minor breaking change ! Manual view recording calls are now ignored when in automatic view recording mode.
* ! Minor breaking change ! If a manual session is already started, it will not be possible to call "BeginSession" without stopping the previous one 
* ! Minor breaking change ! If a manual session has not been started, it will not be possible to call "UpdateSession"
* ! Minor breaking change ! If a manual session has not been started, it will not be possible to call "EndSession"
* ! Minor breaking change ! If Application class is provided, the SDK will use the Application Activity lifecycle callbacks to react to "onStart", "onStop" and "onConfigurationChanged" callbacks. The manual calls will be ignored.

* Adding remaining request queue size information to every request
* Adding SDK health check requests after init
* Added protection for updating the push token. The same value can't be sent within 10 minutes it was sent last time.
* View duration moved from segmentation to the event body
* Added support for recording multiple views at the same time
* First view flag is now reset when a session ends and not when a session starts

* Push messaging mode is not reported anymore. Related calls have been deprecated.
* Deprecated the following calls:
  * Countly.sharedInstance().views().recordView(viewName)
  * Countly.sharedInstance().views().recordView(viewName, viewSegmentation)
  * Countly.sharedInstance().views().isAutomaticViewTrackingEnabled()

* Deprecated the init time configuration of 'setViewTracking(enable)'. Introduced replacement 'enableAutomaticViewTracking()'
* Deprecated the init time configuration of 'setAutoTrackingUseShortName(enable)'. Introduced replacement 'enableAutomaticViewShortNames()'
* Deprecated the init time configuration of 'setAutomaticViewSegmentation(segmentation)'. Introduced replacement 'setGlobalViewSegmentation(segmentation)'
* Deprecated the init time configuration of 'setAutoTrackingExceptions(exceptions)'. Introduced replacement 'setAutomaticViewTrackingExclusions(exclusions)'


## 23.6.0
* !! Major breaking change !! Automatically downloaded remote config values will no longer be automatically enrolled in their AB tests.
* ! Minor breaking change ! Remote config will now return previously downloaded values when remote-config consent is not given

* Introduced a new set of remote config methods
* Deprecated old remote config methods

* Fixed bug where recording views would force send all stored events
* Fixed bug where exiting temporary ID mode would create unintended requests

* Removed the deprecated enum "DeviceId.Type"
* Removed the deprecated value "ADVERTISING_ID" from the enum "DeviceIdType"
* Removed the deprecated function "Countly.changeDeviceIdWithoutMerge(type, deviceID)"
* Removed the deprecated function "Countly.changeDeviceIdWithMerge(deviceID)"
* Removed the deprecated function "Countly.getDeviceID()"
* Removed the deprecated function "Countly.getDeviceIDType()"
* Removed the deprecated function "Countly.enableTemporaryIdMode()"
* Removed the deprecated function "Countly.onCreate(activity)"
* Removed the deprecated function "Countly.isHttpPostForced()"
* Removed the deprecated function "Countly.isDeviceAppCrawler()"
* Removed the deprecated function "Countly.ifShouldIgnoreCrawlers()"
* Removed the deprecated function "Countly.flushRequestQueues()"
* Removed the deprecated function "Countly.doStoredRequests()"
* Removed the deprecated function "Countly.requestQueueOverwriteAppKeys()"
* Removed the deprecated function "Countly.requestQueueEraseAppKeysRequests()"
* Removed the deprecated function "CountlyConfig.setRemoteConfigAutomaticDownload(enabled, callback)"
* Removed the deprecated user profile interface accessed on "Countly.userData"


## 22.09.4
* Added server side configuration functionality. This is an experimental feature.
* Default max segmentation value count changed from 30 to 100
* Fixing bug that prevented device ID to be changed when there is no consent given

## 22.09.3
* Added "previous event ID" logic for non-internal events

## 22.09.2
* Deprecated the init time configuration of 'setEnableAttribution'
* Session update interval upper limit (10 minutes) has been lifted

## 22.09.1
* Fixed a bug where metric override values were not applying to crash metrics
* Fixed a bug where crash metrics sent the "manufacturer" value under the wrong key
* Fixed a bug where orientation events would have the same view ID as the previous view event
* Fixed a bug where automatic view tracking reported view duration with a duration of "0"
* Fixed a bug where view ID's were being reported incorrectly

## 22.09.0
* Fixed a race condition bug where a recorded event would have the wrong user properties in the drill database on the server. Now event queue is emptied (formed into a request) before recording any user profile changes.
* Events are now recorded with an internal ID.
* Added a variant of "esWriteCachesToPersistence" that has a callback to indicate if any writes were performed
* When calling "esWriteCachesToPersistence", no write to storage is performed if the same data would be written
* Expanded feedback widget functionality. Added ability to use rating widgets.
* Added functionality to access tags for feedback widgets.

## 22.06.2
* Fixed a bug that would throw a null pointer exception when calling "CountlyPush.displayNotification " and CountlyPush was not initialized 

## 22.06.1
* Fixed a bug that would throw a null pointer exception when calling "CountlyPush.onTokenRefresh" and CountlyPush was not initialized 

## 22.06.0
* Fatal crash reports will now be written in sync mode to shared preferences 
* Added explicit storage mode where the event queue and request queue are not written to persistent storage until a signal is given

* Added mitigations for rare crashes while updating remote config values
* Adding a init time cleanup procedure to remove any leftover temporary id requests

* Removed the deprecated "disableLocation" method
* Removed the deprecated "setLocation" method

## 22.02.3
* Added CountlyConfigPush object which is used during CountlyPush init.
* Added a way to add allowed package names for push notification intent security.
* Added a way to add allowed class names for push notification intent security.

## 22.02.2
* Added ability to record direct requests.

## 22.02.1
* Fixed bug that would be opening two intents for MainActivity when clicking on a push notification with a deep-link.

## 22.02.0
* Fixed notification trampoline restrictions in Android 12 using reverse activity trampolining implementation.
* Adding a call to provide user properties during initialization.

## 21.11.2
* Fixed bug that caused crashes when migrating from older versions that don't have a device ID type stored. When migrating from no device ID and no type, SDK will fall back to a generated ID. 
When migrating from device ID and no type, SDK will set id type to 'DEVELOPER_SUPPLIED' if a custom ID was provided during init. Otherwise the new type will be 'OPEN_UDID'. Adding handling for additional edge cases.

## 21.11.1
* Made 'ModuleDeviceId' public so that it would eliminate access issues for kotlin projects.

## 21.11.0
* !! Major breaking change !! Deprecating "ADVERTISING_ID" as device ID generation strategy. SDK will fall back to 'OPEN_UDID'. All "ADVERTISING_ID" device ID's will have their type changed to "OPEN_UDID". If the device will have a "null" device ID, a random one will be generated.
* !! Major breaking change !! Changing device ID without merging will now clear all consent. It has to be given again after this operation.
* !! Major breaking change !! Entering temporary ID mode will now clear all consent. It has to be given again after this operation.
* ! Minor breaking change ! Feedback widget callback has an additional callback. This will cause a compilation error that is trivial to fix by adding the override for the missing call.
* ! Minor breaking change ! Automatic "AdvertisingId" attribution has been removed from the SDK. A call is added for recording provided attribution values.
* ! Minor breaking change ! Automatic install referrer attribution in play stores has been removed. A call is added for recording such provided values.
* ! Minor breaking change ! Orientation tracking is now enabled by default. Can be turned off during init.
* ! Minor breaking change ! A few internal SDK limits are now introduced. The following limits are adjustable at initialization:
  * Maximum amount of provided segmentation in now 30 entries by default.
  * Maximum amount of breadcrumbs that can be recorded at once is now 100 by default.

* Device ID can now be changed when no consent is given
* Push notification now display/use the sent badge number. It's visualization depends on the launcher.
* Added new calls to report attribution.
* When recording internal events with 'recordEvent', the respective feature consent will now be checked instead of the 'events' consent. 
* Consent changes will now send the whole consent state and not just the "delta"
* Deprecated old user profile calls. Introduced a new API by which to change them.
* Added platform information to push actioned events

* Fixed bug where, on the start of a new session, the first view was not identified correctly
* Fixed bug where the rating widget callback would not be called when the widget is closed and there is no error
* Fixed potential deadlock issue

* Removed the deprecated "init" methods
* Removed the deprecated "recordEvent" methods
* Removed the deprecated "changeDeviceId" methods
* Removed the deprecated "setViewTracking" method
* Removed the deprecated "isViewTrackingEnabled" method
* Removed the deprecated "recordView" methods
* Removed the deprecated "setCustomCrashSegments" method
* Removed the deprecated "addCrashBreadcrumb" method
* Removed the deprecated "recordHandledException" methods
* Removed the deprecated "recordUnhandledException" methods
* Removed the deprecated "enableCrashReporting" method
* Removed the deprecated "startEvent" method
* Removed the deprecated "endEvent" methods
* Removed the deprecated "setDisableUpdateSessionRequests" method
* Removed the deprecated "enableParameterTamperingProtection" method
* Removed the deprecated "enablePublicKeyPinning" method
* Removed the deprecated "enableCertificatePinning" method
* Removed the deprecated "setPushIntentAddMetadata" method
* Removed the deprecated "setEnableAttribution" method
* Removed the deprecated "setEventQueueSizeToSend" method
* Removed the deprecated "setStarRatingDialogTexts" method
* Removed the deprecated "setIfStarRatingShownAutomatically" method
* Removed the deprecated "setStarRatingDisableAskingForEachAppVersion" method
* Removed the deprecated "setAutomaticStarRatingSessionLimit" method
* Removed the deprecated "getAutomaticStarRatingSessionLimit" method
* Removed the deprecated "getStarRatingsCurrentVersionsSessionCount" method
* Removed the deprecated "clearAutomaticStarRatingSessionCount" method
* Removed the deprecated "setIfStarRatingDialogIsCancellable" method
* Removed the deprecated "setHttpPostForced" method
* Removed the deprecated "setShouldIgnoreCrawlers" method
* Removed the deprecated "addAppCrawlerName" method
* Removed the deprecated "setRequiresConsent" method
* Removed the deprecated "createFeatureGroup" method
* Removed the deprecated "setConsentFeatureGroup" method
* Removed the deprecated "setConsent" method
* Removed the deprecated "giveConsent" method
* Removed the deprecated "removeConsent" method
* Removed the deprecated "removeConsentAll" method
* Removed the deprecated "getConsent" method
* Removed the deprecated "checkAllConsent" method
* Removed the deprecated "showFeedbackPopup" method
* Removed the deprecated "setRemoteConfigAutomaticDownload" method
* Removed the deprecated "remoteConfigUpdate" method
* Removed the deprecated "updateRemoteConfigForKeysOnly" method
* Removed the deprecated "updateRemoteConfigExceptKeys" method
* Removed the deprecated "getRemoteConfigValueForKey" method
* Removed the deprecated "remoteConfigClearValues" method
* Removed the deprecated "addCustomNetworkRequestHeaders" method
* Removed the deprecated "setLoggingEnabled" method

## 20.11.12
* Fixed bug due to "CLOSE_SYSTEM_DIALOGS" permission denial
* Adding an init time configuration option to set the maximum request queue size
* Updating the "compileSdkVersion" and "targetSdkVersion" to "31".
* Updating the "buildToolsVersion" to "31.0.0".

## 20.11.11
* Fixed a bug where events would be sent with a larger delay if SDK callbacks are not called
* Fixed a bug where "platform" and "app_version" were provided incorectly when retrieving the feedback widget data

## 20.11.10
* Fixed bug that would close manually recorded views early when automatic view recording is disabled

## 20.11.9
* Moving a push related broadcast receiver decleration to the manifest to comply with 'PendingIntent' checks
* Fixed issue with star rating lowest allowed value. Changing it from 0 to 1.
* Fixed bug that would prevent certain codepath to be executed if the Application class was not provided during init.
* Added additional protections in remote config logic to protect against rare crashes
* Increasing default event threshold to 100.
* Printing approximate data usage in logs when sending requests

## 20.11.8
* Minor tweaks. Changes related to publishing to MavenCentral

## 20.11.7
* Adding metric for the device manufacturer.
* Fixing potential issues by sending all available events before the "end session" request
  
## 20.11.6
* Fixed init time bug where the context from the provided application class was not used.
* Fixed init time bug which happened when location was disabled during init.

## 20.11.5
* Added option to enable additional push redirection protections

## 20.11.4
* Added a way to retrieve feedback widget data and manually report them
* Fixed bug where network request were retried too soon after a failed request

## 20.11.3
* Removed thrown exceptions in places where that is possible

## 20.11.2
* Added SDK log listener
* Refactored all logs to use the new logging mechanism
* Fixed bug where manual session control calls were working even when not enabled

## 20.11.1
* Lessened push notification security restrictions
* Added a default way to acquire app start timestamp for APM
* Added a way to override the current app start timestamp for APM
* Added manual trigger for when app has finished loading for APM
* Added manual foreground/background triggers for APM

## 20.11.0
* !! Consent change !! To record orientation you now need to give "user" consent
* !! Consent change !! To use remote config, you now need to give "remote-config" consent
* !! Push breaking changes !! Due to a security vulnerability the following permission need to be added to your app manifest:
  '
  <uses-permission android:name="${applicationId}.CountlyPush.BROADCAST_PERMISSION" />
  '
  To make these breaking changes noticable, the broadcast listener id 'NOTIFICATION_BROADCAST' has been replaced with 'SECURE_NOTIFICATION_BROADCAST'

* Added survey, nps feature
* Added retries to push media download
* Added call that removes all server requests that don't have the currently set appKey
* Added call that updates all server requests so that all of them have the currently set appKey
* Update breakpad for native exception catching
* Symbol upload tool now sends the used breakpad version
* Reworked openID device ID to not be a separate service  
* Sending device type (phone, tablet, tv) as part of metrics
* Reworked location data persistence and when it is sent. It's recommended to recheck your app to see if it's still behaving as expected.
* Deprecated CountlyConfig constructor that takes 'Context'. Added new constructor that also takes the Application class instead of 'Context'
* Initialising the SDK without providing the application class is not deprecated
* Fixed a push consent edge case bug where it would have been counted when set in the past
* Fixed a push consent bug where 'anyConsent' would have returned true when no consent would have been set
* Fixed a bug regarding temporary ID mode transitioning device ID's which would merge it to the previous value
* Fixed init time openUDID bug which made it less persistent than required
* Fixed potential multithreaded synchronization issues
* Fixed location url encoding bug
* Fixed init issue where the device ID was not saved and could be changed by changing it's value during next init

## 20.04.5
* Replacing sha-1 hash with sha-256 hash for requests.
* Adding apm calls for cancelTrace, cancelAllTraces, recordNetworkTrace
* Adding option to use Huawei push notifications
* making tweaks to APM key validation logic

## 20.04.4
* Adding metric override feature
* Adding functionality to override SDK name and SDK version strings
* Fixed an issue in the network request retry logic
* Removed requirement for any consent to be set before device ID can be changed.
* Mitigating issue which would happen if push token is refreshed before SDK is initialized.
* Fixed the sending of empty event collections in cases where event sending was forced.

## 20.04.3
* Adding fallback cache to CountlyPush for cases where the SDK is not initialised
* Fixed bug which happened when consent was required but no consent was provided during init

## 20.04.2
* Fixed bug where data was not removed from the URL in case of forced http POST
* Fixed HTTP post issue with remote config and rating widget

## 20.04.1
* Changing permissions for modules so that they are accessible from kotlin

## 20.04
* Adding functionality for filtering crashes
* Adding functionality to set segmentation for automatic and manual views
* Adding functionality to ignore activities for automatic view tracking
* Adding functionality to track orientation changes
* Adding call to manually record user rating without showing any UI
* Adding call to record an event with a given timestamp
* Adding call to cancel a timed event
* Adding manual session control
* Adding option to set tamper protection salt in config object
* Adding calls to give and remove consent to all features at the same time
* Adding a way to change the session update timer interval
* Adding call to set notification accent color
* Adding APM functionality to record app start time
* Adding APM functionality to record custom traces
* Adding APM functionality to record network request traces
* Adding APM functionality to record app time spent in foreground and background
* Adding remote config call to get all saved values
* Moving crash, event, view, rating related calls to a separate modules
* Deprecating functions that are being put into modules
* Migrated to AndroidX
* Improved internal time management which fixes inconsistencies that could arrise on day changes
* Fixed a bug where deep links from notifications did not include the sent message as part of the intent
* Removing deprecated "addCrashLog" call
* Removing deprecated "logException" call
* Removing deprecated "setCustomUserData" call
* Removing deprecated "setUserData" call

## 19.09.3
* Fixed a bug that did not add the link to the push dialog button.
  
## 19.09.2
* Fixed a bug that did not add links to push buttons.

## 19.09.1
* Applying null pointer exception mitigations to immediate request maker.

## 19.09
* Adding feature to flush all requests in the queue
* Adding CountlyConfig object which is used during init
* Added call to try to complete stored requests
* Merging Push module with SDK module
* Fixing a potential 'null' bug for push
* Fixing a consent bug which disabled features when consent was not enabled
* Fixed a race condition bug when sending push token to server
* Fixed remote config bug during init when no device ID was available
* Fixed bug with events not using the provided event duration.
* Fixing issue that automated star rating session count was not cleared when changing device ID
* Removing GCM push support

## 19.02.3
* Adding support for native crash handling and symbolication

## 19.02.2
* Fixing bug with location data not being erased during begin_session
* Fix potential issue with push Activity

## 19.02.1
* fixing endEvent bug that ignores provided "count" and "sum" values

## 19.02
* Added remote config
* Star rating requests now use the same urlConnection as other requests
* Added functionality for adding custom header key/value pairs to every request

## 18.08.1
* Fixed bug with events getting unrelated segmentation fields added
* Added flags to push action intent
* Refactored push URL action handling

## 18.08
* Added functionality for webView user rating popup dialog
* Added call for recording unhandled exceptions
* Added 10 second delay before merging device Id's
* Added functionality for sending integers and doubles with segmentation, instead of just strings
* Added call to record exception with a throwable
* Improved Countly SDK logging messages
* SDK now tries to send messages in it's timer event when in the background
* Limited the size of crash logs to 10k characters
* Limited the size of breadcrumbs to 1000 characters
* Limited the amount of breadcrumbs to 1000 entries. If a newer one is added, the oldest one is discarded
* Fixed a deviceId changing related bug
* Fixed a bug for setting push consent before init

## 18.04
* Added functionality for GDPR (giving and removing consent for features)
* Added separate module for FCM push notifications

## 18.01.2
* Fixing a crash in messaging because of null context 
  
## 18.01.1
* Fixing small push notification accent color bug
* Properly deleting cached location data 
* Improving debug and log messages

## 18.01
* Changes made how location data is passed and handled
* Adding option to disable sending of location data (it can be reenabled later)
* Adding option to add a large icon and accent color to push notifications
* Adding option to add meta information to push notification intents

## 17.09.2
* Adding option to override the icon for push notifications

## 17.09.1
* Adding additional calls for manipulating the star rating dialog

## 17.09
* Fixed app crawler filtering & ANR if substantially changing device date back in time

## 17.05
* Added Rich Push Notifications support (attachments and custom action buttons)
* Added functionality to ignore app crawlers
* Added calls to retrieve device ID and ID type
* Added call see if onStart has been called at least once

## 16.12.3
* Adding certificate pinning in addition to public key pinning

## 16.12.02
* Changing automatic star rating default behaviour (disabling it)
* Removing Context as a needed field from some function calls

## 16.12.01
* Added additional meta data to each API request
* Added support for the star rating plugin
* Added option to force HTTP POST for all requests
* Added support for optional parameters during initialization

## 16.02
* Views support
* User data part updated

## 15.08.01
* Lowering required API level back to 9

## 15.08
* Bug fixes:
  * Incorrect handling of empty review message #50
  * Change GCM registration ID whenever sender ID changed #51

## 15.06
* Bug fixes & other improvements
* Attribution analytics
* Crash reports
