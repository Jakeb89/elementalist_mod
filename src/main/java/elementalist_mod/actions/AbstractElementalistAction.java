package elementalist_mod.actions;

import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.orbs.ElementOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import java.util.ArrayList;

public class AbstractElementalistAction extends AbstractGameAction {
	public AbstractElementalistCard sourceCard;
	public AbstractCreature specificTarget;
	

	public AbstractElementalistAction(AbstractElementalistCard sourceCard, AbstractCreature specificTarget) {
		this.sourceCard = sourceCard;
		this.specificTarget = specificTarget;
	}
	public AbstractElementalistAction() {
		this.sourceCard = null;
		this.specificTarget = null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	
	/*Pass-through to ElementalistMod functions*/

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

	public void actionCallback(int memory) {
		// TODO Auto-generated method stub
		
	}
	public void actionCallback(int memory, int memory2) {
		// TODO Auto-generated method stub
		
	}

}
