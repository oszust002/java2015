package pl.edu.agh.kis.project.main.gui.cells;

import pl.edu.agh.kis.project.main.model.coords.Coords2D;
import pl.edu.agh.kis.project.main.model.states.AntState;
import pl.edu.agh.kis.project.main.model.states.CellState;
import pl.edu.agh.kis.project.main.model.states.LangtonCell;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Kamil on 05.01.2016.
 */
public class LangtonCellPaint extends BinaryStatePaint{
    private Image ant;
    private final Map<AntState, Image> antDirectionsImages;
    public LangtonCellPaint(Coords2D size) throws IOException {
        super(size);
        ant = loadImage("images/ant.png");
        if(ant == null)
            throw new FileNotFoundException("Failed to load an image");
        antDirectionsImages = getAntDirectionsImages();
    }

    @Override
    public void paint(Graphics g, Coords2D coords, CellState state) {
        LangtonCell cell = (LangtonCell) state;
        super.paint(g, coords, cell.cellState);
        if (cell.hasAnt())
            paintAnt(g,coords,cell);

    }

    private static Image loadImage(String path) throws IOException {
        URL resourceURL = LangtonCellPaint.class.getClassLoader().getResource(path);
        if(resourceURL == null)
            throw new FileNotFoundException("Not found ant image in path: " + path);
        return ImageIO.read(resourceURL);
    }

    private void paintAnt(Graphics g, Coords2D coords, LangtonCell cell) {
        g.drawImage(antDirectionsImages.get(cell.antState),coords.x,coords.y,null);
    }

    private Map<AntState,Image> getAntDirectionsImages(){
        int width = rectSize.x;
        int height = rectSize.y;

        if(width == 0 || height == 0)
            return null;
        TreeMap<AntState,Image> imageTreeMap = new TreeMap<AntState,Image>();
        for(AntState state: AntState.values()){
            if(state == AntState.NORTH)
                imageTreeMap.put(state,rotateAndScale(ant,width,height,0));
            else if (state == AntState.EAST)
                imageTreeMap.put(state,rotateAndScale(ant,width,height,90));
            else if (state == AntState.SOUTH)
                imageTreeMap.put(state,rotateAndScale(ant,width,height,180));
            else if(state == AntState.WEST){
                imageTreeMap.put(state,rotateAndScale(ant,width,height,270));
            }
        }
        return imageTreeMap;
    }

    private Image rotateAndScale(Image image, int width, int height, int rotateDegree){
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        BufferedImage rotatedImage = new BufferedImage(imageWidth,imageHeight,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = rotatedImage.createGraphics();
        g.drawImage(image, AffineTransform.getRotateInstance(Math.toRadians(rotateDegree),imageWidth/(double) 2,
                imageHeight/(double) 2),
                null);
        g.dispose();

        return rotatedImage.getScaledInstance(width,height,Image.SCALE_SMOOTH);
    }
}
