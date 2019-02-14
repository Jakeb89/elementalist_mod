package elementalist_mod.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import elementalist_mod.ElementalistMod;
import elementalist_mod.actions.CallbackAction;
import elementalist_mod.actions.CustomDamageAction;
import elementalist_mod.actions.SpellChantAction;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.effects.SpellCastEffect;
import elementalist_mod.patches.*;

public class Heavensfall extends AbstractElementalistCard {
	public static final String ID = "elementalist:Heavensfall";
	public static final String NAME = "Heavensfall";
	public static String DESCRIPTION = "Exhaust. Elementcast X: Deal !D! damage to ALL enemies X times.";
	private static final int COST = 3;
	private static final int DAMAGE = 3;
	private static final int DAMAGE_UPGRADE = 2;

	private static String[] spellChant = {
		"I, who stand in the full light of the heavens, ",
		"command thee, who opens the gates of hell.",
		"Come forth, divine lightning! ",
		"This ends now! Indignation!!"
		};

	public Heavensfall() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.HEAVENSFALL), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ALL_ENEMY);
		
		this.baseDamage = DAMAGE;

		addElementalCost("Air", -1);
		addElementalCost("Water", -1);
		addElementalCost("Earth", -1);
		addElementalCost("Fire", -1);
		this.exhaust = true;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		super.use(p, m);
		
		int attacks = getElement("Air") + getElement("Water") + getElement("Earth") + getElement("Fire");
		
		this.cast("Air");
		this.cast("Water");
		this.cast("Earth");
		this.cast("Fire");

		float duration = 0.5f;
		int hits = 0;
		for(int i=0; i<attacks; i++) {
			duration *= 0.7f;
			for(AbstractMonster enemy : this.getAllLivingEnemies()) {
				if(hits > 40) {
					duration = 0.001f;
				}
				int expectedTotalHits = hits + (attacks-i)*getAllLivingEnemies().size();
				AbstractDungeon.actionManager.addToBottom(new CallbackAction(this, hits, expectedTotalHits));
				AbstractDungeon.actionManager.addToBottom(makeDamageAction(p, enemy, duration));
				hits++;
			}
		}

	}
	
	public void actionCallback(int hits, int expectedTotalHits) {
		queueSpeechAction(hits, expectedTotalHits);
	}
	
	public void queueSpeechAction(int hit, int totalHits) {
		if(totalHits > 5 && hit == 0) {
			AbstractDungeon.actionManager.addToTop(new SpellChantAction(spellChant[0], 2f));
			
		    AbstractDungeon.actionManager.addToTop(new SFXAction("WIND"));
		    AbstractDungeon.actionManager.addToTop(new VFXAction(AbstractDungeon.player, getSpellCastEffect("circle", 1.5f, true, 2F), 0.01F));
		}

		if(totalHits > 10 && hit == 5) {
			AbstractDungeon.actionManager.addToTop(new SpellChantAction(spellChant[1], 2f));
			
		    AbstractDungeon.actionManager.addToTop(new SFXAction("ORB_DARK_CHANNEL"));
		    AbstractDungeon.actionManager.addToTop(new VFXAction(AbstractDungeon.player, getSpellCastEffect("circle fancy", 1.7f, true, 1.5F), 0.01F));
		    AbstractDungeon.actionManager.addToTop(new VFXAction(AbstractDungeon.player, getSpellCastEffect("circle", 1.2f, true, 1.5F), 0.01F));
		}

		if(totalHits > 15 && hit == 10) {
			AbstractDungeon.actionManager.addToTop(new SpellChantAction(spellChant[2], 2f));
			
		    AbstractDungeon.actionManager.addToTop(new SFXAction("ORB_DARK_CHANNEL"));
		    AbstractDungeon.actionManager.addToTop(new VFXAction(AbstractDungeon.player, getSpellCastEffect("orokin circle", 2f, false, 1F), 0.01F));
		    AbstractDungeon.actionManager.addToTop(new VFXAction(AbstractDungeon.player, getSpellCastEffect("circle", 2.2f, true, 1F), 0.01F));
		    AbstractDungeon.actionManager.addToTop(new VFXAction(AbstractDungeon.player, getSpellCastEffect("cross", 2f, false, 1F), 0.01F));
		}

		if(totalHits > 30 && hit == 19) {
			AbstractDungeon.actionManager.addToTop(new SpellChantAction(spellChant[3], 2f));
			
		    AbstractDungeon.actionManager.addToTop(new SFXAction("ORB_DARK_CHANNEL"));
		    
			SpellCastEffect spellEffect =  getSpellCastEffect("indignation circle", 2.5f, true, 1F);
			spellEffect.isSudden = true;
		    AbstractDungeon.actionManager.addToTop(new VFXAction(AbstractDungeon.player, spellEffect, 1.0F));
		}

		if(totalHits > 30 && hit == 20) {
		    AbstractDungeon.actionManager.addToTop(new SFXAction("ORB_DARK_EVOKE"));
		    
			SpellCastEffect spellEffect = getSpellCastEffect("divine lightning", 5f, false, 0.5F);
			spellEffect.isSudden = true;
		    AbstractDungeon.actionManager.addToTop(new VFXAction(AbstractDungeon.player, spellEffect, 0.01F));
		}
	}
	
	public SpellCastEffect getSpellCastEffect(String name, float scale, boolean canRotate, float length) {
		SpellCastEffect effect = new SpellCastEffect(name, scale, canRotate);
		effect.setDuration(length);
		return effect;
	}
	
	public CustomDamageAction makeDamageAction(AbstractPlayer p, AbstractMonster enemy, float duration) {
		CustomDamageAction damageAction = (new CustomDamageAction(enemy, new DamageInfo(p, this.damage), AbstractGameAction.AttackEffect.SMASH));
		damageAction.setDuration(duration);
		return damageAction;
	}

	public AbstractCard makeCopy() {
		return new Heavensfall();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeDamage(DAMAGE_UPGRADE);
			initializeDescription();
		}
	}

}