package elementalist_mod.characters;

import basemod.BaseMod;
import basemod.abstracts.CustomPlayer;
import basemod.abstracts.CustomSavable;
import elementalist_mod.CardList;
import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.basic.*;
import elementalist_mod.patches.AbstractCardEnum;
import elementalist_mod.patches.TheElementalistEnum;
import elementalist_mod.relics.MagusStaff;
import elementalist_mod.ui.EnergyOrbNavy;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;

public class TheElementalist extends CustomPlayer implements CustomSavable<ArrayList<Integer>> {
	public static final int ENERGY_PER_TURN = 3;

	public static final int STARTING_HP = 70;
	public static final int MAX_HP = 70;
	public static final int ORB_SLOTS = 0;
	public static final int STARTING_GOLD = 99;
	public static final int HAND_SIZE = 5;
	public static int totalExp = 0;
	public static int level = 1;
	
	public static float rotationIndex = 0f;
	float[] circleRotationTarget = new float[6];
	float[] circleRotationActual = new float[6];

    public static Texture[] magic_circle = new Texture[ElementalistMod.MAGIC_CIRCLE.length];
    
    public static Texture testTexture;
    
    static {
    	for(int i=0; i<magic_circle.length; i++) {
    		//ElementalistMod.log("Loading texture '"+ElementalistMod.MAGIC_CIRCLE[i]+"'");
    		magic_circle[i] = ImageMaster.loadImage(ElementalistMod.MAGIC_CIRCLE[i]);
    	}
    }

	public static final String[] orbTextures = { "img/char/elementalist/orb/layer1.png",
			"img/char/elementalist/orb/layer2.png", "img/char/elementalist/orb/layer3.png",
			"img/char/elementalist/orb/layer4.png", "img/char/elementalist/orb/layer5.png",
			"img/char/elementalist/orb/layer6.png", "img/char/elementalist/orb/layer1d.png",
			"img/char/elementalist/orb/layer2d.png", "img/char/elementalist/orb/layer3d.png",
			"img/char/elementalist/orb/layer4d.png", "img/char/elementalist/orb/layer5d.png", };

	public static final float[] layerSpeeds = { 80.0F, 40.0F, -40.0F, 20.0F, 0.0F, 16.0F, 8.0F, -8.0F, 5.0F, 0.0F, };

	public TheElementalist(String name, PlayerClass setClass) {
		super(name, setClass, orbTextures, "img/char/elementalist/orb/vfx.png", layerSpeeds, null, null);
		testTexture = ImageMaster.loadImage("img/cards/elemental_defend.png");
		circleRotationTarget[0] = 0;

		initializeClass(ElementalistMod.makePath(ElementalistMod.ELEMENTALIST_MAIN),
				ElementalistMod.makePath(ElementalistMod.ELEMENTALIST_SHOULDER_2),
				ElementalistMod.makePath(ElementalistMod.ELEMENTALIST_SHOULDER_1),
				ElementalistMod.makePath(ElementalistMod.ELEMENTALIST_CORPSE), getLoadout(), 20.0F, -10.0F, 220.0F,
				290.0F, new EnergyManager(ENERGY_PER_TURN));
		// if (ElementalistMod.vs == null) {
		// ElementalistMod.vs = new VisionScreen();
		// }
		this.energyOrb = new EnergyOrbNavy();

        BaseMod.addSaveField("elementalistExp", this);
	}

	@Override
	public CardColor getCardColor() {
		return AbstractCardEnum.ELEMENTALIST_BLUE;
	}

	@Override
	public Color getCardRenderColor() {
		return ElementalistMod.ELEMBLUE;
	}

	@Override
	public Color getCardTrailColor() {
		return ElementalistMod.ELEMBLUE;
	}

	public CharSelectInfo getLoadout() { // the rest of the character loadout so includes your character select screen
											// info plus hp and starting gold
		return new CharSelectInfo("The Elementalist", "A wandering mage drawn in by the spire's magic.", STARTING_HP, MAX_HP, ORB_SLOTS,
				STARTING_GOLD, HAND_SIZE, this, getStartingRelics(), getStartingDeck(), false);
	}

	@Override
	public Color getSlashAttackColor() {
		return ElementalistMod.ELEMBLUE;
	}

	@Override
	public ArrayList<String> getStartingDeck() {
		return CardList.getStarterDeck();
	}

	@Override
	public ArrayList<String> getStartingRelics() {
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add(MagusStaff.ID);
		UnlockTracker.markRelicAsSeen(MagusStaff.ID);
		return retVal;
	}

	@Override
	public AbstractPlayer newInstance() {
		return new TheElementalist(this.name, TheElementalistEnum.THE_ELEMENTALIST);
	}

	public AttackEffect[] getSpireHeartSlashEffect() {
		return new AttackEffect[] { AttackEffect.SLASH_DIAGONAL, AttackEffect.SLASH_HORIZONTAL,
				AttackEffect.SLASH_DIAGONAL, AttackEffect.SLASH_VERTICAL, AttackEffect.SLASH_HORIZONTAL,
				AttackEffect.SLASH_VERTICAL };
	}

	public String getCustomModeCharacterButtonSoundKey() {
		return "ATTACK_DAGGER_6";
	}

	public void doCharSelectScreenSelectEffect() {
		CardCrawlGame.sound.playV("ATTACK_DAGGER_6", 1.75f);
		CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
	}

	@Override
	public int getAscensionMaxHPLoss() {
		return 5;
	}

	@Override
	public BitmapFont getEnergyNumFont() {
		return FontHelper.energyNumFontBlue;
	}

	@Override
	public String getLocalizedCharacterName() {
		return "The Elementalist";
	}

	@Override
	public String getSpireHeartText() {
		return "[SpireHeartText]";
	}

	@Override
	public String getTitle(PlayerClass arg0) {
		// TODO Auto-generated method stub
		return "the Elementalist";
	}

	@Override
	public String getVampireText() {
		// TODO Auto-generated method stub
		return "[getVampireText()]";
	}

	@Override
	public AbstractCard getStartCardForEvent() {
		// TODO Auto-generated method stub
		return new Elemental_Strike();
	}
	
	//It's not receiveModelRender() or renderPlayerImage()
	
	@Override
	public void render(SpriteBatch sb) {
		//ElementalistMod.logger.info("TheElementalist.render(...)");
		
		//renderMagicCircle(sb);
		super.render(sb);
		//renderMagicCircle(sb);
	}

	private void renderMagicCircle(SpriteBatch sb) {
		sb.setColor(Color.WHITE.cpy());
		
		int src = sb.getBlendSrcFunc();
		int dest = sb.getBlendDstFunc();
		
		sb.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE);
        //sb.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		/*sb.draw(testTexture,
				this.hb_x+this.drawX-250/2, this.hb_y+this.drawY-190/2,
				250/2, 190/2,
				250, 190,
				1f, 1f, 0f,
				0, 0,
				250, 190,
				false, false
				);*/
		
		rotationIndex += Gdx.graphics.getDeltaTime();
		
		//SpriteBatch circle = new SpriteBatch(320);
		//circle.begin();
		

		circleRotationTarget[0] = (int) -rotationIndex;
		circleRotationTarget[1] = (int) (circleRotationTarget[0]/2);
		circleRotationTarget[2] = (int) (circleRotationTarget[1]/2);
		circleRotationTarget[3] += 0.01f;
		circleRotationTarget[4] -= 0.005f;
		circleRotationTarget[5] = 0;
		
		for(int i=0; i<circleRotationTarget.length; i++) {
			circleRotationActual[i] = circleRotationActual[i]*0.5f + circleRotationTarget[i]*0.5f;
		}

		//circleRotationActual[1] = (int) rotationIndex*(360/60);
		
		
		//circleRotationTargets[0] = 
		
		/*float[] angles = {
				(rotationIndex*2.5f)%360,
				360 - (rotationIndex*2f)%360,
				(rotationIndex*1.5f)%360,
				360 - (rotationIndex*1f)%360,
				(rotationIndex*0.5f)%360,
				0f
		};*/
		
		for(int i=0; i<magic_circle.length; i++) {
			sb.draw(magic_circle[i], 
					this.drawX-320+20, this.drawY-160, //screen draw position 
					320, 320, //rotation origin
					640, 640, //size of draw 
					0.75f, 0.75f, circleRotationActual[i], //scaling and rotation
					0, 0, //texel space position
					640, 640, //source size in texels
					false, false); // horizontal/vertical flip
		}


		//circle.end();
		
		//sb.draw(circle.);
		
		//ElementalistMod.logger.info("MagicCircleEffect.renderMagicCircle(...)");
		//ElementalistMod.logger.info("-> magic_circle.length => "+magic_circle.length);
		//int width = 938;
		//float scale = 1f;
		//float time = System.nanoTime()/1000f;
		//float drawX = AbstractDungeon.player.drawX + AbstractDungeon.player.animX;
		//float drawY = AbstractDungeon.player.drawY + AbstractDungeon.player.animY + AbstractDungeon.sceneOffsetY;
		//ElementalistMod.logger.info("-> drawX => "+drawX);
		//ElementalistMod.logger.info("-> drawY => "+drawY);
		/*for(int i=0; i<magic_circle.length; i++) {
			float rotation = time * (1+i*0.1f) * ((i%2)*2-1);
    		//sb.draw(magic_circle[i], drawX - width/2, drawY - width/2, width/2, width/2, width, width, scale, scale, rotation, 0, 0, width, width, false, false);
			//ElementalistMod.logger.info("-> (x, y) => ("+this.hb_x+", "+this.hb_y+")");
			//ElementalistMod.logger.info("-> (x, y) => ("+this.animX+", "+this.animY+")");
			//ElementalistMod.logger.info("-> (x, y) => ("+this.vX+", "+this.vY+")");
			//ElementalistMod.logger.info("-> (x, y) => ("+this.drawX+", "+this.drawY+")");
			//ElementalistMod.logger.info("-> scale => ("+scale+")");
    		sb.draw(magic_circle[i], 
    				this.drawX-160, this.drawY-160 -160, //screen draw position 
    				160, 160, //rotation origin
    				320, 320, //size of draw 
    				1f, 1f, rotation, //scaling and rotation
    				0, 0, //texel space position
    				320, 320, //source size in texels
    				false, false); // horizontal/vertical flip
    	}*/

		sb.setBlendFunction(src, dest);
	}
	
	public static void addExp(int amount) {
		totalExp += amount;
	}
	
	public static boolean readyForLevelup() {
		return totalExp >= getTotalExpNeededToHitNextLevel();
	}
	
	public static void doLevelup() {
		level++;
	}
	
	public static int getTotalExpNeededToHitNextLevel() {
		int expNeeded = 0;
		for(int i=1; i<=level; i++) {
			expNeeded += getExpNeededThisLevel(i);
		}
		return expNeeded;
	}
	
	public static int getExpNeededThisLevel(int currentLevel) {
		return (currentLevel+1)*10;
	}
	
	public static int getExpIntoThisLevel() {
		int expUsedToGetToThisLevel = getTotalExpNeededToHitNextLevel() - getExpNeededThisLevel(level);
		int expIntoThisLevel = totalExp - expUsedToGetToThisLevel;
		return expIntoThisLevel;
	}

	@Override
	public void onLoad(ArrayList<Integer> expData) {
		ElementalistMod.log("onLoad()");
		totalExp = expData.get(0);
		level = expData.get(1);
		ElementalistMod.log("totalExp: "+totalExp);
		ElementalistMod.log("level: "+level);
	}

	@Override
	public ArrayList<Integer> onSave() {
		ElementalistMod.log("onSave()");
		ArrayList<Integer> expData = new ArrayList<Integer>();
		expData.add(0, totalExp);
		expData.add(1, level);
		return expData;
	}
}
