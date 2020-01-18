package ly.count.android.sdk.messaging;

import android.os.Build;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Utility class
 */

public class Utils {
    private static final Utils utils = new Utils();

    static final String UTF8 = "UTF-8";
    static final String CRLF = "\r\n";
    static final char[] BASE_16 = "0123456789ABCDEF".toCharArray();

    /**
     * Joins objects with a separator
     *
     * @param objects   objects to join
     * @param separator separator to use
     * @return resulting string
     */
    static <T> String join(Collection<T> objects, String separator) {
        StringBuilder sb = new StringBuilder();
        Iterator<T> iter = objects.iterator();
        while (iter.hasNext()) {
            sb.append(iter.next());
            if (iter.hasNext()) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    /**
     * URLEncoder wrapper to remove try-catch
     *
     * @param str string to encode
     * @return url-encoded {@code str}
     */
    static String urlencode(String str) {
        try {
            return URLEncoder.encode(str, UTF8);
        } catch (UnsupportedEncodingException e) {
            Log.wtf("Countly", "No UTF-8 encoding? " + e);
            return "";
        }
    }

    /**
     * URLDecoder wrapper to remove try-catch
     *
     * @param str string to decode
     * @return url-decoded {@code str}
     */
    static String urldecode(String str) {
        try {
            return URLDecoder.decode(str, UTF8);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    static boolean reflectiveClassExists(String cls) {
        return utils._reflectiveClassExists(cls);
    }

    /**
     * Check wether class exists in default class loader.
     *
     * @param cls Class name to check
     * @return true if class exists, false otherwise
     */
    public boolean _reflectiveClassExists(String cls) {
        try {
            Class.forName(cls);
            return true;
        } catch (ClassNotFoundException e) {
            Log.d("Countly", "Class " + cls + " not found");
            return false;
        }
    }

    /**
     * Reflective method call encapsulation.
     *
     * @param className  class to call method in
     * @param instance   instance to call on, null for static methods
     * @param methodName method name
     * @param args       optional arguments to pass to that method
     * @return false in case of failure, method result otherwise
     */
    static Object reflectiveCall(String className, Object instance, String methodName, Object... args) {
        return utils._reflectiveCall(className, instance, methodName, args);
    }

    public Object _reflectiveCall(String className, Object instance, String methodName, Object... args) {
        try {
            Log.d("Countly", "cls " + className + ", inst " + instance);
            className = className == null && instance != null ? instance.getClass().getName() : className;
            Class<?> cls = instance == null ? Class.forName(className) : instance.getClass();
            Class<?> types[] = null;

            if (args != null && args.length > 0) {
                types = new Class[args.length];

                for (int i = 0; i < types.length; i++) {
                    types[i] = args[i].getClass();
                }
            }
            Method method = cls.getDeclaredMethod(methodName, types);
            return method.invoke(instance, args);
        } catch (ClassNotFoundException t) {
            Log.w("Countly", "Cannot call " + methodName + " of " + className, t);
            return false;
        } catch (NoSuchMethodException t) {
            Log.w("Countly", "Cannot call " + methodName + " of " + className, t);
            return false;
        } catch (IllegalAccessException t) {
            Log.w("Countly", "Cannot call " + methodName + " of " + className, t);
            return false;
        } catch (InvocationTargetException t) {
            Log.w("Countly", "Cannot call " + methodName + " of " + className, t);
            return false;
        }
    }

    /**
     * StringUtils.isEmpty replacement.
     *
     * @param str string to check
     * @return true if null or empty string, false otherwise
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    /**
     * StringUtils.isNotEmpty replacement.
     *
     * @param str string to check
     * @return false if null or empty string, true otherwise
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean API(int version) {
        return Build.VERSION.SDK_INT >= version;
    }

    public static boolean isNotEqual(Object a, Object b) {
        return !isEqual(a, b);
    }

    public static boolean isEqual(Object a, Object b) {
        if (a == null || b == null || a == b) {
            return a == b;
        }
        return a.equals(b);
    }

    public static boolean contains(String string, String part) {
        if (string == null || part == null) {
            return false;
        } else {
            return string.contains(part);
        }
    }

    /**
     * Calculate digest (SHA-1, SHA-256, etc.) hash of the string provided
     *
     * @param digestName digest name like {@code "SHA-256"}, must be supported by Java, see {@link MessageDigest}
     * @param string     string to hash
     * @return hash of the string or null in case of error
     */
    public static String digestHex(String digestName, String string) {
        try {
            MessageDigest digest = MessageDigest.getInstance(digestName);
            byte[] bytes = string.getBytes(UTF8);
            digest.update(bytes, 0, bytes.length);
            return hex(digest.digest());
        } catch (Throwable e) {
            Log.e("Countly", "Cannot calculate sha1: " + e);
            return null;
        }
    }

    /**
     * Get hexadecimal string representation of a byte array
     *
     * @param bytes array of bytes to convert
     * @return hex string of the byte array in lower case
     */
    public static String hex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = BASE_16[v >>> 4];
            hexChars[j * 2 + 1] = BASE_16[v & 0x0F];
        }
        return new String(hexChars).toLowerCase();
    }

    /**
     * Read stream into a byte array
     *
     * @param stream input to read
     * @return stream contents or {@code null} in case of error
     */
    public static byte[] readStream(InputStream stream) {
        if (stream == null) {
            return null;
        }

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = stream.read(buffer)) != -1) {
                bytes.write(buffer, 0, len);
            }
            return bytes.toByteArray();
        } catch (IOException e) {
            Log.e("Countly", "Couldn't read stream: " + e);
            return null;
        } finally {
            try {
                bytes.close();
                stream.close();
            } catch (Throwable ignored) {
            }
        }
    }
}