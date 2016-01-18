package cal;

import gui.JFrame1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
//import java.io.IOException;
import java.lang.Exception;
import java.text.SimpleDateFormat;
import java.util.Date;

//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.NodeList;

public class Calculation {

	static char d = 59; // ,=44/;=59/:=58
	static int i = 0; // Csv input laenge
	static int j = 0; // Csv input breite
	static int y = 0; // Csv output laenge
	static int z = 0; // Csv output breite
	static int mc = 0; // migrate counter
	static int max_width = 100; // maximale Breite
	public static int max_high = 10000;// maximale Hoehe
	static int max_commands = 100;// maximale Hoehe
	static int found_commands;
	static int max_stat_width = 3;
	static int max_stat_high = 10000;
	static String multicolumn[][] = new String[max_width][max_high];
	static String migrate[][] = new String[max_width][max_high];
	static String dupelist[] = new String[max_high];
	public static String statlist[][] = new String[max_stat_width][max_high];
	public static String chartlist1[][] = new String[max_stat_width][max_stat_high];
	public static String chartlist2[][] = new String[max_stat_width][max_stat_high];
	public static String chartlist3[][] = new String[max_stat_width][max_stat_high];
	public static String chartlist_header1 = "Chart";
	public static String chartlist_header2 = "Chart";
	public static String chartlist_header3 = "Chart";
	static int statlistcounter = 1;
	public static int chartlistcounter1 = 1;
	public static int chartlistcounter2 = 1;
	public static int chartlistcounter3 = 1;

	static String outputpath = "Exchange Output.csv";
	static String handlerpath = "Handler.txt";
	static String datum = "10.01.2016";// last Modify
	static long sort_count;

	// -----------------------------------------------
	static String textarray[] = {
			("//Java Eclipse Version: 3.8.1" + " / Jigloo Version: 4.6.6"),
			("//http://www.eclipse.org/platform"),
			("//http://www.cloudgarden.com/jigloo/"),
			("//Version: " + JFrame1.titel + JFrame1.subversion
					+ ", last modify: " + datum),
			("//V2.4: New Feature: Compare Column und Compare Instring Column"),
			("//CSV-Spalten-Max=100, CSV-Zeilen-Max=10000, Handler-Commands-Max=100"),
			("//------------------------------------------------------"),
			("//Interaktions-Commands:"),
			("//Filename: Ausgabe Name , Parameter: Date, Date/Time , Parameter: Front,Back"),
			("//Separator: Ausgabe Trennzeichen 44=, / 58=: / 59=;"),
			("//Spalten: Spaltenanzahl des Ausgabe File"),
			("//Copy: Kopiere Spalte 2 nach Spalte 3"),
			("//Set Header: Setze nur Header in Spalte 0 mit Test"),
			("//Set Spalte: Setze Spalte 0 ohne Header mit bla"),
			("//Set Block: Setze Spalte 0 ohne Header beginnend mit 2000 fortlaufend incremental"),
			("//Find and Replace: Suche in Spalte 0 nach bla und ersetze mit blupp"),
			("//Instring Find Replace: Suche in Spalte 0 Instring nach up und ersetze mit ap"),
			("//Instring Find Move: Suche in Spalte 0 Instring nach up und ersetze mit ap in Spalte 1"),
			("//Instring Find Clear: Suche in Spalte 0 Instring nach up und lösche die gesamte CSV-Zeile"),
			("//Instring Not Find Clear: Suche in Spalte 0 Instring nach up, wenn nicht vorhanden lösche die gesamte CSV-Zeile"),
			("//String Combine Front: new String = bla + old String in Spalte 0"),
			("//String Combine Back: new String = old String + blupp in Spalte 0"),
			("//Convert To Lower Case:  in Spalte 0"),
			("//Convert To Upper Case: in Spalte 0"),
			("//Trim: entfernt alle nicht darstellbaren Zeichen am Anfang und am Ende in Spalte 0"),
			("//Extract Chars: von Position 0 bis Position 3+1 in Spalte 0  = Extract 4 Chars"),
			("//Migrate In: Daten zum Migrieren aus Spalte 1 in Migrationsarray Spalte 0 laden"),
			("//Migrate Out:vergleicht Wert aus  Migrationsarray Spalte 0 mit Wert in Spalte 1"),
			("//Migrate Out:wenn beide Werte gleich, dann Copy aus Migrationsarray Spalte 1 nach Spalte 2"),
			("//Bubblesort: Sortierung der Spalte 0, Numbers/Strings, up/down"),
			("//Quicksort: Sortierung der Spalte 0, Numbers/Strings, up/down"),
			("//Writefile: 1, Press Button Writefile"),
			("//Autoexit: 1, Applikation schliessen"),
			("//------------------------------------------------------"),
			("//Check-Commands:"),
			("//Find Numerical Gaps: 0, suche numerische Lücken zwischen Min. und Max. in Spalte 0 > Terminalresults"),
			("//Compare Column: Vergleiche String in Spalte 0 mit Spalte 1 pos/neg > Terminalresults"),
			("//Compare Instring Column: Suche String der Spalte 0 Instring in Spalte 1 pos/neg > Terminalresults"),
			("//Dupe Check: 0, Findet mehrfach vorhandene Werte in Spalte 0 > Terminalresults"),
			("//Stats: 0, Prozentuale Ausgabe der Spalte 0, Chart1/2/3: Grafische Ausgabe, max. 3 Charts"),
			("//------------------------------------------------------"),
			("//Es sind folgende Handler Commands moeglich:"),
			("Filename,Output Filename,Date,Front,"),// Standard,Date
			("Separator,59,"), // ------------------------------------
			("Spalten,30,"), // --------------------------------------
			("Copy Spalte,2,3,"), // ---------------------------------
			("Set Header,0,Test,"), // -------------------------------
			("Set Spalte,0,bla,"), // --------------------------------
			("Set Block,0,2000,"), // --------------------------------
			("Find Replace,0,bla,blupp,"), // ------------------------
			("Instring Find Replace,0,up,ap,"), // -------------------
			("Instring Find Move,0,up,1,ap,"), // --------------------
			("Instring Find Clear,0,up,"), // ------------------------
			("Instring Not Find Clear,0,up,"), // --------------------
			("String Combine Front,0,bla,"), // ----------------------
			("String Combine Back,0,blupp,"), // ---------------------
			("Convert To Lower Case,0,"), // -------------------------
			("Convert To Upper Case,0,"), // -------------------------
			("Trim,0,"), // ------------------------------------------
			("Extract Chars,0,0,3,"), // -----------------------------
			("Migrate In,1,0,"), // ----------------------------------
			("Migrate Out,0,1,1,2,"), // -----------------------------
			("Bubblesort,0,Numbers,up,"), // -------------------------
			("Quicksort,0,Strings,up,"), // --------------------------
			("Writefile,1,"), // -------------------------------------
			("Autoexit,1,"), // --------------------------------------
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
	public static void readfile(String path1) {
		// Init table
		JFrame1.jTextPane1.setText("");
		clearall();

		System.out.println("Path: " + path1);
		String row = "";
		row = ("Path=" + path1);
		JFrame1.jList1(row);

		// Autoscan separator
		String c = ";";
		String line = "";
		try {
			if (path1 != null) {
				JFrame1.jList1("Start Separator Autoscan");
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
					JFrame1.jList1("Header 1st. Line: " + line);
				}
				fr1.close();
				JFrame1.jList1("Read Separator Done: " + c);
			}
		} catch (Exception e) {

			JFrame1.jTextPane1.setText("Error: " + e);
			e.printStackTrace();
		}
		// ----------------------------------

		try {
			if (path1 != null) {

				JFrame1.jList1("Open File");
				i = 0; // Laenge
				int x = 0;// Zaehler
				boolean first = true;
				int jheader = 0;
				int jmin = 0;
				int jmax = 0;
				int jdata = 0;
				FileReader fr2 = new FileReader(path1);
				BufferedReader br2 = new BufferedReader(fr2);
				while ((row = br2.readLine()) != null) {
					String splitrow[] = row.split(c);
					if (first == true) {
						jheader = splitrow.length; // Header Breite ermitteln
													// nur in der 1. Zeile
						j = jheader;
						jmin = jheader;
						jmax = jheader;
						first = false;
					}
					if (first == false) {
						jdata = splitrow.length; // Data Breite ermitteln ab der
													// 2. Zeile
						if (jdata < j) {
							jmin = jdata;
							j = jdata;
							z = j;
						}
						if (jdata > jmax) {
							jmax = jdata;
						}
					}
					for (x = 0; x < j; x++) {
						multicolumn[x][i] = splitrow[x];
						// System.out.println(multicolumn[x][i]);
					}
					// JFrame1.jList1(row);
					// JFrame1.jList1(String.valueOf(j));
					i++;
				}
				fr2.close();
				//
				if (jheader == jmin && jmin == jmax) {
					JFrame1.jList1("CSV Format OK: Header = Data Columns");//
				}
				if (jmin < jheader) {
					JFrame1.jList1("Error: Header (" + jheader + ") > "
							+ "Data min (" + jmin + ") Columns");//
				}
				if (jmax > jheader) {
					JFrame1.jList1("Error: Data max (" + jmax + ") > "
							+ "Header (" + jheader + ") Columns");//
				}
				if (jmin != jmax) {
					JFrame1.jList1("Error: Data min (" + jmin + ") != "
							+ "Data max (" + jmax + ") Columns");//
				}
				JFrame1.jList1("Work with: " + (j) + " columns");// Spalte
				JFrame1.jList1("Work with: " + (i) + " rows, incl. Header"); // Zeile
				JFrame1.jList1("Work with: " + (i * j) + " fields");
				JFrame1.jList1("Read File Done");
				outputpath = "Exchange output.csv";
				// System.out.println("Open File:" + " i=" + i + " j=" +j);
			}
		} catch (Exception e) {

			JFrame1.jTextPane1.setText("Error: " + e);
			e.printStackTrace();
		}
	}

	// *****************************************************************************************
	public static void writefile() {
		JFrame1.jTextPane1.setText("");
		// char d = 59;
		y = i;
		// z = 30; // Breite der csv Datei
		// ---------------------------------------------
		String filename = "." + File.separator + outputpath;
		// String directory = "./" + File.separator;
		// *************************************************************************
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
				JFrame1.jList1("Start Write File");
				int x = 0;
				int p = 0;
				while (x < y) {
					String line = "";
					for (p = 0; p < z; p++) {
						if (multicolumn[p][x] == null) {
							multicolumn[p][x] = "";
						}// init mit""
						if (p == 0) {
							line = (line + multicolumn[p][x]);
						}
						if (p > 0) {
							line = (line + d + multicolumn[p][x]);
						}
					}
					bw.write(line);
					System.out.println(line);
					JFrame1.jList1(line);
					x++;
					bw.newLine();
				}
				bw.flush();
				bw.close();
				System.out.println("Created File in: " + filename);
				JFrame1.jList1("Created File in: " + filename);

			}
		} catch (Exception e) {
			JFrame1.jTextPane1.setText("Error: " + e);
			e.printStackTrace();
		}
	}

	// *****************************************************************************************
	public static void handler(String path2) {
		String commands[] = new String[max_commands];
		System.out.println("Path: " + path2);
		String row = "";
		row = ("Path=" + path2);
		JFrame1.jList1(row);
		JFrame1.jTextPane1.setText("");
		try {
			if (path2 != null) {
				JFrame1.jList1("Open Handler File");
				int x = 0;// Zaehler
				int m = 0; // Zaehler
				FileReader fr1 = new FileReader(path2);
				BufferedReader br1 = new BufferedReader(fr1);
				while ((row = br1.readLine()) != null) {
					if (row.length() >= 2) {
						if (row.indexOf("//") == -1) {
							if ((row.indexOf("Filename,") != -1)
									|| (row.indexOf("Separator,") != -1)
									|| (row.indexOf("Spalten,") != -1)
									|| (row.indexOf("Copy Spalte,") != -1)
									|| (row.indexOf("Set Header,") != -1)
									|| (row.indexOf("Set Spalte,") != -1)
									|| (row.indexOf("Find Replace,") != -1)
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
									|| (row.indexOf("Set Block,") != -1)) {
								// --------------------------------------------
								if (row.length() >= 9) {
									if (row.substring(0, 9).equals("Filename,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 10) {
									if (row.substring(0, 10).equals(
											"Separator,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 8) {
									if (row.substring(0, 8).equals("Spalten,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 12) {
									if (row.substring(0, 12).equals(
											"Copy Spalte,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 11) {
									if (row.substring(0, 11).equals(
											"Set Header,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 11) {
									if (row.substring(0, 11).equals(
											"Set Spalte,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// ---------------------------------------------
								if (row.length() >= 10) {
									if (row.substring(0, 10).equals(
											"Set Block,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 13) {
									if (row.substring(0, 13).equals(
											"Find Replace,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 22) {
									if (row.substring(0, 22).equals(
											"Instring Find Replace,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 19) {
									if (row.substring(0, 19).equals(
											"Instring Find Move,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 20) {
									if (row.substring(0, 20).equals(
											"Instring Find Clear,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 24) {
									if (row.substring(0, 24).equals(
											"Instring Not Find Clear,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 21) {
									if (row.substring(0, 21).equals(
											"String Combine Front,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 20) {
									if (row.substring(0, 20).equals(
											"String Combine Back,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 22) {
									if (row.substring(0, 22).equals(
											"Convert To Lower Case,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 22) {
									if (row.substring(0, 22).equals(
											"Convert To Upper Case,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 15) {
									if (row.substring(0, 15).equals(
											"Compare Column,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// -------------------------------------------
								if (row.length() >= 24) {
									if (row.substring(0, 24).equals(
											"Compare Instring Column,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 5) {
									if (row.substring(0, 5).equals("Trim,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 14) {
									if (row.substring(0, 14).equals(
											"Extract Chars,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 11) {
									if (row.substring(0, 11).equals(
											"Migrate In,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 12) {
									if (row.substring(0, 12).equals(
											"Migrate Out,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// ----------------------------------------
								if (row.length() >= 11) {
									if (row.substring(0, 11).equals(
											"Bubblesort,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 10) {
									if (row.substring(0, 10).equals(
											"Quicksort,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// --------------------------------------------
								if (row.length() >= 10) {
									if (row.substring(0, 10).equals(
											"Writefile,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// ----------------------------------------------
								if (row.length() >= 20) {
									if (row.substring(0, 20).equals(
											"Find Numerical Gaps,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// ----------------------------------------------
								if (row.length() >= 11) {
									if (row.substring(0, 11).equals(
											"Dupe Check,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// ----------------------------------------------
								if (row.length() >= 6) {
									if (row.substring(0, 6).equals("Stats,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// ----------------------------------------------
								if (row.length() >= 9) {
									if (row.substring(0, 9).equals("Autoexit,")) {
										commands[x] = (row);
										JFrame1.jList1(commands[x]);
										x++;
									}
								}
								// ----------------------------------------------
							}
						}
					}
				}
				fr1.close();

				JFrame1.jList1("Found: " + x + " Handler Commands");
				found_commands = x;
				JFrame1.jProgressBar1.setMaximum(x - 1);
				if (x <= max_commands) {
					JFrame1.jList1("Read Handler File OK");
				}
				if (x > max_commands) {
					JFrame1.jList1("Fail: to many Commands in Handler File");
				}

				for (x = 0; x < found_commands; x++) {
					// Progressbar
					JFrame1.jProgressBar1.setValue(x);
					JFrame1.jProgressBar1.paint(JFrame1.jProgressBar1
							.getGraphics());// zwingen zum Aktualliesieren
					Thread.sleep(200);
					// --------------
					if (commands[x] != null) {
						if (!commands[x].equals("")) {
							// System.out.println(commands[x]);
							// ######################################################
							int jj = 0;
							String splitcommands[] = commands[x].split(",");
							jj = splitcommands.length; // Breite

							String command = "";
							String attribute1 = "";
							String attribute2 = "";
							String attribute3 = "";
							String attribute4 = "";

							for (m = 0; m < jj; m++) {
								if (m == 0) {
									command = splitcommands[m];
									System.out.println(command);
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
								outputpath = outputpath + ".csv";
								System.out.println(outputpath);
							}
							// -----------------------------------
							if (command.equals("Separator")) {
								d = (char) Integer.parseInt(attribute1);
								System.out.println(d);
							}
							// -----------------------------------
							if (command.equals("Spalten")) {
								z = Integer.parseInt(attribute1);
								System.out.println(z);
							}
							// -----------------------------------
							if (command.equals("Copy Spalte")) {
								int v;
								int a;
								int b;
								a = Integer.parseInt(attribute1);
								b = Integer.parseInt(attribute2);
								for (v = 0; v < i; v++) {
									multicolumn[b][v] = multicolumn[a][v];
								}
								System.out.println(a + " " + b);
							}
							// -----------------------------------
							if (command.equals("Set Header")) {
								int a;
								String b;
								a = Integer.parseInt(attribute1);
								b = attribute2;
								if (a == 0) {
									b = "//" + b;
								}
								multicolumn[a][0] = b;
								System.out.println(a + " " + b);
							}
							// -----------------------------------
							if (command.equals("Set Spalte")) {
								int v;
								int a;
								String b;
								a = Integer.parseInt(attribute1);
								b = attribute2;
								for (v = 1; v < i; v++) {
									multicolumn[a][v] = b;
								}
								System.out.println(a + " " + b);
							}
							// -----------------------------------
							if (command.equals("Set Block")) {
								int v;
								int a;
								int b;
								a = Integer.parseInt(attribute1);
								b = Integer.parseInt(attribute2);
								for (v = 1; v < i; v++) {
									multicolumn[a][v] = Integer.toString(b);
									b++;
								}
								System.out.println(a + " " + b);
							}
							// -----------------------------------
							if (command.equals("Find Replace")) {
								int v;
								int a;
								String b;
								String c;
								a = Integer.parseInt(attribute1);
								b = attribute2;
								c = attribute3;
								for (v = 1; v < i; v++) {
									if (multicolumn[a][v].equals(b)) {
										multicolumn[a][v] = (c);
									}
								}
								System.out.println(a + " " + b + " " + c);
							}
							// -----------------------------------
							if (command.equals("Instring Find Replace")) {
								int v;
								int a;
								String b;
								String c;
								a = Integer.parseInt(attribute1);
								b = attribute2;
								c = attribute3;
								for (v = 1; v < i; v++) { // i = Csv input
															// laenge
									if (multicolumn[a][v].indexOf(b) != -1) {
										// --------------------------------
										String splitmulticolumn[] = multicolumn[a][v]
												.split(b);
										jj = splitmulticolumn.length; // Breite
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
								System.out.println(a + " " + b + " " + c);
							}
							// -----------------------------------
							if (command.equals("Instring Find Move")) {
								int v;
								int a;
								String b;
								int c;
								String d;
								a = Integer.parseInt(attribute1);// 0
								b = attribute2; // up
								c = Integer.parseInt(attribute3);// 1
								d = attribute4; // ap
								for (v = 1; v < i; v++) {// i = Csv input laenge
									if (multicolumn[a][v].indexOf(b) != -1) {
										// --------------------------------
										String splitmulticolumn[] = multicolumn[a][v]
												.split(b);
										jj = 0;
										jj = splitmulticolumn.length; // Breite
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
								System.out.println(a + " " + b + " " + c + " "
										+ d);
							}
							// -----------------------------------
							if (command.equals("Instring Find Clear")) {
								int v;
								int a;
								String b;
								a = Integer.parseInt(attribute1);// 0
								b = attribute2; // Compare
								v = 1;
								System.out.println("Start Clear ," + " i="
										+ (i) + " j=" + (j));
								do {
									// System.out.println("Searchstring=" + b);
									if (multicolumn[a][v].contains(b)) {
										// Lösche Line: tausche aktuelle
										// Position durch hoch kopieren
										for (int h = v; h < i; h++) { // i = Csv
																		// input
																		// laenge
											for (int g = 0; g < j; g++) {
												multicolumn[g][h] = multicolumn[g][h + 1];
											}
										}
										i--;
									} else {
										v++;
									}
									// }
								} while (v < i); // liefert while ein true > do
								System.out.println(a + " " + b + " " + i);
							}
							// -----------------------------------
							if (command.equals("Instring Not Find Clear")) {
								int v;
								int a;
								String b;
								a = Integer.parseInt(attribute1);// 0
								b = attribute2; // Compare
								v = 1;
								System.out.println("Start Not Clear ," + " i="
										+ (i) + " j=" + (j));
								do {
									// System.out.println("Searchstring=" + b);
									if (!multicolumn[a][v].contains(b)) {
										// Lösche Line: tausche aktuelle
										// Position durch hoch kopieren
										for (int h = v; h < i; h++) { // i = Csv
																		// input
																		// laenge
											for (int g = 0; g < j; g++) {
												multicolumn[g][h] = multicolumn[g][h + 1];
											}
										}
										i--;
									} else {
										v++;
									}
									// }
								} while (v < i); // liefert while ein true > do
								System.out.println(a + " " + b + " " + i);
							}
							// -----------------------------------
							if (command.equals("String Combine Front")) {
								int v;
								int a;
								String b = "";
								a = Integer.parseInt(attribute1);
								b = (attribute2);
								for (v = 1; v < i; v++) {
									multicolumn[a][v] = (b + multicolumn[a][v]);
								}
								System.out.println(a + " " + b);
							}
							// -----------------------------------
							if (command.equals("String Combine Back")) {
								int v;
								int a;
								String b = "";
								a = Integer.parseInt(attribute1);
								b = (attribute2);
								for (v = 1; v < i; v++) {
									multicolumn[a][v] = (multicolumn[a][v] + b);
								}
								System.out.println(a + " " + b);
							}
							// -----------------------------------
							if (command.equals("Convert To Upper Case")) {
								int v;
								int a;
								String b = "";
								a = Integer.parseInt(attribute1);
								for (v = 1; v < i; v++) {
									b = multicolumn[a][v].toUpperCase();
									multicolumn[a][v] = b;
								}
								System.out.println(a + " " + b);
							}
							// -----------------------------------
							if (command.equals("Convert To Lower Case")) {
								int v;
								int a;
								String b = "";
								a = Integer.parseInt(attribute1);
								for (v = 1; v < i; v++) {
									b = multicolumn[a][v].toLowerCase();
									multicolumn[a][v] = b;
								}
								System.out.println(a + " " + b);
							}
							// -----------------------------------
							if (command.equals("Compare Column")) {
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
								for (v = 1; v < i; v++) {// i = Csv input laenge
									if (b.equals("pos")) {
										if (multicolumn[a][v]
												.equals(multicolumn[c][v])) {
											// --------------------------------
											JFrame1.jList1("Compare Result pos: Line "
													+ v
													+ " "
													+ (multicolumn[a][v])
													+ ","
													+ (multicolumn[c][v])
													+ " :EQUAL");
											// ---------------------------------
										}
									}

									if (b.equals("neg")) {
										if (!multicolumn[a][v]
												.equals(multicolumn[c][v])) {
											// --------------------------------
											JFrame1.jList1("Compare Result neg: Line "
													+ v
													+ " "
													+ (multicolumn[a][v])
													+ ","
													+ (multicolumn[c][v])
													+ " :ODDS");
											// ---------------------------------
										}
									}
								}
								System.out.println(a + " " + c + " " + b);
							}
							// -------------------------------------------------
							if (command.equals("Compare Instring Column")) {
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
								for (v = 1; v < i; v++) {// i = Csv input laenge
									if (b.equals("pos")) {
										if (multicolumn[a][v]
												.contains(multicolumn[c][v])) {
											// --------------------------------
											JFrame1.jList1("Compare Instring Result pos: Line "
													+ v
													+ " "
													+ (multicolumn[a][v])
													+ ","
													+ (multicolumn[c][v])
													+ " :EQUAL");
											// ---------------------------------
										}
									}

									if (b.equals("neg")) {
										if (!multicolumn[a][v]
												.contains(multicolumn[c][v])) {
											// --------------------------------
											JFrame1.jList1("Compare Instring Result neg: Line "
													+ v
													+ " "
													+ (multicolumn[a][v])
													+ ","
													+ (multicolumn[c][v])
													+ " :ODDS");
											// ---------------------------------
										}
									}
								}
								System.out.println(a + " " + c + " " + b);
							}
							// -----------------------------------
							if (command.equals("Trim")) {
								int v;
								int a;
								String b = "";
								a = Integer.parseInt(attribute1);
								for (v = 1; v < i; v++) {
									b = multicolumn[a][v].trim();
									multicolumn[a][v] = b;
								}
								System.out.println(a + " " + b);
							}
							// -----------------------------------
							if (command.equals("Extract Chars")) {
								int v;
								int a;
								int b;
								int c;
								String d = "";
								v = Integer.parseInt(attribute1);// 0 Spalte
								a = Integer.parseInt(attribute2);// 0 Start
																	// Position
								b = Integer.parseInt(attribute3);// 3 End
																	// Position
																	// + 1
								for (c = 1; c < i; c++) {// i = Csv input laenge
									d = multicolumn[v][c];
									if (a <= d.length() && b <= d.length()
											&& a < b) {// && = AND
										multicolumn[v][c] = d.substring(a, b);
										d = multicolumn[v][c];
									}
								}
								System.out.println(v + " " + a + " " + b + " "
										+ d);
							}
							// -----------------------------------
							if (command.equals("Migrate In")) {
								int v;
								int a;
								int c;
								String d = "";
								mc = i;
								// System.out.println("migrate in length = " +
								// mc);
								v = Integer.parseInt(attribute1);// 0 Spalte
																	// Quelle
								a = Integer.parseInt(attribute2);// 0 Spalte
																	// Ziel
								for (c = 1; c < i; c++) {// i = Csv input laenge
									d = multicolumn[v][c];
									migrate[a][c] = d;
									if (c == 1) {
										JFrame1.jList1("Mig In: " + d);
									}// Visuelle Kontrolle
									if (c == 2) {
										JFrame1.jList1("Mig In: " + d);
									}
									if (c == 3) {
										JFrame1.jList1("Mig In: " + d);
									}
									if (c == 4) {
										JFrame1.jList1("Mig In: ....................................");
									}
									// System.out.println("migin:" + " v=" + v +
									// " a=" + a + " c=" + c);
									// System.out.println("migin:" +
									// migrate[a][c] + "*" + multicolumn[v][c]);
									System.out.println(v + " " + a + " " + d);
								}
							}
							// -----------------------------------
							if (command.equals("Migrate Out")) {
								int v;
								int a;
								int b;
								int g;
								String d = "";
								int c; // counter 1
								int l = mc; // counter 2
								int o = 0; // visueller counter
								v = Integer.parseInt(attribute1);// 0 Spalte
																	// Quelle
																	// Mig
								a = Integer.parseInt(attribute2);// 0 Spalte
																	// Ziel Mig
								b = Integer.parseInt(attribute3);// 0 Spalte
																	// Quelle
																	// copy
								g = Integer.parseInt(attribute4);// 0 Spalte
																	// Ziel copy
								for (c = 1; c < mc; c++) {// i = Csv input
															// laenge todo!
									d = migrate[v][c];

									for (l = 1; l < i; l++) {// i = Csv input
																// laenge
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
												JFrame1.jList1("Mig Out: " + d
														+ " > "
														+ multicolumn[g][l]);
											}// Visuelle Kontrolle
											if (o == 2) {
												JFrame1.jList1("Mig Out: " + d
														+ " > "
														+ multicolumn[g][l]);
											}
											if (o == 3) {
												JFrame1.jList1("Mig Out: " + d
														+ " > "
														+ multicolumn[g][l]);
											}
											if (o == 4) {
												JFrame1.jList1("Mig Out: ....................................................");
											}
											// }
											// System.out.println("migout:" +
											// multicolumn[g][l] + "," +
											// migrate[b][c]);
											System.out.println(g + " " + l
													+ " " + d);
										}
									}
								}
							}
							// -----------------------------------
							if (command.equals("Bubblesort")) {

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
								w = i - 1; // i-1 , vorletzter index
								System.out.println("Bubblesort: " + "a=" + a
										+ " b= " + b + " c=" + c);
								System.out.println("Bubblesort: " + "i=" + i
										+ " w= " + w + " j=" + j);

								sort_count = 0;
								// static int i = 0; // Csv input laenge
								// static int j = 0; // Csv input breite
								// static int y = 0; // Csv output laenge
								// static int z = 0; // Csv output breite

								for (f = 1; f < i; f++) { // aussere Schleife
															// 0-i = Csv input
															// länge
									for (v = 1; v < w; v++) { // innere Schleife
																// 0-i, jeder
																// Runde 1
																// weniger

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
													for (int g = 0; g < j; g++) {
														copy = multicolumn[g][v];
														multicolumn[g][v] = multicolumn[g][index];
														multicolumn[g][index] = copy;
													}
													sort_count++;
												}
											}
											if (c.equals("down")) {
												if (numberscompare1 < numberscompare2) {
													for (int g = 0; g < j; g++) {
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
													for (int g = 0; g < j; g++) {
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
													for (int g = 0; g < j; g++) {
														copy = multicolumn[g][v];
														multicolumn[g][v] = multicolumn[g][index];
														multicolumn[g][index] = copy;
													}
													sort_count++;
												}
											}
										}
									}
									w--; // jede Runde 1 weniger
									// if (sort_ok = true) break;
								}
								JFrame1.jList1("Bubblesort: " + "Sortings "
										+ sort_count + "  done");
								// System.out.println("row=" + v + " value=" +
								// multicolumn[a][v]);
								// System.out.println(a + " " + b + " " + c);
							}
							// -----------------------------------
							if (command.equals("Quicksort")) {
								// int v;
								int a;
								String b;
								String c;
								a = Integer.parseInt(attribute1);// Spalte
								b = attribute2; // Numbers/String
								c = attribute3; // up/down
								sort_count = 0;
								if (b.equals("Numbers") || b.equals("numbers"))
									Quicksort_numbers(1, i - 1, a, c);
								if (b.equals("Strings") || b.equals("strings"))
									Quicksort_strings(1, i - 1, a, c);
								JFrame1.jList1("Quicksort: " + "Sortings "
										+ sort_count + "  done");
							}
							// -------------------------------------
							if (command.equals("Writefile")) {
								int a;
								a = Integer.parseInt(attribute1);// Parameter
								if (a == 1) {
									JFrame1.jList1("Press Button: "
											+ "Writefile");
									writefile();
								}
							}
							// ---------------------------------------------
							if (command.equals("Find Numerical Gaps")) {
								int a;
								a = Integer.parseInt(attribute1);// Spalte
								int min;
								int max;
								int f;
								int g;
								boolean Nachfolger;

								min = Integer.parseInt(multicolumn[a][1]);
								max = Integer.parseInt(multicolumn[a][1]);

								for (f = 1; f < i; f++) {
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
								System.out.println("Numerical Gaps: " + "Min="
										+ min + " Max=" + max);
								JFrame1.jList1("Numerical Gaps: " + "Min="
										+ min + " Max=" + max);

								for (f = min; f <= max; f++) {
									Nachfolger = false;
									for (g = 1; g < i; g++) {
										if (f + 1 == Integer
												.parseInt(multicolumn[a][g])) {
											Nachfolger = true;
										}
									}
									if (Nachfolger == false) {
										System.out.println("Gaps: " + (f + 1));
										JFrame1.jList1("Gaps: " + (f + 1));
									}
								}
							}
							// ---------------------------------------------
							if (command.equals("Dupe Check")) {
								int a;
								String c;
								int v;
								int f;
								int dj;
								String g;
								boolean dupe = false;
								a = Integer.parseInt(attribute1);// Spalte
								int dupelistcounter = 1;
								boolean dupelistfound = false;

								System.out.println("Dupe Check: Start");
								JFrame1.jList1("Dupe Check: Start");
								for (v = 1; v < i; v++) { // i = Csv input
															// laenge
									c = (multicolumn[a][v]);

									for (f = 1; f < i; f++) {
										g = (multicolumn[a][f]);

										if (c.equals(g) && v != f) {
											dupe = true;
											// -----------------------
											dupelistfound = false;
											for (dj = 1; dj < i; dj++) {
												if (c.equals(dupelist[dj])) {
													dupelistfound = true;
												}
											}
											if (dupelistfound == false) {
												(dupelist[dupelistcounter]) = c;
												System.out
														.println("Dupe: " + c);
												JFrame1.jList1("Dupe: " + c);
												dupelistcounter++;
											}
											// -------------------------
										}
									}

								}
								if (dupe == false) {
									System.out.println("No Dupes");
									JFrame1.jList1("No Dupes");
								}
							}
							// ---------------------------------------------
							if (command.equals("Stats")) {
								int a;
								String b;
								String c;
								int v;
								int k;
								int sj;
								float p;
								boolean statlistfound = false;
								statlistcounter = 1;
								a = Integer.parseInt(attribute1);// Spalte
								b = (attribute2);// Chart
								int hundert_prozent = i - 1;

								System.out.println("Stats Start: "
										+ (multicolumn[a][0]) + " / " + (i - 1)
										+ " Values = 100%");
								JFrame1.jList1("Stats Start: "
										+ (multicolumn[a][0]) + " / " + (i - 1)
										+ " Values = 100%");

								// Format statlist
								for (v = 1; v < i; v++) { // i = Csv input
															// laenge
									statlist[0][v] = "";
									statlist[1][v] = "";
									statlist[2][v] = "";
								}
								// ----------------

								for (v = 1; v < i; v++) { // i = Csv input
															// laenge
									c = (multicolumn[a][v]);
									// -----------------------
									statlistfound = false;
									for (sj = 1; sj < i; sj++) {
										if (c.equals(statlist[0][sj])) {
											statlistfound = true;
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
									if (statlistfound == false) {
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

								for (f = 1; f < statlistcounter; f++) { // aussere
																		// Schleife
																		// 0-i =
																		// Csv
																		// input
																		// länge
									for (v = 1; v < w; v++) { // innere Schleife
																// 0-i, jeder
																// Runde 1
																// weniger
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
									w--; // jede Runde 1 weniger
								}
								System.out.println("Stats Sort: " + "Sortings "
										+ sort_count + "  done");
								JFrame1.jList1("Stats Sort: " + "Sortings "
										+ sort_count + "  done");
								// --------------------------------------
								for (sj = 1; sj < statlistcounter; sj++) {
									k = Integer.parseInt(statlist[1][sj]);
									p = (float) k / (float) hundert_prozent
											* (float) 100; // %
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
									System.out.println("Stats Result: "
											+ (statlist[1][sj]) + " x "
											+ (statlist[0][sj]) + " = "
											+ statlist[2][sj] + "%");
									JFrame1.jList1("Stats Result: "
											+ (statlist[1][sj]) + " x "
											+ (statlist[0][sj]) + " = "
											+ statlist[2][sj] + "%");
								}
								// --------------------
								if (b.equals("Chart1")) {
									chartlist_header1 = (multicolumn[a][0]);
									chartlistcounter1 = statlistcounter;
									cal.chart.start(chartlistcounter1);
								}
								if (b.equals("Chart2")) {
									chartlist_header2 = (multicolumn[a][0]);
									chartlistcounter2 = statlistcounter;
									cal.chart2.start(chartlistcounter2);
								}
								if (b.equals("Chart3")) {
									chartlist_header3 = (multicolumn[a][0]);
									chartlistcounter3 = statlistcounter;
									cal.chart3.start(chartlistcounter3);
								}
								System.out.println("Stats End");
								JFrame1.jList1("Stats End");
							}
							// ---------------------------------------------
							if (command.equals("Autoexit")) {
								int a;
								a = Integer.parseInt(attribute1);
								if (a == 1) {
									System.exit(0);
								}
								System.out.println("Exit:" + a);
								JFrame1.jList1("Exit:" + a);

							}
							// -----------------------------------
						}
					}
				}
			}
			gui.JFrame1.jTextPane1.setText("Execute Handler: OK");
		} catch (Exception e) {

			JFrame1.jTextPane1.setText("Error: " + e);
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
		System.out.println("Init Table: Done");
		JFrame1.jList1("Init Table: Done");
	}

	// *****************************************************************************************
	public static void clearall() {
		int x;
		int y;
		for (x = 0; x < max_width; x++) {
			for (y = 0; y < max_high; y++) {
				multicolumn[x][y] = ("");

			}
		}
		System.out.println("Init Table: Done");
		JFrame1.jList1("Init Table: Done");
	}

	// *****************************************************************************************
	public static void create_handlerfile() {
		String filename = "." + File.separator + handlerpath;
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
				for (i = 0; i < textarray.length; i++) {
					bw.write(textarray[i]);
					bw.newLine();
				}
				// ------------------------------------------------
				bw.flush();
				bw.close();
				JFrame1.jList1("Created File in: " + filename);
				System.out.println("Created File in: " + filename);
				System.out.println("Text Array Length: " + textarray.length);
			}
		} catch (Exception e) {
			JFrame1.jTextPane1.setText("Error: " + e);
			e.printStackTrace();
		}
	}

	// *****************************************************************************************
	public static void writetext() {

		// System.out.println(textarray.length);
		// -----------------------------------------------
		for (i = 0; i < textarray.length; i++) {
			JFrame1.jList1(textarray[i]);
		}
		System.out.println("Created Help Text");
		System.out.println("Text Array Length: " + textarray.length);
	}

	// -----------------------------------------------------------------------
	public static void Quicksort_numbers(int start, int end, int spalte,
			String direction) {
		System.out.println("Quicksort Numbers : " + start + " " + end + " "
				+ spalte + " " + direction);
		try {
			if (start < end) {
				int ii;
				ii = partition_numbers(start, end, spalte, direction);
				Quicksort_numbers(start, ii - 1, spalte, direction);
				Quicksort_numbers(ii + 1, end, spalte, direction);
			}
		} catch (Exception e) {

			JFrame1.jTextPane1.setText("Error: " + e);
			e.printStackTrace();
		}
	}

	// ------------------------------------------------------------------------
	public static int partition_numbers(int start, int end, int spalte,
			String direction) {
		int pivot;
		int ii;
		int jj;
		String help;
		pivot = Integer.parseInt(multicolumn[spalte][end]);
		ii = start;
		jj = end - 1;
		try {
			// sortierung up
			if (direction.equals("up")) {
				while (ii <= jj) {
					if (Integer.parseInt(multicolumn[spalte][ii]) > pivot) {
						// tausche array [i] und array [j]
						// System.out.println("Quicksort Change1: " +
						// multicolumn[spalte][ii] + " " +
						// multicolumn[spalte][jj]);
						for (int g = 0; g < j; g++) {
							help = multicolumn[g][ii];
							multicolumn[g][ii] = multicolumn[g][jj];
							multicolumn[g][jj] = help;
						}
						sort_count++;
						jj--;
					} else
						ii++;
				}
				// tausche array [ii] und array [end]
				// System.out.println("Quicksort Change2: " +
				// multicolumn[spalte][ii] + " " + multicolumn[spalte][end]);
				for (int g = 0; g < j; g++) {
					help = multicolumn[g][ii];
					multicolumn[g][ii] = multicolumn[g][end];
					multicolumn[g][end] = help;
				}
				sort_count++;
			}
			// sortierung down
			if (direction.equals("down")) {
				while (ii <= jj) {
					if (Integer.parseInt(multicolumn[spalte][ii]) < pivot) {
						// tausche array [i] und array [j]
						// System.out.println("Quicksort Change1: " +
						// multicolumn[spalte][ii] + " " +
						// multicolumn[spalte][jj]);
						for (int g = 0; g < j; g++) {
							help = multicolumn[g][ii];
							multicolumn[g][ii] = multicolumn[g][jj];
							multicolumn[g][jj] = help;
						}
						sort_count++;
						jj--;
					} else
						ii++;
				}
				// tausche array [ii] und array [end]
				// System.out.println("Quicksort Change2: " +
				// multicolumn[spalte][ii] + " " + multicolumn[spalte][end]);
				for (int g = 0; g < j; g++) {
					help = multicolumn[g][ii];
					multicolumn[g][ii] = multicolumn[g][end];
					multicolumn[g][end] = help;
				}
				sort_count++;
			}
		} catch (Exception e) {

			JFrame1.jTextPane1.setText("Error:  Quicksort Numbers," + e);
			e.printStackTrace();
		}
		return ii;
	}

	// ----------------------------------------------------------------------------
	public static void Quicksort_strings(int start, int end, int spalte,
			String direction) {
		System.out.println("Quicksort Strings : " + start + " " + end + " "
				+ spalte + " " + direction);
		try {
			if (start < end) {
				int ii;
				ii = partition_strings(start, end, spalte, direction);
				Quicksort_strings(start, ii - 1, spalte, direction);
				Quicksort_strings(ii + 1, end, spalte, direction);
			}
		} catch (Exception e) {

			JFrame1.jTextPane1.setText("Error: " + e);
			e.printStackTrace();
		}
	}

	// ------------------------------------------------------------------------
	public static int partition_strings(int start, int end, int spalte,
			String direction) {
		String pivot;
		int ii;
		int jj;
		String help;
		pivot = (multicolumn[spalte][end]);
		ii = start;
		jj = end - 1;
		try {
			// sortierung up
			if (direction.equals("up")) {
				while (ii <= jj) {
					if ((multicolumn[spalte][ii]).compareTo(pivot) > 0) {
						// tausche array [ii] und array [jj]
						// System.out.println("Quicksort Change1: " +
						// multicolumn[spalte][ii] + " " +
						// multicolumn[spalte][jj]);
						for (int g = 0; g < j; g++) {
							help = multicolumn[g][ii];
							multicolumn[g][ii] = multicolumn[g][jj];
							multicolumn[g][jj] = help;
						}
						sort_count++;
						jj--;
					} else
						ii++;
				}
				// tausche array [ii] und array [end]
				// System.out.println("Quicksort Change2: " +
				// multicolumn[spalte][ii] + " " + multicolumn[spalte][end]);
				for (int g = 0; g < j; g++) {
					help = multicolumn[g][ii];
					multicolumn[g][ii] = multicolumn[g][end];
					multicolumn[g][end] = help;
				}
				sort_count++;
			}
			// sortierung down
			if (direction.equals("down")) {
				while (ii <= jj) {
					if ((multicolumn[spalte][ii]).compareTo(pivot) < 0) {
						// tausche array [i] und array [j]
						// System.out.println("Quicksort Change1: " +
						// multicolumn[spalte][ii] + " " +
						// multicolumn[spalte][jj]);
						for (int g = 0; g < j; g++) {
							help = multicolumn[g][ii];
							multicolumn[g][ii] = multicolumn[g][jj];
							multicolumn[g][jj] = help;
						}
						sort_count++;
						jj--;
					} else
						ii++;
				}
				// tausche array [ii] und array [end]
				// System.out.println("Quicksort Change2: " +
				// multicolumn[spalte][ii] + " " + multicolumn[spalte][end]);
				for (int g = 0; g < j; g++) {
					help = multicolumn[g][ii];
					multicolumn[g][ii] = multicolumn[g][end];
					multicolumn[g][end] = help;
				}
				sort_count++;
			}
		} catch (Exception e) {

			JFrame1.jTextPane1.setText("Error: Quicksort String," + e);
			e.printStackTrace();
		}
		return ii;
	}
	// ----------------------------------------------------------------------------
}
