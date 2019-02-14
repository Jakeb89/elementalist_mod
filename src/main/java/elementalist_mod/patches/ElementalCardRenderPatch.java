package elementalist_mod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(clz = AbstractCard.class, method = "render", paramtypes = { "com.badlogic.gdx.graphics.g2d.SpriteBatch", "boolean" })
public class ElementalCardRenderPatch {


	public static void Postfix(AbstractCard obj, final SpriteBatch sb, boolean selected) {
		ElementalCardRenderer.RenderPostfix(obj, sb, selected);
	}

}

