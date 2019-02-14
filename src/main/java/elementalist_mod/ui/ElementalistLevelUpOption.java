package elementalist_mod.ui;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

import elementalist_mod.ElementalistLevelUpEffect;
import elementalist_mod.ElementalistMod;
import elementalist_mod.characters.TheElementalist;

public class ElementalistLevelUpOption extends AbstractCampfireOption {

    public static final String LABEL = "Level Up";
    public static final String FREE_DESCRIPTION = "(Free action) Increase understanding of the Elements.";
    public static final String DESCRIPTION = "Increase understanding of the Elements.";
    public static final String UNUSABLE_DESCRIPTION = "You are not ready to increase your understanding.";
    public boolean isFree = true;

    public ElementalistLevelUpOption() {
        updateLabel();
        setImageAndDescription();
    }

    public void setImageAndDescription() {
        updateLabel();
        this.usable = ElementalistMod.isReadyForGrowthEvent();
        if (this.usable) {
            if (this.isFree) {
                this.description = FREE_DESCRIPTION;
                this.img = new Texture("img/ui/campfire/elementalgrowth_free.png");
            } else {
                this.description = DESCRIPTION;
                this.img = new Texture("img/ui/campfire/elementalgrowth.png");
            }
        } else {
            this.description = UNUSABLE_DESCRIPTION;
            this.img = new Texture("img/ui/campfire/elementalgrowth_disabled.png");
        }
    }

    @Override
    public void useOption() {
        if(this.usable) {
            AbstractDungeon.effectList.add(new ElementalistLevelUpEffect(this, this.isFree));
        }
    }
    
    public void updateLabel() {
        this.label = LABEL + "(" + TheElementalist.getExpIntoThisLevel() + "/" + TheElementalist.getExpNeededThisLevel(TheElementalist.level) + " exp)";
    }
}