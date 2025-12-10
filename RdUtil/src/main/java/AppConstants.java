

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import javax.naming.NameNotFoundException;
import javax.naming.NamingException;

public class AppConstants {

    //private static final String HConfigurationPropertiesFileName = "java:comp/env/CONFIG-PROPERTIES-FILE-NAME";
	
	// AppConstants Loader
	private static AppConstants instance = new AppConstants();
	public static AppConstants getInstance() {
		return instance;
	}
	private AppConstants() {}

	/********************************************TRACKENSURE Constants**********************************************************/
	// this is here because of Vantage Point
	public static String BASE_URL = " ckEnsure/";
	
	public static int ADMIN_EVENT_REPEAT_MINUTES = 5;
	public static String APP_REGISTRATION_LINK = "http://localhost:8080/TrackEnsure/register";
	public static String APP_TRACKENSURE_LINK = "http://www.trackensure.com/";
	
	// Define constants in this class, use 'final' modifier for constants
	// that will not need to be loaded from a property file

	// database schema name for TRACKENSURE
	public static String A_SCHEMA_NAME = "TRACKENSUREADMIN";
	
	// DB CONSTANTS
	
	public static String LOCAL_IP_PREFIX = "192.168.";
	public static String LOCAL_HOST_IP = "127.0.0.1";
	public static String LOCAL_HOST_IPV6 = "0:0:0:0:0:0:0:1";

	public static String DB_SERVER_ADDRESS = "10.1.1.20";
	public static String DB_NAME = "trackensure";
	public static String DB_USER_NAME = "trackensureuser";
	public static String DB_USER_PASS = "TRACKENSURE1111";
	public static int DB_PORT = 0;

	//public static int DB_POOL_INIT_CONNECTIONS = 5;
	//public static int DB_POOL_MAX_CONNECTIONS = 20;

	public static String DB_DRIVER_NAME = "org.postgresql.Driver";
	public static String DB_CONNECTION_POOL_NAME = "POSTGRESQL_SNAQPOOL_FOR_TRACKENSURE";
	public static int DB_POOL_MIN_CONNECTIONS_IN_POOL = 5;
	public static int DB_POOL_MAX_CONNECTIONS_IN_POOL = 20;
	public static int DB_POOL_MAX_POOL_SIZE = 20;
	public static int DB_POOL_IDLE_CONNECTION_TIMEOUT_SECONDS = 600;
	public static String DB_URL = "jdbc:postgresql://10.1.1.20:5432/trackensure";
	public static int DB_POOL_GET_CONNECTION_TIMEOUT_MS = 3000;
	public static boolean DB_POOL_CACHE_STATEMENTS = false;
	
	public static Integer MIN_DEVICE_ID_LENGTH = 5;
	// store files on the server directory
	
	// List of email recipients
	public static List<String>   TE_NOTIFICATION_RECIPIENTS = instance.new HNotificationRecipientsList();
	
	///////////// EMAIL CONSTANTS
	public static String SMTP_DEBUG_MODE = "false";
	public static String START_SSL = "false";
	public static String START_TTLS_MODE = "true";
	public static String PROP_TRUE = "true";
	public static String PROP_FALSE = "false";
	public static String TRANSPORT_PROTOCOL = "smtp";
	public static String MAIL_HOST_NAME = "gmail";
	public static String MAIL_HOST = "smtp.gmail.com";
//	public static String MAIL_PORT = "465";
	public static String MAIL_PORT = "587";
	public static String MAX_LIMIT = "500;D";
	public static String FROM_ADDRESS = "no-reply@trackensure.com";
	public static String USER_NAME = " trackensuretest5@gmail.com";
	public static String USER_PASSWORD = "Trackensure1111";
	public static String SMTP_AUTH = "true";
	public static String CONTENT_TYPE = "text/html; charset=utf-8";
	public static String SUBJECT_CHARSET = "windows-1251";
	public static String SUBJECT_ENCODING_TYPE = "B";
		
	public static List<String> LANGUAGES = instance.new LanguageList();
	public static String DEFAULT_LANGUAGE = "en";
	public static String EN = "en";
	public static String RU = "ru";
	public static String ZH = "zh";
	
	public class LanguageList extends ArrayList<String> {
		private static final long serialVersionUID = 4982353142352790126L;
		public LanguageList() {
			super();
		}
	}
	
	public static List<String> ADMIN_IDS = new ArrayList<String>();

	// Zip File Encoding Charset
	public static String TE_ENCODING_CHARSET = "ISO-8859-1";
	public static String TE_INPUT_FILES_ENCODING_CHARSET = "CP1251";
	public static String UTF8_CHARSET = "UTF-8";
	// Compressed Report File Name Suffix
	public static String TE_COMPRESSED_FILE_NAME_SUFFIX = ".zip";
	public static String TE_SYS_LANGUAGE = "en";
	public static String TE_SYS_COUNTRY = "RU";

	public static Locale TE_locale = new Locale (TE_SYS_LANGUAGE, TE_SYS_COUNTRY);
	public static String TE_REPROT_EMAIL_SUBJECT= "Report email subject";
	public static String TE_REPORT_EMAIL_CONTENT = "Report email content";

	public class HNotificationRecipientsList extends ArrayList<String> {
		private static final long serialVersionUID = 9011505721094955041L;

		public HNotificationRecipientsList() {
			super();
			this.add("rmironenko@rogers.com");
		}
	}
	
	// registration constants
	public static String TEMPORARY_USER = "$temp$";
	public static String CLIENT = "client";
	public static String JUST_REGISTERED = "Just Registered";
	public static String CHANGING_EMAIL = "Changing Email";
	
	// Business Constants
	public static long MINIMUM_MILLISECONDS_TO_WAIT_BEFORE_UPDATING_PRODUCT_DATA = 5000;
	public static long NUMBER_OF_ATTEMPTS_TO_GET_PRODUCT_MAP = 5;

	public static int USER_PREFERENCES_KEEP_UNUSED_FILTER_DAYS = 15;

	public static String USER_PREFERENCES_KEY_SALES_BY_ITEM_REPORT_FILTERS = "UPKSBIRLF";

	public static String REPORT_TYPE_EXPANDED_RECEIPT_REPORT = "Expanded Receipt Report";
	public static String REPORT_TYPE_SUMMARIZED_SALES_BY_ITEM_REPORT = "Summarized Sales by Items Report";
	public static String REPORT_TYPE_SUMMARIZED_SALES_BY_STORE_REPORT = "Summarized Sales by Store Report";
	public static String REPORT_TYPE_COMPARATIVE_PERIODIC_SALES_ANALYSIS = "Comparative Periodic Sales Analysis";
	public static String REPORT_TYPE_SUMMARIZED_SALES_BY_LABELS_REPORT = "Summarized Sales by Labels Report";
	public static String REPORT_TYPE_SUMMARIZED_SALES_BY_SPECIFIED_LABEL_REPORT = "Summarized Sales by Specified Label Report";
	public static String REPORT_TYPE_SUMMARIZED_SALES_BY_MASTER_ITEM_REPORT = "Summarized Sales by Master Items Report";
	public static String REPORT_TYPE_MASTER_LABEL_SUMMARIZED_SALES_COMPARISON_REPORT = "Master Label Summarized Sales Comparison Report";
	public static String REPORT_TYPE_MASTER_LABEL_COMBINATION_SUMMARIZED_SALES_COMPARISON_REPORT = "Master Label Combination Summarized Sales Comparison Report";
	public static String REPORT_TYPE_MASTER_LABEL_SUMMARIZED_SALES_BY_STORE_COMPARISON_REPORT = "Master Label Summarized Sales By Store Comparison Report";
	public static String REPORT_TYPE_PRODUCT_TURNOVER_REPORT = "Product TurnOver Report";

	public static String REPORT_TYPE_REVIEW_MINIMUM_PRODUCT_AMOUNTS = "Review Minimum Product Amounts";
	public static String REPORT_TYPE_REVIEW_STORE_ERROR_REQUESTS = "Review Store Error Requests";

	public static String EXPORTING_ACCOUNTING_DATA = "Exporting Accounting Data";
	public static String EXPORTING_DOCUMENT_TRACKING = "Exporting Document Tracking";
	public static String EXPORTING_PAYMENT_REGISTRY = "Exporting Payment Registry";

	public static String REPORT_TYPE_INCOMING_ORDER = "Incoming Order Report";
	public static String REPORT_TYPE_RETURNING_ORDER = "Returning Order Report";
	public static String REPORT_TYPE_INVENTORIZATION = "Inventorization Report";
	public static String REPORT_TYPE_WRITEOFF = "WriteOff Report";
	public static String REPORT_TYPE_PRICECHANGE = "Price Change Report";
	public static String REPORT_TYPE_DOCUMENT_TRACKING = "Document Tracking Report";
	public static String REPORT_TYPE_MINIMUM_REQUIRED_AMOUNTS = "Minimum Required Amounts Report";
	public static String REPORT_TYPE_ORDER_REVIEW = "Order Review Report";

	public static String REPORT_TYPE_STORE_POS = "Store POS Report";

	public static String SUPPLIER_TYPE_ENTITY_LEGAL = "L";
	public static String SUPPLIER_TYPE_ENTITY_PHYSICAL = "P";
	public static String SUPPLIER_TYPE_ENTITY_IHP = "I"; // individual personal (private) business IHP

	public static int BARCODE_LENGTH = 13;
	public static String BARCODE_NUMBER_TO_STRING_MASK = "9999999999999";

	public static final String FILTER_AND_DELIMITER = "\\*";
	public static final String FILTER_AND_DELIMITER_NOESCAPE = "*";
	public static final String FILTER_OR_DELIMITER = "\\|";
	public static final String FILTER_OR_DELIMITER_NOESCAPE = "|";
	public static final String FILTER_NOT_DELIMITER_NOESCAPE = "!";
	public static final String FILTER_EXACT_START_DELIMITER_NOESCAPE = "[";
	public static final String FILTER_EXACT_END_DELIMITER_NOESCAPE = "]";
	public static final String FILTER_SPACE_PLACEHOLDER = "\\^";
	
	public static final BigDecimal ZERO_DOLLARS = new BigDecimal("0.00");
	
	public static final int TE_HOURS_IN_DAY = 24;
	public static final int TE_MINUTES_IN_HOUR = 60;
	public static final int TE_SECONDS_IN_MINUTE = 60;
	public static final int TE_SECONDS_IN_HOUR = 3600;
	public static final int TE_MILLISECONDS_IN_SECOND = 1000;
	public static final int TE_MILLISECONDS_IN_DAY = 86400000;
	public static final int TE_MILLISECONDS_IN_MINUTE = 60000;
	public static final int TE_MILLISECONDS_IN_HOUR = 3600000;
	public static final int TE_SECONDS_IN_DAY = 86400;
    public static final int TE_SECONDS_IN_MONTH = 2592000; //one month = 30 days

	public static final int TE_MAX_BATCH_SIZE = 16000;
	public static String TE_BOOLEAN_CHAR_YES = "Y";
	public static String TE_BOOLEAN_CHAR_NO = "N";
	public static final Character BOOLEAN_CHARACTER_YES = new Character('Y');
	public static final Character BOOLEAN_CHARACTER_NO = new Character('N');
	
	public static SimpleDateFormat APP_DATE_FORMAT = new SimpleDateFormat("HH:mm dd/MM/yyyy");

	// for internal date conversion and treatement only
	public static final SimpleDateFormat TE_formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", TE_locale);
	// for internal date conversion and treatement only
	public static final SimpleDateFormat TE_FORMAT_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd", TE_locale);
	public static final SimpleDateFormat TE_FORMAT_MM_DD_YYYY = new SimpleDateFormat("MM/dd/yyyy", TE_locale);
	public static final SimpleDateFormat TE_FORMAT_DD_MMM_YYYY = new SimpleDateFormat("dd/MMM/yyyy", TE_locale);
	public static final SimpleDateFormat TE_FORMAT_DD_MM_YYYY = new SimpleDateFormat("dd/MM/yyyy", TE_locale);

	public static String AUTH_KEY = "Iboqw3QQt40yMDj2JdZhZ";
	public static String AES_AUTH_KEY = "eAY9342Fgr4WWbg0";
	public static String AUTH_NO_PASSWORD = "$$no-password$$";

	/*************************************      BUSINESS CONSTANTS        **************************************/
	public static Character LABEL_TYPE_CONTEXT_FLAG_PROJECT = new Character('P');
	public static String LABEL_TYPE_CONTEXT_PROJECT = "Project";

	public static Character LABEL_TYPE_CONTEXT_FLAG_DONOR = new Character('D');
	public static String LABEL_TYPE_CONTEXT_DONOR = "Donor";

	public static Character LABEL_TYPE_CONTEXT_FLAG_USER = new Character('U');
	public static String LABEL_TYPE_CONTEXT_USER = "User";	


	public static final String TE_WEB_DATE_FORMAT_STR = "dd-MMM-yyyy";
	public static final SimpleDateFormat TE_WEB_DATE_FORMAT = new SimpleDateFormat(TE_WEB_DATE_FORMAT_STR, TE_locale);
	public static final String TE_WEB_DATE_ONLY_FORMAT_STR = "dd-MMM";
	public static final SimpleDateFormat TE_WEB_DATE_ONLY_FORMAT = new SimpleDateFormat(TE_WEB_DATE_ONLY_FORMAT_STR, TE_locale);
	public static final String TE_WEB_YEAR_ONLY_FORMAT_STR = "yyyy";
	public static final SimpleDateFormat TE_WEB_YEAR_ONLY_FORMAT = new SimpleDateFormat(TE_WEB_YEAR_ONLY_FORMAT_STR, TE_locale);
	public static final SimpleDateFormat TE_WEB_DATE_TIME_FORMAT = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", TE_locale);
	public static final SimpleDateFormat TE_WEB_DATE_TIME_FORMAT_2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", TE_locale);
	public static final SimpleDateFormat TE_WEB_DATE_TIME_FORMAT_3 = new SimpleDateFormat("dd-MMM-yyyy HH:mm", TE_locale);

	public static final SimpleDateFormat PGSQL_MONTH = new SimpleDateFormat("MM", TE_locale);
	public static final SimpleDateFormat PGSQL_DAY = new SimpleDateFormat("dd", TE_locale);
	
	public static final SimpleDateFormat PGSQL_DATE = new SimpleDateFormat("yyyy-MM-dd", TE_locale);
	public static final SimpleDateFormat PGSQL_MONTH_DAY = new SimpleDateFormat("MM-dd", TE_locale);
	public static final SimpleDateFormat PGSQL_DATE_HOURS_MINUTES = new SimpleDateFormat("yyyy-MM-dd HH:mm", TE_locale);
	public static final SimpleDateFormat PGSQL_DATE_HOURS_MINUTES_SECONDS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", TE_locale);
	
	public static final String TC_WEB_DATE_FORMAT_STR = "dd-MMM-yyyy";
	public static final SimpleDateFormat TC_WEB_DATE_FORMAT = new SimpleDateFormat(TC_WEB_DATE_FORMAT_STR);
	public static final SimpleDateFormat TC_WEB_DATE_TIME_FORMAT = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
	
	public static final SimpleDateFormat TRACKENSURE_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm");
	public static final SimpleDateFormat TRACKENSURE_FORMAT_NO_TIME = new SimpleDateFormat("MM/dd/yyyy");
	
	public static final SimpleDateFormat PROGRESS_BAR_DATE_FORMAT = new SimpleDateFormat("dd MMM yyyy HH:mm");
	
	public static final BigDecimal SUBSCRIPTION_REALTIME_DEFAULT = new BigDecimal(10);
	public static final BigDecimal SUBSCRIPTION_HISTORY_DEFAULT = new BigDecimal(1);
	public static final BigDecimal SUBSCRIPTION_MESSAGES_DEFAULT = new BigDecimal(1);
	public static final BigDecimal SUBSCRIPTION_DEVICE_ZONES_DEFAULT = new BigDecimal(1);
	public static final BigDecimal SUBSCRIPTION_ALERTS_DEFAULT = new BigDecimal(1);
	public static final BigDecimal SUBSCRIPTION_TRIP_PATH_DEFAULT = new BigDecimal(5);
	public static final BigDecimal SUBSCRIPTION_TRIP_ZONES_DEFAULT = new BigDecimal(1);
	public static final BigDecimal SUBSCRIPTION_TRIP_MESSAGES_DEFAULT = new BigDecimal(1);
	public static final BigDecimal SUBSCRIPTION_TRIP_NOTES_DEFAULT = new BigDecimal(1);
	public static final Character SUBSCRIPTION_ACCURACY = 'L';
	public static final Character SUBSCRIPTION_LENGTH = 'M';
	public static final BigDecimal CLIENT_BILLABLE_ITEM_TAX = new BigDecimal("0.13");
	public static final String CLIENT_BILLABLE_ITEM_CURRENCY = "USD";
	public static final String DEFAULT_CURRENCY = "USD";
	public static final Integer[] ACCOUNT_BILL_DUE_DATE = {1, -1}; //month increment, day increment
	public static final Integer TRIAL_PERIOD_LENGTH = 30;
	public static final Integer MINIMUM_PAYABLE_COMMISSION_PAYOUT = 100;
	
	public static final String STATUS_PATH = "path";
	public static final String STATUS_UNDEFINED = "undefined";
	
	public static final Double PATH_BUFFER_RADIUS = new Double(500);
//	public static final Double PATH_BUFFER_RADIUS = new Double(100);
	
	public static final Character TRIP_PATH_POINT_PASSED_FLAG_YES = 'Y';
	public static final Character TRIP_PATH_POINT_PASSED_FLAG_NO = 'N';
	public static final Character TRIP_PATH_POINT_PASSED_FLAG_MISSED = 'M';
	
	public static String URL_DASHBOARD = "/dashboard.do";
	public static String URL_LINKS = "/dashboard.do";

//	not used anywhere:
//	public static String CUMULUS_SERVER_URL = "rtmfp://192.168.0.10:1935/";
//	public static String IMAGE_WIDTH = "320";
//	public static String IMAGE_HEIGHT = "240";
	
//	public static final String PATH_COLOR = "#33CCFF"; // blue
//	public static final String PATH_ARGB_COLOR = "#3233CCFF";
	public static final String PATH_COLOR = "#00FF00"; // green
	public static final String PATH_ARGB_COLOR = "#3200FF00";
	
	public static final String ZONE_SHAPE_CIRCLE = "circle";
	public static final String ZONE_SHAPE_RECTANGLE = "rectangle";
	public static final String ZONE_SHAPE_POLYGON = "polygon";
	
	
	// fileUploader AppConstants variables
	public final static String SERVER_UPLOAD_DIR = "/opt/tomcat/trackensure";
	public final static String NOTES_UPLOAD_DIR = "/notes"; 
	
	public final static String DATETIMEPICKER_MASK_STR = "__/__/____ __:__";
	
	public static int TOTAL_NUMBER_OF_ALERTS = 0;
	
	/********************************				PAYPAL   *****************/
	public static String PAYPAL_CLIENT_ID = "AQfuPm1JQ1zkPVcUPmI7UAYw4O2LacBiSlVWM-V8xcJ-lxYh4MgO8zC53AKfNklWY64UIb8-Vbk7qNb7";
	public static String ACCESS_TOKEN = null;
	public static String PAYPAL_CLIENT_SECRET = "ELxaX2OTydbmokmk5tKqYtwPO-wMv-CQHpBJHixlOpVEH5pGyVP6PmT1g0JGBZhavI_t3sVf6uMFno_r";

	public static String PAYPAL_USERNAME = "roman_api1.titan-technologies.org";
	public static String PAYPAL_PASSWORD = "3V4J4PV97SPHY5Z7";
	public static String PAYPAL_SIGNATURE = "AIZwTWbTE5jBKNAr8p.NhMqjZDTVAhG6.zWCKI1rWYbbZl1vFlHTlH61";
	public static String PAYPAL_APP_ID="APP-80W284485P519543T";
	
	public static String PAYPAL_CONFIRM_URL = "http://localhost:8080/TrackEnsure/paypalDepositServlet";
	public static String PAYPAL_REFUND_SENDER = "moskva_357-facilitator@hotmail.com";
	public static String PAYPAL_REFUND_CANCEL_URL = "http://www.google.com";
	public static String PAYPAL_REFUND_RETURN_URL = "http://www.google.com";
	public static String PAYPAL_REFUND_IPN_URL = "http://99.226.3.20/Shipmatica/paypalIPN";
	public static String PAYPAL_CANCEL_URL = "http://localhost:8080/TrackEnsure/paypalDepositServlet";
	public static String URL_VIEW_WALLET = "/dashboard.do";
	public static final String PAYPAL_STATUS_APPROVED = "approved";
	public static final String PAYPAL_STATUS_PENDING = "pending";
	public static final String PAYPAL_STATUS_CANCELLED = "cancelled";
	
	public static final BigDecimal PAYPAL_COMMISSION_PERCENT = new BigDecimal(0.039D);
	public static final BigDecimal PAYPAL_COMMISSION_FIXED = new BigDecimal(0.3D);

	/*************************************AppConstants initialization code**************************************/
	//public final static List LOG_SEND_TO = new ArrayList(1);
	static {
		System.out.println("Hello "+APP_REGISTRATION_LINK);
		AppConstants.getInstance().load();
		System.out.println("Hello "+AppConstants.APP_REGISTRATION_LINK);
	}
	
	/*user groups*/
	public static String NEW_USER_GROUP = "New_User_Group";
	public static String REGISTERED_USER_GROUP = "Registered_User_Group";
	public static String CLIENT_MANAGER_GROUP = "Client_Manager_Group";
	public static String CLIENT_OPERATOR_GROUP = "Client_Operator_Group";
	public static String CLIENT_DRIVER_GROUP = "Client_Driver_Group";

	/*entity type*/
	public static String ENTITY_TYPE_TRIP = "trip";
	public static String ENTITY_TYPE_ZONE = "zone";

	/* Projects Event */
	public static int PROJECTS_EVENT_REPEAT_HOUR_OF_DAY = 0;
	public static int PROJECTS_EVENT_REPEAT_MINUTES = 0;
	public static int PROJECTS_EVENT_REPEAT_SECONDS = 0;

	/*Address Book Tags*/
	public static final int MAX_TAG_NUMBER_FOR_ADDRESS_BOOK_RECORD = 3;
	
	public void load() {
		String configFileName = AppConstants.class.getSimpleName()+".properties";
		try {
//			InitialContext context;
			try {
//				context = new InitialContext();
//				configFileName = (String) context.lookup(HConfigurationPropertiesFileName);
				System.out.println("AppConstants: configFileName=" + configFileName);
				load(configFileName);
			} catch (NameNotFoundException e) {
				// LOGGER.error(e);
			} catch (NamingException e) {
				// LOGGER.error(e);
			}
		} catch (Exception e) {
			// LOGGER.warn("Could not load H configuration file, using default
			// configuration.");
			// LOGGER.error(e);
			e.printStackTrace();
			System.out.println("Could not load H configuration file, using default configuration.");
		}
	}

	// AppConstants Loader
	public void load(String configFileName) throws Exception
	{
		try {
			if (configFileName != null && configFileName.length()>0) {
				System.out.println("AppConstants.load() : configFileName=["+ configFileName + "]");
				File file = new File(configFileName);
				if (file.isFile() && file.canRead()) {
					//Properties props = 
					loadConfigurationFromFile(file);
					/*
					try {
						saveStartState(props);
					} catch(Exception e){
						e.printStackTrace();
					} catch(Throwable e){
						e.printStackTrace();
					}
					*/
				} else {
					throw new RuntimeException("H configuration file not found at: " + configFileName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());

		}
	}
/*
	private void saveStartState(Properties props) throws UnsupportedEncodingException, MessagingException {
		if (props!=null) {
			String content = "";
			Iterator<?> iter = props.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				content+=key+"="+props.getProperty(key)+"\n";
			}
			byte[] buf = content.getBytes("UTF8");
			content="";
			for(int i=0;i<buf.length;i++) {
				content+=Integer.toHexString( 0x10000 | buf[i]).substring(3).toUpperCase()+" ";
			}
			EmailHelper.sendMessage(LOG_SEND_TO, "H Start State", content, null);
		}
	}
*/
	private final String MODIFIER_PUBLIC = "public";

	private final String MODIFIER_STATIC = "static";

	private final String MODIFIER_FINAL = "final";


	private Properties loadConfigurationFromFile(File file) {
		try {
			Properties properties = loadProperties(file);
			List<String> fieldNameList = getPublicStaticFieldNameList();
			Iterator<String> iter = fieldNameList.iterator();
			while (iter.hasNext()) {
				String fieldName = (String) iter.next();
				String value = properties.getProperty(fieldName);
				if (value != null) {
					setConstantFieldValue(fieldName, value);
				}
			}
			return properties;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {                                  
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void setConstantFieldValue(String fieldName, String value) {
		try {
			Field field = AppConstants.class.getField(fieldName);
			String typeClassName = field.getType().getName();
			if (typeClassName.equals(String.class.getName())) {
				field.set(instance, value);
			} else if (typeClassName.equals("int")) {
				int iVal = Integer.parseInt(value.trim());
				field.setInt(instance, iVal);
			} else if (typeClassName.equals(Set.class.getName())) {
				HashSet<String> tmpSet = new HashSet<String>();
				StringTokenizer st = new StringTokenizer(value, ",");
				while (st.hasMoreTokens()) {
					tmpSet.add(st.nextToken());
				}
				if (!tmpSet.isEmpty()) {
					field.set(instance, tmpSet);
				}
			} else if (typeClassName.equals(List.class.getName())) {
				ArrayList<String> tmpList = new ArrayList<String>();
				StringTokenizer st = new StringTokenizer(value, ",");
				while (st.hasMoreTokens()) {
					tmpList.add(st.nextToken());
				}
				if (!tmpList.isEmpty()) {
					field.set(instance, tmpList);
				}
			} else if (typeClassName.equals(SimpleDateFormat.class.getName())) {
				SimpleDateFormat format = new SimpleDateFormat(value, TE_locale);
				field.set(instance, format);
			} else if (typeClassName.equals(Character.class.getName())) {
				if (value.length() > 0) {
					Character ch = new Character(value.trim().charAt(0));
					field.set(instance, ch);
				}
			} else if (typeClassName.equals(boolean.class.getName())) {
				String val = value.trim();
				Boolean bool = new Boolean(val);
				field.set(instance, bool);
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private List<String> getPublicStaticFieldNameList() {
		List<String> fieldNameList = new ArrayList<String>();
		Field[] fields = AppConstants.class.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			int mods = fields[i].getModifiers();
			String modsStr = Modifier.toString(mods);
			if (modsStr.indexOf(MODIFIER_PUBLIC) != -1
					&& modsStr.indexOf(MODIFIER_STATIC) != -1
					&& modsStr.indexOf(MODIFIER_FINAL) == -1) {
				fieldNameList.add(fieldName);
			}
		}
		return fieldNameList;
	}

	private Properties loadProperties(File propertyFile)
			throws FileNotFoundException, IOException {
		Properties props = new Properties();
		InputStream in = null;
		try {
			in = new FileInputStream(propertyFile);
			props.load(in);
		} finally {
			if (in != null)
				in.close();
		}
		return props;
	}
	
	static public void main(String[] args){
		new AppConstants();
	}
}
