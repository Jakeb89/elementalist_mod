package elementalist_mod;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import elementalist_mod.ElementalistMod;
import elementalist_mod.cards.AbstractElementalistCard;

public class ElementizeRNG {
	private static ArrayList<String> previousElementPicks = new ArrayList<String>();

	private static final int AIR = 0;
	private static final int WATER = 1;
	private static final int EARTH = 2;
	private static final int FIRE = 3;
	
	private static double[] hoarderMemory = {0, 0, 0, 0};

	private static String getElementName(int element) {
		switch(element) {
			case(FIRE): return "Fire";
			case(EARTH): return "Earth";
			case(WATER): return "Water";
			case(AIR): return "Air";
		}
		return "";
	}
	private static int getElementID(String element) {
		for(int i=0; i<4; i++) {
			if(getElementName(i) == element) return i;
		}
		return -1;
	}

	public static String getRandomElement() {
		ElementalistMod.log("getRandomElement()");
		double[] weights = {0, 0, 0, 0};

		weights = addWeights(weights, 1, trueRNG(), Math.random());
		weights = addWeights(weights, 1, trueRNG(), Math.random());
		weights = addWeights(weights, 1, trueRNG(), Math.random());
		weights = addWeights(weights, 1, hoarderRNG(), 0.5*Math.random());
		weights = addWeights(weights, 1, historicalRNG(), Math.random());
		weights = addWeights(weights, 1, spenderRNG(), 0.5*Math.random());
		
		weights = normalize(weights);
		logWeights("Final weights: ", weights);
		
		double highestValue = 0;
		String element = "";
		
		for(int i=0; i<weights.length; i++) {
			if(weights[i] > highestValue) {
				highestValue = weights[i];
				element = getElementName(i);
			}
		}
		
		previousElementPicks.add(0, element);
		ElementalistMod.log("Result: " + element);
		
		return element;
	}
	
	private static double[] addWeights(double[] weights1, double[] weights2) {
		return addWeights(weights1, 1, weights2, 1);
	}
	
	private static double[] addWeights(double[] weights1, double lean1, double[] weights2, double lean2) {
		for(int i=0; i<weights1.length; i++) {
			weights1[i] = weights1[i]*lean1 + weights2[i]*lean2;
		}
		return weights1;
	}
	
	private static void logWeights(String title, double[] weights) {
		String output = "";
		for(int i=0; i<4; i++) {
			if(i>0) output += ", ";
			output += Math.round(weights[i]*100);
		}
		ElementalistMod.log(title+" ( " + output + " ) ");
	}

	private static double[] trueRNG() {
		double[] weights = {0, 0, 0, 0};
		
		weights[FIRE] 	= Math.random();
		weights[EARTH] 	= Math.random();
		weights[AIR] 	= Math.random();
		weights[WATER] 	= Math.random();
		
		weights = normalize(weights);
		logWeights("trueRNG()", weights);
		
		return weights;
	}

	private static double[] hoarderRNG() {
		double[] weights = {0, 0, 0, 0};
		
		weights[FIRE] 	= ElementalistMod.getElement("Fire");
		weights[EARTH] 	= ElementalistMod.getElement("Earth");
		weights[WATER] 	= ElementalistMod.getElement("Water");
		weights[AIR] 	= ElementalistMod.getElement("Air");

		for(AbstractCard abstractCard : AbstractDungeon.player.hand.group) {
			if(abstractCard instanceof AbstractElementalistCard) {
				AbstractElementalistCard elementalistCard = (AbstractElementalistCard) abstractCard;
				for(int i=0; i<elementalistCard.costElement.size(); i++) {
					if(elementalistCard.generatesElement) {
						String element = elementalistCard.element;
						int amount = elementalistCard.generatedElementAmount;
						
						weights[getElementID(element)] += amount*0.5;
					}
				}
			}
		}

		//weights = normalize(weights);
		weights = invert(weights);
		weights = normalize(weights);
		
		weights = addWeights(weights, normalize(invert(hoarderMemory)));
		weights = normalize(weights);
		logWeights("hoarderRNG()", weights);
		
		hoarderMemory = normalize(addWeights(hoarderMemory, weights));
		
		return weights;
	}

	private static double[] spenderRNG() {
		double[] weights = {0, 0, 0, 0};
		
		for(AbstractCard abstractCard : AbstractDungeon.player.hand.group) {
			if(abstractCard instanceof AbstractElementalistCard) {
				AbstractElementalistCard elementalistCard = (AbstractElementalistCard) abstractCard;
				for(int i=0; i<elementalistCard.costElement.size(); i++) {
					String element = elementalistCard.costElement.get(i);
					int cost = elementalistCard.costElementAmount.get(i);
					if(element != "" && cost > 0) {
						weights[getElementID(element)] += cost;
					}
				}
			}
		}
		
		weights = normalize(weights);
		logWeights("spenderRNG()", weights);
		
		return weights;
	}
	
	private static double[] normalize(double[] weights) {
		double sum = 0;
		for(int i=0; i<weights.length; i++) {
			sum += weights[i];
		}
		if(sum<=0) {
			sum = 1;
		}
		for(int i=0; i<weights.length; i++) {
			weights[i] = weights[i]/sum;
		}
		return weights;
	}
	
	private static double[] invert(double[] weights) {
		for(int i=0; i<weights.length; i++) {
			if(weights[i] <= 0) weights[i] += 0.001;
			weights[i] = 1/weights[i];
		}
		return weights;
	}

	private static double[] historicalRNG() {
		double[] weights = {0, 0, 0, 0};
		
		//double fire = 0, earth = 0, water = 0, air = 0;
		while (previousElementPicks.size() > 10) {
			previousElementPicks.remove(previousElementPicks.size() - 1);
		}

		for (int i = 0; i < previousElementPicks.size(); i++) {
			switch (previousElementPicks.get(i)) {
			case ("Fire"):
				weights[FIRE] += 10-i;
				break;
			case ("Earth"):
				weights[EARTH] += 10-i;
				break;
			case ("Water"):
				weights[WATER] += 10-i;
				break;
			case ("Air"):
				weights[AIR] += 10-i;
				break;
			}
		}
		
		//weights = normalize(weights);
		weights = invert(weights);
		weights = normalize(weights);
		logWeights("historicalRNG()", weights);
		
		return weights;
	}
}
