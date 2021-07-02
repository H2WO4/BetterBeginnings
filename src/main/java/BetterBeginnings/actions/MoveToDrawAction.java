package BetterBeginnings.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MoveToDrawAction extends AbstractGameAction {

    private final AbstractCard card;
    private final CardGroup source;

    public MoveToDrawAction(AbstractCard card, CardGroup source) {
        this.duration = 0.0F;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.card = card;
        this.source = source;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (source.contains(card)) {
            source.removeCard(card);
            p.drawPile.addToRandomSpot(card);
        }

        this.isDone = true;
    }
}
