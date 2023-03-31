import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class StickerGenerator {

    public void create(InputStream inputStream, String fileName) throws Exception {
        BufferedImage originalImg = ImageIO.read(inputStream);

        int width = originalImg.getWidth();
        int height = originalImg.getHeight();
        int newHeight = height + 200;
        BufferedImage newImg = new BufferedImage(width, newHeight, BufferedImage.TRANSLUCENT);

        Graphics2D graphics = (Graphics2D) newImg.getGraphics();
        graphics.drawImage(originalImg, 0, 0, null);

        Font font = new Font("Impact", Font.BOLD, 80);
        
        graphics.setFont(font);
        graphics.setColor(Color.YELLOW);
        
        var str = "TOPZERA";

        FontMetrics metrics = graphics.getFontMetrics(font);
        int x = (width / 2) - (metrics.stringWidth(str) / 2);
        int y = newHeight - 100 + (metrics.getAscent() / 2);

        graphics.drawString(str, x, y);

        FontRenderContext context = graphics.getFontRenderContext();
        var layout = new TextLayout(str, font, context);
        Shape outline = layout.getOutline(null);
        AffineTransform transform = graphics.getTransform();
        transform.translate(x, y);
       
        graphics.setTransform(transform);
        graphics.setStroke(new BasicStroke(width * 0.004f));
        graphics.setColor(Color.BLACK);
        graphics.draw(outline);
        graphics.setClip(outline);

        ImageIO.write(newImg, "png", new File(fileName));
    }
}
