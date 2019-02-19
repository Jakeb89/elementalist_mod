package elementalist_mod.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.actions.VaccuumDrawAction;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Vaccuum extends AbstractElementalistCard {
	public static final String ID = "elementalist:Vaccuum";
	public static final String NAME = "Vacuum";
	public static String DESCRIPTION = "Aircast 2: Draw 3 cards. For each status or curse you draw, draw another card. For each card drawn, target takes !D! damage.";
	private static final int COST = 1;
	public static final int DAMAGE = 3;
	public static final int DAMAGE_UPGRADE = 2;

	public Vaccuum() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_ATTACK_GREEN_3), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
		this.baseDamage = DAMAGE;
		
		addElementalCost(Element.AIR, 2);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster target) {
		super.use(p, target);

		if(cast(Element.AIR, 2)) {
			AbstractDungeon.actionManager.addToBottom(new VaccuumDrawAction(this, target, 3));
		}
	}


	public AbstractCard makeCopy() {
		return new Vaccuum();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeDamage(DAMAGE_UPGRADE);
			initializeDescription();
		}
	}

}