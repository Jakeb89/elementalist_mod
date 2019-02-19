package elementalist_mod.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.actions.CallbackAction;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Fission_Lance extends AbstractElementalistCard {
	public static final String ID = "elementalist:Fission_Lance";
	public static final String NAME = "Fission Lance";
	public static String DESCRIPTION = "Deal !D! damage. NL Firecast 2: Deal !D! damage to a random enemy twice then gain !M! Air.";
	private static final int COST = 1;
	private static final int ATTACK_DMG = 8;
	private static final int UPGRADE_PLUS_DMG = 4;
	private static final int MAGIC_NUM = 1;

	public Fission_Lance() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.FISSION_LANCE), COST, DESCRIPTION,
				AbstractCard.CardType.ATTACK, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.ENEMY);
		this.baseDamage = ATTACK_DMG;
		this.baseMagicNumber = MAGIC_NUM;
		this.magicNumber = this.baseMagicNumber;

		addElementalCost(Element.FIRE, 2);
		
		generatesElement = true;
		generatedElementAmount = 1;
		this.element = Element.AIR;
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		AbstractDungeon.actionManager.addToBottom(
				new DamageAction(
						m, 
						new DamageInfo(p, this.damage, this.damageTypeForTurn), 
						AbstractGameAction.AttackEffect.SLASH_DIAGONAL
				)
		);
		if (cast(Element.FIRE, 2)) {
			AbstractDungeon.actionManager.addToBottom(new CallbackAction(this, 0));
		}
	}
	
	public void actionCallback(int step) {
		switch(step) {
		case 0:
		case 1:
			AbstractMonster enemy = pickRandomLivingEnemy();
			if (enemy != null) {
				AbstractDungeon.actionManager
						.addToBottom(new DamageAction(enemy, new DamageInfo(AbstractDungeon.player, this.damage, this.damageTypeForTurn),
								AbstractGameAction.AttackEffect.BLUNT_LIGHT));
			}
			AbstractDungeon.actionManager.addToBottom(new CallbackAction(this, step+1));
		case 2:
			changeElement(Element.AIR, this.magicNumber);
		}
	}

	public AbstractCard makeCopy() {
		return new Fission_Lance();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPGRADE_PLUS_DMG);
			upgradeMagicNumber(1);
			generatedElementAmount = magicNumber;
		}
	}
}