package cal;

import gui.JFrame1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.Exception;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
//import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Calculation implements Runnable {

	private static String subcall;
	private static String parameter;

	@Override
	public void run() {
		// System.out.println(subcall + "/" + parameter);
		if (subcall.equals("readcsvfile")) {
			readcsvfile(parameter);
		}
		if (subcall.equals("readxmlfile")) {
			readxmlfile(parameter);
		}
		if (subcall.equals("readscriptfile")) {
			readscriptfile(parameter);
		}
		if (subcall.equals("writecsvfile")) {
			writecsvfile();
		}
		if (subcall.equals("writexmlfile")) {
			writexmlfile();
		}
		if (subcall.equals("writehelptext")) {
			writehelptext();
		}
		if (subcall.equals("writescriptfile")) {
			writescriptfile();
		}
		if (subcall.equals("clearall")) {
			clearall();
		}
	}

	public static void main(String input1, String input2) {
		(new Thread(new Calculation())).start();
		subcall = input1;
		parameter = input2;
	}

	// global variable
	static char separator_character = 59; // ,=44/;=59/:=58
	static int csv_input_lines = 0; // Csv input length
	static int csv_input_columns = 0; // Csv input width
	static int csv_output_lines = 0; // Csv output length
	static int csv_output_columns = 0; // Csv output width
	static int mc = 0; // migrate counter
	static int max_width = 100; // maximum width
	static int max_high = 10000;// maximum lines
	static int max_commands = 100;// maximum commands
	static int found_commands; // how many commands found
	static int max_stat_width = 3;
	static int max_stat_high = max_high;
	static String multicolumn[][] = new String[max_width][max_high];
	static String migrate[][] = new String[max_width][max_high];
	static String dupelist[] = new String[max_high];
	// ------------------------------------------------
	public static String statlist[][] = new String[max_stat_width][max_high];
	public static String chartlist1[][] = new String[max_stat_width][max_stat_high];
	public static String chartlist2[][] = new String[max_stat_width][max_stat_high];
	public static String chartlist3[][] = new String[max_stat_width][max_stat_high];
	public static String chartlist_header1 = "Chart";
	public static String chartlist_header2 = "Chart";
	public static String chartlist_header3 = "Chart";
	public static int statlistcounter = 1;
	public static int chartlistcounter1 = 1;
	public static int chartlistcounter2 = 1;
	public static int chartlistcounter3 = 1;
	// ------------------------------------------------
	static String outputpath = "";
	static final String DEAFAULT_OUTPUPATH = "Exchange Output";
	static final String SCRIPTFILEPATH = "Script-ExChange.txt";
	static final String LOGFILEPATH = "LogFile-ExChangeCsv.log";
	static final String DATE = "20.02.2016";// last Modify
	static long sort_count;
	static int loglevel = 0; // 0 nothing,1 log,2 log+errors
	static final int TIME_VALUE_ms = 50;// delay time in working process ,100ms
	static int outputheaderline = 1;// default=1 Output-file use a Header-line
	// ------------------------------------------------
	// global constants for Messages
	public static final String url1 = "https://en.wikipedia.org/wiki/Comma-separated_values";
	public static final String url2 = "https://en.wikipedia.org/wiki/XML";
	static final String ERROR01 = "Error:";
	static final String ERROR02 = "Fail:";
	static final String MESSAGE01 = "Start Separator Autoscan";
	static final String MESSAGE02 = "> Read Separator Done:";
	static final String MESSAGE03 = "> First CSV-Line:";
	static final String MESSAGE04 = "Open CSV File:";
	static final String MESSAGE05 = "> CSV Format OK: Count of Header Columns = Data Columns";
	static final String MESSAGE06 = "> Work With:";
	static final String MESSAGE07 = "Read File Done";
	static final String MESSAGE08 = "Created File In:";
	static final String MESSAGE09 = "Columns";
	static final String MESSAGE10 = "Lines, Inclusive Header";
	static final String MESSAGE11 = "Fields";
	static final String MESSAGE12 = "> Start Write File";
	static final String MESSAGE13 = "Open Script File:";
	static final String MESSAGE14 = "> Found:";
	static final String MESSAGE15 = "Script Commands";
	static final String MESSAGE16 = "Open XML File:";
	static final String MESSAGE17 = "> To Many Commands In Script File";
	static final String MESSAGE18 = "> Compare Instring Result Pos: Line";
	static final String MESSAGE19 = "> Compare Instring Result Neg: Line";
	static final String MESSAGE20 = "> Mig In:";
	static final String MESSAGE21 = "> Mig In: .....................................................";
	static final String MESSAGE22 = "> Mig Out:";
	static final String MESSAGE23 = "> Mig Out: ....................................................";
	static final String MESSAGE24 = "> Bubblesort:";
	static final String MESSAGE25 = "Sortings";
	static final String MESSAGE26 = "> Quicksort:";
	static final String MESSAGE27 = "Done";
	static final String MESSAGE28 = "> Press Button:";
	static final String MESSAGE29 = "Writefile";
	static final String MESSAGE30 = "> Numerical Gaps:";
	static final String MESSAGE31 = "Max =";
	static final String MESSAGE32 = "Min =";
	static final String MESSAGE33 = "> Gaps:";
	static final String MESSAGE34 = "Start Read File";
	static final String MESSAGE35 = "> Found Dupes:";
	static final String MESSAGE36 = "> No Dupes";
	// static final String MESSAGE37 = "";
	// static final String MESSAGE38 = "";
	static final String MESSAGE39 = "> Stats Sort:";
	static final String MESSAGE40 = "> Stats Result:";
	static final String MESSAGE41 = "x";
	static final String MESSAGE42 = "=";
	static final String MESSAGE43 = "%";
	static final String MESSAGE44 = "............................................";
	static final String MESSAGE45 = "> AutoExit:";
	// static final String MESSAGE46 = "";
	static final String MESSAGE47 = "Execute Script: OK";
	static final String MESSAGE48 = "Created File In:";
	static final String MESSAGE49 = "Application Init: Done";
	// static final String MESSAGE50 = "";
	// static final String MESSAGE51 = "";
	static final String MESSAGE52 = "Quicksort Numbers:";
	static final String MESSAGE53 = "Quicksort Strings:";
	// static final String MESSAGE54 = "";
	static final String MESSAGE55 = "Compare Result Pos: Line";
	static final String MESSAGE56 = "Compare Result Neg: Line";
	static final String MESSAGE57 = ":EQUAL";
	static final String MESSAGE58 = ":ODDS";
	static final String MESSAGE59 = "********************************************";
	static final String MESSAGE60 = "****************Read File*******************";
	static final String MESSAGE61 = "****************Write File******************";
	static final String MESSAGE62 = "****************Script File*****************";
	static final String MESSAGE63 = "****************Start Log File**************";
	static final String MESSAGE64 = "Data Min";
	static final String MESSAGE65 = "Data Max";
	static final String MESSAGE66 = "ASCII: ,=44 / ;=59 / :=58";
	static final String MESSAGE67 = "(";
	static final String MESSAGE68 = ")";
	static final String MESSAGE69 = ") >";
	static final String MESSAGE70 = ") !=";
	static final String MESSAGE71 = "Read Script Command:";
	static final String MESSAGE72 = "Start Execute:";
	static final String MESSAGE73 = "Header";
	static final String MESSAGE99 = " ";
	static final char BACKSLASH = 92;
	static final char QUOTES = 34;
	// XML-Statements
	static final String XMLHEADER = "<?xml version=" + QUOTES + "1.0" + QUOTES
			+ " encoding=" + QUOTES + "UTF-8" + QUOTES + "?>";
	private static String xmlrootelement = "objects";
	private static String xmlelement = "object";
	private static String xmlfield = "attribute";
	private static String xselement = "";
	// -----------------------------------------------
	static String TEXTARRAY[] = {
			// 2 backslashes are not allowed \\
			("//*******************************************************************"),
			("//*******************************************************************"),
			("//*********                   EXCHANGE                      *********"),
			("//*********        _______     ______     _        _        *********"),
			("//*********       |  _____|   /  ____|   | |      | |       *********"),
			("//*********       | |         " + BACKSLASH + " " + BACKSLASH
					+ "__      " + BACKSLASH + " " + BACKSLASH + "      / /       *********"),
			("//*********       | |          " + BACKSLASH + "__ " + BACKSLASH
					+ "      " + BACKSLASH + " " + BACKSLASH + "    / /        *********"),
			("//*********       | |             " + BACKSLASH + " " + BACKSLASH
					+ "      " + BACKSLASH + " " + BACKSLASH + "  / /         *********"),
			("//*********       | |_____    _____" + BACKSLASH + " "
					+ BACKSLASH + "      " + BACKSLASH + " " + BACKSLASH + "/ /          *********"),
			("//*********       |_______|  |_______/       " + BACKSLASH + "__/           *********"),
			("//*********                                                 *********"),
			("//*********                      XML                        *********"),
			("//*******************************************************************"),
			("//*******************************************************************"),
			("//" + url1),
			("//Java Eclipse Version: 3.8.1" + " / Jigloo Version: 4.6.6"),
			("//http://www.eclipse.org/platform"),
			("//http://www.cloudgarden.com/jigloo/"),
			("//Version: " + JFrame1.TITEL + JFrame1.SUBVERSION
					+ ", last modify: " + DATE),
			("//V2.5-R-Stable New Feature:"),
			("//XML Parser / XML Writer"),
			("//Compare Column / Compare Instring Column / Log File"),
			("//Find Move / Find Clear / Not Find Clear"),
			("//Change: Set Maximum CSV Lines,100000,"),
			("//Change: Set Maximum CSV Columns,1000,"),
			("//Default: CSV-Columns-Max=100, CSV-Lines-Max=10000, Script-Commands-Max=100"),
			("//------------------------------------------------------"),
			("//TRANSFORM-COMMANDS:"),
			("//Output Header Line: 0, Hide Header Line in Output-CSV, Set default Fields in XML / default=1"),
			("//Set Maximum CSV Lines: Integer, maximum of the OS"),
			("//Set Maximum CSV Columns: Integer, maximum of the OS"),
			("//Filename: Output Name , Parameter: Date, Date/Time , Parameter: Front,Back"),
			("//Separator: Output Separator Char:" + MESSAGE99 + MESSAGE66),
			("//Columns: Columns of Output File"),
			("//Copy: Copy Column 2 > Column 3"),
			("//Set Header: Set Header of Column 0 to Test"),
			("//Set Column: Set Column 0 without Header to bla"),
			("//Set Block: Set Column 0 without Header from 2000 ++ increment"),
			("//Find Replace: Search in Column 0 for bla and replace with blupp"),
			("//Find Move: Search in Column 0 for up and replace in Column 1 with ap"),
			("//Find Clear: Search in Column 0 for up,if exist then clear CSV-Line"),
			("//Not Find Clear: Search in Column 0 for up, if not exist then clear CSV-Line"),
			("//Instring Find Replace: Search in Column 0 Instring for up and replace with ap"),
			("//Instring Find Move: Search in Column 0 Instring for up and replace in Column 1 with ap"),
			("//Instring Find Clear: Search in Column 0 Instring for up,if exist then clear CSV-Line"),
			("//Instring Not Find Clear: Search in Column 0 Instring for up,if not exist then clear CSV-Line"),
			("//String Combine Front: new String = bla + old String in Column 0"),
			("//String Combine Back: new String = old String + blupp in Column 0"),
			("//Convert To Lower Case:  in Column 0"),
			("//Convert To Upper Case: in Column 0"),
			("//Trim: Delete strange Chars in Front and Back of String in Column 0"),
			("//Extract Chars: in Column 5 from Position 0 to Position 3+1 = Extract 4 Chars"),
			("//Migrate In: Load data from Column 1 into Migrationarray Column 0"),
			("//Migrate Out:Compare value of Migrationarray Column 0 with value of Column 1"),
			("//Migrate Out:If both values the same then copy from Migrationarray Column 1 to Column 2"),
			("//Bubblesort: Sorting of Column 0, Numbers/Strings, up/down"),
			("//Quicksort: Sorting of Column 0, Numbers/Strings, up/down"),
			("//Writefile: 1, Press Button Writefile"),
			("//Autoexit: 1, Application exit"),
			("//Log File: Output Logfile > LogLevel 0-3, 0=off, 1=standard, 2=error"),
			("//------------------------------------------------------"),
			("//XML-COMMANDS:"),
			("//Set XML Rootelement : Objects = <Objects> & </Objects> "),
			("//Set XML Element: Object = <Object> & </Object>"),
			("//Set XS Element: xs = <Object+xs>"),
			("//------------------------------------------------------"),
			("//CHECK-COMMANDS:"),
			("//Find Numerical Gaps: 0, Find numerical Gaps between min and max value in Column 0 > Terminalresults"),
			("//Compare Column: Compare String in Column 0 with Column 1 pos/neg > Terminalresults"),
			("//Compare Instring Column: Compare Instring a String  in Column 0 with Column 1 pos/neg > Terminalresults"),
			("//Dupe Check: 0, Find multiple exists Values in Column 0 > Terminalresults"),
			("//Stats: 0, Percent Output of Column 0, Chart1/2/3: Grafical Output, max. 3 Charts"),
			("//------------------------------------------------------"),
			("//SCRIPT-COMMANDS:"),// --------------------------------
			("Output Header Line,1,"), // ----------------------------
			("Set Maximum CSV Lines,10000,"), // ---------------------
			("Set Maximum CSV Columns,100,"),// ----------------------
			("Set XML Rootelement,Objects,"), // ------------------------
			("Set XML Element,Object,"),// ---------------------------
			("Set XS Element,xs,"),// --------------------------------
			("Filename,Output Filename,Date,Front,"),// Standard,Date-
			("Separator,59,"), // ------------------------------------
			("Columns,30,"), // --------------------------------------
			("Copy Columns,2,3,"), // --------------------------------
			("Set Header,0,Test,"), // -------------------------------
			("Set Columns,0,bla,"), // -------------------------------
			("Set Block,0,2000,"), // --------------------------------
			("Find Replace,0,bla,blupp,"), // ------------------------
			("Find Move,0,up,1,ap,"), // -----------------------------
			("Find Clear,0,up,"), // ---------------------------------
			("Not Find Clear,0,up,"), // -----------------------------
			("Instring Find Replace,0,up,ap,"), // -------------------
			("Instring Find Move,0,up,1,ap,"), // --------------------
			("Instring Find Clear,0,up,"), // ------------------------
			("Instring Not Find Clear,0,up,"), // --------------------
			("String Combine Front,0,bla,"), // ----------------------
			("String Combine Back,0,blupp,"), // ---------------------
			("Convert To Lower Case,0,"), // -------------------------
			("Convert To Upper Case,0,"), // -------------------------
			("Trim,0,"), // ------------------------------------------
			("Extract Chars,5,0,3,"), // -----------------------------
			("Migrate In,1,0,"), // ----------------------------------
			("Migrate Out,0,1,1,2,"), // -----------------------------
			("Bubblesort,0,Numbers,up,"), // -------------------------
			("Quicksort,0,Strings,up,"), // --------------------------
			("Writefile,1,"), // -------------------------------------
			("Autoexit,1,"), // --------------------------------------
			("Log File,1,"), // --------------------------------------
			("Find Numerical Gaps,0,"), // ---------------------------
			("Compare Column,0,1,pos,"), // --------------------------
			("Compare Instring Column,0,1,pos,"), // -----------------
			("Dupe Check,0,"), // ------------------------------------
			("Stats,0,"), // -----------------------------------------
			("Stats,0,Chart1,"), // ----------------------------------
			("Stats,1,Chart2,"), // ----------------------------------
			("Stats,2,Chart3,"), // ----------------------------------
	// ---------------------------------------------------------------
	};

	// *****************************************************************************************
	public static void readcsvfile(String path1) {
		// Init table
		JFrame1.jTextPane1.setText("");
		clearall();
		String row = "";
		String now2 = new SimpleDateFormat("dd.MM.yyy").format(new Date());
		if (loglevel >= 1) {
			write_log(MESSAGE59);
			write_log(MESSAGE60);
			write_log(MESSAGE59);
			write_log(now2);
		} // standard = 1
			// Autoscan separator
		JFrame1.jList1(MESSAGE04 + MESSAGE99 + path1);
		if (loglevel >= 1) {
			write_log(MESSAGE04 + MESSAGE99 + path1);
		} // standard = 1
		String c = ";";
		String line = "";
		try {
			if (path1 != null) {
				JFrame1.jList1(MESSAGE34);
				if (loglevel >= 1) {
					write_log(MESSAGE34);
				} // standard = 1
				JFrame1.jList1(MESSAGE01);
				if (loglevel >= 1) {
					write_log(MESSAGE01);
				} // standard = 1

				FileReader fr1 = new FileReader(path1);
				BufferedReader br1 = new BufferedReader(fr1);
				if ((line = br1.readLine()) != null) {
					if (line.indexOf(":") != -1) {
						c = ":";
					}
					if (line.indexOf(";") != -1) {
						c = ";";
					}
					if (line.indexOf(",") != -1) {
						c = ",";
					}
					JFrame1.jList1(MESSAGE02 + MESSAGE99 + MESSAGE67 + c
							+ MESSAGE68 + MESSAGE99 + MESSAGE66);
					if (loglevel >= 1) {
						write_log(MESSAGE02 + MESSAGE99 + MESSAGE67 + c
								+ MESSAGE68 + MESSAGE99 + MESSAGE66);
					} // standard = 1

					JFrame1.jList1(MESSAGE03 + MESSAGE99 + line);
					if (loglevel >= 1) {
						write_log(MESSAGE03 + MESSAGE99 + line);
					} // standard = 1
				}
				fr1.close();
			}
		} catch (Exception e) {

			JFrame1.jTextPane1.setText(ERROR01 + MESSAGE99 + e);
			e.printStackTrace();
			if (loglevel >= 2) {
				write_log(ERROR01 + MESSAGE99 + e);
			} // debug = 2
		}

		// ----------------------------------

		try {
			if (path1 != null) {
				int x = 0;// counter
				boolean isValidFirstLine = true;// first line
				int jheader = 0;
				int jmin = 0;
				int jmax = 0;
				int jdata = 0;
				FileReader fr2 = new FileReader(path1);
				BufferedReader br2 = new BufferedReader(fr2);
				csv_input_lines = 0;// line-position=0(first-line)
				while ((row = br2.readLine()) != null) {
					String splitrow[] = row.split(c);
					if (isValidFirstLine == true) {
						jheader = splitrow.length; // Header width ermitteln
													// nur in der 1. Zeile
						csv_input_columns = jheader;
						jmin = jheader;
						jmax = jheader;
						isValidFirstLine = false;
					}
					if (isValidFirstLine == false) {
						jdata = splitrow.length; // Data width ermitteln ab der
													// 2. Zeile
						if (jdata < csv_input_columns) {
							jmin = jdata;
							csv_input_columns = jdata;
							csv_output_columns = csv_input_columns;
						}
						if (jdata > jmax) {
							jmax = jdata;
						}
					}
					for (x = 0; x < csv_input_columns; x++) {
						multicolumn[x][csv_input_lines] = splitrow[x];
						// System.out.println("csv_input_line=" +
						// csv_input_lines + " " +
						// multicolumn[x][csv_input_lines]);
					}
					// JFrame1.jList1(row);
					// JFrame1.jList1(String.valueOf(j));
					csv_input_lines++;
				}
				fr2.close();
				//
				if (jheader == jmin && jmin == jmax) {
					JFrame1.jList1(MESSAGE05);//
					if (loglevel >= 1) {
						write_log(MESSAGE05);
					} // standard = 1
				}
				if (jmin < jheader) {
					JFrame1.jList1(ERROR01 + MESSAGE99 + MESSAGE99 + MESSAGE99
							+ MESSAGE73 + MESSAGE99 + MESSAGE67 + jheader
							+ MESSAGE69 + MESSAGE99 + MESSAGE64 + MESSAGE99
							+ MESSAGE67 + jmin + MESSAGE68 + MESSAGE99
							+ MESSAGE09);//
					if (loglevel >= 1) {
						write_log(ERROR01 + MESSAGE99 + MESSAGE99 + MESSAGE73
								+ MESSAGE99 + MESSAGE67 + jheader + MESSAGE69
								+ MESSAGE99 + MESSAGE64 + MESSAGE99 + MESSAGE67
								+ jmin + MESSAGE68 + MESSAGE99 + MESSAGE09);
					} // standard = 1
				}
				if (jmax > jheader) {
					JFrame1.jList1(ERROR01 + MESSAGE99 + MESSAGE99 + MESSAGE65
							+ MESSAGE99 + MESSAGE67 + jmax + MESSAGE69
							+ MESSAGE99 + MESSAGE73 + MESSAGE99 + MESSAGE67
							+ jheader + MESSAGE68 + MESSAGE99 + MESSAGE09);//
					if (loglevel >= 1) {
						write_log(ERROR01 + MESSAGE99 + MESSAGE99 + MESSAGE65
								+ MESSAGE99 + MESSAGE67 + jmax + MESSAGE69
								+ MESSAGE99 + MESSAGE73 + MESSAGE99 + MESSAGE67
								+ jheader + MESSAGE68 + MESSAGE99 + MESSAGE09);
					} // standard = 1
				}
				if (jmin != jmax) {
					JFrame1.jList1(ERROR01 + MESSAGE99 + MESSAGE99 + MESSAGE64
							+ MESSAGE99 + MESSAGE67 + jmin + MESSAGE70
							+ MESSAGE99 + MESSAGE65 + MESSAGE99 + MESSAGE67
							+ jmax + MESSAGE68 + MESSAGE99 + MESSAGE09);//
					if (loglevel >= 1) {
						write_log(ERROR01 + MESSAGE99 + MESSAGE99 + MESSAGE64
								+ MESSAGE99 + MESSAGE67 + jmin + MESSAGE70
								+ MESSAGE99 + MESSAGE65 + MESSAGE99 + MESSAGE67
								+ jmax + MESSAGE68 + MESSAGE99 + MESSAGE09);
					} // standard = 1
				}
				JFrame1.jList1(MESSAGE06 + MESSAGE99 + (csv_input_columns)
						+ MESSAGE99 + MESSAGE09);// column
				if (loglevel >= 1) {
					write_log(MESSAGE06 + MESSAGE99 + (csv_input_columns)
							+ MESSAGE99 + MESSAGE09);
				} // standard = 1
				JFrame1.jList1(MESSAGE06 + MESSAGE99 + (csv_input_lines)
						+ MESSAGE99 + MESSAGE10); // Zeile
				if (loglevel >= 1) {
					write_log(MESSAGE06 + MESSAGE99 + (csv_input_lines)
							+ MESSAGE99 + MESSAGE10);
				} // standard = 1
				JFrame1.jList1(MESSAGE06 + MESSAGE99 + csv_input_columns
						+ MESSAGE99 + MESSAGE41 + MESSAGE99 + csv_input_lines
						+ MESSAGE99 + MESSAGE42 + MESSAGE99
						+ (csv_input_lines * csv_input_columns) + MESSAGE99
						+ MESSAGE11);
				if (loglevel >= 1) {
					write_log(MESSAGE06 + MESSAGE99 + csv_input_columns
							+ MESSAGE99 + MESSAGE41 + MESSAGE99
							+ csv_input_lines + MESSAGE99 + MESSAGE42
							+ MESSAGE99 + (csv_input_lines * csv_input_columns)
							+ MESSAGE99 + MESSAGE11);
				} // standard = 1
				JFrame1.jList1(MESSAGE07);
				if (loglevel >= 1) {
					write_log(MESSAGE07);
				} // standard = 1
				csv_output_columns = csv_input_columns;
				csv_output_lines = csv_input_lines;
				outputpath = DEAFAULT_OUTPUPATH;
				// System.out.println("Open File:" + " i=" + i + " j=" +j);
			}

		} catch (Exception e) {
			JFrame1.jTextPane1.setText(ERROR01 + MESSAGE99 + e);
			e.printStackTrace();
			if (loglevel >= 2) {
				write_log(ERROR01 + MESSAGE99 + e);
			} // error = 2
		}
	}

	// *****************************************************************************************
	public static void readxmlfile(String path1) {
		// Init table
		JFrame1.jTextPane1.setText("");
		clearall();
		String attributenodearray[] = new String[max_width];
		String now2 = new SimpleDateFormat("dd.MM.yyy").format(new Date());
		int errorobject = 0;
		int errorattributes = 0;
		String errortag = "";
		if (loglevel >= 1) {
			write_log(MESSAGE59);
			write_log(MESSAGE60);
			write_log(MESSAGE59);
			write_log(now2);
		} // standard = 1
			// Autoscan separator
		JFrame1.jList1(MESSAGE16 + MESSAGE99 + path1);
		if (loglevel >= 1) {
			write_log(MESSAGE16 + MESSAGE99 + path1);
		} // standard = 1

		try {
			if (path1 != null) {
				JFrame1.jList1(MESSAGE34);
				if (loglevel >= 1) {
					write_log(MESSAGE34);
				}
				// ---------------------------------
				// XML
				// http://www.cs.hs-rm.de/~knauf/SWTProjekt2009/xml/
				// http://stackoverflow.com/questions/3273682/get-the-name-of-all-attributes-in-a-xml-file
				// http://examples.javacodegeeks.com/core-java/xml/dom/list-all-attributes-of-dom-element/
				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder documentBuilder = documentBuilderFactory
						.newDocumentBuilder();
				File xmlfile = new File(path1);
				Document xmldoc = documentBuilder.parse(xmlfile);

				// read root
				// element------------------------------------------------
				NodeList readlistRoot = xmldoc.getElementsByTagName("*");// Objects
				Element readrootElement = (Element) readlistRoot.item(0);
				JFrame1.jList1("Read Root Element:"
						+ readrootElement.getNodeName());
				xmlrootelement = readrootElement.getNodeName();

				// read with rootelement
				NodeList listRoot = xmldoc.getElementsByTagName(readrootElement
						.getNodeName());// Objects
				Element rootElement = (Element) listRoot.item(0);

				// read child
				// element:----------------------------------------------
				NodeList readlistElement = rootElement
						.getElementsByTagName("*");// Object
				Element readchildElement = (Element) readlistElement.item(0);
				String readTagFirstElement = readchildElement.getTagName();
				JFrame1.jList1("Read Child Node:" + readTagFirstElement);
				xmlelement = readTagFirstElement;

				// read with child element
				NodeList listElement = rootElement
						.getElementsByTagName(readTagFirstElement);// Object
				// Element childElement = (Element) listElement.item(0);
				int objects = (listElement.getLength());
				JFrame1.jList1("Count Child Nodes:" + Integer.toString(objects));
				// -----------------------------------------------------------------
				// <read all attributes nodes to Array>
				int countattributenodes = 0;
				for (int intIndex = 0; intIndex < 1; intIndex++) {
					Element childAttributes = (Element) listElement
							.item(intIndex);
					for (int attributeIndex = 0; attributeIndex < max_width; attributeIndex++) {// max_width
						// List of Elements with Attributes
						if (null != childAttributes.getElementsByTagName("*")
								.item(attributeIndex)) {
							attributenodearray[attributeIndex] = (childAttributes
									.getElementsByTagName("*")
									.item(attributeIndex).getNodeName()
									.toString());
							multicolumn[attributeIndex][0] = attributenodearray[attributeIndex];// set
																								// table
																								// line
																								// 0
							JFrame1.jList1("Read Attribute Node:"
									+ attributenodearray[attributeIndex]);
							countattributenodes++;
						}
					}
				}
				errorattributes = countattributenodes;
				JFrame1.jList1("Count Attributes Nodes:"
						+ Integer.toString(countattributenodes));// nodes per
																	// object
				// </read all attributes nodes to Array>
				// -----------------------------------------------------------------
				String attributearraynode = "";
				String attributexmlnode = "";
				String attributenodevalue = "";
				int line = 1;
				for (int Index = 0; Index < listElement.getLength(); Index++) {
					Element childAttributes = (Element) listElement.item(Index);
					// String AttributeContent =
					// childAttributes.getTextContent();
					// JFrame1.jList1(" Counts Attributes:" + AttributeContent);
					for (int attributeIndex1 = 0; attributeIndex1 < countattributenodes; attributeIndex1++) {
						// problem to read this empty tag <msisdnList/>
						attributearraynode = attributenodearray[attributeIndex1];
						errortag = attributearraynode;
						// JFrame1.jList1("1.Attribute Array Node:"+
						// attributearraynode +"-index:" +attributeIndex1);
						// problem
						for (int attributeIndex2 = 0; attributeIndex2 < countattributenodes; attributeIndex2++) {
							if (null != (childAttributes
									.getElementsByTagName("*")
									.item(attributeIndex2))) {
								attributexmlnode = (childAttributes
										.getElementsByTagName("*")
										.item(attributeIndex2).getNodeName()
										.toString());
								if (attributearraynode == attributexmlnode) {
									// JFrame1.jList1("2.Attribute Xml Node:"+
									// attributearraynode +"-index:"
									// +attributeIndex2);
									if (null != (childAttributes
											.getElementsByTagName("*")
											.item(attributeIndex2)
											.getChildNodes().item(0))) {
										attributenodevalue = "";
										attributenodevalue = (childAttributes
												.getElementsByTagName("*")
												.item(attributeIndex2)
												.getChildNodes().item(0)
												.getNodeValue());
										// multicolumn[width][high];
										multicolumn[attributeIndex1][line] = attributenodevalue;
										// JFrame1.jList1("3.Read Attribute Xml Value:"+
										// String sComment =
										// childAttributes.getTextContent();
										// JFrame1.jList1("4.Read Attribute Xml Value:"+
										// sComment);
									}
								}
							}
						}
					}
					errorobject = line;
					line++;
				}
				csv_input_columns = countattributenodes;
				csv_output_columns = csv_input_columns;
				csv_input_lines = line;
				csv_output_lines = csv_input_lines;
				JFrame1.jList1("Count All Attribute Values:"
						+ Integer.toString(countattributenodes * (line - 1)));
				JFrame1.jList1("Convert To Table Columns:	" + csv_input_columns);
				JFrame1.jList1("Convert To Table Lines:	" + csv_input_lines);
				// ------------------------------------------------------------------------
				JFrame1.jList1(MESSAGE07);
				if (loglevel >= 1) {
					write_log(MESSAGE07);
				} // standard = 1
				outputpath = DEAFAULT_OUTPUPATH;
				// ---------------------------------
			}
		} catch (Exception e) {
			JFrame1.jList1(ERROR01 + MESSAGE99 + "at xml Object > "
					+ errorobject);
			JFrame1.jList1(ERROR01 + MESSAGE99 + "at xml Tag > " + errortag);
			JFrame1.jList1(ERROR01 + MESSAGE99 + "at xml Line > "
					+ ((errorobject * (errorattributes + 2)) + 4));
			JFrame1.jTextPane1.setText(ERROR01 + MESSAGE99 + e);
			e.printStackTrace();
			if (loglevel >= 2) {
				write_log(ERROR01 + MESSAGE99 + e);
				write_log(ERROR01 + MESSAGE99 + "at xml Object > "
						+ errorobject);
				write_log(ERROR01 + MESSAGE99 + "at xml Tag > " + errortag);
				write_log(ERROR01 + MESSAGE99 + "at xml Line > "
						+ ((errorobject * (errorattributes + 2)) + 4));
			} // debug = 2
		}
	}

	// *****************************************************************************************
	public static void writecsvfile() {

		JFrame1.jTextPane1.setText("");
		// char d = 59;
		csv_output_lines = csv_input_lines;
		int outputcounter = 0;
		// z = 30; // width of csv file
		// ---------------------------------------------
		String filename = "." + File.separator + outputpath + ".csv";
		// String directory = "./" + File.separator;
		// *************************************************************************
		String now2 = new SimpleDateFormat("dd.MM.yyy").format(new Date());
		if (loglevel >= 1) {
			write_log(MESSAGE59);
			write_log(MESSAGE61);
			write_log(MESSAGE59);
			write_log(now2);
		} // standard = 1
			// -----------------------------------------------------------------------------
		try {

			File file1 = new File(filename);
			if (!file1.exists()) {
				file1.createNewFile();
			} else {
				file1.delete();
				file1.createNewFile();
			}
			if (file1.exists()) {
				BufferedWriter bw;
				bw = new BufferedWriter(new FileWriter(file1, true));// True=Append
				JFrame1.jList1(MESSAGE12);
				if (loglevel >= 1) {
					write_log(MESSAGE12);
				} // standard = 1
				int x = 0;
				int p = 0;
				while (x < csv_output_lines) {
					String line = "";
					for (p = 0; p < csv_output_columns; p++) {
						if (multicolumn[p][x] == null) {
							multicolumn[p][x] = "";// init_with""
						}
						if (p == 0) {
							line = (line + multicolumn[p][x]);
						}
						if (p > 0) {
							line = (line + separator_character + multicolumn[p][x]);
							// System.out.println(line);
						}
					}
					if (x == 0 && outputheaderline == 1) {// UseHeaderLine=0>noPrint-Line0
						bw.write(line);
						// System.out.println("x=" + x + " p=" + p + " :" +
						// line);
					}
					if (x > 0) {
						bw.write(line);
						// System.out.println("x=" + x + " p=" + p + " :" +
						// line);
					}
					bw.newLine();
					// System.out.println(line);
					if (outputcounter < 10) {
						if (x == 0 && outputheaderline == 1) {// UseHeaderLine=0>noPrint-Line0
							JFrame1.jList1(line);
							if (loglevel >= 1) {
								write_log(line);
							} // standard = 1
						}
						if (x > 0) {
							JFrame1.jList1(line);
							if (loglevel >= 1) {
								write_log(line);
							} // standard = 1
						}
					}
					outputcounter++;
					x++;
				}
				bw.flush();
				bw.close();
				// System.out.println(MESSAGE08 + MESSAGE99 + filename);
				JFrame1.jList1(MESSAGE44);
				JFrame1.jList1(MESSAGE08 + MESSAGE99 + filename);
				if (loglevel >= 1) {
					write_log(MESSAGE44);
					write_log(MESSAGE08 + MESSAGE99 + filename);
				} // standard = 1

			}
		} catch (Exception e) {
			JFrame1.jTextPane1.setText(ERROR01 + MESSAGE99 + e);
			e.printStackTrace();
			if (loglevel >= 2) {
				write_log(ERROR01 + MESSAGE99 + e);
			} // debug = 2
		}
	}

	// *****************************************************************************************
	public static void writexmlfile() {

		String ROOTELEMENTOPEN = "<" + xmlrootelement + ">";
		String ROOTELEMENTCLOSE = "</" + xmlrootelement + ">";
		// String ROOTELEMENTEMPTY = "<" + xmlrootelement + "/>";
		String ELEMENTOPEN = "  " + "<" + xmlelement + xselement + ">";
		String ELEMENTCLOSE = "  " + "</" + xmlelement + ">";
		// String ELEMENTEMPTY = "  " + "<" + xmlelement + "/>";
		String ATTRIBUTEOPEN = "    " + "<" + xmlfield + ">";
		String ATTRIBUTECLOSE = "</" + xmlfield + ">";
		String ATTRIBUTEEMPTY = "<" + xmlfield + "/>";

		JFrame1.jTextPane1.setText("");
		// char d = 59;
		csv_output_lines = csv_input_lines;
		int outputcounter = 0;
		// ---------------------------------------------
		String filename = "." + File.separator + outputpath + ".xml";
		// String directory = "./" + File.separator;
		// *************************************************************************
		String now2 = new SimpleDateFormat("dd.MM.yyy").format(new Date());
		if (loglevel >= 1) {
			write_log(MESSAGE59);
			write_log(MESSAGE61);
			write_log(MESSAGE59);
			write_log(now2);
		} // standard = 1
			// -----------------------------------------------------------------------------
		try {

			File file1 = new File(filename);
			if (!file1.exists()) {
				file1.createNewFile();
			} else {
				file1.delete();
				file1.createNewFile();
			}
			if (file1.exists()) {
				BufferedWriter bw;
				bw = new BufferedWriter(new FileWriter(file1, true));// True=Append
				JFrame1.jList1(MESSAGE12);
				if (loglevel >= 1) {
					write_log(MESSAGE12);
				} // standard = 1
				int x = 1; // start at datablock
				int p = 0;
				String line = "";
				bw.write(XMLHEADER); // xml header
				bw.newLine();
				// ----------------------
				JFrame1.jList1(XMLHEADER);
				if (loglevel >= 1) {
					write_log(XMLHEADER);
				}
				// ----------------------
				bw.write(ROOTELEMENTOPEN); // open tag,
				bw.newLine();
				// ----------------------
				JFrame1.jList1(ROOTELEMENTOPEN);
				if (loglevel >= 1) {
					write_log(ROOTELEMENTOPEN);
				}
				// ----------------------
				while (x < csv_output_lines) {
					line = "";
					bw.write(ELEMENTOPEN);
					bw.newLine();
					// ----------------------
					if (outputcounter < 10) {
						JFrame1.jList1(ELEMENTOPEN);
						if (loglevel >= 1) {
							write_log(ELEMENTOPEN);
						} // standard = 1
					}
					// ----------------------
					for (p = 0; p < csv_output_columns; p++) {
						if (multicolumn[p][x] == null) {
							multicolumn[p][x] = "";// init_with""
						}
						if (outputheaderline == 1) {
							if (multicolumn[p][x] != "") {
								line = ("    " + "<" + multicolumn[p][0] + ">"
										+ multicolumn[p][x] + "</"
										+ multicolumn[p][0] + ">");
							} else {
								line = ("    " + "<" + multicolumn[p][0] + "/>"); // empty
																					// tag
							}
						}
						if (outputheaderline == 0) {
							if (multicolumn[p][x] != "") {
								line = (ATTRIBUTEOPEN + multicolumn[p][x] + ATTRIBUTECLOSE);
							} else {
								line = (ATTRIBUTEEMPTY); // empty tag
							}
						}
						bw.write(line);
						bw.newLine();
						// ----------------------
						if (outputcounter < 10) {
							JFrame1.jList1(line);
							if (loglevel >= 1) {
								write_log(line);
							} // standard = 1
						}
						// ----------------------
					}
					bw.write(ELEMENTCLOSE);
					bw.newLine();
					// ----------------------
					if (outputcounter < 10) {
						JFrame1.jList1(ELEMENTCLOSE);
						if (loglevel >= 1) {
							write_log(ELEMENTCLOSE);
						}
					}
					// ----------------------
					outputcounter++;
					x++;
				}
				bw.write(ROOTELEMENTCLOSE); // open tag
				bw.flush();
				bw.close();
			}
			JFrame1.jList1(MESSAGE44);
			JFrame1.jList1(MESSAGE08 + MESSAGE99 + filename);
			if (loglevel >= 1) {
				write_log(MESSAGE44);
				write_log(MESSAGE08 + MESSAGE99 + filename);
			} // standard = 1
		} catch (Exception e) {
			JFrame1.jTextPane1.setText(ERROR01 + MESSAGE99 + e);
			e.printStackTrace();
			if (loglevel >= 2) {
				write_log(ERROR01 + MESSAGE99 + e);
			} // debug = 2
		}
	}

	// *****************************************************************************************
	public static void readscriptfile(String path2) {
		String commands[] = new String[max_commands];
		String row = "";
		// -----------------------------------------------------------------------------
		String now2 = new SimpleDateFormat("dd.MM.yyy").format(new Date());
		if (loglevel >= 1) {
			write_log(MESSAGE59);
			write_log(MESSAGE62);
			write_log(MESSAGE59);
			write_log(now2);
		} // standard = 1
			// -----------------------------------------------------------------------------
		JFrame1.jList1(MESSAGE13 + MESSAGE99 + path2);
		if (loglevel >= 1) {
			write_log(MESSAGE13 + MESSAGE99 + path2);
		} // standard = 1
		JFrame1.jTextPane1.setText("");
		try {
			if (path2 != null) {
				JFrame1.jList1(MESSAGE34);
				if (loglevel >= 1) {
					write_log(MESSAGE34);
				} // standard = 1
				int x = 0;// counter
				int m = 0; // counter
				FileReader fr1 = new FileReader(path2);
				BufferedReader br1 = new BufferedReader(fr1);
				while ((row = br1.readLine()) != null) {
					if (row.length() >= 2) {
						if (row.indexOf("//") == -1) {
							if ((row.indexOf("Filename,") != -1)
									|| (row.indexOf("Output Header Line,") != -1)
									|| (row.indexOf("Separator,") != -1)
									|| (row.indexOf("Spalten,") != -1)// german,useforoldscriptfiles
									|| (row.indexOf("Columns,") != -1)// english
									|| (row.indexOf("Copy Spalte,") != -1)// german,useforoldscriptfiles
									|| (row.indexOf("Copy Column,") != -1)// english
									|| (row.indexOf("Set Header,") != -1)
									|| (row.indexOf("Set Spalte,") != -1)// german,useforoldscriptfiles
									|| (row.indexOf("Set Column,") != -1)// english
									|| (row.indexOf("Find Replace,") != -1)
									|| (row.indexOf("Find Move,") != -1)
									|| (row.indexOf("Find Clear,") != -1)
									|| (row.indexOf("Not Find Clear,") != -1)
									|| (row.indexOf("Instring Find Replace,") != -1)
									|| (row.indexOf("Instring Find Move,") != -1)
									|| (row.indexOf("Instring Find Clear,") != -1)
									|| (row.indexOf("Instring Not Find Clear,") != -1)
									|| (row.indexOf("String Combine Front,") != -1)
									|| (row.indexOf("String Combine Back,") != -1)
									|| (row.indexOf("Convert To Lower Case,") != -1)
									|| (row.indexOf("Convert To Upper Case,") != -1)
									|| (row.indexOf("Compare Column,") != -1)
									|| (row.indexOf("Compare Instring Column,") != -1)
									|| (row.indexOf("Trim,") != -1)
									|| (row.indexOf("Extract Chars,") != -1)
									|| (row.indexOf("Migrate In,") != -1)
									|| (row.indexOf("Migrate Out,") != -1)
									|| (row.indexOf("Bubblesort,") != -1)
									|| (row.indexOf("Quicksort,") != -1)
									|| (row.indexOf("Writefile,") != -1)
									|| (row.indexOf("Find Numerical Gaps,") != -1)
									|| (row.indexOf("Dupe Check,") != -1)
									|| (row.indexOf("Stats,") != -1)
									|| (row.indexOf("Autoexit,") != -1)
									|| (row.indexOf("Log File,") != -1)
									|| (row.indexOf("Set Block,") != -1)
									|| (row.indexOf("Set XML Rootelement,") != -1)
									|| (row.indexOf("Set XML Element,") != -1)
									|| (row.indexOf("Set XS Element,") != -1)
									|| (row.indexOf("Set Maximum CSV Lines,") != -1)
									|| (row.indexOf("Set Maximum CSV Columns,") != -1)) {
								// --------------------------------------------
								if (row.length() >= 9) {
									if (row.substring(0, 9).equals("Filename,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 19) {
									if (row.substring(0, 19).equals(
											"Output Header Line,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 10) {
									if (row.substring(0, 10).equals(
											"Separator,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 8) {
									if ((row.substring(0, 8).equals("Spalten,"))
											|| (row.substring(0, 8)
													.equals("Columns,"))) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 12) {
									if ((row.substring(0, 12)
											.equals("Copy Spalte,"))
											|| (row.substring(0, 12)
													.equals("Copy Column,"))) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 11) {
									if (row.substring(0, 11).equals(
											"Set Header,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 11) {
									if ((row.substring(0, 11)
											.equals("Set Spalte,"))
											|| (row.substring(0, 11)
													.equals("Set Column,"))) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// ---------------------------------------------
								if (row.length() >= 10) {
									if (row.substring(0, 10).equals(
											"Set Block,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 22) {
									if (row.substring(0, 22).equals(
											"Set Maximum CSV Lines,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// ---------------------------------------------
								if (row.length() >= 24) {
									if (row.substring(0, 24).equals(
											"Set Maximum CSV Columns,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// ---------------------------------------------
								if (row.length() >= 20) {
									if (row.substring(0, 20).equals(
											"Set XML Rootelement,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// ---------------------------------------------
								if (row.length() >= 16) {
									if (row.substring(0, 16).equals(
											"Set XML Element,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 15) {
									if (row.substring(0, 15).equals(
											"Set XS Element,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 13) {
									if (row.substring(0, 13).equals(
											"Find Replace,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// ---------------------------------------------
								if (row.length() >= 10) {
									if (row.substring(0, 10).equals(
											"Find Move,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// ---------------------------------------------
								if (row.length() >= 11) {
									if (row.substring(0, 11).equals(
											"Find Clear,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// ---------------------------------------------
								if (row.length() >= 15) {
									if (row.substring(0, 15).equals(
											"Not Find Clear,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 22) {
									if (row.substring(0, 22).equals(
											"Instring Find Replace,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 19) {
									if (row.substring(0, 19).equals(
											"Instring Find Move,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 20) {
									if (row.substring(0, 20).equals(
											"Instring Find Clear,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 24) {
									if (row.substring(0, 24).equals(
											"Instring Not Find Clear,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 21) {
									if (row.substring(0, 21).equals(
											"String Combine Front,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 20) {
									if (row.substring(0, 20).equals(
											"String Combine Back,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 22) {
									if (row.substring(0, 22).equals(
											"Convert To Lower Case,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 22) {
									if (row.substring(0, 22).equals(
											"Convert To Upper Case,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 15) {
									if (row.substring(0, 15).equals(
											"Compare Column,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// -------------------------------------------
								if (row.length() >= 24) {
									if (row.substring(0, 24).equals(
											"Compare Instring Column,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 5) {
									if (row.substring(0, 5).equals("Trim,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 14) {
									if (row.substring(0, 14).equals(
											"Extract Chars,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 11) {
									if (row.substring(0, 11).equals(
											"Migrate In,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 12) {
									if (row.substring(0, 12).equals(
											"Migrate Out,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// ----------------------------------------
								if (row.length() >= 11) {
									if (row.substring(0, 11).equals(
											"Bubblesort,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 10) {
									if (row.substring(0, 10).equals(
											"Quicksort,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 10) {
									if (row.substring(0, 10).equals(
											"Writefile,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// ----------------------------------------------
								if (row.length() >= 20) {
									if (row.substring(0, 20).equals(
											"Find Numerical Gaps,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// ----------------------------------------------
								if (row.length() >= 11) {
									if (row.substring(0, 11).equals(
											"Dupe Check,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// ----------------------------------------------
								if (row.length() >= 6) {
									if (row.substring(0, 6).equals("Stats,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// ----------------------------------------------
								if (row.length() >= 9) {
									if (row.substring(0, 9).equals("Autoexit,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// ----------------------------------------------
								if (row.length() >= 9) {
									if (row.substring(0, 9).equals("Log File,")) {
										commands[x] = (row);
										JFrame1.jList1(MESSAGE71 + MESSAGE99
												+ commands[x]);
										if (loglevel >= 1) {
											write_log(MESSAGE71 + MESSAGE99
													+ commands[x]);
										} // standard = 1
										x++;
									}
								}
								// ----------------------------------------------
							}
						}
					}
				}
				fr1.close();

				JFrame1.jList1(MESSAGE14 + MESSAGE99 + x + MESSAGE99
						+ MESSAGE15);
				if (loglevel >= 1) {
					write_log(MESSAGE14 + MESSAGE99 + x + MESSAGE99 + MESSAGE15);
				} // standard = 1
				found_commands = x;
				JFrame1.jProgressBar1.setMaximum(x - 1);
				if (x <= max_commands) {
					JFrame1.jList1(MESSAGE07);
					if (loglevel >= 1) {
						write_log(MESSAGE07);
					} // standard = 1
				}
				if (x > max_commands) {
					JFrame1.jList1(ERROR02 + MESSAGE99 + MESSAGE99 + MESSAGE17);
					if (loglevel >= 1) {
						write_log(ERROR02 + MESSAGE99 + MESSAGE99 + MESSAGE17);
					} // standard = 1
				}

				for (x = 0; x < found_commands; x++) {
					// Progressbar-Update
					JFrame1.jProgressBar1.setValue(x);
					Thread.sleep(TIME_VALUE_ms);
					// --------------
					if (commands[x] != null) {
						if (!commands[x].equals("")) {
							// System.out.println(commands[x]);
							// ######################################################
							int jj = 0;
							String splitcommands[] = commands[x].split(",");
							jj = splitcommands.length; // width

							String command = "";
							String attribute1 = "";
							String attribute2 = "";
							String attribute3 = "";
							String attribute4 = "";

							for (m = 0; m < jj; m++) {
								if (m == 0) {
									command = splitcommands[m];
									// System.out.println(command);
								}
								if (m == 1) {
									attribute1 = splitcommands[m];
									// System.out.println(attribute1);
								}
								if (m == 2) {
									attribute2 = splitcommands[m];
									// System.out.println(attribute2);
								}
								if (m == 3) {
									attribute3 = splitcommands[m];
									// System.out.println(attribute3);
								}
								if (m == 4) {
									attribute4 = splitcommands[m];
									// System.out.println(attribute4);
								}
							}
							// -----------------------------------
							if (command.equals("Filename")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Filename");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Filename");
								} // standard = 1
									// --------------------------------------------------
								String a;
								String b;
								String c;
								a = (attribute1);// Filename
								b = (attribute2);// Date Parameter "Date,
													// Date/Time
								c = (attribute3);// Format Parameter
													// "Front,Back"

								if (!a.equals("")) {
									outputpath = a;
									if (b.equals("Date") && c.equals("Front")) {
										String now = new SimpleDateFormat(
												"dd.MM.yyy").format(new Date());
										outputpath = now + "_" + outputpath;
									}
									if (b.equals("Date") && c.equals("Back")) {
										String now = new SimpleDateFormat(
												"dd.MM.yyy").format(new Date());
										outputpath = outputpath + "_" + now;
									}
									if (b.equals("Date/Time")
											&& c.equals("Front")) {
										String now = new SimpleDateFormat(
												"dd.MM.yyy hh:mm:ss")
												.format(new Date());
										outputpath = now + "_" + outputpath;
									}
									if (b.equals("Date/Time")
											&& c.equals("Back")) {
										String now = new SimpleDateFormat(
												"dd.MM.yyy hh:mm:ss")
												.format(new Date());
										outputpath = outputpath + "_" + now;
									}
								}
								// outputpath = outputpath + ".csv";,
								// System.out.println(outputpath);
							}
							// -----------------------------------
							if (command.equals("Output Header Line")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Output Header Line");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Output Header Line");
								} // standard = 1
									// --------------------------------------------------
								int a;
								a = Integer.parseInt(attribute1);
								if (a == 0 || a == 1) {
									outputheaderline = a;
									// System.out.println("outputheaderline=" +
									// a);
								}
							}
							// -----------------------------------
							if (command.equals("Separator")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Separator");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Separator");
								} // standard = 1
									// --------------------------------------------------
								separator_character = (char) Integer
										.parseInt(attribute1);
								// System.out.println(d);
							}
							// -----------------------------------
							if (command.equals("Spalten")
									|| command.equals("Columns")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Columns");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99 + "Columns");
								} // standard = 1
									// --------------------------------------------------
								csv_output_columns = Integer
										.parseInt(attribute1);
								// System.out.println(z);
							}
							// -----------------------------------
							if (command.equals("Copy Spalte")
									|| command.equals("Copy Column")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Copy Column");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Copy Column");
								} // standard = 1
									// --------------------------------------------------
								int v;
								int a;
								int b;
								a = Integer.parseInt(attribute1);
								b = Integer.parseInt(attribute2);
								for (v = 0; v < csv_input_lines; v++) {
									multicolumn[b][v] = multicolumn[a][v];
								}
								// System.out.println(a + " " + b);
							}
							// -----------------------------------
							if (command.equals("Set Header")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Set Header");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Set Header");
								} // standard = 1
									// --------------------------------------------------
								int a;
								String b;
								a = Integer.parseInt(attribute1);
								b = attribute2;
								if (a == 0) {
									b = "//" + b;
								}
								multicolumn[a][0] = b;
								// System.out.println(a + " " + b);
							}
							// -----------------------------------
							if (command.equals("Set Spalte")
									|| command.equals("Set Column")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Set Column");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Set Column");
								} // standard = 1
									// --------------------------------------------------
								int v;
								int a;
								String b;
								a = Integer.parseInt(attribute1);
								b = attribute2;
								for (v = 1; v < csv_input_lines; v++) {
									multicolumn[a][v] = b;
								}
								// System.out.println(a + " " + b);
							}
							// -----------------------------------
							if (command.equals("Set Block")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Set Block");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Set Block");
								} // standard = 1
									// --------------------------------------------------
								int v;
								int a;
								int b;
								a = Integer.parseInt(attribute1);
								b = Integer.parseInt(attribute2);
								for (v = 1; v < csv_input_lines; v++) {
									multicolumn[a][v] = Integer.toString(b);
									b++;
								}
								// System.out.println(a + " " + b);
							}
							// -----------------------------------
							if (command.equals("Set Maximum CSV Lines")) {
								// --------------------------------------------------
								int a;
								a = Integer.parseInt(attribute1);
								max_high = a;

								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Set Maximum CSV Lines" + MESSAGE99
										+ a);
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Set Maximum CSV Lines"
											+ MESSAGE99 + a);
								} // standard = 1
									// System.out.println(a);
							}
							// -----------------------------------
							if (command.equals("Set Maximum CSV Columns")) {
								// --------------------------------------------------
								int a;
								a = Integer.parseInt(attribute1);
								max_width = a;

								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Set Maximum CSV Columns" + MESSAGE99
										+ a);
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Set Maximum CSV Columns"
											+ MESSAGE99 + a);
								} // standard = 1
									// System.out.println(a);
							}
							// -----------------------------------
							if (command.equals("Set XML Rootelement")) {
								// --------------------------------------------------
								String a;
								a = attribute1;
								xmlrootelement = a;

								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Set XML Rootelement" + MESSAGE99 + a);
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Set XML Rootelement" + MESSAGE99
											+ a);
								} // standard = 1
									// System.out.println(a);
							}
							// -----------------------------------
							if (command.equals("Set XML Element")) {
								// --------------------------------------------------
								String a;
								a = attribute1;
								xmlelement = a;

								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Set XML Element" + MESSAGE99 + a);
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Set XML Element" + MESSAGE99 + a);
								} // standard = 1
									// System.out.println(a);
							}
							// -----------------------------------
							if (command.equals("Set XS Element")) {
								// --------------------------------------------------
								String a;
								a = attribute1;
								xselement = a;

								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Set XS Element" + MESSAGE99 + a);
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Set XS Element" + MESSAGE99 + a);
								} // standard = 1
									// System.out.println(a);
							}
							// -----------------------------------
							if (command.equals("Find Replace")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Find Replace");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Find Replace");
								} // standard = 1
									// --------------------------------------------------
								int v;
								int a;
								String b;
								String c;
								a = Integer.parseInt(attribute1);
								b = attribute2;
								c = attribute3;
								for (v = 1; v < csv_input_lines; v++) {
									if (multicolumn[a][v].equals(b)) {
										multicolumn[a][v] = (c);
									}
								}
								// System.out.println(a + " " + b + " " + c);
							}
							// -----------------------------------
							if (command.equals("Find Move")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Find Move");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Find Move");
								} // standard = 1
									// --------------------------------------------------
								int v;
								int a;
								String b;
								int c;
								String d;
								a = Integer.parseInt(attribute1);
								b = attribute2;
								c = Integer.parseInt(attribute3);
								d = attribute4;
								for (v = 1; v < csv_input_lines; v++) {
									if (multicolumn[a][v].equals(b)) {
										multicolumn[c][v] = (d);
									}
								}
								// System.out.println(a + " " + b + " " + c);
							}
							// -----------------------------------
							if (command.equals("Find Clear")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Find Clear");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Find Clear");
								} // standard = 1
									// --------------------------------------------------
								int v;
								int a;
								String b;
								a = Integer.parseInt(attribute1);// 0
								b = attribute2; // Compare
								v = 1;
								// System.out.println("Start Clear ," + " i="
								// + (i) + " j=" + (j));
								do {
									// System.out.println("Searchstring=" + b);
									if (multicolumn[a][v].equals(b)) {
										// clear Line: change Position
										for (int h = v; h < csv_input_lines; h++) { // i
																					// =
											// Csvinputlength
											for (int g = 0; g < csv_input_columns; g++) {
												multicolumn[g][h] = multicolumn[g][h + 1];
											}
										}
										csv_input_lines--;
									} else {
										v++;
									}
									// }
								} while (v < csv_input_lines); // result while
																// true > do
								// System.out.println(a + " " + b + " " + i);
							}
							// -----------------------------------
							if (command.equals("Not Find Clear")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Not Find Clear");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Not Find Clear");
								} // standard = 1
									// --------------------------------------------------
								int v;
								int a;
								String b;
								a = Integer.parseInt(attribute1);// 0
								b = attribute2; // Compare
								v = 1;
								// System.out.println("Start Not Clear ," +
								// " i="
								// + (i) + " j=" + (j));
								do {
									// System.out.println("Searchstring=" + b);
									if (!multicolumn[a][v].equals(b)) {
										// clear Line: change Position
										for (int h = v; h < csv_input_lines; h++) { // i
																					// =
																					// Csv
											// input
											// length
											for (int g = 0; g < csv_input_columns; g++) {
												multicolumn[g][h] = multicolumn[g][h + 1];
											}
										}
										csv_input_lines--;
									} else {
										v++;
									}
									// }
								} while (v < csv_input_lines); // result while
																// ein true > do
								// System.out.println(a + " " + b + " " + i);
							}
							// -----------------------------------
							if (command.equals("Instring Find Replace")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Instring Find Replace");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Instring Find Replace");
								} // standard = 1
									// --------------------------------------------------
								int v;
								int a;
								String b;
								String c;
								a = Integer.parseInt(attribute1);
								b = attribute2;
								c = attribute3;
								for (v = 1; v < csv_input_lines; v++) { // i =
																		// Csv
																		// input
									// length
									if (multicolumn[a][v].indexOf(b) != -1) {
										// --------------------------------
										String splitmulticolumn[] = multicolumn[a][v]
												.split(b);
										jj = splitmulticolumn.length; // width
										// System.out.println("0."+jj);
										String split1 = "";
										String split2 = "";

										if (jj == 0) {
											multicolumn[a][v] = c;
											// System.out.println("3."+multicolumn[a][v]);
										}
										if (jj > 0) {
											for (m = 0; m < jj; m++) {
												// System.out.println("m"+m);
												if (m == 0) {
													split1 = splitmulticolumn[m];
													multicolumn[a][v] = (split1 + c);
													// System.out.println("1."+split1);
													// System.out.println("3."+multicolumn[a][v]);
												}
												if (m == 1) {
													split2 = splitmulticolumn[m];
													multicolumn[a][v] = (multicolumn[a][v] + split2);
													// System.out.println("2."+split2);
													// System.out.println("3."+multicolumn[a][v]);
												}
											}
										}
									}
								}
								// System.out.println(a + " " + b + " " + c);
							}
							// -----------------------------------
							if (command.equals("Instring Find Move")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Instring Find Move");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Instring Find Move");
								} // standard = 1
									// --------------------------------------------------
								int v;
								int a;
								String b;
								int c;
								String d;
								a = Integer.parseInt(attribute1);// 0
								b = attribute2; // up
								c = Integer.parseInt(attribute3);// 1
								d = attribute4; // ap
								for (v = 1; v < csv_input_lines; v++) {// i =
																		// Csv
																		// input
																		// length
									if (multicolumn[a][v].indexOf(b) != -1) {
										// --------------------------------
										String splitmulticolumn[] = multicolumn[a][v]
												.split(b);
										jj = 0;
										jj = splitmulticolumn.length; // width
										// System.out.println("row=" + v +
										// " value=" + multicolumn[a][v]+ " jj="
										// +jj);
										if (jj > 0) {
											multicolumn[c][v] = d;
											// System.out.println("JJ=" + jj +
											// " value= " + multicolumn[c][v]);
										}
									}
								}
								// System.out.println(a + " " + b + " " + c +
								// " "
								// + d);
							}
							// -----------------------------------
							if (command.equals("Instring Find Clear")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Instring Find Clear");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Instring Find Clear");
								} // standard = 1
									// --------------------------------------------------
								int v;
								int a;
								String b;
								a = Integer.parseInt(attribute1);// 0
								b = attribute2; // Compare
								v = 1;
								// System.out.println("Start Clear ," + " i="
								// + (i) + " j=" + (j));
								do {
									// System.out.println("Searchstring=" + b);
									if (multicolumn[a][v].contains(b)) {
										// Lösche Line: change aktuelle
										// Position durch hoch kopieren
										for (int h = v; h < csv_input_lines; h++) { // i
																					// =
																					// Csv
											// input
											// length
											for (int g = 0; g < csv_input_columns; g++) {
												multicolumn[g][h] = multicolumn[g][h + 1];
											}
										}
										csv_input_lines--;
									} else {
										v++;
									}
									// }
								} while (v < csv_input_lines); // result while
																// ein true > do
								// System.out.println(a + " " + b + " " + i);
							}
							// -----------------------------------
							if (command.equals("Instring Not Find Clear")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Instring Not Find Clear");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Instring Not Find Clear");
								} // standard = 1
									// --------------------------------------------------
								int v;
								int a;
								String b;
								a = Integer.parseInt(attribute1);// 0
								b = attribute2; // Compare
								v = 1;
								// System.out.println("Start Not Clear ," +
								// " i="
								// + (i) + " j=" + (j));
								do {
									// System.out.println("Searchstring=" + b);
									if (!multicolumn[a][v].contains(b)) {
										// Lösche Line: change aktuelle
										// Position durch hoch kopieren
										for (int h = v; h < csv_input_lines; h++) { // i
																					// =
																					// Csv
											// input
											// length
											for (int g = 0; g < csv_input_columns; g++) {
												multicolumn[g][h] = multicolumn[g][h + 1];
											}
										}
										csv_input_lines--;
									} else {
										v++;
									}
									// }
								} while (v < csv_input_lines); // result while
																// ein true > do
								// System.out.println(a + " " + b + " " + i);
							}
							// -----------------------------------
							if (command.equals("String Combine Front")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "String Combine Front");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "String Combine Front");
								} // standard = 1
									// --------------------------------------------------
								int v;
								int a;
								String b = "";
								a = Integer.parseInt(attribute1);
								b = (attribute2);
								for (v = 1; v < csv_input_lines; v++) {
									multicolumn[a][v] = (b + multicolumn[a][v]);
								}
								// System.out.println(a + " " + b);
							}
							// -----------------------------------
							if (command.equals("String Combine Back")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "String Combine Back");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "String Combine Back");
								} // standard = 1
									// --------------------------------------------------
								int v;
								int a;
								String b = "";
								a = Integer.parseInt(attribute1);
								b = (attribute2);
								for (v = 1; v < csv_input_lines; v++) {
									multicolumn[a][v] = (multicolumn[a][v] + b);
								}
								// System.out.println(a + " " + b);
							}
							// -----------------------------------
							if (command.equals("Convert To Upper Case")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Convert To Upper Case");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Convert To Upper Case");
								} // standard = 1
									// --------------------------------------------------
								int v;
								int a;
								String b = "";
								a = Integer.parseInt(attribute1);
								for (v = 1; v < csv_input_lines; v++) {
									b = multicolumn[a][v].toUpperCase();
									multicolumn[a][v] = b;
								}
								// System.out.println(a + " " + b);
							}
							// -----------------------------------
							if (command.equals("Convert To Lower Case")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Convert To Lower Case");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Convert To Lower Case");
								} // standard = 1
									// --------------------------------------------------
								int v;
								int a;
								String b = "";
								a = Integer.parseInt(attribute1);
								for (v = 1; v < csv_input_lines; v++) {
									b = multicolumn[a][v].toLowerCase();
									multicolumn[a][v] = b;
								}
								// System.out.println(a + " " + b);
							}
							// -----------------------------------
							if (command.equals("Compare Column")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Compare Column");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Compare Column");
								} // standard = 1
									// --------------------------------------------------
								int v;
								int a;
								int c;
								String b;
								a = Integer.parseInt(attribute1);// 0
								c = Integer.parseInt(attribute2);// 1
								b = attribute3; // pos/neg
								if (!b.equals("pos") && !b.equals("neg")) {
									b = "pos";
								}
								for (v = 1; v < csv_input_lines; v++) {// i =
																		// Csv
																		// input
																		// length
									if (b.equals("pos")) {
										if (multicolumn[a][v]
												.equals(multicolumn[c][v])) {
											// --------------------------------
											JFrame1.jList1(MESSAGE55
													+ MESSAGE99 + v + " "
													+ (multicolumn[a][v]) + ","
													+ (multicolumn[c][v])
													+ MESSAGE99 + MESSAGE57);
											if (loglevel >= 1) {
												write_log(MESSAGE55 + MESSAGE99
														+ v + " "
														+ (multicolumn[a][v])
														+ ","
														+ (multicolumn[c][v])
														+ MESSAGE99 + MESSAGE57);
											} // standard = 1
												// ---------------------------------
										}
									}

									if (b.equals("neg")) {
										if (!multicolumn[a][v]
												.equals(multicolumn[c][v])) {
											// --------------------------------
											JFrame1.jList1(MESSAGE56
													+ MESSAGE99 + v + " "
													+ (multicolumn[a][v]) + ","
													+ (multicolumn[c][v])
													+ MESSAGE99 + MESSAGE58);
											if (loglevel >= 1) {
												write_log(MESSAGE56 + MESSAGE99
														+ v + " "
														+ (multicolumn[a][v])
														+ ","
														+ (multicolumn[c][v])
														+ MESSAGE99 + MESSAGE58);
											} // standard = 1
												// ---------------------------------
										}
									}
								}
								// System.out.println(a + " " + c + " " + b);
							}
							// -------------------------------------------------
							if (command.equals("Compare Instring Column")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Compare Instring Column");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Compare Instring Column");
								} // standard = 1
									// --------------------------------------------------
								int v;
								int a;
								int c;
								String b;
								a = Integer.parseInt(attribute1);// 0
								c = Integer.parseInt(attribute2);// 1
								b = attribute3; // pos/neg
								if (!b.equals("pos") && !b.equals("neg")) {
									b = "pos";
								}
								for (v = 1; v < csv_input_lines; v++) {// i =
																		// Csv
																		// input
																		// length
									if (b.equals("pos")) {
										if (multicolumn[a][v]
												.contains(multicolumn[c][v])) {
											// --------------------------------
											JFrame1.jList1(MESSAGE18
													+ MESSAGE99 + v + " "
													+ (multicolumn[a][v]) + ","
													+ (multicolumn[c][v])
													+ MESSAGE99 + MESSAGE57);
											if (loglevel >= 1) {
												write_log(MESSAGE18 + MESSAGE99
														+ v + " "
														+ (multicolumn[a][v])
														+ ","
														+ (multicolumn[c][v])
														+ MESSAGE99 + MESSAGE57);
											} // standard = 1
												// ---------------------------------
										}
									}

									if (b.equals("neg")) {
										if (!multicolumn[a][v]
												.contains(multicolumn[c][v])) {
											// --------------------------------
											JFrame1.jList1(MESSAGE19
													+ MESSAGE99 + v + " "
													+ (multicolumn[a][v]) + ","
													+ (multicolumn[c][v])
													+ MESSAGE99 + MESSAGE58);
											if (loglevel >= 1) {
												write_log(MESSAGE19 + MESSAGE99
														+ v + " "
														+ (multicolumn[a][v])
														+ ","
														+ (multicolumn[c][v])
														+ MESSAGE99 + MESSAGE58);
											} // standard = 1
												// ---------------------------------
										}
									}
								}
								// System.out.println(a + " " + c + " " + b);
							}
							// -----------------------------------
							if (command.equals("Trim")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99 + "Trim");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99 + "Trim");
								} // standard = 1
									// --------------------------------------------------
								int v;
								int a;
								String b = "";
								a = Integer.parseInt(attribute1);
								for (v = 1; v < csv_input_lines; v++) {
									b = multicolumn[a][v].trim();
									multicolumn[a][v] = b;
								}
								// System.out.println(a + " " + b);
							}
							// -----------------------------------
							if (command.equals("Extract Chars")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Extract Chars");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Extract Chars");
								} // standard = 1
									// --------------------------------------------------
								int v;
								int a;
								int b;
								int c;
								String d = "";
								v = Integer.parseInt(attribute1);// 0 column
								a = Integer.parseInt(attribute2);// 0 Start
																	// Position
								b = Integer.parseInt(attribute3);// 3 End
																	// Position
																	// + 1
								for (c = 1; c < csv_input_lines; c++) {// i =
																		// Csv
																		// input
																		// length
									d = multicolumn[v][c];
									if (a <= d.length() && b <= d.length()
											&& a < b) {// && = AND
										multicolumn[v][c] = d.substring(a, b);
										d = multicolumn[v][c];
									}
								}
								// System.out.println(v + " " + a + " " + b +
								// " "
								// + d);
							}
							// -----------------------------------
							if (command.equals("Migrate In")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Migrate In");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Migrate In");
								} // standard = 1
									// --------------------------------------------------
								int v;
								int a;
								int c;
								String d = "";
								mc = csv_input_lines;
								// System.out.println("migrate in length = " +
								// mc);
								v = Integer.parseInt(attribute1);// 0 column
																	// Quelle
								a = Integer.parseInt(attribute2);// 0 column
																	// Ziel
								for (c = 1; c < csv_input_lines; c++) {// i =
																		// Csv
																		// input
																		// length
									d = multicolumn[v][c];
									migrate[a][c] = d;
									if (c == 1) {
										JFrame1.jList1(MESSAGE20 + MESSAGE99
												+ d);
										if (loglevel >= 1) {
											write_log(MESSAGE20 + MESSAGE99 + d);
										} // standard = 1
									}// Visuelle Kontrolle
									if (c == 2) {
										JFrame1.jList1(MESSAGE20 + MESSAGE99
												+ d);
										if (loglevel >= 1) {
											write_log(MESSAGE20 + MESSAGE99 + d);
										} // standard = 1
									}
									if (c == 3) {
										JFrame1.jList1(MESSAGE20 + MESSAGE99
												+ d);
										if (loglevel >= 1) {
											write_log(MESSAGE20 + MESSAGE99 + d);
										} // standard = 1
									}
									if (c == 4) {
										JFrame1.jList1(MESSAGE21);
										if (loglevel >= 1) {
											write_log(MESSAGE21);
										} // standard = 1
									}
									// System.out.println("migin:" + " v=" + v +
									// " a=" + a + " c=" + c);
									// System.out.println("migin:" +
									// migrate[a][c] + "*" + multicolumn[v][c]);
									// System.out.println(v + " " + a + " " +
									// d);
								}
							}
							// -----------------------------------
							if (command.equals("Migrate Out")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Migrate Out");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Migrate Out");
								} // standard = 1
									// --------------------------------------------------
								int v;
								int a;
								int b;
								int g;
								String d = "";
								int c; // counter 1
								int l = mc; // counter 2
								int o = 0; // visueller counter
								v = Integer.parseInt(attribute1);// 0 column
																	// Quelle
																	// Mig
								a = Integer.parseInt(attribute2);// 0 column
																	// Ziel Mig
								b = Integer.parseInt(attribute3);// 0 column
																	// Quelle
																	// copy
								g = Integer.parseInt(attribute4);// 0 column
																	// Ziel copy
								for (c = 1; c < mc; c++) {// i = Csv input
															// length todo!
									d = migrate[v][c];

									for (l = 1; l < csv_input_lines; l++) {// i
																			// =
																			// Csv
																			// input
										// length
										// System.out.println("migout compare:"
										// + migrate[v][c] + "," +
										// multicolumn[a][l]);
										if (d.equals(multicolumn[a][l])) {
											// (a.equals(b))
											// if
											// (!"".equals(multicolumn[g][l])) {
											multicolumn[g][l] = migrate[b][c];
											o++;
											if (o == 1) {
												JFrame1.jList1(MESSAGE22
														+ MESSAGE99 + d + " > "
														+ multicolumn[g][l]);
												if (loglevel >= 1) {
													write_log(MESSAGE22
															+ MESSAGE99 + d
															+ " > "
															+ multicolumn[g][l]);
												} // standard = 1
											}// Visuelle Kontrolle
											if (o == 2) {
												JFrame1.jList1(MESSAGE22
														+ MESSAGE99 + d + " > "
														+ multicolumn[g][l]);
												if (loglevel >= 1) {
													write_log(MESSAGE22
															+ MESSAGE99 + d
															+ " > "
															+ multicolumn[g][l]);
												} // standard = 1
											}
											if (o == 3) {
												JFrame1.jList1(MESSAGE22
														+ MESSAGE99 + d + " > "
														+ multicolumn[g][l]);
												if (loglevel >= 1) {
													write_log(MESSAGE22
															+ MESSAGE99 + d
															+ " > "
															+ multicolumn[g][l]);
												} // standard = 1
											}
											if (o == 4) {
												JFrame1.jList1(MESSAGE23);
												if (loglevel >= 1) {
													write_log(MESSAGE23);
												} // standard = 1
											}
											// }
											// System.out.println("migout:" +
											// multicolumn[g][l] + "," +
											// migrate[b][c]);
											// System.out.println(g + " " + l
											// + " " + d);
										}
									}
								}
							}
							// -----------------------------------
							if (command.equals("Bubblesort")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Bubblesort");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Bubblesort");
								} // standard = 1
									// --------------------------------------------------

								int v;
								int a;
								String b;
								String c;
								a = Integer.parseInt(attribute1);
								b = attribute2; // Numbers/String
								c = attribute3; // up/down
								int numberscompare1;
								int numberscompare2;
								String stringcompare1;
								String stringcompare2;
								int index;
								String copy;
								int w;
								int f;
								w = csv_input_lines - 1; // i-1 , vorletzter
															// index
								// System.out.println(MESSAGE24+ MESSAGE99 +
								// "a=" + a
								// + " b= " + b + " c=" + c);
								// System.out.println(MESSAGE24+ MESSAGE99 +
								// "i=" + i
								// + " w= " + w + " j=" + j);

								sort_count = 0;
								// static int i = 0; // Csv input length
								// static int j = 0; // Csv input width
								// static int y = 0; // Csv output length
								// static int z = 0; // Csv output width

								for (f = 1; f < csv_input_lines; f++) { // outside
																		// loop
									// 0-i = Csv input
									// length
									for (v = 1; v < w; v++) { // innere loop
																// 0-i, eachr
																// Round 1
																// decr.

										// System.out.println("Lange w:" + w);
										if (b.equals("Numbers")
												|| b.equals("numbers")) {
											numberscompare1 = Integer
													.parseInt(multicolumn[a][v]);
											index = v;
											index++;
											numberscompare2 = Integer
													.parseInt(multicolumn[a][index]);

											if (c.equals("up")) {
												if (numberscompare1 > numberscompare2) {
													for (int g = 0; g < csv_input_columns; g++) {
														copy = multicolumn[g][v];
														multicolumn[g][v] = multicolumn[g][index];
														multicolumn[g][index] = copy;
													}
													sort_count++;
												}
											}
											if (c.equals("down")) {
												if (numberscompare1 < numberscompare2) {
													for (int g = 0; g < csv_input_columns; g++) {
														copy = multicolumn[g][v];
														multicolumn[g][v] = multicolumn[g][index];
														multicolumn[g][index] = copy;
													}
													sort_count++;
												}
											}
										}
										if (b.equals("Strings")
												|| b.equals("strings")) {
											stringcompare1 = (multicolumn[a][v]);
											index = v;
											index++;
											stringcompare2 = (multicolumn[a][index]);
											if (c.equals("up")) {
												if (stringcompare1
														.compareTo(stringcompare2) > 0) {
													for (int g = 0; g < csv_input_columns; g++) {
														copy = multicolumn[g][v];
														multicolumn[g][v] = multicolumn[g][index];
														multicolumn[g][index] = copy;
													}
													sort_count++;
												}
											}
											if (c.equals("down")) {
												if (stringcompare1
														.compareTo(stringcompare2) < 0) {
													for (int g = 0; g < csv_input_columns; g++) {
														copy = multicolumn[g][v];
														multicolumn[g][v] = multicolumn[g][index];
														multicolumn[g][index] = copy;
													}
													sort_count++;
												}
											}
										}
									}
									w--; // each round 1 decr.
									// if (sort_ok = true) break;
								}
								JFrame1.jList1(MESSAGE24 + MESSAGE99
										+ MESSAGE25 + MESSAGE99 + sort_count
										+ MESSAGE99 + MESSAGE27);
								if (loglevel >= 1) {
									write_log(MESSAGE24 + MESSAGE99 + MESSAGE25
											+ MESSAGE99 + sort_count
											+ MESSAGE99 + MESSAGE27);
								} // standard = 1
									// System.out.println("row=" + v + " value="
									// +
									// multicolumn[a][v]);
									// System.out.println(a + " " + b + " " +
									// c);
							}
							// -----------------------------------
							if (command.equals("Quicksort")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Quicksort");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Quicksort");
								} // standard = 1
									// --------------------------------------------------
									// int v;
								int a;
								String b;
								String c;
								a = Integer.parseInt(attribute1);// column
								b = attribute2; // Numbers/String
								c = attribute3; // up/down
								sort_count = 0;
								if (b.equals("Numbers") || b.equals("numbers"))
									Quicksort_numbers(1, csv_input_lines - 1,
											a, c);
								if (b.equals("Strings") || b.equals("strings"))
									Quicksort_strings(1, csv_input_lines - 1,
											a, c);
								JFrame1.jList1(MESSAGE26 + MESSAGE99
										+ MESSAGE25 + MESSAGE99 + sort_count
										+ MESSAGE99 + MESSAGE27);
								if (loglevel >= 1) {
									write_log(MESSAGE26 + MESSAGE99 + MESSAGE25
											+ MESSAGE99 + sort_count
											+ MESSAGE99 + MESSAGE27);
								} // standard = 1
							}
							// -------------------------------------
							if (command.equals("Writefile")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Writefile");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Writefile");
								} // standard = 1
									// --------------------------------------------------
								int a;
								a = Integer.parseInt(attribute1);// Parameter
								if (a == 1) {
									JFrame1.jList1(MESSAGE28 + MESSAGE99
											+ MESSAGE29);
									if (loglevel >= 1) {
										write_log(MESSAGE28 + MESSAGE99
												+ MESSAGE29);
									} // standard = 1
									writecsvfile();
									writexmlfile();
								}
							}
							// ---------------------------------------------
							if (command.equals("Find Numerical Gaps")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Find Numerical Gaps");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Find Numerical Gaps");
								} // standard = 1
									// --------------------------------------------------
								int a;
								a = Integer.parseInt(attribute1);// column
								int min;
								int max;
								int f;
								int g;
								boolean isValidFollower;

								min = Integer.parseInt(multicolumn[a][1]);
								max = Integer.parseInt(multicolumn[a][1]);

								for (f = 1; f < csv_input_lines; f++) {
									if (min > Integer
											.parseInt(multicolumn[a][f])) {
										min = Integer
												.parseInt(multicolumn[a][f]);
									}
									if (max < Integer
											.parseInt(multicolumn[a][f])) {
										max = Integer
												.parseInt(multicolumn[a][f]);
									}
								}
								// System.out.println("Numerical Gaps: " +
								// "Min="
								// + min + " Max=" + max);
								JFrame1.jList1(MESSAGE30 + MESSAGE99
										+ MESSAGE32 + MESSAGE99 + min
										+ MESSAGE99 + MESSAGE31 + MESSAGE99
										+ max);
								if (loglevel >= 1) {
									write_log(MESSAGE30 + MESSAGE99 + MESSAGE32
											+ MESSAGE99 + min + MESSAGE99
											+ MESSAGE31 + MESSAGE99 + max);
								} // standard = 1

								for (f = min; f <= max; f++) {
									isValidFollower = false;
									for (g = 1; g < csv_input_lines; g++) {
										if (f + 1 == Integer
												.parseInt(multicolumn[a][g])) {
											isValidFollower = true;
										}
									}
									if (isValidFollower == false) {
										// System.out.println(MESSAGE33+
										// MESSAGE99 + (f +
										// 1));
										JFrame1.jList1(MESSAGE33 + MESSAGE99
												+ (f + 1));
										if (loglevel >= 1) {
											write_log(MESSAGE33 + MESSAGE99
													+ (f + 1));
										} // standard = 1
									}
								}
							}
							// ---------------------------------------------
							if (command.equals("Dupe Check")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Dupe Check");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Dupe Check");
								} // standard = 1
									// --------------------------------------------------
								int a;
								String c;
								int v;
								int f;
								int dj;
								String g;
								boolean isValidDupe = false;
								a = Integer.parseInt(attribute1);// column
								int dupelistcounter = 1;
								boolean isValidDupeListFound = false;

								for (v = 1; v < csv_input_lines; v++) { // i =
																		// Csv
																		// input
									// length
									c = (multicolumn[a][v]);

									for (f = 1; f < csv_input_lines; f++) {
										g = (multicolumn[a][f]);

										if (c.equals(g) && v != f) {
											isValidDupe = true;
											// -----------------------
											isValidDupeListFound = false;
											for (dj = 1; dj < csv_input_lines; dj++) {
												if (c.equals(dupelist[dj])) {
													isValidDupeListFound = true;
												}
											}
											if (isValidDupeListFound == false) {
												(dupelist[dupelistcounter]) = c;
												JFrame1.jList1(MESSAGE35
														+ MESSAGE99 + c);
												if (loglevel >= 1) {
													write_log(MESSAGE35
															+ MESSAGE99 + c);
												} // standard = 1
												dupelistcounter++;
											}
											// -------------------------
										}
									}

								}
								if (isValidDupe == false) {
									JFrame1.jList1(MESSAGE36);
									if (loglevel >= 1) {
										write_log(MESSAGE36);
									} // standard = 1
								}
							}
							// ---------------------------------------------
							if (command.equals("Stats")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99 + "Stats");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99 + "Stats");
								} // standard = 1
									// --------------------------------------------------
								int a;
								String b;
								String c;
								int v;
								int k;
								int sj;
								float p;
								boolean isValidStatListFound = false;
								statlistcounter = 1;
								a = Integer.parseInt(attribute1);// column
								b = (attribute2);// Chart
								int hundert_prozent = csv_input_lines - 1;
								// Format statlist
								for (v = 1; v < csv_input_lines; v++) { // i =
																		// Csv
																		// input
									// length
									statlist[0][v] = "";
									statlist[1][v] = "";
									statlist[2][v] = "";
								}
								// ----------------

								for (v = 1; v < csv_input_lines; v++) { // i =
																		// Csv
																		// input
									// length
									c = (multicolumn[a][v]);
									// -----------------------
									isValidStatListFound = false;
									for (sj = 1; sj < csv_input_lines; sj++) {
										if (c.equals(statlist[0][sj])) {
											isValidStatListFound = true;
											k = Integer
													.parseInt(statlist[1][sj]);
											k++;
											(statlist[1][sj]) = String
													.valueOf(k);
											// System.out.println("Stat true: "
											// +
											// (statlist[0][sj])+","+(statlist[1][sj]));
										}
									}
									if (isValidStatListFound == false) {
										(statlist[0][statlistcounter]) = c;
										(statlist[1][statlistcounter]) = String
												.valueOf(1);
										// System.out.println("Stat false: " +
										// (statlist[0][statlistcounter])+","+(statlist[1][statlistcounter]));
										statlistcounter++;
									}
								}
								// Sortierung Statlist-------------------
								int numberscompare1;
								int numberscompare2;
								int index;
								String copy;
								int w;
								int f;

								w = statlistcounter - 1; // statlistcounter-1 ,
															// vorletzter index
								sort_count = 0;

								for (f = 1; f < statlistcounter; f++) { // outside
																		// loop
																		// 0-i =
																		// Csv
																		// input
																		// length
									for (v = 1; v < w; v++) { // innere loop
																// 0-i, eachr
																// round 1
																// decr.
										// System.out.println("Lange w:" + w);
										numberscompare1 = Integer
												.parseInt(statlist[1][v]);
										index = v;
										index++;
										numberscompare2 = Integer
												.parseInt(statlist[1][index]);

										if (numberscompare1 < numberscompare2) {
											for (int g = 0; g < 3; g++) {
												copy = statlist[g][v];
												statlist[g][v] = statlist[g][index];
												statlist[g][index] = copy;
											}
											sort_count++;
										}
									}
									w--; // each round 1 decr.
								}
								// System.out.println(MESSAGE39+ MESSAGE99 +
								// MESSAGE25+ MESSAGE99
								// + sort_count + MESSAGE99 + MESSAGE27);
								JFrame1.jList1(MESSAGE39 + MESSAGE99
										+ MESSAGE25 + MESSAGE99 + sort_count
										+ MESSAGE99 + MESSAGE27);
								if (loglevel >= 1) {
									write_log(MESSAGE39 + MESSAGE99 + MESSAGE25
											+ MESSAGE99 + sort_count
											+ MESSAGE99 + MESSAGE27);
								} // standard = 1
									// --------------------------------------
								for (sj = 1; sj < statlistcounter; sj++) {
									k = Integer.parseInt(statlist[1][sj]);
									p = (float) k / (float) hundert_prozent
											* 100; // %
									statlist[2][sj] = Float.toString(p);
									if (b.equals("Chart1")) {
										chartlist1[0][sj] = statlist[0][sj];
										chartlist1[1][sj] = statlist[1][sj];
										chartlist1[2][sj] = statlist[2][sj];
									}
									if (b.equals("Chart2")) {
										chartlist2[0][sj] = statlist[0][sj];
										chartlist2[1][sj] = statlist[1][sj];
										chartlist2[2][sj] = statlist[2][sj];
									}
									if (b.equals("Chart3")) {
										chartlist3[0][sj] = statlist[0][sj];
										chartlist3[1][sj] = statlist[1][sj];
										chartlist3[2][sj] = statlist[2][sj];
									}
									// System.out.println(MESSAGE40+ MESSAGE99
									// + (statlist[1][sj]) +
									// MESSAGE99+MESSAGE41+MESSAGE99
									// + (statlist[0][sj]) +
									// MESSAGE99+MESSAGE42+MESSAGE99
									// + statlist[2][sj] +
									// MESSAGE99+MESSAGE43+MESSAGE99);
									JFrame1.jList1(MESSAGE40 + MESSAGE99
											+ (statlist[1][sj]) + MESSAGE99
											+ MESSAGE41 + MESSAGE99
											+ (statlist[0][sj]) + MESSAGE99
											+ MESSAGE42 + MESSAGE99
											+ statlist[2][sj] + MESSAGE99
											+ MESSAGE43 + MESSAGE99);
									if (loglevel >= 1) {
										write_log(MESSAGE40 + MESSAGE99
												+ (statlist[1][sj]) + MESSAGE99
												+ MESSAGE41 + MESSAGE99
												+ (statlist[0][sj]) + MESSAGE99
												+ MESSAGE42 + MESSAGE99
												+ statlist[2][sj] + MESSAGE99
												+ MESSAGE43 + MESSAGE99);
									} // standard = 1
								}
								// --------------------
								if (b.equals("Chart1")) {
									chartlist_header1 = (multicolumn[a][0]);
									chartlistcounter1 = statlistcounter;
									cal.Chart1.start(chartlistcounter1);
								}
								if (b.equals("Chart2")) {
									chartlist_header2 = (multicolumn[a][0]);
									chartlistcounter2 = statlistcounter;
									cal.Chart2.start(chartlistcounter2);
								}
								if (b.equals("Chart3")) {
									chartlist_header3 = (multicolumn[a][0]);
									chartlistcounter3 = statlistcounter;
									cal.Chart3.start(chartlistcounter3);
								}
							}
							// ---------------------------------------------
							if (command.equals("Autoexit")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Autoexit");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Autoexit");
								} // standard = 1
									// --------------------------------------------------
								int a;
								a = Integer.parseInt(attribute1);
								if (a == 1) {
									System.exit(0);
								}
								// System.out.println(MESSAGE45+ MESSAGE99 + a);
								JFrame1.jList1(MESSAGE45 + MESSAGE99 + a);
								if (loglevel >= 1) {
									write_log(MESSAGE45 + MESSAGE99 + a);
								} // standard = 1
							}
							// ---------------------------------------
							if (command.equals("Log File")) {
								// --------------------------------------------------
								JFrame1.jList1(MESSAGE72 + MESSAGE99
										+ "Log File");
								if (loglevel >= 1) {
									write_log(MESSAGE72 + MESSAGE99
											+ "Log File");
								} // standard = 1
									// --------------------------------------------------
								int a;
								a = Integer.parseInt(attribute1);
								if (a == 0) {
									loglevel = 0;
								}
								if (a == 1) {
									loglevel = 1;
								}
								if (a == 2) {
									loglevel = 2;
								}
							}
							// -----------------------------------
						}
					}
				}
			}
			JFrame1.jTextPane1.setText(MESSAGE47);
			if (loglevel >= 1) {
				write_log(MESSAGE47);
			}
		} catch (Exception e) {

			JFrame1.jTextPane1.setText(ERROR01 + MESSAGE99 + e);
			e.printStackTrace();
			if (loglevel >= 2) {
				write_log(ERROR01 + MESSAGE99 + e);
			} // debug = 2
		}
	}

	// *****************************************************************************************
	public static void write_log(String line2) {
		// ---------------------------------------------
		String filename2 = "." + File.separator + LOGFILEPATH;
		// String directory = "./" + File.separator;
		// *************************************************************************
		try {

			File file2 = new File(filename2);
			if (!file2.exists()) {
				file2.createNewFile();
				// *************************************************************************
				String now2 = new SimpleDateFormat("dd.MM.yyy")
						.format(new Date());
				BufferedWriter bw2;
				bw2 = new BufferedWriter(new FileWriter(file2, true));// True=Append
				bw2.write(MESSAGE59);
				bw2.newLine();
				bw2.write(MESSAGE63);
				bw2.newLine();
				bw2.write(MESSAGE59);
				bw2.newLine();
				bw2.write(now2);
				bw2.newLine();
				bw2.write(MESSAGE48 + MESSAGE99 + filename2);
				bw2.newLine();
				bw2.flush();
				bw2.close();
				// -----------------------------------------------------------------------------
				// System.out.println(MESSAGE48+ MESSAGE99 + filename2);
				JFrame1.jList1(MESSAGE48 + MESSAGE99 + filename2);
			} else {
				// file2.delete();
				// file2.createNewFile();
			}
			if (file2.exists()) {
				BufferedWriter bw2;
				bw2 = new BufferedWriter(new FileWriter(file2, true));// True=Append
				bw2.write(line2);
				bw2.newLine();
				bw2.flush();
				bw2.close();
			}
		} catch (Exception e) {
			JFrame1.jTextPane1.setText(ERROR01 + MESSAGE99 + e);
			e.printStackTrace();
		}
	}

	// *****************************************************************************************
	public static void clearall_migrate() {
		int x;
		int y;
		for (x = 0; x < max_width; x++) {
			for (y = 0; y < max_high; y++) {
				migrate[x][y] = ("");
			}
		}
		// System.out.println(MESSAGE49);
		JFrame1.jList1(MESSAGE49);
		if (loglevel >= 1) {
			write_log(MESSAGE49);
		} // standard = 1
	}

	// *****************************************************************************************
	public static void showall() {
		int x;
		int y;
		for (x = 0; x < csv_input_columns; x++) {
			for (y = 0; y < csv_input_lines; y++) {
				System.out.println("Column:" + x + " Line:" + y + " Value:"
						+ multicolumn[x][y]);
				// multicolumn[width][high];
			}
		}
	}

	// *****************************************************************************************
	public static void clearall() {
		int x;
		int y;
		for (x = 0; x < max_width; x++) {
			for (y = 0; y < max_high; y++) {
				multicolumn[x][y] = ("");
				// multicolumn[width][high];
			}
		}
		// System.out.println(MESSAGE49);
		JFrame1.jList1(MESSAGE49);
		if (loglevel >= 1) {
			write_log(MESSAGE49);
		} // standard = 1
	}

	// *****************************************************************************************
	public static void writescriptfile() {
		String filename = "." + File.separator + SCRIPTFILEPATH;
		@SuppressWarnings("unused")
		char c = 34;
		try {

			File file1 = new File(filename);
			if (!file1.exists()) {
				file1.createNewFile();
			} else {
				file1.delete();
				file1.createNewFile();
			}
			if (file1.exists()) {
				BufferedWriter bw;
				bw = new BufferedWriter(new FileWriter(file1, true));// True=Append
				// -----------------------------------------------
				for (csv_input_lines = 0; csv_input_lines < TEXTARRAY.length; csv_input_lines++) {
					bw.write(TEXTARRAY[csv_input_lines]);
					bw.newLine();
				}
				// ------------------------------------------------
				bw.flush();
				bw.close();
				JFrame1.jList1(MESSAGE48 + MESSAGE99 + filename);
				if (loglevel >= 1) {
					write_log(MESSAGE48 + MESSAGE99 + filename);
				} // standard = 1
					// System.out.println(MESSAGE48+ MESSAGE99 + filename);
			}
		} catch (Exception e) {
			JFrame1.jTextPane1.setText(ERROR01 + MESSAGE99 + e);
			e.printStackTrace();
			if (loglevel >= 2) {
				write_log(ERROR01 + MESSAGE99 + e);
			} // debug = 2
		}
	}

	// *****************************************************************************************
	public static void writehelptext() {

		// System.out.println(TEXTARRAY.length);
		// -----------------------------------------------
		for (int count = 0; count < TEXTARRAY.length; count++) {
			JFrame1.jList1(TEXTARRAY[count]);
			// System.out.println(TEXTARRAY[count]);
			if (loglevel >= 1) {
				write_log(TEXTARRAY[count]);
			} // standard = 1
		}
	}

	// -----------------------------------------------------------------------
	public static void Quicksort_numbers(int start, int end, int column,
			String direction) {
		// System.out.println(MESSAGE52+ MESSAGE99 + start + " " + end + " "
		// + column + " " + direction);
		try {
			if (start < end) {
				int ii;
				ii = partition_numbers(start, end, column, direction);
				Quicksort_numbers(start, ii - 1, column, direction);
				Quicksort_numbers(ii + 1, end, column, direction);
			}
		} catch (Exception e) {

			JFrame1.jTextPane1.setText(ERROR01 + MESSAGE99 + e);
			e.printStackTrace();
			if (loglevel >= 2) {
				write_log(ERROR01 + MESSAGE99 + e);
			} // debug = 2
		}
	}

	// ------------------------------------------------------------------------
	public static int partition_numbers(int start, int end, int column,
			String direction) {
		int pivot;
		int ii;
		int jj;
		String help;
		pivot = Integer.parseInt(multicolumn[column][end]);
		ii = start;
		jj = end - 1;
		try {
			// sortierung up
			if (direction.equals("up")) {
				while (ii <= jj) {
					if (Integer.parseInt(multicolumn[column][ii]) > pivot) {
						// change array [i] und array [j]
						// System.out.println("Quicksort Change1: " +
						// multicolumn[column][ii] + " " +
						// multicolumn[column][jj]);
						for (int g = 0; g < csv_input_columns; g++) {
							help = multicolumn[g][ii];
							multicolumn[g][ii] = multicolumn[g][jj];
							multicolumn[g][jj] = help;
						}
						sort_count++;
						jj--;
					} else
						ii++;
				}
				// change array [ii] und array [end]
				// System.out.println("Quicksort Change2: " +
				// multicolumn[column][ii] + " " + multicolumn[column][end]);
				for (int g = 0; g < csv_input_columns; g++) {
					help = multicolumn[g][ii];
					multicolumn[g][ii] = multicolumn[g][end];
					multicolumn[g][end] = help;
				}
				sort_count++;
			}
			// sortierung down
			if (direction.equals("down")) {
				while (ii <= jj) {
					if (Integer.parseInt(multicolumn[column][ii]) < pivot) {
						// change array [i] und array [j]
						// System.out.println("Quicksort Change1: " +
						// multicolumn[column][ii] + " " +
						// multicolumn[column][jj]);
						for (int g = 0; g < csv_input_columns; g++) {
							help = multicolumn[g][ii];
							multicolumn[g][ii] = multicolumn[g][jj];
							multicolumn[g][jj] = help;
						}
						sort_count++;
						jj--;
					} else
						ii++;
				}
				// change array [ii] und array [end]
				// System.out.println("Quicksort Change2: " +
				// multicolumn[column][ii] + " " + multicolumn[column][end]);
				for (int g = 0; g < csv_input_columns; g++) {
					help = multicolumn[g][ii];
					multicolumn[g][ii] = multicolumn[g][end];
					multicolumn[g][end] = help;
				}
				sort_count++;
			}
		} catch (Exception e) {

			JFrame1.jTextPane1.setText(ERROR01 + MESSAGE99 + MESSAGE52
					+ MESSAGE99 + e);
			e.printStackTrace();
			if (loglevel >= 2) {
				write_log(ERROR01 + MESSAGE99 + MESSAGE52 + MESSAGE99 + e);
			} // debug = 2
		}
		return ii;
	}

	// ----------------------------------------------------------------------------
	public static void Quicksort_strings(int start, int end, int column,
			String direction) {
		// System.out.println(MESSAGE53+ MESSAGE99 + start + " " + end + " "
		// + column + " " + direction);
		try {
			if (start < end) {
				int ii;
				ii = partition_strings(start, end, column, direction);
				Quicksort_strings(start, ii - 1, column, direction);
				Quicksort_strings(ii + 1, end, column, direction);
			}
		} catch (Exception e) {

			JFrame1.jTextPane1.setText(ERROR01 + MESSAGE99 + e);
			e.printStackTrace();
			if (loglevel >= 2) {
				write_log(ERROR01 + MESSAGE99 + e);
			} // debug = 2
		}
	}

	// ------------------------------------------------------------------------
	public static int partition_strings(int start, int end, int column,
			String direction) {
		String pivot;
		int ii;
		int jj;
		String help;
		pivot = (multicolumn[column][end]);
		ii = start;
		jj = end - 1;
		try {
			// sortierung up
			if (direction.equals("up")) {
				while (ii <= jj) {
					if ((multicolumn[column][ii]).compareTo(pivot) > 0) {
						// change array [ii] und array [jj]
						// System.out.println("Quicksort Change1: " +
						// multicolumn[column][ii] + " " +
						// multicolumn[column][jj]);
						for (int g = 0; g < csv_input_columns; g++) {
							help = multicolumn[g][ii];
							multicolumn[g][ii] = multicolumn[g][jj];
							multicolumn[g][jj] = help;
						}
						sort_count++;
						jj--;
					} else
						ii++;
				}
				// change array [ii] und array [end]
				// System.out.println("Quicksort Change2: " +
				// multicolumn[column][ii] + " " + multicolumn[column][end]);
				for (int g = 0; g < csv_input_columns; g++) {
					help = multicolumn[g][ii];
					multicolumn[g][ii] = multicolumn[g][end];
					multicolumn[g][end] = help;
				}
				sort_count++;
			}
			// sortierung down
			if (direction.equals("down")) {
				while (ii <= jj) {
					if ((multicolumn[column][ii]).compareTo(pivot) < 0) {
						// change array [i] und array [j]
						// System.out.println("Quicksort Change1: " +
						// multicolumn[column][ii] + " " +
						// multicolumn[column][jj]);
						for (int g = 0; g < csv_input_columns; g++) {
							help = multicolumn[g][ii];
							multicolumn[g][ii] = multicolumn[g][jj];
							multicolumn[g][jj] = help;
						}
						sort_count++;
						jj--;
					} else
						ii++;
				}
				// change array [ii] und array [end]
				// System.out.println("Quicksort Change2: " +
				// multicolumn[column][ii] + " " + multicolumn[column][end]);
				for (int g = 0; g < csv_input_columns; g++) {
					help = multicolumn[g][ii];
					multicolumn[g][ii] = multicolumn[g][end];
					multicolumn[g][end] = help;
				}
				sort_count++;
			}
		} catch (Exception e) {

			JFrame1.jTextPane1.setText(ERROR01 + MESSAGE99 + MESSAGE53
					+ MESSAGE99 + e);
			e.printStackTrace();
			if (loglevel >= 2) {
				write_log(ERROR01 + MESSAGE99 + MESSAGE53 + MESSAGE99 + e);
			} // debug = 2
		}
		return ii;
	}
	// ----------------------------------------------------------------------------
}
