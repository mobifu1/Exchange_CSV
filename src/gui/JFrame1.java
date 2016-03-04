package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import cal.Calculation;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
@SuppressWarnings("serial")
public class JFrame1 extends javax.swing.JFrame {
	private static int Y_HIGH = 345; // default 340
	private static int X_WIDE = 650; // default 650
	private static int TIME_VALUE_ms = 100;
	private JButton jButton1;
	public static JTextPane jTextPane1;
	public static JProgressBar jProgressBar1;
	public static JList<String> jList1;
	private JButton jButton7;
	private JButton jButton6;
	private JButton jButton5;
	private JButton jButton4;
	private JButton jButton3;
	private JScrollPane jScrollPane1;
	private JLabel jLabel1;
	private JSeparator jSeparator1;
	private JButton jButton2;
	static DefaultListModel<String> listModel = new DefaultListModel<String>();
	public static String TITEL = "Exchange CSV V2.5"; // V2.x

	// titel+substring = Version
	// public static String SUBVERSION = ".1-Alpha"; // Subversion
	//public static String SUBVERSION = ".9-Beta"; // Subversion
	public static String SUBVERSION = ".8-RC"; // Subversion
	// public static String SUBVERSION = "-R-Stable"; // Subversion
	// public static String SUBVERSION = "-R-Final"; // Subversion

	static String MASSAGE01 = "Application Started";
	static String MASSAGE02 = "Application Init";
	static String MASSAGE03 = "Read File";
	static String MASSAGE04 = "Input File";
	static String MASSAGE05 = "Write CSV File";
	static String MASSAGE06 = "Output File";
	static String MASSAGE07 = "Load Script";
	static String MASSAGE08 = "Load Script File";
	static String MASSAGE09 = "Create Script";
	static String MASSAGE10 = "Create New Script File";
	static String MASSAGE11 = "Init Application";
	static String MASSAGE12 = "Help";
	static String MASSAGE13 = "Help In Listbox";
	static String MASSAGE14 = "Progress";
	static String MASSAGE15 = "Wiki CSV";
	static String MASSAGE16 = "File Choose OK";
	static String MASSAGE17 = "Write XML File";

	// static String MASSAGE18 = ;
	// static String MASSAGE19 = ;
	// static String MASSAGE20 = ;
	// static String MASSAGE21 = ;
	// static String MASSAGE22 = ;

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame1 inst = new JFrame1();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				inst.setResizable(false);// Maximized=false
				inst.setPreferredSize(new java.awt.Dimension(X_WIDE, Y_HIGH));
				inst.setSize(X_WIDE, Y_HIGH);
				// this.setIconImage(Toolkit.getDefaultToolkit().getImage("images\\mcsv-icon-wp.png"));
			}
		});
	}

	public JFrame1() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			this.setTitle(TITEL + SUBVERSION);
			this.setPreferredSize(new java.awt.Dimension(X_WIDE, Y_HIGH));
			if (TITEL == "Exchange CSV V1.0") {
				getContentPane().setBackground(
						new java.awt.Color(215, 253, 252));
			}
			if (TITEL == "Exchange CSV V1.1") {
				getContentPane().setBackground(
						new java.awt.Color(206, 180, 254));
			}
			if (TITEL == "Exchange CSV V1.2") {
				getContentPane().setBackground(
						new java.awt.Color(254, 252, 188));
			}
			if (TITEL == "Exchange CSV V1.3") {
				getContentPane().setBackground(
						new java.awt.Color(212, 208, 200));
			}
			if (TITEL == "Exchange CSV V1.4") {
				getContentPane().setBackground(
						new java.awt.Color(128, 255, 128));
			}
			if (TITEL == "Exchange CSV V1.5") {
				getContentPane().setBackground(
						new java.awt.Color(215, 253, 252));
			}
			if (TITEL == "Exchange CSV V1.6") {
				getContentPane().setBackground(
						new java.awt.Color(119, 219, 239));
			}
			if (TITEL == "Exchange CSV V1.7") {
				getContentPane().setBackground(
						new java.awt.Color(119, 219, 239));
			}
			if (TITEL == "Exchange CSV V1.8") {
				getContentPane().setBackground(
						new java.awt.Color(119, 219, 239));
			}
			if (TITEL == "Exchange CSV V1.9") {
				getContentPane().setBackground(
						new java.awt.Color(119, 219, 239));
			}
			if (TITEL == "Exchange CSV V2.0") {
				getContentPane().setBackground(
						new java.awt.Color(119, 219, 239));
			}
			if (TITEL == "Exchange CSV V2.1") {
				getContentPane().setBackground(
						new java.awt.Color(206, 180, 254));
			}
			if (TITEL == "Exchange CSV V2.2") {
				getContentPane().setBackground(
						new java.awt.Color(119, 219, 239));
			}
			if (TITEL == "Exchange CSV V2.3") {
				getContentPane().setBackground(
						new java.awt.Color(119, 219, 239));
			}
			if (TITEL == "Exchange CSV V2.4") {
				getContentPane().setBackground(
						new java.awt.Color(128, 255, 128));
			}
			if (TITEL == "Exchange CSV V2.5") {
				getContentPane().setBackground(
						new java.awt.Color(119, 219, 239));
			}
			if (TITEL == "Exchange CSV V2.6") {
				getContentPane().setBackground(
						new java.awt.Color(119, 219, 239));
			}
			if (TITEL == "Exchange CSV V2.7") {
				getContentPane().setBackground(
						new java.awt.Color(119, 219, 239));
			}
			if (TITEL == "Exchange CSV V2.8") {
				getContentPane().setBackground(
						new java.awt.Color(119, 219, 239));
			}
			if (TITEL == "Exchange CSV V2.9") {
				getContentPane().setBackground(
						new java.awt.Color(119, 219, 239));
			}
			if (TITEL == "Exchange CSV V3.0") {
				getContentPane().setBackground(
						new java.awt.Color(119, 219, 239));
			}
			{
				jButton1 = new JButton();
				getContentPane().add(jButton1);
				jButton1.setText(MASSAGE03);
				jButton1.setBounds(507, 47, 115, 23);
				jButton1.setBorder(BorderFactory
						.createBevelBorder(BevelBorder.RAISED));
				jButton1.setToolTipText(MASSAGE04);
				jButton1.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						jButton1ActionPerformed(evt);
					}
				});
			}
			{
				jButton2 = new JButton();
				getContentPane().add(jButton2);
				jButton2.setText(MASSAGE05);
				jButton2.setBounds(507, 117, 115, 23);
				jButton2.setBorder(BorderFactory
						.createBevelBorder(BevelBorder.RAISED));
				jButton2.setToolTipText(MASSAGE06);
				jButton2.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						jButton2ActionPerformed(evt);
					}
				});
			}
			{
				jButton3 = new JButton();
				getContentPane().add(jButton3);
				jButton3.setText(MASSAGE07);
				jButton3.setBounds(507, 82, 115, 23);
				jButton3.setBorder(BorderFactory
						.createBevelBorder(BevelBorder.RAISED));
				jButton3.setToolTipText(MASSAGE08);
				jButton3.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						jButton3ActionPerformed(evt);
					}
				});
			}
			{
				jButton4 = new JButton();
				getContentPane().add(jButton4);
				jButton4.setText(MASSAGE09);
				jButton4.setBounds(507, 238, 115, 23);
				jButton4.setBorder(BorderFactory
						.createBevelBorder(BevelBorder.RAISED));
				jButton4.setToolTipText(MASSAGE10);
				jButton4.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						jButton4ActionPerformed(evt);
					}
				});
			}
			{
				jButton5 = new JButton();
				getContentPane().add(jButton5);
				jButton5.setBounds(507, 12, 16, 16);
				jButton5.setBorder(BorderFactory
						.createBevelBorder(BevelBorder.RAISED));
				jButton5.setToolTipText(MASSAGE11);
				jButton5.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						jButton5ActionPerformed(evt);
					}
				});
			}
			{
				jButton6 = new JButton();
				getContentPane().add(jButton6);
				jButton6.setBounds(507, 203, 115, 23);
				jButton6.setBorder(BorderFactory
						.createBevelBorder(BevelBorder.RAISED));
				jButton6.setText(MASSAGE12);
				jButton6.setToolTipText(MASSAGE13);
				jButton6.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						jButton6ActionPerformed(evt);
					}
				});
			}
			{
				jButton7 = new JButton();
				getContentPane().add(jButton7);
				jButton7.setText(MASSAGE17);
				jButton7.setBorder(BorderFactory
						.createBevelBorder(BevelBorder.RAISED));
				jButton7.setToolTipText(MASSAGE17);
				jButton7.setBounds(507, 152, 115, 23);
				jButton7.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jButton7ActionPerformed(evt);
					}
				});
			}
			{
				jScrollPane1 = new JScrollPane();
				getContentPane().add(jScrollPane1);
				jScrollPane1.setBounds(29, 13, 466, 250);
				jScrollPane1.getHorizontalScrollBar().setEnabled(true);
				{
					// Swing components are NOT thread-safe and may
					// sometimes-throw exceptions.
					// http://stackoverflow.com/questions/3440360/jlist-throws-arrayindexoutofboundsexceptions-randomly
					jList1 = new JList<String>();
					jScrollPane1.setViewportView(jList1);
					jList1.setModel(listModel);
					jList1.setBounds(109, 109, 395, 152);
					jList1.setBorder(BorderFactory
							.createBevelBorder(BevelBorder.RAISED));
					Font font = new Font("Courier", Font.PLAIN, 10);
					jList1.setFont(font);
				}
			}
			{
				jTextPane1 = new JTextPane();
				getContentPane().add(jTextPane1);
				jTextPane1.setText(MASSAGE01);
				jTextPane1.setBounds(29, 271, 593, 20);
				jTextPane1.setBorder(BorderFactory
						.createBevelBorder(BevelBorder.RAISED));
				jTextPane1.setEditable(false);
			}
			{
				jProgressBar1 = new JProgressBar();
				getContentPane().add(jProgressBar1);
				jProgressBar1.setBounds(29, 297, 593, 7);
				jProgressBar1.setToolTipText(MASSAGE14);
				// jProgressBar1.setMaximum(100);
				jProgressBar1.setMinimum(0);
				jProgressBar1.setValue(0);
			}
			{
				jSeparator1 = new JSeparator();
				getContentPane().add(jSeparator1);
				jSeparator1.setBounds(507, 187, 115, 5);
			}
			{
				jLabel1 = new JLabel();
				getContentPane().add(jLabel1);
				jLabel1.setBounds(550, 9, 32, 32);
				pack();
				this.setSize(X_WIDE, Y_HIGH);
				// Set Icon-----
				jLabel1.setToolTipText(MASSAGE15);
				jLabel1.setText(MASSAGE15);
				jLabel1.setIcon(new ImageIcon(getClass().getClassLoader()
						.getResource("gui/csv.gif")));

				jLabel1.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						try {
							try {
								openUrl(Calculation.url1);
							} catch (IOException e1) {

								e1.printStackTrace();
							}
						} catch (URISyntaxException e1) {

							e1.printStackTrace();
						}
					}
				});
			}
		} catch (Exception e) {
			// add your error handling code here
			e.printStackTrace();
			// System.out.println(e);
			JFrame1.jList1.updateUI();
		}
	}

	private void jButton1ActionPerformed(ActionEvent evt) {
		// System.out.println("jButton1.actionPerformed, event=" + evt);

		JFileChooser fc1 = new JFileChooser();
		fc1.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int status = fc1.showOpenDialog(null);
		if (status == JFileChooser.APPROVE_OPTION) {
			// System.out.println(MASSAGE16);
			File selFile = fc1.getSelectedFile();
			int length = (selFile.getPath().length());// string.length of path

			// System.out.println(selFile.getPath().substring(length -4,
			// length));

			if (selFile.getPath().substring(length - 4, length).equals(".csv")) {
				Calculation.main("readcsvfile", selFile.getPath());
			}
			if (selFile.getPath().substring(length - 4, length).equals(".xml")) {
				Calculation.main("readxmlfile", selFile.getPath());
			}
			refresh_jlist();
		}
	}

	private void jButton2ActionPerformed(ActionEvent evt) {
		// System.out.println("jButton2.actionPerformed, event=" + evt);
		Calculation.main("writecsvfile", "");
		refresh_jlist();
	}

	private void jButton7ActionPerformed(ActionEvent evt) {
		// System.out.println("jButton2.actionPerformed, event=" + evt);
		Calculation.main("writexmlfile", "");
		refresh_jlist();
	}

	private void jButton3ActionPerformed(ActionEvent evt) {
		// System.out.println("jButton3.actionPerformed, event=" + evt);
		JFileChooser fc1 = new JFileChooser();
		fc1.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int status = fc1.showOpenDialog(null);
		if (status == JFileChooser.APPROVE_OPTION) {
			// System.out.println(MASSAGE16);
			File selFile = fc1.getSelectedFile();
			Calculation.main("readscriptfile", selFile.getPath());
			refresh_jlist();
		}
	}

	private void jButton4ActionPerformed(ActionEvent evt) {
		// System.out.println("jButton4.actionPerformed, event=" + evt);
		Calculation.main("writescriptfile", "");
		refresh_jlist();
	}

	public static void jList1(String oroww) {
		try {
			JFrame1.listModel.addElement(oroww);
		} catch (Exception e) {
			// add your error handling code here
			// System.out.println("jList1:"+e);
			JFrame1.jList1.updateUI();
		}
	}

	private void jButton5ActionPerformed(ActionEvent evt) {
		// System.out.println("jButton5.actionPerformed, event=" + evt);
		listModel.clear();
		Calculation.main("clearall", "");
		refresh_jlist();
		jProgressBar1.setValue(0);
		jTextPane1.setText(MASSAGE02);
	}

	private void jButton6ActionPerformed(ActionEvent evt) {
		// System.out.println("jButton6.actionPerformed, event=" + evt);
		Calculation.main("writehelptext", "");
		refresh_jlist();
	}

	public void openUrl(String url) throws IOException, URISyntaxException {
		if (java.awt.Desktop.isDesktopSupported()) {
			java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

			if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
				java.net.URI uri = new java.net.URI(url);
				desktop.browse(uri);
			}
		}
	}

	private void refresh_jlist() {
		try {
			// Swing components are NOT thread-safe and may
			// sometimes-throw exceptions.
			// http://stackoverflow.com/questions/3440360/jlist-throws-arrayindexoutofboundsexceptions-randomly
			// this is a workaround for the problem
			Thread.sleep(TIME_VALUE_ms);
			JFrame1.jList1.updateUI();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
