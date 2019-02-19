package elementalist_mod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.actions.CallbackAction;
import elementalist_mod.actions.FlameAction;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Flame extends AbstractElementalistCard {
	public static final String ID = "elementalist:Flame";
	public static final String NAME = "Flame";
	public static String DESCRIPTION = "Deal !D! damage. NL NL Firecast 1: Increase this card's damage by !M! this combat. NL NL Firecast 1: Increase the damage of ALL Flames in your deck by !M! this combat.";
	private static final int COST = 0;
	private static final int ATTACK_DMG = 5;
	private static final int MAGIC_NUM = 3;
	private static final int UPGRADE_MAGIC_NUM = 1;

	public Flame() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.FLAME), COST, DESCRIPTION,
				AbstractCard.CardType.ATTACK, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.COMMON,
				AbstractCard.CardTarget.ENEMY);
		this.baseDamage = ATTACK_DMG;
		this.baseMagicNumber = MAGIC_NUM;
	    this.magicNumber = this.baseMagicNumber;

		addElementalCost(Element.FIRE, 1);
		addElementalCost(Element.FIRE, 1);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
			AbstractGameAction.AttackEffect.FIRE));

		if (cast(Element.FIRE, 1)) {
		    AbstractDungeon.actionManager.addToBottom(new ModifyDamageAction(this.uuid, this.magicNumber));
		    AbstractDungeon.actionManager.addToBottom(new CallbackAction(this, 0));
		}
		
	}
	
	@Override
	public void actionCallback(int value) {
		if (cast(Element.FIRE, 1)) {
		    //AbstractDungeon.actionManager.addToBottom(new ModifyDamageAction(this.uuid, this.magicNumber));
		    AbstractDungeon.actionManager.addToTop(new FlameAction(this.magicNumber));
			AbstractDungeon.actionManager.addToBottom(new ModifyDamageAction(this.uuid, this.magicNumber));
		}
	}

	public AbstractCard makeCopy() {
		return new Flame();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADE_MAGIC_NUM);
			initializeDescription();
		}
	}

}