package poker.graphics;

import java.awt.Color;
import java.awt.Graphics2D;

import poker.graphics.util.FontUtils;

public class PlayerBubble {
	private final String name;
	private final String action;
	private final int chipCount;
	private final int x;
	private final int y;
	private final int radius;

	public PlayerBubble(String name, String action, int chipCount, int x, int y, int radius) {
		super();
		this.name = name;
		this.action = action;
		this.chipCount = chipCount;
		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	/**
	 * Draws a circle split into three horizontal sections: action, name, and
	 * chip count.
	 * 
	 * @param g2
	 *            the graphics object being used to draw
	 * @param x
	 *            the x coordinate of the center of the bubble
	 * @param y
	 *            the y coordinate of the center of the bubble
	 * @param radius
	 *            the radius of the bubble
	 */
	public void draw(Graphics2D g2) {
		(new Circle(x, y, radius)).fill(g2);
		
		int y1 = y - radius / 3;
		int y2 = y + radius / 3;

		int x1 = (int) (x - 0.9 * (Math.sqrt(2) * radius) / 1.5);
		int x2 = (int) (x + 0.9 * (Math.sqrt(2) * radius) / 1.5);
		
		g2.setColor(Color.WHITE);
		g2.drawLine(x1, y1, x2, y1);
		g2.drawLine(x1, y2, x2, y2);
		
		// Write the info
		g2.setFont(FontUtils.getLargestPossibleFont(g2, radius, radius, name));
		int nameWidth = g2.getFontMetrics().stringWidth(name);
		g2.drawString(name, x - nameWidth/2, y + radius / 12);

		g2.setColor(new Color(100, 100, 255));
		g2.setFont(FontUtils.getLargestPossibleFont(g2, radius, radius, action));
		int actionWidth = g2.getFontMetrics().stringWidth(action);
		g2.drawString(action, x - actionWidth/2, y - (4 * radius) / 9);
		
		g2.setColor(Color.WHITE);
		g2.setFont(FontUtils.getLargestPossibleFont(g2, radius/2, radius/2, "" + chipCount));
		int chipWidth = g2.getFontMetrics().stringWidth("" + chipCount);
		g2.drawString("" + chipCount, x - chipWidth/2, y + (7 * radius) / 9);
		
	}
}










