package elementalist_mod.ui;

import basemod.abstracts.CustomEnergyOrb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;


public class EnergyOrbNavy extends CustomEnergyOrb {
    Texture ENERGY_LAYER1;
    Texture ENERGY_LAYER2;
    Texture ENERGY_LAYER3;
    Texture ENERGY_LAYER4;
    Texture ENERGY_LAYER5;
    Texture ENERGY_LAYER6;
    Texture ENERGY_LAYER7;
    public static float fontScale = 1.0F;
    private static final float ORB_IMG_SCALE = 1.15F * Settings.scale;
    private float angle1 = 0.0F;
    private float angle2 = 0.0F;
    private float angle3 = 0.0F;
    private float angle4 = 0.0F;
    private float angle5 = 0.0F;
    
    //private HashMap<String, Pixmap> pixmapList = new HashMap<String, Pixmap>();

    public EnergyOrbNavy() {
        super(null,null,null);
        

        ENERGY_LAYER1 = ImageMaster.loadImage("img/ui/navy/back.png");
        ENERGY_LAYER2 = ImageMaster.loadImage("img/ui/navy/starburst.png");
        ENERGY_LAYER3 = ImageMaster.loadImage("img/ui/navy/centerglow.png");
        ENERGY_LAYER4 = ImageMaster.loadImage("img/ui/navy/colorwheel.png");
        ENERGY_LAYER5 = ImageMaster.loadImage("img/ui/navy/swirl.png");
        ENERGY_LAYER6 = ImageMaster.loadImage("img/ui/navy/border.png");
        ENERGY_LAYER7 = ImageMaster.loadImage("img/ui/navy/glass.png");

    }

    /*private Pixmap getPixmap(Texture texture) {
        textureData = texture.getTextureData();
        textureData.prepare();
        return textureData.consumePixmap();
	}*/

	@Override
    public void updateOrb(int orbCount) {
        if(orbCount == 0){
            this.angle1 += Gdx.graphics.getDeltaTime() * -8.0F;
            this.angle2 += Gdx.graphics.getDeltaTime() * 5.0F;
            this.angle3 += Gdx.graphics.getDeltaTime() * -4.0F;
            this.angle4 += Gdx.graphics.getDeltaTime() * 8.0F;
            this.angle5 += Gdx.graphics.getDeltaTime() * -6.0F;
        } else {
            this.angle1 += Gdx.graphics.getDeltaTime() * -40.0F;
            this.angle2 += Gdx.graphics.getDeltaTime() * 20.0F;
            this.angle3 += Gdx.graphics.getDeltaTime() * -16.0F;
            this.angle4 += Gdx.graphics.getDeltaTime() * 40.0F;
            this.angle5 += Gdx.graphics.getDeltaTime() * -30.0F;
        }
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
    	
        //SpriteBatch sbTmp = new SpriteBatch();
        /*if (enabled) {
            sb.setColor(Color.WHITE);
            sb.end();

            sbTmp.enableBlending();
            sbTmp.begin();
            sbTmp.setColor(Color.WHITE);
            sbTmp.draw(this.ENERGY_BEIGE_LAYER1, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle1, 0, 0, 256, 256, false, false);
            sbTmp.draw(this.ENERGY_BEIGE_LAYER2, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle2, 0, 0, 256, 256, false, false);
            sbTmp.draw(this.ENERGY_BEIGE_LAYER3, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle3, 0, 0, 256, 256, false, false);
            sbTmp.setBlendFunction(770, 1); //Copied from silent
            sbTmp.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
            sbTmp.draw(this.ENERGY_BEIGE_LAYER4, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle4, 0, 0, 256, 256, false, false);

            sbTmp.setBlendFunction(GL20.GL_ZERO,GL20.GL_ONE_MINUS_SRC_ALPHA); //Should let the background image through
            sbTmp.draw(this.ENERGY_BEIGE_MASK, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0F, 0, 0, 256, 256, false, false);
            sbTmp.setBlendFunction(770, 771);
            Gdx.gl.glColorMask(true, true, true, true);
            sbTmp.end();

            sb.begin();
            sb.draw(this.ENERGY_BEIGE_LAYER5, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0F, 0, 0, 256, 256, false, false);
            sb.draw(this.ENERGY_BEIGE_LAYER6, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0F, 0, 0, 256, 256, false, false);
        }else{*/
            sb.setColor(Color.WHITE);
            
            //sb.setBlendFunction(770, 771);
            sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            sb.draw(this.ENERGY_LAYER1, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle1, 	0, 0, 256, 256, false, false);

            //if(true) return;
            sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
            sb.draw(this.ENERGY_LAYER2, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle2, 	0, 0, 256, 256, false, false);
            sb.draw(this.ENERGY_LAYER3, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle3, 	0, 0, 256, 256, false, false);
            sb.draw(this.ENERGY_LAYER4, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle4, 	0, 0, 256, 256, false, false);
            sb.draw(this.ENERGY_LAYER5, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle5, 	0, 0, 256, 256, false, false);

            sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            sb.draw(this.ENERGY_LAYER6, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0, 			0, 0, 256, 256, false, false);

            sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
            sb.draw(this.ENERGY_LAYER7, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0, 			0, 0, 256, 256, false, false);

            sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            
            
            //sb.setBlendFunction(GL20.GL_DST_ALPHA, GL20.GL_SRC_COLOR);
            
            //sb.draw(this.ENERGY_BEIGE_MASK3, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0F, 0, 0, 256, 256, false, false);
            
            //sb.setBlendFunction(770, 771);
            

            //sb.draw(this.ENERGY_BEIGE_LAYER5D, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0F, 0, 0, 256, 256, false, false);
            //sb.draw(this.ENERGY_BEIGE_LAYER6, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0F, 0, 0, 256, 256, false, false);
        //}
        
    }
	
    /*@Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
        sb.setColor(Color.WHITE);
        pixmap.drawPixmap(pixmapList.get("layer1"), 0, 0);
        pixmap.drawPixmap(pixmapList.get("layer2"), 0, 0);
        pixmap.drawPixmap(pixmapList.get("layer3"), 0, 0);
        pixmap.drawPixmap(pixmapList.get("layer4"), 0, 0);
        pixmap.drawPixmap(pixmapList.get("layer5"), 0, 0);
        pixmap.drawPixmap(pixmapList.get("layer6"), 0, 0);
        pixmap.drawPixmap(pixmapList.get("mask2"), 0, 0);
        //sb.setBlendFunction(GL20.GL_ZERO, GL20.GL_SRC_ALPHA);
        //texture2.draw(pixmapList.get("layer3"), 0, 0);
        //maskPixmap(pixmap, pixmapList.get("mask2"));
        texture2 = new Texture(pixmap);

        //sb.draw(texture2, current_x-50, current_y-50);
        //sb.setBlendFunction(770, 771);
        //sb.draw(texture2, current_x, current_y);
        
        //sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        sb.setBlendFunction(770, 771);
        sb.draw(ENERGY_BEIGE_MASK3, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle1, 0, 0, 256, 256, false, false);
        
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        sb.draw(texture2, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle1, 0, 0, 256, 256, false, false);
        sb.draw(ENERGY_BEIGE_LAYER5D, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0F, 0, 0, 256, 256, false, false);
        sb.draw(ENERGY_BEIGE_LAYER6, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0F, 0, 0, 256, 256, false, false);
        

        sb.setBlendFunction(770, 771);
    }*/
    
    /*
    private void maskPixmap(Pixmap image, Pixmap mask) {
    	Color imageCol, maskCol, maskedCol;
    	for(int x=0; x<image.getWidth(); x++) {
    		for(int y=0; y<image.getHeight(); y++) {
    			imageCol = new Color(image.getPixel(x, y));
    			maskCol = new Color(mask.getPixel(x, y));
    			maskedCol = new Color(imageCol.r, imageCol.g, imageCol.b, maskCol.a);
    			image.drawPixel(x, y, Color.argb8888(maskedCol));
    		}
    	}
    }

    private void drawMask(SpriteBatch sb, boolean enabled, float current_x, float current_y){
        Gdx.gl.glColorMask(false, false, false, true);
        sb.setBlendFunction(GL20.GL_ONE, GL20.GL_ZERO);
        sb.draw(this.ENERGY_BEIGE_MASK, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0F, 0, 0, 256, 256, false, false);
        sb.flush();
    }
    */

}
