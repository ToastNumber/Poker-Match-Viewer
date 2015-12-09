package poker.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class Circle {
	private final int x;
	private final int y;
	private final int radius;
	
	public Circle(int x, int y, int radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	public void fill(Graphics2D g2) {
		Color initialColor = g2.getColor();
		Stroke initialStroke = g2.getStroke();
		
		g2.setColor(Color.BLACK);
		g2.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
		
		g2.setStroke(new BasicStroke(5));
		g2.setColor(Color.WHITE);
		g2.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
		
		g2.setColor(initialColor);
		g2.setStroke(initialStroke);
	}
}
