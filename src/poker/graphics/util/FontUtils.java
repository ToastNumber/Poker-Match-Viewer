package poker.graphics.util;

import java.awt.Font;
import java.awt.Graphics2D;

public class FontUtils {
	public static Font getLargestPossibleFont(Graphics2D g2, int width, int height, String s) {
		g2.setFont(new Font("arial", 0, 10));
		
		Font prev = null;
		Font current = g2.getFont();
		String fontFamily = current.getFamily();

		int prevFontSize = g2.getFont().getSize();

		do {
			prev = copyFont(current);

			current = new Font(fontFamily, 0, prevFontSize + 1);
			++prevFontSize;

			g2.setFont(current);
		} while ((g2.getFontMetrics()).stringWidth(s) < width && g2.getFontMetrics().getHeight() < height);

		return prev;
	}

	public static Font copyFont(Font font) {
		return new Font(font.getFamily(), font.getStyle(), font.getSize());
	}
}
