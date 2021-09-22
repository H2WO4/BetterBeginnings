package BetterBeginnings.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class MoveCardsShuffleAction extends AbstractGameAction {
    public static final String[] TEXT;
    private static final UIStrings uiStrings;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("stslib:MoveCardsAction");
        TEXT = uiStrings.TEXT;
    }

    private final AbstractPlayer p;
    private final CardGroup source;
    private final CardGroup destination;
    private final Predicate<AbstractCard> predicate;
    private final Consumer<List<AbstractCard>> callback;
    private boolean sortCards;

    public MoveCardsShuffleAction(CardGroup destination, CardGroup source, Predicate<AbstractCard> predicate, int amount, Consumer<List<AbstractCard>> callback) {
        this.sortCards = false;
        this.p = AbstractDungeon.player;
        this.destination = destination;
        this.source = source;
        this.predicate = predicate;
        this.callback = callback;
        this.setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public MoveCardsShuffleAction(CardGroup destination, CardGroup source, Predicate<AbstractCard> predicate, Consumer<List<AbstractCard>> callback) {
        this(destination, source, predicate, 1, callback);
    }

    public MoveCardsShuffleAction(CardGroup destination, CardGroup source, int amount, Consumer<List<AbstractCard>> callback) {
        this(destination, source, (c) -> {
            return true;
        }, amount, callback);
    }

    public MoveCardsShuffleAction(CardGroup destination, CardGroup source, Consumer<List<AbstractCard>> callback) {
        this(destination, source, (c) -> {
            return true;
        }, 1, callback);
    }

    public MoveCardsShuffleAction(CardGroup destination, CardGroup source, Predicate<AbstractCard> predicate, int amount) {
        this(destination, source, predicate, amount, null);
    }

    public MoveCardsShuffleAction(CardGroup destination, CardGroup source, Predicate<AbstractCard> predicate) {
        this(destination, source, predicate, 1);
    }

    public MoveCardsShuffleAction(CardGroup destination, CardGroup source, int amount) {
        this(destination, source, (c) -> {
            return true;
        }, amount);
    }

    public MoveCardsShuffleAction(CardGroup destination, CardGroup source) {
        this(destination, source, (c) -> {
            return true;
        }, 1);
    }

    public MoveCardsShuffleAction sort(boolean sortCards) {
        this.sortCards = sortCards;
        return this;
    }

    public void update() {
        ArrayList<AbstractCard> callbackList;
        AbstractCard card;
        if (this.duration == Settings.ACTION_DUR_MED) {
            CardGroup tmp = new CardGroup(CardGroupType.UNSPECIFIED);
            for (AbstractCard c : this.source.group) {
                if (this.predicate.test(c)) {
                    if (this.source == this.p.drawPile) {
                        tmp.addToRandomSpot(c);
                    } else {
                        tmp.addToTop(c);
                    }
                }
            }

            if (tmp.size() == 0) {
                this.isDone = true;
            } else if (tmp.size() == 1) {
                AbstractCard card2 = tmp.getTopCard();
                if (this.source == this.p.exhaustPile) {
                    card2.unfadeOut();
                }

                if (this.destination == this.p.hand && this.p.hand.size() == BaseMod.MAX_HAND_SIZE) {
                    this.source.moveToDiscardPile(card2);
                    this.p.createHandIsFullDialog();
                } else {
                    card2.untip();
                    card2.unhover();
                    card2.lighten(true);
                    card2.setAngle(0.0F);
                    card2.drawScale = 0.12F;
                    card2.targetDrawScale = 0.75F;
                    card2.current_x = CardGroup.DRAW_PILE_X;
                    card2.current_y = CardGroup.DRAW_PILE_Y;
                    this.source.removeCard(card2);
                    this.destination.addToTop(card2);
                    AbstractDungeon.player.hand.refreshHandLayout();
                    AbstractDungeon.player.hand.applyPowers();
                }

                List<AbstractCard> callbackList2 = new ArrayList<>();
                callbackList2.add(card2);
                if (this.callback != null) {
                    this.callback.accept(callbackList2);
                }

                this.isDone = true;
            } else if (tmp.size() > this.amount) {
                if (this.sortCards) {
                    tmp.sortAlphabetically(true);
                    tmp.sortByRarityPlusStatusCardType(true);
                }

                AbstractDungeon.gridSelectScreen.open(tmp, this.amount, this.makeText(), false);
                this.tickDuration();
            } else {
                callbackList = new ArrayList<>();

                for (int i = 0; i < tmp.size(); ++i) {
                    card = tmp.getNCardFromTop(i);
                    callbackList.add(card);
                    if (this.source == this.p.exhaustPile) {
                        card.unfadeOut();
                    }

                    if (this.destination == this.p.hand && this.p.hand.size() == BaseMod.MAX_HAND_SIZE) {
                        this.source.moveToDiscardPile(card);
                        this.p.createHandIsFullDialog();
                    } else {
                        card.untip();
                        card.unhover();
                        card.lighten(true);
                        card.setAngle(0.0F);
                        card.drawScale = 0.12F;
                        card.targetDrawScale = 0.75F;
                        card.current_x = CardGroup.DRAW_PILE_X;
                        card.current_y = CardGroup.DRAW_PILE_Y;
                        this.source.removeCard(card);
                        this.destination.addToRandomSpot(card);
                        this.p.hand.refreshHandLayout();
                        this.p.hand.applyPowers();
                    }
                }

                if (this.callback != null) {
                    this.callback.accept(callbackList);
                }

                this.isDone = true;
            }
        } else {
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
                callbackList = new ArrayList<>();
                for (AbstractCard abstractCard : AbstractDungeon.gridSelectScreen.selectedCards) {
                    card = abstractCard;
                    callbackList.add(card);
                    card.untip();
                    card.unhover();
                    if (this.source == this.p.exhaustPile) {
                        card.unfadeOut();
                    }

                    if (this.destination == this.p.hand && this.p.hand.size() == BaseMod.MAX_HAND_SIZE) {
                        this.source.moveToDiscardPile(card);
                        this.p.createHandIsFullDialog();
                    } else {
                        this.source.removeCard(card);
                        this.destination.addToTop(card);
                    }

                    this.p.hand.refreshHandLayout();
                    this.p.hand.applyPowers();
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.hand.refreshHandLayout();
                if (this.callback != null) {
                    this.callback.accept(callbackList);
                }
            }

            this.tickDuration();
        }
    }

    private String makeText() {
        String ret;
        if (this.amount == 1) {
            ret = TEXT[0];
        } else {
            ret = TEXT[1];
        }

        String location = null;
        if (this.destination == this.p.hand) {
            location = uiStrings.TEXT_DICT.get("HAND");
        } else if (this.destination == this.p.drawPile) {
            location = uiStrings.TEXT_DICT.get("DRAW");
        } else if (this.destination == this.p.discardPile) {
            location = uiStrings.TEXT_DICT.get("DISCARD");
        } else if (this.destination == this.p.exhaustPile) {
            location = uiStrings.TEXT_DICT.get("EXHAUST");
        }

        if (location == null) {
            location = "<Unknown>";
        }

        return String.format(ret, location);
    }
}
