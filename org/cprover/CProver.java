package org.cprover;

import java.io.BufferedInputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;

/**
 * This class provides the interface to functions implemented internally
 * in CProver, such as assume and nondetInt.
 */
public final class CProver {
  private CProver() {
  }

  /**
   * Flag to control the behavior of the {@link #assume} function
   * when executed in the JVM.
   * If true {@link #assume} will throw an exception;
   * otherwise it will be a nop.
   * The flag has no influence on the analysis in CProver.
   */
  public static boolean enableAssume = true;

  /**
   * Flag to control the behavior of the nondetX functions,
   * e.g. {@link #nondetInt}, when executed in the JVM.
   * If true nondetX will throw an exception;
   * otherwise it will take the default value of the domain of X.
   * The flag has no influence on the analysis in CProver.
   */
  public static boolean enableNondet = true;

  /**
   * Flag to control the behavior of concurrency-related functions,
   * e.g. {@link #startThread}.
   * If true this functions will throw an exception;
   * otherwise they will be a nop.
   * The flag has no influence on the analysis in CProver.
   */
  public static boolean enableConcurrency = true;

  /**
   * In an analysis in CProver the return value can take any value
   * of the domain of Booleans (true, false).
   */
  public static boolean nondetBoolean() {
    if (enableNondet) {
      throw new RuntimeException(
        "Cannot execute program with CProver.nondetBoolean()");
    }

    return false;
  }

  /**
   * In an analysis in CProver the return value can take any value
   * of the domain of byte (8-bit signed integers).
   */
  public static byte nondetByte() {
    if (enableNondet) {
      throw new RuntimeException(
        "Cannot execute program with CProver.nondetByte()");
    }

    return 0;
  }

  /**
   * In an analysis in CProver the return value can take any value
   * of the domain of UTF-16 characters.
   */
  public static char nondetChar() {
    if (enableNondet) {
      throw new RuntimeException(
        "Cannot execute program with CProver.nondetChar()");
    }

    return '\0';
  }

  /**
   * In an analysis in CProver the return value can take any value
   * of the domain of short (16-bit signed integers).
   */
  public static short nondetShort() {
    if (enableNondet) {
      throw new RuntimeException(
        "Cannot execute program with CProver.nondetShort()");
    }

    return 0;
  }

  /**
   * In an analysis in CProver the return value can take any value
   * of the domain of int (32-bit signed integers).
   */
  public static int nondetInt() {
    if (enableNondet) {
      throw new RuntimeException(
        "Cannot execute program with CProver.nondetInt()");
    }

    return 0;
  }

  /**
   * In an analysis in CProver the return value can take any value
   * of the domain of long (64-bit signed integers).
   */
  public static long nondetLong() {
    if (enableNondet) {
      throw new RuntimeException(
        "Cannot execute program with CProver.nondetLong()");
    }

    return 0;
  }

  /**
   * In an analysis in CProver the return value can take any value
   * of the domain of float (IEEE-754 binary32).
   */
  public static float nondetFloat() {
    if (enableNondet) {
      throw new RuntimeException(
        "Cannot execute program with CProver.nondetFloat()");
    }

    return 0;
  }

  /**
   * In an analysis in CProver the return value can take any value
   * of the domain of double (IEEE-754 binary64).
   */
  public static double nondetDouble() {
    if (enableNondet) {
      throw new RuntimeException(
        "Cannot execute program with CProver.nondetDouble()");
    }

    return 0;
  }

  private static <T> T nondetWithNull() {
    if (enableNondet) {
      throw new RuntimeException(
        "Cannot execute program with CProver.nondetWithNull<T>(T)");
    }

    return null;
  }

  /**
   * @param instance an instance of the type T, this is not used but is there
   *         to make sure the class T is loaded. The parameter should
   *         not be `null`.
   * @param <T> class of the object to return
   * @return a non-deterministic object of type T, possibly `null`.
   */
  public static <T> T nondetWithNull(T instance) {
    return nondetWithNull();
  }

  private static <T> T nondetWithoutNull() {
    T retVal = nondetWithNull();
    assume(retVal != null);
    return retVal;
  }

  /**
   * @param instance an instance of the type T, this is not used but is there
   *         to make sure the class T is loaded. The parameter should
   *         not be `null`.
   * @param <T> class of the object to return
   * @return a non-deterministic object of type T, assumed to be non-null.
   */
  public static <T> T nondetWithoutNull(T instance) {
    return nondetWithoutNull();
  }

  /**
   * Method to indicate that a library method with return value T
   * is not modeled.
   * @return a non-deterministic object of type T, possibly `null`.
   */
  public static <T> T nondetWithNullForNotModelled() {
    return nondetWithNull();
  }

  /**
   * Method to indicate that a library method with return value T
   * is not modeled.
   * @return a non-deterministic object of type T, assumed to be non-null.
   */
  public static <T> T nondetWithoutNullForNotModelled() {
    return nondetWithoutNull();
  }

  /**
   * Method to indicate that a library method with return value void
   * is not modeled.
   */
  public static void notModelled() {
    assume(false);
  }

  /**
   * Return a non-deterministic PrintStream.
   * It is not recommended to use it, since it will not enforce that
   * PrintStreamis loaded, but is necessary for initializing System.out
   * and System.err.
   */
  public static PrintStream nondetPrintStream() {
    return nondetWithoutNull();
  }

  /**
   * Return a non-deterministic BufferedInputStream.
   * It is not recommended to use it, since it will not enforce that
   * BufferedInputStream is loaded, but is necessary for initializing
   * System.in.
   */
  public static BufferedInputStream nondetBufferedInputStream() {
    return nondetWithoutNull();
  }

  /**
   * @param condition to be assumed to hold true on any execution.
   */
  public static void assume(boolean condition) {
    if (enableAssume) {
      throw new RuntimeException(
        "Cannot execute program with CProver.assume()");
    }
  }

  /**
   * This method is used by JBMC to detect the start of a new thread and
   * create a multithreaded bmc equation.
   * Refer to the method `start` in the model for the class `java.lang.Thread`
   * in the JBMC sources to see an example of usage
   */
  public static void startThread(int id) {
    if (enableConcurrency) {
      throw new RuntimeException(
        "Cannot execute program with CProver.startThread()");
    }
  }

  /**
   * This method is used by JBMC to detect the end of a new thread to
   * manage a multithreaded bmc equation
   * Refer to the method `start` in the model for the class `java.lang.Thread`
   * in the JBMC sources to see an example of usage
   */
  public static void endThread(int id) {
    if (enableConcurrency) {
      throw new RuntimeException(
        "Cannot execute program with CProver.endThread()");
    }
  }

  /**
   * This method is used by JBMC to return the ID of the executing thread.
   */
  public static int getCurrentThreadId() {
    if (enableConcurrency) {
      throw new RuntimeException(
        "Cannot execute program with CProver.getCurrentThreadId()");
    }
    return 0;
  }

  /**
   * This method is used by JBMC to indicate an atomic section.
   * This avoids any thread interleavings of the code inside the section.
   */
  public static void atomicBegin() {
    if (enableConcurrency) {
      throw new RuntimeException(
        "Cannot execute program with CProver.atomicBegin()");
    }
  }

  /**
   * This method is used by JBMC to indicate the end of an atomic section
   * (see atomicBegin).
   */
  public static void atomicEnd() {
    if (enableConcurrency) {
      throw new RuntimeException(
        "Cannot execute program with CProver.atomicEnd()");
    }
  }

  /**
   * Array copy for byte arrays. Does not check for exceptions.
   * Use instead of System.arraycopy when the bounds are ensured to be
   * respected, that is, the following should be false:
   * srcPos < 0 || destPos < 0 || length < 0 ||
   * srcPos + length > src.length || destPos + length > dest.length
   *
   * @param    src    the source array.
   * @param    srcPos   starting position in the source array.
   * @param    dest   the destination array.
   * @param    destPos  starting position in the destination data.
   * @param    length   the number of array elements to be copied.
  */
  public static void arraycopy(
      byte[] src, int srcPos, byte[] dest, int destPos, int length)  {
    byte[] temp = new byte[length];
    for (int i = 0; i < length; i++) {
      temp[i] = src[srcPos + i];
    }
    for (int i = 0; i < length; i++) {
      dest[destPos + i] = temp[i];
    }
  }

  /**
   * Array copy for byte arrays. Does not check for exceptions,
   * and assumes that `src` and `dest`.
   * Use instead of System.arraycopy when `src` and `dest` are guaranteed to
   * be different and the bounds are ensured to be
   * respected, that is, the following should be false:
   * src == dest || srcPos < 0 || destPos < 0 || length < 0 ||
   * srcPos + length > src.length || destPos + length > dest.length
   *
   * @param    src    the source array.
   * @param    srcPos   starting position in the source array.
   * @param    dest   the destination array.
   * @param    destPos  starting position in the destination data.
   * @param    length   the number of array elements to be copied.
  */
  public static void arraycopyInPlace(
      byte[] src, int srcPos, byte[] dest, int destPos, int length)  {
    for (int i = 0; i < length; i++) {
      dest[destPos + i] = src[srcPos + i];
    }
  }

  /**
   * Retrieves the current locking count for 'object'.
   */
  public static int getMonitorCount(Object object) {
    // Dummy implementation.
    // The actual implementation is in JBMC itself.
    return 0;
  }

  /**
   * Class identifier of an Object. For instance "java.lang.String",
   * "java.lang.Integer". This gives direct read access to the
   * {@code @class_identifier} field used internally by JBMC.
   * The body of this function is replaced by preprocessing of the java
   * bytecode, it is only meant to give meaningful output in case the model is
   * executed.
   */
  public static String classIdentifier(Object object) {
    return object.getClass().getCanonicalName();
  }

  /**
   * This method converts a double to a float and adds assumes that the
   * conversion did not result any loss of precision. Calling this method is
   * useful when weneed to call a certain complicated operation on double,
   * but we only have it implemented for floats.
   * 
   * <p>The method is a workaround for the current limitations of the string
   * solver, which is able to convert float to String but not double to
   * String. Once this limitation goes away, the method and its usages can be
   * removed.
   * 
   * <p>The method itself has limitations:
   * * There are doubles that can't be converted to float and back without
   *   loss of precision, so the assertion will sometimes be violated
   * * Even if the assertion is satisfied for numbers d and converted, these
   *   might not have the same String representation.
   */
  public static float doubleToFloat(double value) {
    float converted = nondetFloat();
    CProver.assume(value == (double) converted);
    return converted;
  }

  /**
   * Instantiates (but does not populate) an array with type matching a given
   * array, but with a potentially different length. Used by
   * ArrayList.toArray, for example, whose internal array is an Object[] but
   * must provide that array as a user-supplied T[], copying the runtime type
   * of whatever array the user provided as a template.
   * 
   * <p>The implementation given here is correct, but JBMC cannot currently
   * understand these reflective methods and therefore replaces this function
   * with its own implementation.
   */
  public static <T> T[] createArrayWithType(int length, T[] type) {
    Class typeClass = type.getClass();
    return (T[])Array.newInstance(typeClass.getComponentType(), length);
  }
}
