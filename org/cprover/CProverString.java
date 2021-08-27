package org.cprover;

/**
 * This class provides an interface with string functions modeled internally
 * in CProver, for which the CProver model differs from the JDK actual behavior.
 * This is in particular the case for functions that throw exceptions.
 */
public final class CProverString {
  private CProverString() {
  }

  /**
   * Returns the character (Unicode code point) at the specified
   * index.
   *
   * @param instance The String instance
   * @param index    The index to the {@code char} values
   * @return The code point value of the character at the {@code index}
   */
  public static int codePointAt(String instance, int index) {
    return CProver.nondetInt();
  }

  /**
   * Returns the character (Unicode code point) before the specified
   * index.
   *
   * @param instance The String instance
   * @param index    The index following the code point that should be returned
   * @return The code point value of the character before the {@code index}
   */
  public static int codePointBefore(String instance, int index) {
    return CProver.nondetInt();
  }

  /**
   * Returns the number of Unicode code points in the specified text
   * range of this {@code String}.
   *
   * @param instance The String instance
   * @param start    The index to the first {@code char} of the text range.
   * @param end      The index after the last {@code char} of the text range.
   * @return The number of Unicode code points in the specified text range
   */
  public static int codePointCount(
      String instance, int start, int end) {
    return CProver.nondetInt();
  }

  /**
   * Returns the index within this {@code String} that is
   * offset from the given {@code index} by
   * {@code codePointOffset} code points.
   *
   * @param instance        The String instance
   * @param index           The index to be offset
   * @param codePointOffset The offset in code points
   * @return The index within this {@code String}
   */
  public static int offsetByCodePoints(
      String instance, int index, int codePointOffset) {
    return  CProver.nondetInt();
  }

  /**
   * Modeled internally in CBMC.
   * @return '\u0000' if index is out of bounds and behave as s.charAt(i)
   *     otherwise.
   */
  public static char charAt(String string, int index) {
    return CProver.nondetChar();
  }

  /**
   * Returns the {@code char} value in this sequence at the specified index.
   *
   * @param instance The StringBuffer instance
   * @param index    The index of the desired {@code char} value.
   * @return The {@code char} value at the specified index.
   */
  public static char charAt(StringBuffer instance, int index) {
    return CProver.nondetChar();
  }

  /**
   * Modeled internally in CBMC.
   * @return Empty string if index is too large, s if index too small and
   *     behave as s.substring(i) otherwise.
   */
  public static String substring(String string, int index) {
    return CProver.nondetWithoutNullForNotModelled();
  }

  /**
   * Modeled internally in CBMC.
   * @return Empty string if start >= end, s.substring(k, l)
   *     where k = max(0, start)
   *     and l = min(string.length() - 1, end) otherwise.
   */
  public static String substring(String string, int start, int end) {
    return CProver.nondetWithoutNullForNotModelled();
  }

  /**
   * Returns a new {@code String} that contains a subsequence of
   * characters currently contained in this sequence.
   *
   * @param instance The StringBuffer instance.
   * @param start    The beginning index, inclusive.
   * @param end      The ending index, exclusive.
   * @return The new string.
   */
  public static String substring(StringBuffer instance, int start, int end) {
    return CProver.nondetWithoutNullForNotModelled();
  }

  /**
   * Returns a character sequence that is a subsequence of this sequence.
   *
   * @param instance the String instance
   * @param start    the begin index, inclusive.
   * @param end      the end index, exclusive.
   * @return the specified subsequence.
   */
  public static CharSequence subSequence(
      String instance, int start, int end) {
    return CProver.nondetWithoutNullForNotModelled();
  }

  /**
   * Appends a subsequence of the specified {@code CharSequence} to this
   * sequence.
   *
   * @param instance  the StringBuilder instance
   * @param cs        the sequence to append.
   * @param start     the starting index of the subsequence to be appended.
   * @param end       the end index of the subsequence to be appended.
   * @return the modified StringBuilder.
   */
  public static StringBuilder append(
      StringBuilder instance, CharSequence cs, int start, int end) {
    return CProver.nondetWithoutNullForNotModelled();
  }

  /**
   * Removes the characters in a substring of this sequence.
   *
   * @param instance The StringBuilder instance
   * @param start    The beginning index, inclusive.
   * @param end      The ending index, exclusive.
   * @return The modified StringBuilder.
   */
  public static StringBuilder delete(
      StringBuilder instance, int start, int end) {
    return CProver.nondetWithoutNullForNotModelled();
  }

  /**
   * Removes the characters in a substring of this sequence.
   *
   * @param instance The StringBuffer instance
   * @param start    The beginning index, inclusive.
   * @param end      The ending index, exclusive.
   * @return The modified StringBuffer.
   */
  public static StringBuffer delete(
      StringBuffer instance, int start, int end) {
    return CProver.nondetWithoutNullForNotModelled();
  }

  /**
   * Removes the {@code char} at the specified position in this
   * sequence. This sequence is shortened by one {@code char}.
   *
   * @param instance The StringBuilder instance
   * @param index    Index of {@code char} to remove
   * @return The modified StringBuilder.
   */
  public static StringBuilder deleteCharAt(
      StringBuilder instance, int index) {
    return CProver.nondetWithoutNullForNotModelled();
  }

  /**
   * Removes the {@code char} at the specified position in this
   * sequence. This sequence is shortened by one {@code char}.
   *
   * <p>Note: If the character at the given index is a supplementary
   * character, this method does not remove the entire character. If
   * correct handling of supplementary characters is required,
   * determine the number of {@code char}s to remove by calling
   * {@code Character.charCount(thisSequence.codePointAt(index))},
   * where {@code thisSequence} is this sequence.
   *
   * @param instance The StringBuffer instance.
   * @param index    Index of {@code char} to remove
   * @return This object.
   */
  public static StringBuffer deleteCharAt(StringBuffer instance, int index) {
    return CProver.nondetWithoutNullForNotModelled();
  }

  /**
   * Equality of two strings. Should be much more efficient
   * than String.equals because the type is known at
   * compilation time.
   */
  public static boolean equals(String str1, String str2) {
    if (str1 == null) {
      return str2 == null;
    }
    return str1.length() == str2.length() && str1.startsWith(str2);
  }

  /**
   * Inserts the string into this character sequence.
   *
   * @param instance The StringBuilder instance
   * @param offset   The offset.
   * @param str      A string.
   * @return The modified StringBuilder.
   */
  public static StringBuilder insert(
      StringBuilder instance, int offset, String str) {
    return CProver.nondetWithoutNullForNotModelled();
  }

  /**
   * Inserts the string into this character sequence.
   *
   * @param instance The StringBuffer instance.
   * @param offset   The offset.
   * @param str      A string.
   * @return The modified StringBuffer.
   */
  public static StringBuffer insert(
      StringBuffer instance, int offset, String str) {
    return CProver.nondetWithoutNullForNotModelled();
  }

  /**
   * Inserts the string representation of the {@code boolean}
   * argument into this sequence.
   *
   * @param  offset  The offset.
   * @param  boolVal A {@code boolean}.
   * @return A reference to this object.
   */
  public static StringBuffer insert(
      StringBuffer instance, int offset, boolean boolVal) {
    return CProver.nondetWithoutNullForNotModelled();
  }

  /**
   * Inserts the string representation of the {@code char}
   * argument into this sequence.
   *
   * @param offset    The offset.
   * @param character A {@code char}.
   * @return A reference to this object.
   */
  public static StringBuffer insert(
      StringBuffer instance, int offset, char character) {
    return CProver.nondetWithoutNullForNotModelled();
  }

  /**
   * Inserts the string representation of the {@code int}
   * argument into this sequence.
   *
   * @param offset The offset.
   * @param intVal A {@code int}.
   * @return A reference to this object.
   */
  public static StringBuffer insert(
      StringBuffer instance, int offset, int intVal) {
    return CProver.nondetWithoutNullForNotModelled();
  }

  /**
   * Inserts the string representation of the {@code long}
   * argument into this sequence.
   *
   * @param offset  The offset.
   * @param longVal A {@code long}.
   * @return A reference to this object.
   */
  public static StringBuffer insert(
      StringBuffer instance, int offset, long longVal) {
    return CProver.nondetWithoutNullForNotModelled();
  }

  /**
   * Sets the length of the character sequence.
   *
   * @param instance  The StringBuffer instance
   * @param newLength The new length
   */
  public static void setLength(StringBuffer instance, int newLength) {
  }

  /**
   * Sets the length of the character sequence.
   *
   * @param instance  The StringBuilder instance
   * @param newLength The new length
   */
  public static void setLength(StringBuilder instance, int newLength) {
  }

  /**
   * The character at the specified index is set to {@code ch}.
   *
   * @param instance  The StringBuffer instance
   * @param index     The index of the character to modify.
   * @param character The new character.
   */
  public static void setCharAt(
      StringBuffer instance, int index, char character) {
  }

  /**
   * The character at the specified index is set to {@code ch}.
   *
   * @param instance  The StringBuilder instance
   * @param index     The index of the character to modify.
   * @param character The new character.
   */
  public static void setCharAt(
      StringBuilder instance, int index, char character) {
  }

  /**
   * Converts an array of characters to a string. This uses a loop and
   * therefore in test-generation the {@code count} will be limited by the
   * unwind parameter.
   * This constrains {@code value} to not be null, its length to be greater
   * or equal to {@code offset + count} and {@code offset} and {@code count}
   * to be non-negative.
   *
   * @param value  Array of characters which is the source to copy from
   * @param offset Index in {@code value} of the first character to copy
   * @param count  Number of characters to copy
   * @return The created String
   */
  public static String ofCharArray(char[] value, int offset, int count) {
    CProver.assume(value != null);
    CProver.assume(value.length - count >= offset
                   && offset >= 0 && count >= 0);
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < count; i++) {
      builder.append(value[offset + i]);
    }
    return builder.toString();
  }

  /**
   * Format a string according to the given {@code formatString}.
   * Unlike the JDK version, all arguments are given by strings, there is
   * a fixed number of them and they should not be null.
   * This fixed number corresponds to the constant {@code MAX_FORMAT_ARGS}
   * defined in {@code java_string_library_preprocess.h} from JBMC.
   */
  public static String format(
      String formatString, String arg0, String arg1, String arg2, String arg3,
      String arg4, String arg5, String arg6, String arg7, String arg8, String arg9) {
    return CProver.nondetWithoutNullForNotModelled();
  }

  /**
   * Returns a {@code String} object representing the
   * specified integer. The argument is converted to signed decimal
   * representation and returned as a string.
   *
   * @param intVal An integer to be converted.
   * @return A string representation of the argument in base&nbsp;10.
   */
  public static String toString(int intVal) {
    return CProver.nondetWithoutNullForNotModelled();
  }

  /**
   * Returns a string representation of the first argument in the
   * radix specified by the second argument.
   *
   * @param intVal An integer to be converted to a string.
   * @param radix  The radix to use in the string representation.
   * @return A string representation of the argument in the specified radix.
   */
  public static String toString(int intVal, int radix) {
    return CProver.nondetWithoutNullForNotModelled();
  }

  /**
   * Returns a {@code String} object representing the specified
   * {@code long}.  The argument is converted to signed decimal
   * representation and returned as a string.
   *
   * @param longVal A {@code long} to be converted.
   * @return A string representation of the argument in base&nbsp;10.
   */
  public static String toString(long longVal) {
    return CProver.nondetWithoutNullForNotModelled();
  }

  /**
   * Returns a string representation of the first argument in the
   * radix specified by the second argument.
   *
   * @param longVal A {@code long} to be converted to a string.
   * @param radix   The radix to use in the string representation.
   * @return A string representation of the argument in the specified radix.
   */
  public static String toString(long longVal, int radix) {
    return CProver.nondetWithoutNullForNotModelled();
  }

  /**
   * Returns a string representation of the {@code float}
   * argument.
   *
   * @param floatVal The float to be converted.
   * @return A string representation of the argument.
   */
  public static String toString(float floatVal) {
    return CProver.nondetWithoutNullForNotModelled();
  }

  /**
   * Returns a string representation of the {@code double}
   * argument.
   *
   * <p>The double is converted to a float first as the string solver currently
   * can convert float to String but not double to String.
   *
   * @param doubleVal The double to be converted.
   * @return A string representation of the argument.
   */
  public static String toString(double doubleVal) {
    return toString(CProver.doubleToFloat(doubleVal));
  }

  /**
   * Exactly as Integer.parseInt, except str is already checked non-null, the
   * radix is already checked in-range and 'str' is known to be a valid integer
   * according to isValidInt below
   */
  public static int parseInt(String str, int radix) {
    return CProver.nondetInt();
  }

  /**
   * Exactly as Long.parseLong, except str is already checked non-null, the
   * radix is already checked in-range and 'str' is known to be a valid integer
   * according to isValidLong below
   */
  public static long parseLong(String str, int radix) {
    return CProver.nondetLong();
  }

  /**
   * Returns true if string 'str' is a valid integer: contains at least one
   * digit, perhaps a leading '+' or '-', and doesn't contain invalid chars
   * for the given radix.
   */
  public static boolean isValidInt(String str, int radix) {
    return CProver.nondetBoolean();
  }

  /**
   * Returns true if string 'str' is a valid long: contains at least one
   * digit, perhaps a leading '+' or '-', and doesn't contain invalid chars
   * for the given radix.
   */
  public static boolean isValidLong(String str, int radix) {
    return CProver.nondetBoolean();
  }
}
