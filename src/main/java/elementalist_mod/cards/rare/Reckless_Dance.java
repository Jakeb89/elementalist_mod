package elementalist_mod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.actions.CallbackAction;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;
import elementalist_mod.powers.AerialDodgePower;
import elementalist_mod.powers.WindburnPower;

public class Reckless_Dance extends AbstractElementalistCard {

	public static final String ID = "elementalist:Reckless_Dance";
	public static final String NAME = "Reckless Dance";
	public static String DESCRIPTION = "Tiring. NL Gain !M! Aerial Dodge. Gain 2 Windburn. NL NL Aircast 2: Move half your windburn to a random enemy.";
	public static String DESCRIPTION_UPGRADED = "Tiring. NL Gain !M! Aerial Dodge. Gain 2 Windburn. NL NL Aircast 2: Move ALL your windburn to a random enemy.";
	private static final int COST = 1;
	private static final int MAGIC_NUM = 2;

	public Reckless_Dance() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.RECKLESS_DANCE), COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);

		this.baseMagicNumber = MAGIC_NUM;
		this.magicNumber = this.baseMagicNumber;
		this.keywords.add("tiring");

		addElementalCost(Element.AIR, 2);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);

		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AerialDodgePower(p, p, magicNumber), magicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WindburnPower(p, p, 2), 2));
		AbstractDungeon.actionManager.addToBottom(new CallbackAction(this));
		

	}
	
	public void actionCallback(int value) {
		AbstractPlayer player = AbstractDungeon.player;
		if(cast(Element.AIR, 2)) {
			if(player.hasPower(WindburnPower.POWER_ID)) {
				int windburn = player.getPower(WindburnPower.POWER_ID).amount;
				if(!upgraded) windburn /= 2;
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new WindburnPower(player, player, -windburn), -windburn));
				
				int targetIndex = (int) (Math.random()*this.getAllLivingEnemies().size());
				AbstractCreature target = this.getAllLivingEnemies().get(targetIndex);
				
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, player, new WindburnPower(target, player, windburn), windburn));
			}
		}
	}

	public AbstractCard makeCopy() {
		return new Reckless_Dance();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.rawDescription = DESCRIPTION_UPGRADED;
			this.initializeDescription();
			
		}
	}
}
