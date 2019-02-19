package elementalist_mod.cards.rare;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.actions.AccelerantAction;
import elementalist_mod.actions.DrawCardFromPileAction;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;
import elementalist_mod.powers.WindburnPower;

public class Erosion extends AbstractElementalistCard {

	public static final String ID = "elementalist:Erosion";
	public static final String NAME = "Erosion";
	public static String DESCRIPTION = "Tiring. NL Earthcast 1: A random enemy loses !M! Strength. NL Watercast 1: Choose a card from your draw pile and put it in your hand. (It costs 1 less this turn.)";
	private static final int COST = 1;
	private static final int MAGIC_NUM = 2;
	

	public Erosion() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_SKILL_GREY_3), COST, DESCRIPTION,
				AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE, AbstractCard.CardRarity.RARE,
				AbstractCard.CardTarget.NONE);
		
		//this.keywords.add("tiring");
		
		this.baseMagicNumber = MAGIC_NUM;
		this.magicNumber = this.baseMagicNumber;

		addElementalCost(Element.EARTH, 1);
		addElementalCost(Element.WATER, 1);
	}
	
	@Override
	public boolean customCardTest(AbstractCard card) {
		return true;
	}
	
	@Override
	public void actionCallback(AbstractCard card) {
		card.costForTurn = card.cost-1;
		card.isCostModifiedForTurn = true;
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);
		
		ArrayList<AbstractMonster> enemies = this.getAllLivingEnemies();
		AbstractMonster randomEnemy = enemies.get((int)(Math.random()*enemies.size()));
		

		if (cast(Element.EARTH, 1)) {
		    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(randomEnemy, p, new StrengthPower(randomEnemy, -this.magicNumber), -this.magicNumber));
		}

		if (cast(Element.WATER, 1)) {
		    AbstractDungeon.actionManager.addToBottom(new DrawCardFromPileAction(this, AbstractDungeon.player.drawPile, 1));
		}
	}

	public AbstractCard makeCopy() {
		return new Erosion();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(1);
			//editElementalCost(0, "Water", MAGIC_NUM);
		}
	}
}