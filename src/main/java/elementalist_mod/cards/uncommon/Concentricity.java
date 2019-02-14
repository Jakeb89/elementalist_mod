package elementalist_mod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ThunderStrikeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.orbs.*;
import elementalist_mod.patches.*;

public class Concentricity extends AbstractElementalistCard {
	public static final String ID = "elementalist:Concentricity";
	public static final String NAME = "Concentricity";
	public static String DESCRIPTION = "Deal 10 damage. NL Gain 1 in each element with synergy.";
	public static String DESCRIPTION_UPGRADE = "Mercurial. NL Deal 10 damage. NL Gain 1 in each element with synergy.";
	private static final int COST = 1;
	private static final int DAMAGE = 10;

	public Concentricity() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_ATTACK_GREY_4), COST, DESCRIPTION,
				AbstractCard.CardType.ATTACK, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.ENEMY);
		this.baseDamage = DAMAGE;
		
		//upgrade();
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);
		

		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage), AbstractGameAction.AttackEffect.SLASH_VERTICAL));

    	if(ElementalistMod.hasSynergy("Fire")) 	changeElement("Fire", 1);
    	if(ElementalistMod.hasSynergy("Earth")) changeElement("Earth", 1);
    	if(ElementalistMod.hasSynergy("Water")) changeElement("Water", 1);
    	if(ElementalistMod.hasSynergy("Air")) 	changeElement("Air", 1);
		
	}

	public AbstractCard makeCopy() {
		return new Concentricity();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.rawDescription = DESCRIPTION_UPGRADE;
			this.initializeDescription();
			isMercurial = true;
		}
	}
}