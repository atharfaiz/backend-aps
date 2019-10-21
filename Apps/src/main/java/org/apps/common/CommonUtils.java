package org.apps.common;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class CommonUtils {
	private static Logger _log = Logger.getLogger(CommonUtils.class);

	private CommonUtils() {
	}

	private static DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd h:mm:ss a");
	private static SimpleDateFormat dtFmt = new SimpleDateFormat("dd-MMM-yyyy h:mm a");
	private static SimpleDateFormat dtFrmt = new SimpleDateFormat("dd-MMM-yyyy h:mm:ss a");
	private static SimpleDateFormat formatDtAdd = new SimpleDateFormat("dd-MMM-yyyy");
	private static SimpleDateFormat formatDtModify = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
	private static DateFormat formatMM = new SimpleDateFormat("dd-MMM-yy");
	private static SimpleDateFormat formatDt = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
	private static SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat formatDateYY = new SimpleDateFormat("dd/MM/yy");
	// private static SimpleDateFormat formatDateEX = new
	// SimpleDateFormat("dd-MMM-yy h:mm:ss a");
	private static SimpleDateFormat dtFrmtTime = new SimpleDateFormat("dd-MM-yy h:mm:ss a");
	private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

	/**
	 * This method converts an Array of string to the CSV format String.this method
	 * would return an empty String in case passed Array does not have any element
	 * or is null.
	 * 
	 * @param val this is the passed String Array
	 * @return String CSV format string
	 */
	public static String convertArrayToString(String[] val) {
		if (!isEmpty(val)) {
			List<String> values = Arrays.asList(val);
			String str = values.toString();
			return str.substring(1, str.length() - 1);
		}
		return "";
	}

	public static Date getObjectToDate(Object date) {
		try {
			if (date == null) {
				return null;
			} else {
				return (Date) date;
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * This method is responsible for formate the number to the given decimal place.
	 * 
	 * @param anoumt
	 * @param locale
	 * @return
	 */
	public static String formatedNumber(Double anoumt, Locale locale, int digits) {
		NumberFormat nfc = NumberFormat.getNumberInstance(locale);
		nfc.setMaximumFractionDigits(digits);
		nfc.setMinimumFractionDigits(digits);
		if (anoumt == null) {
			return nfc.format(0d);
		}
		return nfc.format(anoumt);
	}

	/**
	 * This method replaces all occurances of a double quote with two double quotes.
	 * 
	 * @param inputSQL
	 * @return String
	 */
	public static String formatSQLString(String inputSQL) {
		return isEmpty(inputSQL) ? "" : inputSQL.replaceAll("\"", "\"\"");
	}

	/**
	 * This method is responsible to append not null string values.
	 * 
	 * @param values
	 * @return : appended string
	 */
	public static String getAppendedNotNullString(String... values) {
		StringBuilder sb = new StringBuilder();
		for (String str : values) {
			if (str != null) {
				sb.append(str);
			}
		}
		return sb.toString();
	}

	/**
	 * this method appends the strings supplied
	 * 
	 * @param values - string values to be appended
	 * @return String - the appended string
	 */
	public static String getAppendedString(String... values) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
			sb.append(values[i]);
		}
		return sb.toString();
	}

	/**
	 * this method is responsible to return String array from a colon seprated
	 * String
	 * 
	 * @param str A :-separated value string
	 * @return String[]
	 */
	public static String[] getArrayOfColonString(String str) {
		return !isEmpty(str) ? str.split(":") : null;
	}

	/**
	 * this method is responsible to return String array from a comma seprated
	 * String
	 * 
	 * @param str A ;SV format string
	 * @return String[]
	 */
	public static String[] getArrayOFCommaString(String str) {
		return !isEmpty(str) ? str.split(",") : null;
	}

	/**
	 * this method is responsible to return String array from a semicolon seprated
	 * String
	 * 
	 * @param str A ;SV format string
	 * @return String[]
	 */
	public static String[] getArrayOFSemicolonString(String str) {
		return !isEmpty(str) ? str.split(";") : null;
	}

	/**
	 * This method returns a string which contains comma separated, single quoted
	 * values from the input List
	 * 
	 * @param list List
	 * @return String
	 */
	public static String getDBInString(Collection<String> list) {
		if (list == null) {
			return "''";
		}
		return list.toString().replaceAll("'", "''").replaceAll("\\[", "'").replaceAll("\\]", "'").replaceAll(", ",
				"', '");
	}

	/**
	 * This method is responsible for returning comma seprated String
	 * 
	 * @param list
	 * @return
	 */
	public static String getCommaSepratedString(Collection<String> list) {
		if (list == null) {
			return "";
		}
		return list.toString().replaceAll("\\[", "").replaceAll("\\]", "");
	}

	/**
	 * This method returns a string which contains comma separated, single quoted
	 * values from the input Array
	 * 
	 * @param arr String []
	 * @return String
	 */
	public static String getDBInString(String[] arr) {
		return getDBInString(arr, 0, arr == null ? 0 : arr.length);
	}

	/**
	 * This method returns the date into GMT date format quoted values from the
	 * input Array
	 * 
	 * @param arr String []
	 * @return String
	 */
	public static String getGMTDate(Date date) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormatter.format(date);
	}

	/**
	 * This method returns the date into GMT date format quoted values from the
	 * input Array
	 * 
	 * @param arr String []
	 * @return String
	 * @throws DatatypeConfigurationException
	 */
	public static XMLGregorianCalendar getXMLGregorianCalendar(String date) throws DatatypeConfigurationException {
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(date);
	}

	/**
	 * This method returns a string which contains comma separated, single quoted
	 * values from the input Array
	 * 
	 * @param arr String []
	 * @return String
	 */
	public static String getDBInString(String[] arr, int fromIndex, int toIndex) {
		if (arr == null) {
			return "''";
		}
		return getDBInString(Arrays.asList(arr).subList(fromIndex, toIndex));
	}

	/**
	 * This method is reponsible to append non-empty string values (from given
	 * string values) separated by given delimiter.
	 * 
	 * @param delimiter
	 * @param values    : string values in Collection
	 * @return appended string
	 */
	public static String getDelimitedString(String delimiter, Collection<String> values) {
		StringBuilder sb = new StringBuilder();
		if (!CommonUtils.isEmpty(values)) {
			for (String str : values) {
				if (!isEmpty(str)) {
					sb.append(str);
					sb.append(delimiter);
				}
			}

			int index = sb.lastIndexOf(delimiter);
			if (index > -1) {
				return sb.substring(0, index);
			}
		}
		return sb.toString();
	}

	/**
	 * This method is reponsible to append non-empty string values (from given
	 * string values) separated by given delimiter.
	 * 
	 * @param delimiter
	 * @param values    : string values in array
	 * @return appended string
	 */
	public static String getDelimitedString(String delimiter, String... values) {
		StringBuilder sb = new StringBuilder();
		for (String str : values) {
			if (!isEmpty(str)) {
				sb.append(str);
				sb.append(delimiter);
			}
		}

		int index = sb.lastIndexOf(delimiter);
		if (index > -1) {
			return sb.substring(0, index);
		}
		return sb.toString();
	}

	/**
	 * The method would return the hypen separated ProductID in xxxx-xxxx-xxxx
	 * format
	 * 
	 * @param partNumber
	 * @return
	 */
	public static String getFormattedPID(String partNumber) {
		if (partNumber == null) {
			return "";
		}
		// Code to display PID in xxxx-xxxx-xxxx format
		StringBuilder conPartId = new StringBuilder(partNumber);
		int length = partNumber.length();
		int k = 0;
		for (int i = 4; i < length; i += 4) {
			conPartId.insert(i + k++, "-");
		}
		return conPartId.toString();
	}

	/**
	 * This method is responsible to return current server name.
	 * 
	 * @return
	 */
	public static String getServerName() {
		String serverName = null;
		try {
			serverName = System.getProperty("server.name", "TESTING");
		} catch (Exception ex) {
			return "TESTING";
		}
		return serverName;
	}

	/**
	 * This method is responsible to return current server port.
	 * 
	 * @return
	 */
	public static String getServerPort() {

		String port;
		try {
			port = System.getProperty("server.port", "80");
		} catch (Exception ex) {
			return "80";
		}
		return port;
	}

	public static String getServerMode() {
		String serverMode;
		try {
			serverMode = System.getProperty("server.mode");
		} catch (Exception ex) {
			return "";
		}
		return serverMode;
	}

	/**
	 * This method is responsible to return current server name and port.
	 * 
	 * @return
	 */
	public static String getServerString() {
		return getServerName() + ":" + getServerPort();
	}

	/**
	 * This method checks whether the given Array has all null elements.
	 * 
	 * @param val this is the passed String Array
	 * @return
	 */
	public static boolean hasAllNulls(String[] val) {
		if (val != null) {
			for (String value : val) {
				if (value != null && !value.equals("null")) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * This method checks whether the given String is purely alphabetic.
	 * 
	 * @param str A non-null string
	 * @return boolean If str is alphabetic
	 */
	public static boolean isAlphabetic(String str) {
		if (isEmpty(str)) {
			return false;
		}
		return str.matches("[a-zA-Z]*");
	}

	/**
	 * This method checks whether the given String is alpha-numeric.
	 * 
	 * @param str A non-null string
	 * @return boolean If str is alpha-numeric
	 */
	public static boolean isAlphaNumeric(String str) {
		if (isEmpty(str)) {
			return false;
		}
		return str.matches("\\w*");
	}

	/**
	 * this Method checks if there is <b>"ANY"</b> exist in the given array
	 * 
	 * @param val this is the passed String Array
	 * @return boolean
	 */
	public static boolean isAny(String[] val) {
		if (!isEmpty(val)) {
			List<String> values = Arrays.asList(val);
			return values.contains("ANY");
		}
		return true;
	}

	/**
	 * This method checks to see if the given Collection is null or contains no
	 * object.
	 * 
	 * @param val passed Collection object
	 * @return boolean
	 */
	public static boolean isEmpty(Collection val) {
		return (val == null || (val.size() == 0));
	}

	/**
	 * This method checks to see if the given Map is null or contains no object.
	 * 
	 * @param val passed Map object
	 * @return boolean
	 */
	public static boolean isEmpty(Map val) {
		return (val == null || (val.size() == 0));
	}

	/**
	 * This method checks to see if the given Object is null or not.
	 * 
	 * @param obj object to be checked
	 * @return boolean
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof String) {
			return isEmpty((String) obj);
		}
		return false;
	}

	/**
	 * This method checks to see if the given String is null or blank.
	 * 
	 * @param val passed String object
	 * @return boolean
	 */
	public static boolean isEmpty(String val) {
		return (val == null || val.trim().equals(""));
	}

	/**
	 * This method checks whether the given Array is null or contain no element.
	 * 
	 * @param val this is the passed String Array
	 * @return boolean
	 */
	public static boolean isEmpty(String[] val) {
		return (val == null || val.length == 0);
	}

	/**
	 * This method checks to see if the given property in the object is null or
	 * blank.
	 * 
	 * @param property The string representing the property that needs to be checked
	 *                 [follows commons.beanutils methodology]
	 * @param bean     The object whose property needs to be checked
	 * @return boolean
	 */
	public static boolean isEmptyProperty(String property, Object bean) {
		try {
			Object value = PropertyUtils.getProperty(bean, property);
			if (value == null) {
				return true;
			}
			if (value instanceof String) {
				return isEmpty((String) value);
			}
			if (value instanceof Collection) {
				return isEmpty((Collection) value);
			}
		} catch (Exception ex) {
			_log.error("Error", ex);
		}
		return false;
	}

	/**
	 * This method checks whether the given String is float number.
	 * 
	 * @param str A non-null string
	 * @return boolean If str is alphabetic
	 */
	public static boolean isFloat(String str) {
		if (isEmpty(str)) {
			return false;
		}
		return str.matches("[0-9]*\\.{0,1}[0-9]*");
	}

	/**
	 * This method checks whether the given String is purely numeric.
	 * 
	 * @param str A non-null string
	 * @return boolean If str is alphabetic
	 */
	public static boolean isNumeric(String str) {
		if (isEmpty(str)) {
			return false;
		}
		return str.matches("[0-9]*");
	}

	/**
	 * Reads the <code>property</code> from the <code>bundle</code> and returns an
	 * array of Strings. The property is assumed to contain a list of
	 * colon-separated values.
	 * 
	 * @param bundle
	 * @param property
	 * @return A <code>String []</code> representing all colon-separated values
	 */
	public static String[] readBundle(ResourceBundle bundle, String property) {
		return getArrayOfColonString(bundle.getString(property));
	}

	/**
	 * Reads the <code>property</code> from the bundle in the <code>locale</code>
	 * with the base <code>bundleName</code> and returns an array of Strings. The
	 * property is assumed to contain a list of colon-separated values.
	 * 
	 * @param bundleName
	 * @param locale
	 * @param property
	 * @return A <code>String []</code> representing all colon-separated values
	 */
	public static String[] readBundle(String bundleName, Locale locale, String property) {
		return readBundle(ResourceBundle.getBundle(bundleName, locale), property);
	}

	/**
	 * Reads the <code>property</code> from the bundle with the base
	 * <code>bundleName</code> and returns an array of Strings. The property is
	 * assumed to contain a list of colon-separated values.
	 * 
	 * @param bundleName
	 * @param property
	 * @return A <code>String []</code> representing all colon-separated values
	 */
	public static String[] readBundle(String bundleName, String property) {
		return readBundle(ResourceBundle.getBundle(bundleName), property);
	}

	/**
	 * Check if the passed value is null. If null, returns the default value
	 * 
	 * @param aValue
	 * @param aDefault
	 * @return
	 */
	public static Object format(Object aValue, Object aDefault) {
		if (aValue == null) {
			return aDefault;
		}
		return aValue;

	}

	/**
	 * Check if the passed value is null. If null, returns the default value
	 * 
	 * @param aValue
	 * @param aDefault
	 * @return
	 */
	public static String format(String aValue, String aDefault) {
		if (aValue == null) {
			return aDefault;
		}
		return aValue.trim();
	}

	public static Date modifyDate(Date origDate, int mod) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(origDate);
		cal.add(Calendar.DATE, mod);
		return cal.getTime();
	}

	public static NumberFormat getNumberFormat(int numDecimals) {
		NumberFormat nf = NumberFormat.getInstance(Locale.US);
		nf.setGroupingUsed(false);
		nf.setMaximumFractionDigits(numDecimals);
		nf.setMinimumFractionDigits(numDecimals);
		nf.setMinimumIntegerDigits(1);
		return nf;
	}

	public static boolean checkLimit(double value, double minRange, double maxRange) {
		return (minRange <= value) && (maxRange >= value);
	}

	public static boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	/*
	 * This method capitalize first letter of word in array and other in small case
	 * 
	 */
	public static String[] convertToCamelCase(String[] word) {
		if (!CommonUtils.isEmpty(word)) {
			for (int i = 0; i < word.length; i++) {
				if (!CommonUtils.isEmpty(word[i])) {
					word[i] = StringUtils.capitalize(word[i].toLowerCase());
				}
			}
		}
		return word;
	}

	public static String convertDateToString(Object da) {
		if (da == null) {
			return null;
		}
		try {
			return df.format((Date) da);
		} catch (Exception e) {
			return null;
		}

	}

	public static String convertDateToString(Object da, int format) {
		if (da == null) {
			return null;
		}
		try {
			if (format == 1)
				return df.format((Date) da);
			else
				return dtFrmt.format((Date) da);

		} catch (Exception e) {
			return null;
		}

	}

	public static String objToStr(Object obj) {
		if (obj == null)
			return null;
		return obj.toString();
	}

	public static Date convertStringToDate(String str) {
		if (str == null) {
			return null;
		}
		try {
			return df.parse(str);
		} catch (ParseException e) {
			return null;
		}

	}

	public static Date convertStringToDate(String str, Integer flg) {
		if (str == null) {
			return null;
		}
		try {
			if (flg == 1)
				return df.parse(str);
			else if (flg == 2)
				return formatDateYY.parse(str);
			else
				return formatDtModify.parse(str);
		} catch (ParseException e) {
			return null;
		}

	}

	public static Date convertStringToDateObj(String str) {
		if (str == null) {
			return null;
		}
		try {
			return formatDtModify.parse(str);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date convertStringToDateFormat(String str) {
		if (str == null) {
			return null;
		}
		try {
			return formatDtAdd.parse(str);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Integer convertToInteger(String str) {
		if (str == null) {
			return 0;
		}

		Integer i = 0;
		try {
			i = Integer.parseInt(str);
		} catch (NumberFormatException ex) {
			i = 0;
		}

		return i;

	}

	public static int convertToInteger(Object str) {
		if (str == null) {
			return 0;
		}

		int i = 0;
		try {
			i = Integer.parseInt(str.toString());
		} catch (NumberFormatException ex) {
			i = 0;
		}

		return i;

	}

	public static String getStringFromDate(Date date) {
		try {
			if (date == null) {
				return null;
			} else {
				return sdf.format(date);
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static String getFmtStringFromDate(Date date) {
		try {
			if (date == null) {
				return null;
			} else {
				return dtFmt.format(date);
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static String getStringFormatedDate(Date date) {
		try {
			if (date == null)
				return null;
			else
				return formatDate.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static Double convertToDouble(Object str) {
		if (str == null) {
			return 0d;
		}

		Double i = 0d;
		try {
			i = Double.parseDouble(str.toString());
		} catch (NumberFormatException ex) {
			i = 0d;
		}

		return i;

	}

	public static String getDateToStringFormat(Date date, Integer flg) {
		try {
			if (date == null) {
				return null;
			} else {
				if (flg == 1) {
					return formatDtAdd.format(date); // Creation Date Format
				} else if (flg == 2) {
					return formatDtModify.format(date); // modification date Format
				} else {
					return null;
				}

			}
		} catch (Exception e) {
			return null;
		}
	}

	public static String getDateToStringFormat(Object date, Integer flg) {
		try {
			if (date == null) {
				return null;
			} else {
				if (flg == 1) {
					return formatDtAdd.format(date); // Creation Date Format
				} else if (flg == 2) {
					return formatDtModify.format(date); // modification date Format
				} else {
					return null;
				}

			}
		} catch (Exception e) {
			return null;
		}
	}

	public static String formatMessage(List<String> stringList) {
		if (stringList == null || stringList.isEmpty()) {
			return "";
		}

		return stringList.toString().substring(1, stringList.toString().length() - 1).replace(", ", ",");
	}

	public static Double convertToDoubleOrNull(Object obj) {
		if (obj == null) {
			return null;
		}

		Double i = 0d;
		try {
			i = Double.parseDouble(obj.toString());
		} catch (NumberFormatException ex) {
			i = null;
		}
		return i;
	}

	public static Boolean getTimeStampLag(Date dbTimestamp, Date objTimestamp) {
		Timestamp timestamp;
		Date date = dbTimestamp;
		if (!CommonUtils.isEmpty(date)) {
			timestamp = new Timestamp(date.getTime());
		} else {
			timestamp = new Timestamp(System.currentTimeMillis());
		}
		Timestamp contractTimestamp = new Timestamp(objTimestamp.getTime() + (8 * 60 * 60 * 1000));
		return timestamp.getTime() < contractTimestamp.getTime();
	}

	public static int getCompare(Object obj1, Object obj2, String sortProperty, String classpath) {
		int result = 0;
		Field field = null;
		if (!CommonUtils.isEmpty(obj1) && !CommonUtils.isEmpty(obj2)) {
			try {
				field = Class.forName(classpath).getDeclaredField(sortProperty);
				if (!CommonUtils.isEmpty(field)) {
					field.setAccessible(true);
					if ("java.lang.String".equals(field.getType().getName())) {
						String str1 = !CommonUtils.isEmpty(field.get(obj1)) ? field.get(obj1).toString() : "";
						String str2 = !CommonUtils.isEmpty(field.get(obj2)) ? field.get(obj2).toString() : "";
						return str1.compareTo(str2);
					} else if ("java.lang.Integer".equals(field.getType().getName())) {
						Integer int1 = !CommonUtils.isEmpty(field.get(obj1))
								? Integer.parseInt(field.get(obj1).toString())
								: 0;
						Integer int2 = !CommonUtils.isEmpty(field.get(obj2))
								? Integer.parseInt(field.get(obj2).toString())
								: 0;
						return int1.compareTo(int2);
					} else if ("java.lang.Date".equals(field.getType().getName())) {
						Date dt1 = !CommonUtils.isEmpty(field.get(obj1))
								? convertStringToDate(field.get(obj1).toString())
								: new Date();
						Date dt2 = !CommonUtils.isEmpty(field.get(obj2))
								? convertStringToDate(field.get(obj2).toString())
								: new Date();
						return dt1.compareTo(dt2);
					} else if ("java.lang.Double".equals(field.getType().getName())) {
						Double d1 = !CommonUtils.isEmpty(field.get(obj1))
								? Double.parseDouble(field.get(obj1).toString())
								: 0.00;
						Double dt2 = !CommonUtils.isEmpty(field.get(obj2))
								? Double.parseDouble(field.get(obj2).toString())
								: 0.00;
						return d1.compareTo(dt2);
					}
				}
			} catch (Exception e) {
				_log.error("Error in Compare", e);
			}
		}
		return result;
	}

	public static Double getDoubleFromString(String str) {
		try {
			if (isEmpty(str)) {
				return 0d;
			} else {
				return new Double(str.replace(",", ""));
			}
		} catch (Exception e) {
			return 0d;
		}
	}

	/**
	 * This method is responsible for formate the number to the given decimal place.
	 * 
	 * @param anoumt
	 * @param locale
	 * @return
	 */
	public static String formatedNumberStr(String anoumt, Locale locale, int digits) {
		NumberFormat nfc = NumberFormat.getNumberInstance(locale);
		nfc.setMaximumFractionDigits(digits);
		nfc.setMinimumFractionDigits(digits);
		if (CommonUtils.isEmpty(anoumt)) {
			return nfc.format(0d);
		}
		double dAmt = 0d;
		try {
			dAmt = Double.parseDouble(anoumt);
		} catch (Exception e) {
			return nfc.format(0d);
		}
		return nfc.format(dAmt);
	}

	/**
	 * Convert date into format (DD-MMM-YY)
	 * 
	 * @param da
	 * @return
	 */
	public static String convertDateMMToString(Object da) {
		if (da == null) {
			return null;
		}
		try {
			return formatMM.format((Date) da);
		} catch (Exception e) {
			return null;
		}

	}

	public static String convertCurrDecimal(String amount, Locale locale) {
		NumberFormat fmt = NumberFormat.getNumberInstance(locale);
		((DecimalFormat) fmt).setParseBigDecimal(true);
		String val = null;
		try {
			val = String.valueOf(fmt.parse(amount));
		} catch (ParseException e) {
			_log.error(e);
			val = "0d";
		}

		return val;
	}

	public static Boolean getTimeStampDiff(Date dbLastSaved, Date catalogOpenDate) {
		if (CommonUtils.isEmpty(catalogOpenDate) || (CommonUtils.isEmpty(catalogOpenDate))) {
			return true;
		}

		return dbLastSaved.getTime() == catalogOpenDate.getTime();
		// return
		// CommonUtils.getCompareFormatter(dbLastSaved).equals(CommonUtils.getCompareFormatter(catalogOpenDate));
	}

	public static String getEscapeCharaterQuery(String query) {
		if (query != null) {
			query = StringEscapeUtils.escapeSql(query);
		}

		return query;
	}

	public static String getCompareFormatter(Date dbLastSaved) {
		return formatDt.format(dbLastSaved);
	}

	public static String getStringDiff(String firstStr, String secondStr, int flg) {

		String diffStr = "";
		if (firstStr == null && secondStr == null)
			return null;
		else if (firstStr == null && secondStr != null)
			return secondStr;
		else if (firstStr != null && secondStr == null)
			return firstStr;
		else {
			boolean tempFlg = false;
			for (String str : secondStr.split(",")) {
				tempFlg = false;
				for (String str_0 : firstStr.split(",")) {
					if (str.equals(str_0)) {
						tempFlg = true;
					}
				}
				if (flg == 1 && !tempFlg) {
					diffStr = diffStr + "," + str;
				} else if (flg == 2 && tempFlg) {
					diffStr = diffStr + "," + str;
				}
			}
		}

		return diffStr.length() >= 2 ? diffStr.substring(1, diffStr.length()) : diffStr;
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(Double.toString(value));
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static String getStringFormatedDateYY(Date date) {
		try {
			if (date == null)
				return null;
			else
				return formatDateYY.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static String getActiveInactive(String status) {
		try {
			if (status == null) {
				return null;
			} else {
				if (status.equals("1")) {

					return "ACTIVE";
				} else {

					return "INACTIVE";
				}

			}
		} catch (Exception e) {
			return null;
		}
	}

	public static String getYesNo(String yesno) {
		try {
			if (yesno == null) {
				return null;
			} else {
				if (yesno.equals("1")) {

					return "Yes";
				} else {

					return "No";
				}

			}
		} catch (Exception e) {
			return null;
		}
	}

	public static Date convertStringToDateYY(String str) {
		if (str == null) {
			return null;
		}
		try {
			return formatDateYY.parse(str);
		} catch (ParseException e) {
			return null;
		}

	}

	public static String getStringFromDateYY(Date date) {
		try {
			if (date == null) {
				return null;
			} else {
				return dtFrmtTime.format(date);
			}
		} catch (Exception e) {
			return null;
		}
	}

	private static Date getStringToTime(String time) {
		try {
			if (!isEmpty(time)) {
				return timeFormat.parse(time);
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public static double getDoubleValue(Double value) {
		return value == null ? 0d : value.doubleValue();
	}

	public static int getIntegerValue(Integer value) {
		return value == null ? 0 : value.intValue();
	}

	public static int getIntegerQuantityValue(Integer value) {
		return value == null ? 1 : value.intValue() == 0 ? 1 : value.intValue();
	}

	public static boolean isQlIdValidation(String qlid) {
		if (isEmpty(qlid)) {
			return false;
		}
		return qlid.matches("^[a-zA-Z]{2}[0-9]{6}$");
	}

	public static String getUpperFunctionColumn(String str) {
		return str != null ? "upper(" + str + ")" : "1";
	}

	public static String getFullName(String firstName, String lastName) {
		return firstName + " " + lastName != null ? lastName : "";
	}

}
