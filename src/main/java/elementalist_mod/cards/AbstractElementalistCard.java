package elementalist_mod.cards;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.abstracts.CustomCard;
import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementizeRNG;
import elementalist_mod.orbs.*;
import elementalist_mod.powers.ElementalPower;

public class AbstractElementalistCard extends CustomCard{
	
	public boolean generatesElement = false;
	public int generatedElementAmount = 1;
	public String element = "";
	public ArrayList<String> costElement = new ArrayList<String>(); //Change this default to "" later.
	public ArrayList<Integer> costElementAmount = new ArrayList<Integer>(); //Change this default to 0 later.
	//public String costElementAmountCustom = "";
	public String customDescription = "";
	public boolean isMercurial = false;
	public boolean effectsChecked = false;
	public boolean emblemedThisTurn = false;
	public boolean isEmblem = false;
	public String emblemID = "";
	public boolean tiringPlayed = false;
	

	public AbstractElementalistCard(String id, String name, String img, int cost, String rawDescription, CardType type,
			CardColor color, CardRarity rarity, CardTarget target) {
		super(id, name, img, cost, rawDescription, type, color, rarity, target);
		// TODO Auto-generated constructor stub
	}
	
	public boolean willSuccessfullyGainElement() {
		//This is a very dumb method that will work in most cases, but should be overwritten if the card has multiple source elements.
		//...or if the card has one, but gaining an element isn't dependant on that element.
		for (int i = 0; i < costElement.size(); i++) {
			int usedAlready = 0;
			for (int j = 0; j < i; j++) {
				if (costElement.get(i) == costElement.get(j)) {
					usedAlready += costElementAmount.get(j);
				}
			}
			boolean elementIsReady = (costElementAmount.get(i) + usedAlready <= ElementalistMod.getElement(costElement.get(i))) ? true : false;
			if(!elementIsReady) return false;
		}
		return true;
	}
	
	public void onAddedToMasterDeck() {
		if(this.isEmblem) {
			//AbstractDungeon.player.decreaseMaxHealth(5);
		}
	}
	
	public void onRemovedFromMasterDeck() {
		if(this.isEmblem) {
			//AbstractDungeon.player.increaseMaxHp(5, true);
		}
	}
	
	public void mercurialEffect() {
		ArrayList<AbstractCard> choices = new ArrayList<AbstractCard>();
		for(AbstractCard card : AbstractDungeon.player.hand.group) {
			if(card.cost > 0) {
				choices.add(card);
			}
		}
		if(choices.size()>0) {
			AbstractCard chosen = choices.get((int) (Math.random()*choices.size()));
			chosen.costForTurn = Math.max(0, chosen.cost - 1);
			chosen.isCostModifiedForTurn = true;
			this.flash();
			chosen.update();
			chosen.flash();
		}
	}
	
	public void addElementalCost(String element, int amount) {
		costElement.add(element);
		costElementAmount.add(amount);
	}
	
	public void editElementalCost(int index, String element, int amount) {
		costElement.remove(index);
		costElement.add(index, element);
		
		costElementAmount.remove(index);
		costElementAmount.add(index, amount);
	}

	
	@Override
	public void triggerWhenDrawn(){
		super.triggerWhenDrawn();
		
		checkEffects();
	}

	public void checkEffects() {
		//if(!effectsChecked) {
		if(isMercurial) {
			mercurialEffect();
		}
		
		if(generatesElement) {
			elementizeEffect();
		}
		
		if(isEmblem) {
			emblemEffect();
		}
		
		
			//effectsChecked = true;
		//}
	}
	
	public void emblemEffect() {
		if(!emblemedThisTurn) {
			this.use(AbstractDungeon.player, null);
			AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(this));
			AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
			emblemedThisTurn = true;
		}else {
			int nonEmblemsInDrawOrDiscard = 0;
			for(AbstractCard card : AbstractDungeon.player.drawPile.group) {
				if(!card.keywords.contains("emblem")) {
					nonEmblemsInDrawOrDiscard++;
				}
			}
			for(AbstractCard card : AbstractDungeon.player.discardPile.group) {
				if(!card.keywords.contains("emblem")) {
					nonEmblemsInDrawOrDiscard++;
				}
			}
			if(nonEmblemsInDrawOrDiscard > 0) {
				this.moveToDiscardPile();
				AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(this));
				AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
			}else {
				AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(this));
			}
		}
	}
	

	/*public Color getCustomEnergyCostColor(int index) {
		if ((AbstractDungeon.player != null) && (AbstractDungeon.player.hand.contains(this))) { // check if lacking elemental cost
			String element = costElement.get(index);
			if (AbstractElementalistCard.getElement(element) < costElementAmount.get(index)) {
				return Color.DARK_GRAY.cpy();
			}
		}
		return Color.WHITE.cpy();
	}*/

	public boolean customCardTest(AbstractCard card) {
		//Override this in child classes if needed.
		return true;
	}
	
	public void actionCallback(int result) {
		//Override this in child classes if needed.
	}
	
	public void actionCallback(AbstractCard card) {
		//Override this in child classes if needed.
	}
	
	public void atTurnStart() {
		emblemedThisTurn = false;
	}
	


	@Override
	public void upgrade() {
		// TODO Auto-generated method stub
		
	}

	public void elementizeEffect() {
		if (!generatesElement) return;
		
		if(!customDescription.toLowerCase().contains("elementize")) return;
		
		if(element != "") return;
			
		element = ElementizeRNG.getRandomElement();
		
		rawDescription = customDescription;
		rawDescription = rawDescription.replace("Elementize", "Create "+element);
		rawDescription = rawDescription.replace("elementize", "Create "+element);
		initializeDescription();
		this.update();
	}
	
	public void undoElementize() {
		element = "";

		rawDescription = customDescription;
		initializeDescription();
		this.update();
	}

	public void tiringEffect() {
		if(!rawDescription.toLowerCase().contains("tiring")) return;
		
		if(tiringPlayed) {
			if(rawDescription.toLowerCase().contains("exhaust")) return;
			
			this.exhaust = true;
			this.keywords.add("exhaust");
			rawDescription = rawDescription.replace("Tiring.", "Tiring. Exhaust.");
			this.initializeDescription();
			this.update();
		}else {
			tiringPlayed = true;
		}
	}
	
	public int cast(String element) {
		int amount = getElement(element);
		changeElement(element, -amount);
		doCastTriggers(element);
		return amount;
	}
	
	public boolean cast(String element, int amount) {
		if(getElement(element) >= amount) {
			changeElement(element, -amount);
			doCastTriggers(element);
			return true;
		}
		return false;
	}
	
	public void doCastTriggers(String element) {
		for(AbstractPower power : AbstractDungeon.player.powers) {
			if(power instanceof ElementalPower) {
				ElementalPower elementalPower = (ElementalPower) power;
				elementalPower.onElementalCast(element);
			}
		}
	}

	@Override
	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		// TODO Auto-generated method stub
		elementizeEffect();

		if(!tiringPlayed) {
			tiringEffect();
		}
	}
	
	@Override
	public void onMoveToDiscard() {
		super.onMoveToDiscard();
		
		if(tiringPlayed) {
			tiringEffect();
		}
	}
	

	public static int getElement(String element) {
		return ElementalistMod.getElement(element);
	}

	public void changeElement(String element, int delta) {
		ElementalistMod.changeElement(element, delta);
	}
	
	public void changeElement(String element, int delta, String source) {
		ElementalistMod.changeElement(element, delta, source);
	}
	
	public ElementOrb makeOrb(String element, int amount) {
		return ElementalistMod.makeOrb(element, amount);
	}
	
	public AbstractMonster pickRandomLivingEnemy() {
		return ElementalistMod.pickRandomLivingEnemy();
	}
	
	public ArrayList<AbstractMonster> getAllLivingEnemies() {
		return ElementalistMod.getAllLivingEnemies();
	}
	
	public boolean intentContainsAttack(Intent intent) {
		return ElementalistMod.intentContainsAttack(intent);
	}

	public String[] getHighestElements() {
		return ElementalistMod.getHighestElements();
	}
	
	public AbstractCard[] getAllStatusOrCurse(CardGroup cardGroup) {
		return ElementalistMod.getAllStatusOrCurse(cardGroup);
	}
	
	public AbstractCard getRandomCard(AbstractCard[] cards) {
		return ElementalistMod.getRandomCard(cards);
	}

	public int getTotalCost() {
		int output = 0;
		output += Math.max(0, this.cost);
		for(int i=0; i<this.costElementAmount.size(); i++) {
			output += Math.max(0, this.costElementAmount.get(i));
		}
		return output;
	}

	public ArrayList<String> getElementalTags() {
		ArrayList<String> output = new ArrayList<String>();
		if(element != "") output.add(element);
		output.addAll(costElement);
		for(String element : costElement) {
			output.add(element+"cast");
		}
		return output;
	}

	public void actionCallback(int memory, int memory2) {
		// TODO Auto-generated method stub
		
	}

}
