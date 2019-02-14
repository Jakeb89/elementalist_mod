package elementalist_mod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ElementalPower extends AbstractPower {
	public AbstractCreature source;

	public void initIcon(String img, String img_small) {
		this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(img), 0, 0, 84, 84);
		this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(img_small), 0, 0, 32, 32);
	}

	public int onAddElement(String element, int amount, String sourceType) {
		return amount;
	}

	public void onElementalCast(String element) {
		// TODO Auto-generated method stub
	}

	public void onCardExhaust(AbstractCard card) {
		// TODO Auto-generated method stub
		
	}
}
