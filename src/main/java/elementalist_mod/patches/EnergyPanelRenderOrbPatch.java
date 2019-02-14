package elementalist_mod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import elementalist_mod.ElementalistMod;

@SpirePatch(clz = EnergyPanel.class, method = "renderOrb", paramtypes = { "com.badlogic.gdx.graphics.g2d.SpriteBatch"})
public class EnergyPanelRenderOrbPatch {


	public static void Prefix(EnergyPanel energyPanel, final SpriteBatch sb) {
		boolean renderElementalEnergies = ElementalistMod.elementalEnergyIsEnabled();
		if(renderElementalEnergies) {
			ElementalistMod.renderElementalEnergies(sb, energyPanel.current_x, energyPanel.current_y);
		}
	}

}

