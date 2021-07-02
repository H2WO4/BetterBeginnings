package BetterBeginnings.patches;

import BetterBeginnings.cards.purple.Piety;
import BetterBeginnings.cards.purple.Prescience;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.purple.Defend_Watcher;
import com.megacrit.cardcrawl.cards.purple.Strike_Purple;

import java.util.ArrayList;

@SpirePatch2(clz = com.megacrit.cardcrawl.characters.Watcher.class, method = "getStartingDeck")
public class Watcher {
    public static void Postfix(com.megacrit.cardcrawl.characters.Watcher __instance, ArrayList<String> __result)
    {
        __result.remove(Defend_Watcher.ID);
        __result.remove(Strike_Purple.ID);
        __result.add(Prescience.ID);
        __result.add(Piety.ID);
    }
}