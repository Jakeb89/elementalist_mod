package elementalist_mod.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Absolute_Zero extends AbstractElementalistCard {
	public static final String ID = "elementalist:Absolute_Zero";
	public static final String NAME = "Absolute Zero";
	public static String DESCRIPTION = "elementalist:Watercast 2: Deal damage equal to your missing health. Gain 1 elementalist:Earth.";
	public static String DESCRIPTION_UPGRADED = "elementalist:Watercast 2: Deal damage equal to your missing health. Target loses 2 Strength. Gain 1 elementalist:Earth.";
	private static final int COST = 1;

	public Absolute_Zero() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.ABSOLUTE_ZERO), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY);

		addElementalCost(Element.WATER, 2);
		
		generatesElement = true;
		generatedElementAmount = 1;
		this.element = Element.EARTH;
		this.baseDamage = 0;

	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);
		
		if(cast(Element.WATER, 2)) {
		    //AbstractDungeon.actionManager.addToBottom(new RemoveAllBlockAction(m, p));
	

			
			
			int missingHealth = p.maxHealth - p.currentHealth;

			AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, missingHealth), AbstractGameAction.AttackEffect.SMASH));

			if(this.upgraded) {
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new StrengthPower(p, -2), -2));
			}
			
			changeElement(Element.EARTH, 1);
		}

	}

	public AbstractCard makeCopy() {
		return new Absolute_Zero();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.rawDescription = DESCRIPTION_UPGRADED;
			this.initializeDescription();
		}
	}
}