package elementalist_mod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import elementalist_mod.ElementalistMod;
import elementalist_mod.orbs.ElementOrb;
import elementalist_mod.relics.MagusStaff;

public class MagusStaffPatch {
	static Hitbox mouseHitbox = new Hitbox(InputHelper.mX, InputHelper.mY, 1, 1);

	@SpirePatch(clz = AbstractPlayer.class, method = "combatUpdate")
	public static class CombatUpdate {
		public static void Postfix(AbstractPlayer self) {
			update();
		}
	}

	private static void update() {
		mouseHitbox.move(InputHelper.mX, InputHelper.mY);
		for (ElementOrb orb : ElementalistMod.getElementOrbs()) {
			orb.hb.update();
			//ElementalistMod.log(orb.hb.toString());
			//ElementalistMod.log(orb.element);
			//ElementalistMod.log("(" + orb.hb.cX + ", " + orb.hb.cY + ") [" + orb.hb.width + ", " + orb.hb.height + "]");
			//ElementalistMod.log("(" + mouseHitbox.cX + ", " + mouseHitbox.cY + ") [" + mouseHitbox.width + ", " + mouseHitbox.height + "]");
			if (hitboxCheck(orb.hb, mouseHitbox)) {
				orb.isHovered = true;
				if(!orb.hb.hovered) {
					orb.hb.justHovered = true;
				}else {
					orb.hb.justHovered = false;
				}
				orb.hb.hovered = true;
				if(AbstractDungeon.player.hasRelic(MagusStaff.ID) && InputHelper.justClickedLeft) {
					ElementalistMod.log("Hit!");
					//ElementOrb elementOrb = (ElementOrb) orb;
					if(MagusStaff.charges > 0) {
						ElementalistMod.changeElement(orb.element, 1);
						MagusStaff.onActivate();
					}
				}
			}else {
				orb.isHovered = false;
				orb.hb.hovered = false;
				orb.hb.justHovered = false;
			}
		}
	}
	
	private static boolean hitboxCheck(Hitbox hitbox1, Hitbox hitbox2) {
		float dx = Math.abs(hitbox1.cX - hitbox2.cX);
		float dy = Math.abs(hitbox1.cY - hitbox2.cY);
		float max_x = (hitbox1.width + hitbox2.width)/3;
		float max_y = (hitbox1.height + hitbox2.height)/3;
		if(dx < max_x && dy < max_y) {
			//ElementalistMod.log("Hit: (" + dx + ", " + dy + ") [" + max_x + ", " + max_y + "]");
			return true;
		}
		
		return false;
	}
}
