package presentacion;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to load and cache game images.
 * Handles all sprite loading and provides scaled versions.
 * 
 * @authors Alejandra Beltran - Adrian Ducuara
 */
public class ImageLoader {
    
    private static final Map<String, BufferedImage> imageCache = new HashMap<>();
    private static final String IMAGE_PATH = "/resources/images/";
    
    /**
     * Loads an image from resources and caches it.
     * 
     * @param imageName name of the image file (with extension)
     * @return the loaded image, or null if not found
     */
    public static BufferedImage loadImage(String imageName) {
        // Si ya está en caché, retornarla
        if (imageCache.containsKey(imageName)) {
            return imageCache.get(imageName);
        }
        
        try {
            // Intentar cargar desde resources
            InputStream stream = ImageLoader.class.getResourceAsStream(IMAGE_PATH + imageName);
            
            if (stream != null) {
                BufferedImage image = ImageIO.read(stream);
                imageCache.put(imageName, image);
                System.out.println("Imagen cargada: " + imageName);
                return image;
            } else {
                System.err.println("No se encontró la imagen: " + IMAGE_PATH + imageName);
                return createPlaceholderImage(imageName);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar imagen " + imageName + ": " + e.getMessage());
            return createPlaceholderImage(imageName);
        }
    }
    
    /**
     * Creates a placeholder image when the real image is not found.
     * This prevents crashes and shows what's missing.
     * 
     * @param imageName name of the missing image
     * @return a colored square as placeholder
     */
    private static BufferedImage createPlaceholderImage(String imageName) {
        BufferedImage placeholder = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = placeholder.createGraphics();
        
        // Color basado en el nombre de la imagen
        if (imageName.contains("vanilla")) {
            g.setColor(Color.WHITE);
        } else if (imageName.contains("strawberry")) {
            g.setColor(new Color(255, 182, 193));
        } else if (imageName.contains("chocolate")) {
            g.setColor(new Color(139, 69, 19));
        } else if (imageName.contains("grape")) {
            g.setColor(new Color(128, 0, 128));
        } else if (imageName.contains("banana")) {
            g.setColor(Color.YELLOW);
        } else if (imageName.contains("cherry")) {
            g.setColor(Color.RED);
        } else if (imageName.contains("pineapple")) {
            g.setColor(new Color(255, 200, 0));
        } else if (imageName.contains("troll")) {
            g.setColor(Color.GREEN);
        } else if (imageName.contains("pot")) {
            g.setColor(new Color(139, 69, 19));
        } else if (imageName.contains("squid")) {
            g.setColor(Color.ORANGE);
        } else if (imageName.contains("ice")) {
            g.setColor(new Color(173, 216, 230));
        } else {
            g.setColor(Color.GRAY);
        }
        
        g.fillRect(0, 0, 40, 40);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, 39, 39);
        
        g.dispose();
        return placeholder;
    }
    
    /**
     * Scales an image to the specified size.
     * 
     * @param image original image
     * @param width target width
     * @param height target height
     * @return scaled image
     */
    public static BufferedImage scaleImage(BufferedImage image, int width, int height) {
        if (image == null) return null;
        
        BufferedImage scaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaled.createGraphics();
        
        // Configurar para mejor calidad
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                          RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, 
                          RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                          RenderingHints.VALUE_ANTIALIAS_ON);
        
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        
        return scaled;
    }
    
    /**
     * Pre-loads all game images at startup.
     * Call this during game initialization to avoid lag during gameplay.
     */
    public static void preloadImages() {
        System.out.println("Precargando imágenes del juego...");
        
        // Helados
        loadImage("vanilla_ice.png");
        loadImage("strawberry_ice.png");
        loadImage("chocolate_ice.png");
        
        // Frutas
        loadImage("grape.png");
        loadImage("banana.png");
        loadImage("cherry.png");
        loadImage("pineapple.png");
        
        // Enemigos
        loadImage("troll.png");
        loadImage("pot.png");
        loadImage("orange_squid.png");
        
        // Obstáculos
        loadImage("ice_block.png");
        
        System.out.println("Imágenes precargadas: " + imageCache.size());
    }
    
    /**
     * Clears the image cache to free memory.
     */
    public static void clearCache() {
        imageCache.clear();
    }
}