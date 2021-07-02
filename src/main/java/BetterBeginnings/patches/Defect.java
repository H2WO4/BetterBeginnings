package BetterBeginnings.patches;

import BetterBeginnings.cards.blue.DataRecovery;
import BetterBeginnings.cards.blue.Subzero;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.blue.Defend_Blue;
import com.megacrit.cardcrawl.cards.blue.Strike_Blue;

import java.util.ArrayList;

@SpirePatch2(clz = com.megacrit.cardcrawl.characters.Defect.class, method = "getStartingDeck")
public class Defect {
    public static void Postfix(com.megacrit.cardcrawl.characters.Defect __instance, ArrayList<String> __result)
    {
        __result.remove(Defend_Blue.ID);
        __result.remove(Strike_Blue.ID);
        __result.add(DataRecovery.ID);
        __result.add(Subzero.ID);
    }
}