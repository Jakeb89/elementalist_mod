package elementalist_mod.cards;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.abstracts.CustomCard;
import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.ElementizeRNG;
import elementalist_mod.actions.CallbackAction;
import elementalist_mod.actions.NextCardStepAction;
import elementalist_mod.orbs.*;
import elementalist_mod.powers.ElementalPower;

public class AbstractElementalistCard extends CustomCard{
	
	public boolean generatesElement = false;
	public int generatedElementAmount = 1;
	public Element element = null;
	public ArrayList<Element> costElement = new ArrayList<Element>(); //Change this default to "" later.
	public ArrayList<Integer> costElementAmount = new ArrayList<Integer>(); //Change this default to 0 later.
	//public String costElementAmountCustom = "";
	public String customDescription = "";
	public boolean isMercurial = false;
	public boolean effectsChecked = false;
	public boolean emblemedThisTurn = false;
	public boolean isEmblem = false;
	public boolean isFakeCard = false;
	public String emblemID = "";
	public boolean tiringPlayed = false;
	public boolean isWard = false;
	public boolean activeWard = false;
	public boolean unplayable = false;
	private int cardStep = 0;
	protected boolean cardStillResolving = true;
	protected AbstractPlayer player = null;
	protected AbstractMonster singleTarget = null;
	protected ArrayList<AbstractGameAction> actionQueue = new ArrayList<AbstractGameAction>();
	

	public AbstractElementalistCard(String id, String name, String img, int cost, String rawDescription, CardType type,
			CardColor color, CardRarity rarity, CardTarget target) {
		super(id, name, img, cost, rawDescription, type, color, rarity, target);
		// TODO Auto-generated constructor stub
	}
	
	public boolean willSuccessfullyGainElement() {
		//This is a very dumb method that will work in most cases, but should be overwritten if the card has multiple source elements.
		//...or if the card has one, but gaining an element isn't dependent on that element.
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
	
	public void addElementalCost(Element element, int amount) {
		costElement.add(element);
		costElementAmount.add(amount);
	}
	
	public void editElementalCost(int index, Element element, int amount) {
		costElement.remove(index);
		costElement.add(index, element);
		
		costElementAmount.remove(index);
		costElementAmount.add(index, amount);
	}

	
	@Override
	public void triggerWhenDrawn(){
		super.triggerWhenDrawn();
		
		checkEffects();
		if(isWard) {
			this.retain = true;
			this.activeWard = true;
		}
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
		if(isEmblem) {
			emblemedThisTurn = false;
		}
		if(isWard) {
			AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(this));
		}
	}
	


	@Override
	public void upgrade() {
		// TODO Auto-generated method stub
		
	}

	public void elementizeEffect() {
		if (!generatesElement) return;
		
		if(!customDescription.toLowerCase().contains("elementize")) return;
		
		if(element != null) return;
			
		element = ElementizeRNG.getRandomElement();
		
		rawDescription = customDescription;
		rawDescription = rawDescription.replace("Elementize", "Create "+ElementalistMod.getElementName(element));
		rawDescription = rawDescription.replace("elementize", "Create "+ElementalistMod.getElementName(element));
		initializeDescription();
		this.update();
	}
	
	public void undoElementize() {
		if(!customDescription.toLowerCase().contains("elementize")) return;
		element = null;

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
	
	public int cast(Element element) {
		int amount = getElement(element);
		changeElement(element, -amount);
		doCastTriggers(element);
		return amount;
	}
	
	public boolean cast(Element element, int amount) {
		if(getElement(element) >= amount) {
			changeElement(element, -amount);
			doCastTriggers(element);
			return true;
		}
		return false;
	}
	
	public int castNow(Element element) {
		int amount = getElement(element);
		changeElementNow(element, -amount);
		doCastTriggers(element);
		return amount;
	}
	
	public boolean castNow(Element element, int amount) {
		if(getElement(element) >= amount) {
			changeElementNow(element, -amount);
			doCastTriggers(element);
			return true;
		}
		return false;
	}
	
	public void doCastTriggers(Element element) {
		for(AbstractPower power : AbstractDungeon.player.powers) {
			if(power instanceof ElementalPower) {
				ElementalPower elementalPower = (ElementalPower) power;
				elementalPower.onElementalCast(element);
			}
		}
	}

	@Override
	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		ElementalistMod.log("AbstractElementalistCard.use() ... ["+this.name+"]");
		// TODO Auto-generated method stub
		elementizeEffect();

		if(!tiringPlayed) {
			tiringEffect();
		}
		
		player = p;
		singleTarget = m;
		
		cardStep = 0;
		//doNextCardStep();
		AbstractDungeon.actionManager.addToBottom(new NextCardStepAction(this));
	}
	
	protected void queueAction(AbstractGameAction action) {
		actionQueue.add(0, action);
	}
	
	protected void flushActionQueue() {
		ElementalistMod.log("AbstractElementalistCard.flushActionQueue() ... ["+this.name+"]");
		while(actionQueue.size()>0) {
			ElementalistMod.log("Adding to top of action queue: "+actionQueue.get(0));
			AbstractDungeon.actionManager.addToTop(actionQueue.remove(0));
		}
	}

	public void doNextCardStep() {
		ElementalistMod.log("AbstractElementalistCard.doNextCardStep() ... ["+this.name+"]");
		if(cardStillResolving) {
			cardStillResolving = doCardStep(cardStep++);
			queueAction(new NextCardStepAction(this));
			flushActionQueue();
		}else {
			cardStillResolving = true;
			cardStep = 0;
		}
	}
	
	public boolean doCardStep(int stepNumber) {
		/*
		switch(stepNumber) {
		case(0):
			return true;
		case(1):
			return true;
		default:
			return false;
		}
		*/
		return false;
	}
	
	@Override
	public void onMoveToDiscard() {
		super.onMoveToDiscard();
		
		if(tiringPlayed) {
			tiringEffect();
		}

		if(isWard) {
			this.retain = true;
			this.activeWard = true;
		}
	}
	

	public static int getElement(Element element) {
		return ElementalistMod.getElement(element);
	}

	public void changeElement(Element element, int delta) {
		ElementalistMod.changeElement(element, delta);
	}
	
	public void changeElement(Element element, int delta, String source) {
		ElementalistMod.changeElement(element, delta, source);
	}

	public void changeElementNow(Element element, int delta) {
		ElementalistMod.changeElementNow(element, delta);
	}
	
	public void changeElementNow(Element element, int delta, String source) {
		ElementalistMod.changeElementNow(element, delta, source);
	}
	
	public ElementOrb makeOrb(Element element, int amount) {
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

	public Element[] getHighestElements() {
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
		if(element != null) output.add(ElementalistMod.getElementName(element));
		for(Element singleElement : costElement){
			output.add(ElementalistMod.getElementName(singleElement));
		}
		//output.addAll(costElement);
		for(Element element : costElement) {
			output.add(element+"cast");
		}
		return output;
	}

	public void actionCallback(int memory, int memory2) {
		// TODO Auto-generated method stub
		
	}

	public int onLoseHp(int damageAmount) {
		//ElementalistMod.log("AbstractElementalistCard.onLoseHp("+damageAmount+")");
		return damageAmount;
	}
	

	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		if(this.unplayable) {
			this.cantUseMessage = "That card is unplayable.";
			return false;
		}
		return super.canUse(p, m);
	}

}
