package elementalist_mod;


import basemod.CustomEventRoom;
import basemod.ReflectionHacks;
import elementalist_mod.cards.AbstractElementalistCard;
import elementalist_mod.cards.special.Air_Emblem;
import elementalist_mod.cards.special.Earth_Emblem;
import elementalist_mod.cards.special.Fire_Emblem;
import elementalist_mod.cards.special.Water_Emblem;
import elementalist_mod.characters.TheElementalist;
import elementalist_mod.events.ElementalGrowthEvent;
import elementalist_mod.ui.ElementalistLevelUpOption;

import com.badlogic.gdx.graphics.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.events.*;
import com.megacrit.cardcrawl.events.beyond.*;
import com.megacrit.cardcrawl.events.exordium.*;
import com.megacrit.cardcrawl.events.city.*;

import java.util.ArrayList;

import com.badlogic.gdx.*;
import com.megacrit.cardcrawl.rewards.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.*;

public class ElementalistLevelUpEffect extends AbstractGameEffect
{
    private static final float DUR = 1.5f;
    private boolean openedScreen = false;
    private Color screenColor = AbstractDungeon.fadeColor.cpy();
    public boolean isFree;
    public boolean choseCard = false;
    public ElementalistLevelUpOption button;

    public ElementalistLevelUpEffect(ElementalistLevelUpOption button, boolean isFree) {
        this.duration = DUR;
        this.screenColor.a = 0.0f;
        this.isFree = isFree;
        this.button = button;
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }

    @Override
    public void update() {
        if(!AbstractDungeon.isScreenUp) {
            this.duration -= Gdx.graphics.getDeltaTime();
            this.updateBlackScreenColor();
        }
        if(!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard card = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            //card.misc = card.baseMisc;
            if(card instanceof AbstractElementalistCard && ((AbstractElementalistCard)card).isEmblem) {
                AbstractDungeon.player.masterDeck.addToBottom(card);
            }else {
                AbstractDungeon.player.masterDeck.addToTop(card);
            }
			TheElementalist.doLevelup();
            card.applyPowers();
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy()));
            AbstractDungeon.topLevelEffects.add(new CardGlowBorder(card));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            choseCard = true;
            button.isFree = false; // we chose a card, so next time we click the reverse withering button it won't be a free action.
            ((RestRoom)AbstractDungeon.getCurrRoom()).fadeIn();
        }
        if(this.duration < 1.0f && !this.openedScreen) {
            this.openedScreen = true;
            /*for(AbstractCard card : AbstractDungeon.player.masterDeck.group) {
                if(card instanceof AbstractCard) {
                    group.group.add(card);
                }
            }*/
            
            CardGroup perkChoices = getLevelUpPerkChoices();
            AbstractDungeon.gridSelectScreen.open(perkChoices, 1, "Choose a level-up perk.", false, false, true, true);
        }
        if(this.duration < 0.0f) {
            this.isDone = true;
            if (this.isFree && choseCard) {
                // reopen options menu
                if (AbstractDungeon.getCurrRoom() instanceof RestRoom) {
                    button.setImageAndDescription();
                    ((RestRoom) AbstractDungeon.getCurrRoom()).campfireUI.reopen();
                    // there was a bug with the fire sound persisting and I'm not sure why,
                    // so this is basically a randomly thrown out preventative measure.
                    ((RestRoom) AbstractDungeon.getCurrRoom()).cutFireSound();
                }
            }else{
                // complete room and bring up continue button
                if(CampfireUI.hidden) {
                    AbstractRoom.waitTimer = 0.0f;
                    AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                    // this is where the fire sound actually should cut off, logically.
                    ((RestRoom) AbstractDungeon.getCurrRoom()).cutFireSound();
                }
            }
        }
    }

    private CardGroup getLevelUpPerkChoices() {
    	ElementalistMod.log("ElementalistLevelUpEffect.getLevelUpPerkChoices()");
        int rewardCardsWanted = TheElementalist.level*2;
    	ElementalistMod.log("reward cards: " + rewardCardsWanted);
        
        if(ElementalistMod.levelupRewardGroup != null && ElementalistMod.levelupRewardGroup.size() == rewardCardsWanted+4) {
        	ElementalistMod.log("Reusing existing reward set.");
        	return ElementalistMod.levelupRewardGroup;
        }
        
        ElementalistMod.levelupRewardGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        
        ElementalistMod.levelupRewardGroup.group.add(new Fire_Emblem());
        ElementalistMod.levelupRewardGroup.group.add(new Water_Emblem());
        ElementalistMod.levelupRewardGroup.group.add(new Air_Emblem());
        ElementalistMod.levelupRewardGroup.group.add(new Earth_Emblem());
        
        try {
	        ArrayList<AbstractCard> rewardCards = AbstractDungeon.getRewardCards();
	        if(rewardCards.size() > 0) {
	        	while(rewardCards.size() < rewardCardsWanted) {
	        		rewardCards.addAll(AbstractDungeon.getRewardCards());
	        	}
	        }
	        while(rewardCards.size() > rewardCardsWanted) {
	        	rewardCards.remove(0);
	        }
	        ElementalistMod.levelupRewardGroup.group.addAll(rewardCards);
        }catch(Exception e) {
        	e.printStackTrace();
        }

        CardGroup levelupRewards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        levelupRewards.group.addAll(ElementalistMod.levelupRewardGroup.group);
        
        return levelupRewards;
	}

	@Override
    public void render(SpriteBatch sb) {
        sb.setColor(screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0f, 0.0f, Settings.WIDTH, Settings.HEIGHT);
        if(AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID) {
            AbstractDungeon.gridSelectScreen.render(sb);
        }
    }

    private void updateBlackScreenColor() {
        if(this.duration > 1.0f) {
            this.screenColor.a = Interpolation.fade.apply(1.0f, 0.0f, (this.duration - 1.0f) * 2.0f);
        } else {
            this.screenColor.a = Interpolation.fade.apply(0.0f, 1.0f, this.duration / 1.5f);
        }
    }

    @Override
    public void dispose() {
}
}