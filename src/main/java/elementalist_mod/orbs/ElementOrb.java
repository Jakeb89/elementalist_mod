package elementalist_mod.orbs;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbPassiveEffect;

import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.Util;
import elementalist_mod.cards.uncommon.Concentricity;
import elementalist_mod.powers.AstralFormPower;
import elementalist_mod.powers.ConfluencePower;
import elementalist_mod.relics.MagusStaff;

public abstract class ElementOrb extends AbstractOrb {
	private float vfxTimer = 1.0F;
	private float vfxIntervalMin = 0.2F;
	private float vfxIntervalMax = 0.7F;
	private static final float ORB_WAVY_DIST = 0.04F;
	private static final float PI_4 = 12.566371F;

	protected boolean showChannelValue = true;

	public int amount = 0;
	public boolean shattering = false;

	public String[] descriptions;
	public Element element = null;
	private Texture img2, img3, img4, img5, imgExtra, imgBevel;
	public float rotation = 0f;
	public float particleDelta = 0f;
	public ArrayList<FloatPair> starPosition = new ArrayList<FloatPair>();
	public ArrayList<FloatPair> starTarget = new ArrayList<FloatPair>();
	public static Texture orbHighlightTexture = null;
	public static Texture orbHighlightPassiveTexture = null;
	private float highlightScale = 0;
	public boolean isHovered = false;
	static Hitbox mouseHitbox = new Hitbox(InputHelper.mX, InputHelper.mY, 1, 1);
	
	static {
		if(orbHighlightTexture == null) {
			orbHighlightTexture = ImageMaster.loadImage("img/orbs/highlight.png");
			orbHighlightPassiveTexture = ImageMaster.loadImage("img/orbs/highlight_passive.png");
		}
	}
    

	public ElementOrb(String ID, int amount) {
		for(int i=0; i<12; i++) {
			starPosition.add(new FloatPair(0f, 0f));
			starTarget.add(new FloatPair(0f, 0f));
		}
		//ElementalistMod.log("ElementOrb(" + ID + ", " + amount + ")");
		this.ID = ID;
		this.img = ImageMaster.loadImage("img/orbs/" + ID + "_back1.png");
		this.img2 = ImageMaster.loadImage("img/orbs/" + ID + "_back2.png");
		this.img3 = ImageMaster.loadImage("img/orbs/" + ID + "_back3.png");
		this.img4 = ImageMaster.loadImage("img/orbs/" + ID + "_back4.png");
		this.img5 = ImageMaster.loadImage("img/orbs/" + ID + "_back5.png");
		this.imgExtra = ImageMaster.loadImage("img/orbs/" + ID + "_extra.png");
		this.imgBevel = ImageMaster.loadImage("img/orbs/elementalist_orb_bevel.png");

		this.amount = amount;

		this.channelAnimTimer = 0.5F;
		this.scale = 1f;

		// this.descriptions =
		// CardCrawlGame.languagePack.getOrbString(this.ID).DESCRIPTION;
		// this.name = CardCrawlGame.languagePack.getOrbString(this.ID).NAME;

		updateDescription();
	}

	public void onStartOfTurn() {
		//this.decrementAmount();
	}

	public void decrementAmount() {
		this.amount--;
		if(this.amount < 0) this.amount = 0;
		//checkShatter();
	}

	public void changeAmount(int delta) {
		this.amount += delta;
		if(this.amount < 0) this.amount = 0;
	}

	public void onCardUse(AbstractCard c) {
	}

	public void onCardDraw(AbstractCard c) {
	}
	
	public void checkShatter() {
		/*if (amount <= 0) {
			onShatter();
			return;
		}*/
	}

	public void onShatter() {
		/*
		AbstractPlayer p = AbstractDungeon.player;
		p.orbs.remove(this);
		
		int index = p.orbs.indexOf(this);
		
		AbstractOrb orbSlot = new EmptyOrbSlot();
		for (int i = index + 1; i < p.orbs.size(); i++) {
			Collections.swap(p.orbs, i, i - 1);
		}
		p.orbs.set(p.orbs.size() - 1, orbSlot);
		for (int i = index; i < p.orbs.size(); i++) {
			((AbstractOrb) p.orbs.get(i)).setSlot(i, p.maxOrbs);
		}
		
		AbstractDungeon.player.decreaseMaxOrbSlots(1);
		*/
	}

	public void atTurnStartPostDraw() {
	}

	public void onVictory() {
	}

	@Override
	public void onEvoke() {
		return;
	}

	@Override
	public void triggerEvokeAnimation() {
		CardCrawlGame.sound.play("DUNGEON_TRANSITION", 0.1F);
		AbstractDungeon.effectsQueue.add(new PlasmaOrbActivateEffect(this.cX, this.cY));
	}

	@Override
	public void updateDescription() {
		applyFocus();

		String turn = " #pturns.";
		if (this.amount == 1) {
			turn = " #pturn.";
		}

		// this.description = (this.descriptions[0] + " NL #pShatters #pin #b" +
		// this.amount + turn);
	}

	/*
	 * @Override public void applyFocus() { AbstractPower power =
	 * AbstractDungeon.player.getPower("Focus"); if (power == null) { return; }
	 * 
	 * this.timer = Math.max(0, this.baseTimer + power.amount - this.timeElapsed); }
	 */

	public void activateEffect() {
		/*
		 * float speedTime = 0.6F / AbstractDungeon.player.orbs.size(); if
		 * (Settings.FAST_MODE) { speedTime = 0.0F; } AbstractDungeon.effectList.add(new
		 * OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA)); //
		 * AbstractDungeon.actionManager.addToBottom(new VFXAction(new //
		 * OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.PLASMA), speedTime));
		 * 
		 */
	}

	@Override
	public void playChannelSFX() {
		CardCrawlGame.sound.play("AUTOMATON_ORB_SPAWN", 0.1F);
	}

	@Override
	public void render(SpriteBatch sb) {
		this.scale = Settings.scale;
		this.updateAnimation();
		
		if(getOrbSlot() == 0) {

			
	    	for(ElementOrb orb : ElementalistMod.getElementOrbs()) {
	    		//if(orb instanceof ElementOrb) {
	    			orb.renderParticles(sb);
	    		//}
    		}
		}
		
		sb.setColor(Color.WHITE.cpy());
		if(element == Element.FIRE) {
			sb.draw(imgExtra, this.cX - 48.0F + this.bobEffect.y / 4.0F, this.cY - 48.0F + this.bobEffect.y / 4.0F, 48.0F, 48.0F,
					96.0F, 96.0F, this.scale, this.scale, rotation, 0, 0, 96, 96, false, false);
		}
		if(element == Element.AIR) {
			sb.draw(imgExtra, this.cX - 48.0F + this.bobEffect.y / 4.0F, this.cY - 48.0F + this.bobEffect.y / 4.0F, 48.0F, 48.0F,
					96.0F, 96.0F, this.scale, this.scale, -rotation, 0, 0, 96, 96, false, false);
		}
		if(element == Element.EARTH) {
			sb.draw(imgExtra, this.cX - 48.0F + this.bobEffect.y / 4.0F, this.cY - 48.0F + this.bobEffect.y / 4.0F, 48.0F, 48.0F,
					96.0F, 96.0F, this.scale, this.scale, (float) (Math.sin(3.14f*rotation/90f)*5f), 0, 0, 96, 96, false, false);
		}
		sb.draw(img, this.cX - 48.0F + this.bobEffect.y / 4.0F, this.cY - 48.0F + this.bobEffect.y / 4.0F, 48.0F, 48.0F,
				96.0F, 96.0F, this.scale, this.scale, rotation, 0, 0, 96, 96, false, false);
		sb.draw(img2, this.cX - 48.0F + this.bobEffect.y / 4.0F, this.cY - 48.0F + this.bobEffect.y / 4.0F, 48.0F,
				48.0F, 96.0F, 96.0F, this.scale, this.scale, 0f, 0, 0, 96, 96, false, false);
		sb.draw(img3, this.cX - 48.0F + this.bobEffect.y / 4.0F, this.cY - 48.0F + this.bobEffect.y / 4.0F, 48.0F,
				48.0F, 96.0F, 96.0F, this.scale, this.scale, -rotation, 0, 0, 96, 96, false, false);
		sb.draw(img4, this.cX - 48.0F + this.bobEffect.y / 4.0F, this.cY - 48.0F + this.bobEffect.y / 4.0F, 48.0F,
				48.0F, 96.0F, 96.0F, this.scale, this.scale, 0f, 0, 0, 96, 96, false, false);
		sb.draw(img5, this.cX - 48.0F + this.bobEffect.y / 4.0F, this.cY - 48.0F + this.bobEffect.y / 4.0F, 48.0F,
				48.0F, 96.0F, 96.0F, this.scale, this.scale, 0f, 0, 0, 96, 96, false, false);
		sb.draw(imgBevel, this.cX - 48.0F + this.bobEffect.y / 4.0F, this.cY - 48.0F + this.bobEffect.y / 4.0F, 48.0F,
				48.0F, 96.0F, 96.0F, this.scale, this.scale, 0f, 0, 0, 96, 96, false, false);

		if(element == Element.WATER) {
			sb.draw(imgExtra, this.cX - 48.0F + this.bobEffect.y / 4.0F, this.cY - 48.0F + this.bobEffect.y / 4.0F, 48.0F,
					48.0F, 96.0F, 96.0F, this.scale, this.scale, (float) (Math.cos(3.14f*rotation/90f)*5f), 0, 0, 96, 96, false, false);
		}

		float degrees, dx, dy;
		
		try {
			for(int i=0; i<Math.min(12, this.amount); i++) {
				FloatPair target = starTarget.get(i);
				FloatPair position = starPosition.get(i);
				degrees = 360f*i/Math.min(12, this.amount) + particleDelta;
				target.x = (float) Math.cos(3.14f*degrees/180);
				target.y = (float) Math.sin(3.14f*degrees/180);
				
				position.Lerp(target, 0.1f);
			}
			
			for(int i=this.amount; i<12; i++) {
				FloatPair position = starPosition.get(i);
				position.x = 0f;
				position.y = 0f;
			}
			
			for(int i=0; i<Math.min(12, this.amount); i++) {
				FloatPair position = starPosition.get(i);
				if(amount < 12) {
					degrees = 360*i/12;
				}else {
					degrees = 360*i/amount;
				}
				dx = position.x;
				dy = position.y;
				sb.draw(img4, 
						this.cX - 48.0F + this.bobEffect.y / 4.0F + dx*32f, 
						this.cY - 48.0F + this.bobEffect.y / 4.0F + dy*32f, 
						48.0F, 48.0F, 96.0F, 96.0F, this.scale*0.5f, this.scale*0.5f, 0f, 0, 0, 96, 96, false, false);
			}
			

		}catch(Exception e) {
			//ElementalistMod.log(e.toString());
		}
		
		if(shouldPassiveHighlight()) {
			renderHighlight(sb, false);
		}
		
		if(shouldActiveHighlight()) {
			renderHighlight(sb, true);
		}

		Util.setBlending(sb, "normal");
		sb.setColor(Color.WHITE.cpy());
		
		
		rotation += 0.5f;

		renderText(sb);
		this.hb.render(sb);

		particleDelta += 0.1f;
	}
	
	private void renderHighlight(SpriteBatch sb, boolean active) {
		float drawScale = this.scale;
		float opacity = 1f;
		if(active) {
			opacity = highlightScale;
			drawScale *= highlightScale;
		}else {
			opacity = 1-highlightScale;
			drawScale *= 1-highlightScale;
		}
		
		
		
		Util.setBlending(sb, "screen");
		Color col = Color.WHITE.cpy();
		col.r = opacity;
		col.g = opacity;
		col.b = opacity;
		sb.setColor(col);
		//ElementalistMod.log("highlightScale => "+highlightScale);
		
		if(active) {
			sb.draw(orbHighlightTexture, 
				this.cX - 64.0F + this.bobEffect.y / 4.0F, 
				this.cY - 64.0F + this.bobEffect.y / 4.0F, 
				64.0F, 64.0F, 128.0F, 128.0F, drawScale, drawScale, 0f, 0, 0, 128, 128, false, false);
		}else {
			for(int i=0; i<MagusStaff.charges; i++) {
				float meteorAngle = 360*this.getOrbSlot()/4 + 360*i/MagusStaff.charges;
				float individualOpacity = (float) (opacity*Math.cos( (Math.PI/360)*(10*particleDelta+meteorAngle+90*this.getOrbSlot())) );
				individualOpacity /= 2;
				individualOpacity += 0.5f;
				col.r = individualOpacity;
				col.g = individualOpacity;
				col.b = individualOpacity;
				sb.setColor(col);
				sb.draw(orbHighlightPassiveTexture, 
					this.cX - 64.0F + this.bobEffect.y / 4.0F, 
					this.cY - 64.0F + this.bobEffect.y / 4.0F, 
					64.0F, 64.0F, 128.0F, 128.0F, drawScale, drawScale, this.angle*MagusStaff.charges+meteorAngle, 0, 0, 128, 128, false, false);
			}
		}
	}
	
	private boolean shouldPassiveHighlight() {
		if(!AbstractDungeon.player.hasRelic(MagusStaff.ID)) return false;
		
		return (MagusStaff.charges > 0);
	}
	
	private boolean shouldActiveHighlight() {
		if(!AbstractDungeon.player.hasRelic(MagusStaff.ID)) return false;
		if(MagusStaff.charges < 1) return false;
		
		return this.isHovered;
	}
	
	private int getOrbSlot() {
		for(int i=0; i<ElementalistMod.getElementOrbs().size(); i++) {
			if(ElementalistMod.getElementOrbs().get(i) == this) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void updateAnimation() {
		//super.updateAnimation();
		this.angle += Gdx.graphics.getDeltaTime() * 45.0F;

		this.vfxTimer -= Gdx.graphics.getDeltaTime();
		if (this.vfxTimer < 0.0F) {
			AbstractDungeon.effectList.add(new PlasmaOrbPassiveEffect(this.cX, this.cY));
			AbstractDungeon.effectList.add(new FrostOrbPassiveEffect(this.cX, this.cY));
			this.vfxTimer = MathUtils.random(this.vfxIntervalMin, this.vfxIntervalMax);
		}
		
		if(guessWhetherHovered()) {
			//ElementalistMod.log("Orb is hovered.");
			highlightScale = highlightScale*0.5f + 1*0.5f;
		}else {
			//ElementalistMod.log("Orb is not hovered.");
			highlightScale = highlightScale*0.5f + 0*0.5f;
		}
	}
	
	public boolean guessWhetherHovered() {
		mouseHitbox.move(InputHelper.mX, InputHelper.mY);
		return hitboxCheck(mouseHitbox, this.hb);
	}

	private static boolean hitboxCheck(Hitbox hitbox1, Hitbox hitbox2) {
		float dx = Math.abs(hitbox1.cX - hitbox2.cX);
		float dy = Math.abs(hitbox1.cY - hitbox2.cY);
		float max_x = (hitbox1.width + hitbox2.width)/3;
		float max_y = (hitbox1.height + hitbox2.height)/3;
		if(dx < max_x && dy < max_y) {
			//ElementalistMod.log("Hit: (" + dx + ", " + dy + ") [" + max_x + ", " + max_y + "]");
			return true;
		}
		
		return false;
	}

	@Override
	protected void renderText(SpriteBatch sb) {
		
		Color textColor, elementColor = Color.WHITE.cpy();
		
		switch(element) {
			case FIRE: 	elementColor = Color.RED.cpy(); break;
			case WATER: elementColor = Color.BLUE.cpy(); break;
			case EARTH: elementColor = Color.YELLOW.cpy(); break;
			case AIR: 	elementColor = Color.GREEN.cpy(); break;
		}
		
		if(amount == 0) textColor = Color.GRAY.cpy();
		//else if(amount > 12) textColor = Color.BLUE.cpy();
		else{
			textColor = Color.WHITE.cpy();
			textColor.lerp(elementColor, amount/12f);
		}

		if (this.showChannelValue && !this.showEvokeValue) {
			FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(this.amount),
					this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, textColor,
					this.fontScale);
		}
		/*
		 * // Render an upgraded green + on the other side, for upgraded replicas only.
		 * if (this.upgraded) { FontHelper.renderFontCentered(sb,
		 * FontHelper.cardEnergyFont_L, "+", this.cX - NUM_X_OFFSET, this.cY +
		 * this.bobEffect.y / 2.0F + NUM_Y_OFFSET, Color.GREEN.cpy(), this.fontScale); }
		 */
	}
	
	private Color getElementColor(Element element2) {
		switch(element2) {
		case FIRE: return Color.RED.cpy();
		case EARTH: return Color.YELLOW.cpy();
		case WATER: return Color.BLUE.cpy();
		case AIR: return Color.GREEN.cpy();
		}
		return null;
	}

	private void renderParticles(SpriteBatch sb) {
		boolean drawSynergy = false;
		/*
		for(AbstractPower power : AbstractDungeon.player.powers) {
			if(power instanceof ConfluencePower) {
				drawSynergy = true;
			}else if(power instanceof AstralFormPower) {
				drawSynergy = true;
			}
		}
		
		for(AbstractCard card : AbstractDungeon.player.hand.group) {
			if(card instanceof Concentricity) {
				drawSynergy = true;
			}
		}
		*/
		drawSynergy = true;
		
		
		Color col;
		float x, y, twinkle, twinkle2;
		if(drawSynergy && amount > 0) {
	    	for(AbstractOrb orb : ElementalistMod.getElementOrbs()) {
	    		if(orb instanceof ElementOrb && orb != this) {
	    			ElementOrb elementOrb = (ElementOrb)orb;
	    			//check for synergy
	    			if(amount == elementOrb.amount) {
	    				//has synergy
	    				float dX = elementOrb.cX - cX;
	    				float dY = elementOrb.cY - cY;
	    				float dis = (float) Math.sqrt(dX*dX + dY*dY);
	    				float seperation = 10f;
	    				for(float i=particleDelta%seperation; i<dis; i+=seperation) {
	        				col = getElementColor(element);
	        				col = col.lerp(getElementColor(elementOrb.element), i/dis);
	        				sb.setColor(col);
	        				x = this.cX + this.bobEffect.y / 4.0F + dX*(i/dis)*this.scale;
	        				y = this.cY + this.bobEffect.y / 4.0F + dY*(i/dis)*this.scale;
	        				x += 5*Math.cos(i/4f);
	        				y += 5*Math.sin(i/4f);
	        				twinkle = (float) Math.cos(i/seperation/10f); 
	        				twinkle2 = (float) Math.sin(i/seperation/10f)*0.8f; 
	    					ElementParticle.draw(sb, "synergy", x, y, this.scale*twinkle, particleDelta+i);
	    					sb.setColor(Color.WHITE.cpy());
	    					ElementParticle.draw(sb, "synergy", x, y, this.scale*twinkle2, particleDelta+i);
	    				}
	    			}
	    		}
	    	}
		}
	}
}