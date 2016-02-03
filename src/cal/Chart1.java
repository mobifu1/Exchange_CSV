package cal;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

@SuppressWarnings("serial")
public class Chart1 extends JFrame {

	static int i;
	public static int xmaxWert;
	static double ymaxWert = 100;
	static double prozentwerte[] = new double[Calculation.max_stat_high];
	static String titel;

	public static class Diagramm extends JPanel {

		private double listwerte[];

		private void setlistwerte(double listwerte[]) {
			this.listwerte = listwerte;
			// ymaxWert =100;
		}

		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			super.paintComponent(g2);
			Dimension d = getSize(null);
			// Skalieren auf 100x100 mit y-Invertierung
			g2.scale(d.getWidth() / 100.0, -d.getHeight() / 100.0);
			// Ursprung verschieben nach (15,-90)
			g2.translate(15, -90);
			// Linien etwas dünner
			g2.setStroke(new BasicStroke(0.5f));
			// x-Achse zeichnen
			g2.draw(new Line2D.Double(0, 0, 85, 0));
			// y-Achse zeichnen
			g2.draw(new Line2D.Double(0, 0, 0, 85));
			// Linien etwas dicker
			// g2.setStroke(new BasicStroke(1.5f));
			// Farbverlauf von rot nach grün
			g2.setPaint(new GradientPaint(0f, 0f, Color.red, 0f, 85f,
					Color.green));
			// Säulen zeichnen
			for (int i = 0; i < xmaxWert - 1; i++) {
				// g2.draw(new Rectangle2D.Double(x, y,rectWidth,rectHeight));
				g2.fill(new Rectangle2D.Double(7 * i, 0, 7, listwerte[i]
						/ ymaxWert * 85));
			}
			// konstante Farbe dunkelgrau
			g2.setPaint(Color.darkGray);
			// Rahmen um Säulen zeichnen
			for (int i = 0; i < xmaxWert - 1; i++) {
				g2.draw(new Rectangle2D.Double(7 * i, 0, 7, listwerte[i]
						/ ymaxWert * 85));
			}
			// y-Invertierung rückgängig machen
			g2.scale(1, -1);
			// Beschriftung x-Achse
			// Fontgröße für Beschriftung ändern
			g2.setFont(g2.getFont().deriveFont(3f));
			for (int i = 0; i < xmaxWert - 1; i++) {
				g2.drawString(String.valueOf(i + 1), (i * 7) + 2, +5);
			}
			// Beschriftung Legende mit Beschriftung und Prozentzahlen
			// Fontgröße für Beschriftung ändern
			g2.setFont(g2.getFont().deriveFont(3f));
			for (int i = 0; i < xmaxWert - 1; i++) {
				// g2.drawString(cal.Calculation.chartlist1[0][i+1], i * 7,
				// +10);
				int length = cal.Calculation.chartlist1[0][i + 1].length();
				if (length > 14) {
					cal.Calculation.chartlist1[0][i + 1] = cal.Calculation.chartlist1[0][i + 1]
							.substring(0, 13);
				}
				int length1 = cal.Calculation.chartlist1[2][i + 1].length();
				if (length1 > 5) {
					cal.Calculation.chartlist1[2][i + 1] = cal.Calculation.chartlist1[2][i + 1]
							.substring(0, 4);
				}
				g2.drawString(String.valueOf(i + 1) + ". "
						+ cal.Calculation.chartlist1[0][i + 1] + " / "
						+ cal.Calculation.chartlist1[2][i + 1] + "% / "
						+ cal.Calculation.chartlist1[1][i + 1], 35,
						(-80 + (4 * i)));
			}
			// Beschriftung y-Achse
			// Fontgröße für Beschriftung ändern
			g2.setFont(g2.getFont().deriveFont(3f));
			for (int i = 0; i < ymaxWert; i = i + 10) {
				g2.drawString(i + "%", -10,
						-Math.round(((double) i) / ymaxWert * 85));
			}
		}
	}

	private Diagramm dgramm;

	public Chart1(String titel, int x, int y, int w, int h) {
		super(titel);
		this.setSize(w, h);
		this.setLocation(x, y);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dgramm = new Diagramm();
		this.setContentPane(dgramm);
	}

	private double[] testwerte() {
		// Erzeugen der Werte
		double testwerte[] = new double[xmaxWert];
		for (int i = 0; i < xmaxWert - 1; i++) {
			testwerte[i] = Double
					.parseDouble(cal.Calculation.chartlist1[2][i + 1]);
			//System.out.println("Prozent " + (i) + ": " + testwerte[i]);
		}
		return testwerte;
	}

	public static void main() {
		String now = new SimpleDateFormat("dd.MM.yyy hh:mm:ss")
				.format(new Date());
		titel = "Chart: " + Calculation.CHARTLIST_HEADER1 + "  " + now;
		int x = 900;
		int y = 600;
		int w = 1200;
		int h = 800;
		Chart1 f = new Chart1(titel, x, y, w, h);
		f.dgramm.setlistwerte(f.testwerte());
		f.setVisible(true);
	}

	public static void start(int chartlistcounter1) {

		xmaxWert = chartlistcounter1;
		if (xmaxWert > 13) {
			xmaxWert = 13;
		}
		main();
		{
		}
	}
}