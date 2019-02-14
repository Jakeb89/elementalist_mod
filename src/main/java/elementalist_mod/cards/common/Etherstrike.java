package elementalist_mod.cards.common;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import elementalist_mod.ElementalistMod;
import elementalist_mod.actions.EtherstrikeAction;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Etherstrike extends AbstractElementalistCard {
	public static final String ID = "elementalist:Etherstrike";
	public static final String NAME = "Etherstrike";
	public static String DESCRIPTION = "Ethereal. Deal !D! damage. NL Your elements with the highest charges lose 1 charge.";
	private static final int COST = 0;
	private static final int ATTACK_DMG = 12;
	private static final int UPGRADE_PLUS_DMG = 4;

	public Etherstrike() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.ETHERSTRIKE), COST, DESCRIPTION,
				AbstractCard.CardType.ATTACK, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.COMMON,
				AbstractCard.CardTarget.ENEMY);
		this.baseDamage = ATTACK_DMG;
		
		this.isEthereal = true;
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		AbstractDungeon.actionManager.addToBottom(new EtherstrikeAction(this, m));
	}

	public AbstractCard makeCopy() {
		return new Etherstrike();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_DMG);
			initializeDescription();
		}
	}

}