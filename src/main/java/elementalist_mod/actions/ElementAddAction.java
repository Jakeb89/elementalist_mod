package elementalist_mod.actions;

import elementalist_mod.ElementalistMod;
import elementalist_mod.orbs.ElementOrb;
import elementalist_mod.powers.ConfluencePower;
import elementalist_mod.powers.ElementalPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;


public class ElementAddAction extends AbstractElementalistAction {
    private ElementOrb orb;
    private int amount;
    private String sourceType = "card";


    public ElementAddAction(ElementOrb newOrb) {
        this(newOrb, "card");
    }
    
    public ElementAddAction(ElementOrb newOrb, String sourceType) {
        //ElementalistMod.log("ElementAddAction()");
        this.actionType = AbstractGameAction.ActionType.POWER;
    	this.orb = newOrb;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = newOrb.amount;
        this.sourceType = sourceType;
    }


    public void update() {
        //ElementalistMod.log("ElementAddAction.update()");
        
        /*if(amount>0 && sourceType == "card") {
	        for(AbstractRelic relic : AbstractDungeon.player.relics) {
	        	if(relic instanceof MagusStaff) {
	        		MagusStaff magusStaff = (MagusStaff)relic;
	        		if(MagusStaff.active) {
	        			magusStaff.registerUse();
	        			orb.amount += 1;
	        	        this.amount = orb.amount;
	        			magusStaff.flash();
	        		}
	        	}
	        }
        }*/
        
    	orb.amount = amount;
    	
        for(AbstractPower power : AbstractDungeon.player.powers) {
        	if(power instanceof ElementalPower) {
        		ElementalPower elementalPower = (ElementalPower) power;
        		orb.amount = elementalPower.onAddElement(orb.element, orb.amount, sourceType);
        	}
        }
    	

    	for(ElementOrb elementOrb : ElementalistMod.getElementOrbs()) {
    		//if(orb instanceof ElementOrb) {
    			//ElementOrb elementOrb = (ElementOrb)orb;
    			if(elementOrb.element == this.orb.element) {
    		        //ElementalistMod.log("ElementAddAction.update() -> adding to existing orb");

    				if(this.orb.amount > 0 && ElementalistMod.hasMultification()) {
    					this.orb.amount *= 2;
    				}
    				
    				elementOrb.amount += this.orb.amount;
    				elementOrb.triggerEvokeAnimation();
    				if(elementOrb.amount<0) elementOrb.amount = 0;

    		        this.isDone = true;
    		        return;
    			}
    		//}
    	}

        //ElementalistMod.log("ElementAddAction.update() -> creating new orb");
        //AbstractDungeon.player.increaseMaxOrbSlots(1, true);
		
    	//AbstractDungeon.player.channelOrb(orb);
    	ElementalistMod.addElementOrb(orb);
		
		for(AbstractPower power : AbstractDungeon.player.powers) {
			if(power instanceof ConfluencePower)
			((ConfluencePower)power).updateDescription();
		}
		
		
		logActionComplete();
        this.isDone = true;
        return;
    }

}
