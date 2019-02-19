package elementalist_mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import elementalist_mod.ElementalistMod;
import elementalist_mod.ElementalistMod.Element;
import elementalist_mod.cards.AbstractElementalistCard;

public class ElementizeRNG {

	public static Element getRandomElement() {
		double rng = Math.random();
		if(rng < 0.25) 	return Element.AIR;
		if(rng < 0.50) 	return Element.WATER;
		if(rng < 0.75) 	return Element.EARTH;
						return Element.FIRE;
	}
	
	/*
	private static ArrayList<Element> previousElementPicks = new ArrayList<Element>();
	
	private static double[] hoarderMemory = {0, 0, 0, 0};

	private static Element getElementName(int element) {
		switch(element) {
			case(FIRE): return Element.FIRE;
			case(EARTH): return Element.EARTH;
			case(WATER): return Element.WATER;
			case(AIR): return Element.AIR;
		}
		return null;
	}
	private static int getElementID(Element element) {
		for(int i=0; i<4; i++) {
			if(getElementName(i) == element) return i;
		}
		return -1;
	}

	public static Element getRandomElement() {
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
		Element element = null;
		
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

	private static HashMap<Element, Double> trueRNG() {
		HashMap<Element, Double> weightMap = new HashMap<Element, Double>();

		weightMap.put(Element.FIRE, 	Math.random());
		weightMap.put(Element.EARTH, 	Math.random());
		weightMap.put(Element.AIR, 		Math.random());
		weightMap.put(Element.WATER, 	Math.random());
		
		weightMap = normalize(weightMap);
		logWeights("trueRNG()", weightMap);
		
		return weightMap;
	}

	private static double[] hoarderRNG() {
		double[] weights = {0, 0, 0, 0};
		
		weights[FIRE] 	= ElementalistMod.getElement(Element.FIRE);
		weights[EARTH] 	= ElementalistMod.getElement(Element.EARTH);
		weights[WATER] 	= ElementalistMod.getElement(Element.WATER);
		weights[AIR] 	= ElementalistMod.getElement(Element.AIR);

		for(AbstractCard abstractCard : AbstractDungeon.player.hand.group) {
			if(abstractCard instanceof AbstractElementalistCard) {
				AbstractElementalistCard elementalistCard = (AbstractElementalistCard) abstractCard;
				for(int i=0; i<elementalistCard.costElement.size(); i++) {
					if(elementalistCard.generatesElement) {
						Element element = elementalistCard.element;
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
					Element element = elementalistCard.costElement.get(i);
					int cost = elementalistCard.costElementAmount.get(i);
					if(element != null && cost > 0) {
						weights[getElementID(element)] += cost;
					}
				}
			}
		}
		
		weights = normalize(weights);
		logWeights("spenderRNG()", weights);
		
		return weights;
	}
	
	private static HashMap<Element, Double> normalize(HashMap<Element, Double> weightMap) {
		double sum = 0;
	    Iterator it = weightMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        sum += (double)pair.getValue();
	        //it.remove(); // avoids a ConcurrentModificationException
	    }
		if(sum<=0) {
			sum = 1;
		}
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        sum += (double)pair.getValue();
	        Element key = (Element) pair.getKey();
	        weightMap.put(key, weightMap.get(key) / sum);
	        //it.remove(); // avoids a ConcurrentModificationException
	    }
		return weightMap;
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
			case FIRE:
				weights[FIRE] += 10-i;
				break;
			case EARTH:
				weights[EARTH] += 10-i;
				break;
			case WATER:
				weights[WATER] += 10-i;
				break;
			case AIR:
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
	*/
}
