package elementalist_mod.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;

import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.patches.*;

public class Refract extends AbstractElementalistCard {
	public static final String ID = "elementalist:Refract";
	public static final String NAME = "Refract";
	public static String DESCRIPTION = "Attempt to copy the target's intent onto this card for the remainder of the battle.";
	private static final int COST = 1;
	private EnemyMoveInfo copiedMove = null;
	private int ATK = 0;
	private int ATK_MUL = 0;
	private int BLK = 0;
	private int MAG = 0;
	private boolean isBuff = false;
	private boolean isDebuff = false;
	private boolean isStrongDebuff = false;
	private boolean isEscape = false;
	private boolean isMagic = false;
	private boolean isSleep = false;
	private boolean isStun = false;
	private boolean isOther = false;
	private boolean isPureDefend = false;
	private boolean isPureBuff = false;
	private boolean isPureDebuff = false;

	public Refract() {
		super(ID, NAME, ElementalistMod.makePath(ElementalistMod.BETA_SKILL_GREY_2), COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.ELEMENTALIST_BLUE,
			AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY);
		
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer player, AbstractMonster target) {
		super.use(player, target);
		if(copiedMove == null) {
			try {
				final java.lang.reflect.Field move = AbstractMonster.class.getDeclaredField("move");
				move.setAccessible(true);
				copiedMove = (EnemyMoveInfo) move.get(target);
				updateCopiedData();
				updateDescription();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			if(ATK_MUL == 1) {
				AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(player, this.damage), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
			}else if(ATK_MUL > 1) {
				for(int i=0; i<ATK_MUL; i++) {
					AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(player, this.damage), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
				}
			}
			
			if(BLK > 0) {
				AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.block));
			}
			
			if(isBuff) {
				int str = (isPureBuff ? 2 : 1);
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new StrengthPower(player, str), str));
			}
			
			if(isDebuff) {
				int vuln = (isPureDebuff ? 2 : 1);
	            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, player, new VulnerablePower(target, vuln, false), vuln));
			}
			
			if(isStrongDebuff) {
				int str = -2;
	            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, player, new StrengthPower(target, str), str));
			}
			
			if(isEscape) {
				AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));

				int goldInc = 25;
		        AbstractDungeon.player.gainGold(goldInc);
		        for (int i = 0; i < goldInc; i++) {
		          AbstractDungeon.effectList.add(new GainPennyEffect(player, target.hb.cX, target.hb.cY, player.hb.cX, player.hb.cY, true));
		        }
		        
		        if (target.type != AbstractMonster.EnemyType.BOSS) {
		            AbstractDungeon.getCurrRoom().smoked = true;
		            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmokeBombEffect(target.hb.cX, target.hb.cY)));
		            AbstractDungeon.player.hideHealthBar();
		            AbstractDungeon.player.isEscaping = true;
		            AbstractDungeon.player.flipHorizontal = (!AbstractDungeon.player.flipHorizontal);
		            AbstractDungeon.overlayMenu.endTurnButton.disable();
		            AbstractDungeon.player.escapeTimer = 2.5F;
		        }
		        
			}
			
			if(isMagic || isStun || isOther) {
			    AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(2));
			}
			
			if(isSleep) {
			      AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth / 10);
			}
		}
	}
	
	public void updateCopiedData() {
		switch(copiedMove.intent) {
		case ATTACK:
		case ATTACK_BUFF:
		case ATTACK_DEBUFF:
		case ATTACK_DEFEND:
			ATK = copiedMove.baseDamage;
			if(copiedMove.isMultiDamage) {
				ATK_MUL = copiedMove.multiplier;
			}else{
				ATK_MUL = 1;
			}
			break;
		default:
			break;
		}

		switch(copiedMove.intent) {
		case DEFEND:
			isPureDefend = true;
		case ATTACK_DEFEND:
		case DEFEND_BUFF:
		case DEFEND_DEBUFF:
			BLK = 10;
		}

		switch(copiedMove.intent) {
		case BUFF:
			isPureBuff = true;
		case ATTACK_BUFF:
		case DEFEND_BUFF:
			isBuff = true;
		}
		
		switch(copiedMove.intent) {
		case DEBUFF:
			isPureDebuff = true;
		case ATTACK_DEBUFF:
		case DEFEND_DEBUFF:
			isDebuff = true;
			break;
		case STRONG_DEBUFF:
			isStrongDebuff = true;
		}
		
		switch(copiedMove.intent) {
		case ESCAPE:
			isEscape = true;
			break;
		case MAGIC:
			isMagic = true;
			break;
		case SLEEP:
			isSleep = true;
			break;
		case STUN:
			isStun = true;
			break;
		case NONE:
		case UNKNOWN:
		case DEBUG:
			isOther = true;
		}
		
		if(copiedMove != null) {
			ElementalistMod.logger.info(copiedMove.intent);
			ElementalistMod.logger.info(copiedMove.baseDamage);
			ElementalistMod.logger.info(copiedMove.isMultiDamage);
			ElementalistMod.logger.info(copiedMove.multiplier);
		}
	}


	public AbstractCard makeCopy() {
		return new Refract();
	}
	
	public void updateDescription() {
		rawDescription = ">>>";
		if(ATK_MUL == 1) {
			this.upgradeDamage(ATK - this.baseDamage);
			rawDescription += " NL Deal !D! damage.";
		}else if(ATK_MUL > 1) {
			this.upgradeDamage(ATK - this.baseDamage);
			rawDescription += " NL Deal !D! damage "+ATK_MUL+" times.";
		}
		
		if(BLK > 0) {
			this.upgradeBlock(BLK - this.baseBlock);
			rawDescription += " NL Gain "+(isPureDefend ? 2*BLK : BLK)+" Block.";
		}
		
		if(isBuff) {
			rawDescription += " NL Gain "+(isPureBuff ? 2 : 1)+" Strength.";
		}
		
		if(isDebuff) {
			rawDescription += " NL Target gains "+(isPureDebuff ? 2 : 1)+" Vulnerable.";
		}
		
		if(isStrongDebuff) {
			rawDescription += " NL Target loses 2 Strength.";
		}
		
		if(isEscape) {
			rawDescription += " NL Exhaust. Steal 25 gold. NL Escape from non-bosses.";
		}
		
		if(isMagic || isStun || isOther) {
			this.modifyCostForCombat(0);
			this.isCostModified = true;
			this.update();
			rawDescription += " NL Gain 2 Energy.";
		}
		
		if(isSleep) {
			rawDescription += " NL Regain "+(AbstractDungeon.player.maxHealth / 10)+" health.";
		}
		
		rawDescription = rawDescription.replace(">>> NL ", "");
		initializeDescription();
		update();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.upgradeBaseCost(0);
			initializeDescription();
		}
	}

}