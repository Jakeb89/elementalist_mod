package elementalist_mod.relics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.abstracts.CustomRelic;
import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.actions.ChooseCardFromPileAction;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.cards.special.*;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;

public class GraduationLetter extends CustomRelic implements ClickableRelic {

	public static final String ID = "elementalistmod:GraduationLetter";
	public static final String IMG = ElementalistMod.GRADUATION_LETTER;
	public static final String OUTLINE_IMG = ElementalistMod.GRADUATION_LETTER_OUTLINE;

	public GraduationLetter() {
		super(ID, new Texture(IMG), new Texture(OUTLINE_IMG), RelicTier.STARTER, LandingSound.FLAT);
		updateDescription();
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	@Override
	public void onPlayCard(AbstractCard card, AbstractMonster monster) {
		updateDescription();
	}

	public void onActivate() {
		updateDescription();
	}

	public void registerUse() {
		updateDescription();
	}

	@Override
	public void onRightClick() {
		ElementalistMod.log("GraduationLetter.onRightClick()");

		CardGroup group = new CardGroup(CardGroupType.UNSPECIFIED);
		group.addToBottom(new RelicCard_RedRibbon());
		group.addToBottom(new RelicCard_BlueRibbon());
		group.addToBottom(new RelicCard_GreenCirclet());
		group.addToBottom(new RelicCard_YellowCirclet());
		// this.exhaust = true;
		//AbstractDungeon.player.loseRelic(this.relicId);

		AbstractDungeon.actionManager.addToBottom(new ChooseCardFromPileAction(this, group, 1));
		

	}

	public void updateDescription() {
		this.description = "While unopened, start each battle with 1 of a random element. Right click to choose an elemental specialization.";
		this.DESCRIPTIONS[0] = this.description;
	}

	public void atBattleStart() {
		double rng = Math.random();
		if (rng < 0.25) {
			ElementalistMod.changeElement(Element.AIR, 1, GraduationLetter.ID);
		} else if (rng < 0.50) {
			ElementalistMod.changeElement(Element.WATER, 1, GraduationLetter.ID);
		} else if (rng < 0.75) {
			ElementalistMod.changeElement(Element.EARTH, 1, GraduationLetter.ID);
		} else {
			ElementalistMod.changeElement(Element.FIRE, 1, GraduationLetter.ID);
		}
	}

	@Override
	public AbstractRelic makeCopy() {
		return new GraduationLetter();
	}

	public void actionCallback(AbstractCard card) {

        for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
            if (AbstractDungeon.player.relics.get(i).relicId.equals(this.relicId)) {
        		if (card instanceof RelicCard_RedRibbon) {
        			(new RedRibbon()).instantObtain(AbstractDungeon.player, i, true);
        		} else if (card instanceof RelicCard_BlueRibbon) {
        			(new BlueRibbon()).instantObtain(AbstractDungeon.player, i, true);
        		} else if (card instanceof RelicCard_GreenCirclet) {
        			(new GreenCirclet()).instantObtain(AbstractDungeon.player, i, true);
        		} else if (card instanceof RelicCard_YellowCirclet) {
        			(new YellowCirclet()).instantObtain(AbstractDungeon.player, i, true);
        		}
                //instantObtain(AbstractDungeon.player, i, true);
                break;
            }
        }
        
	}

}
