package kz.edu.nu.cs.se.hw;

import java.util.*;

/**
 * Starter code for a class that implements the <code>PlayableRummy</code>
 * interface. A constructor signature has been added, and method stubs have been
 * generated automatically in eclipse.
 * 
 * Before coding you should verify that you are able to run the accompanying
 * JUnit test suite <code>TestRummyCode</code>. Most of the unit tests will fail
 * initially.
 * 
 * @see PlayableRummy
 * @see TestRummyCode
 *
 */
public class Rummy implements PlayableRummy {
    private Steps step;
    private String[] players;
    private int curPlayer;
    private ArrayList<ArrayList<String>> playerHands = new ArrayList<>();
    private ArrayList<ArrayList<String>> melds = new ArrayList<>();
    private Stack<String> cardsInDiscardPile = new Stack<>();
    private final String[] suits = new String[] { "C", "D", "H", "S", "M" };
    private final String[] ranks = new String[] { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };
    private boolean[] drawnFromDiscard;
    private boolean meld = true;

    private Stack<String> cardsInDeck = new Stack<>();
    {
        for (String s : suits) {
            for (String r : ranks) {
                cardsInDeck.push(r + s);
            }
        }
    };

    public Rummy(String... players) {
        if(players.length > 6) {
            throw new RummyException("", 8);
        } else if(players.length < 2) {
            throw new RummyException("", 2);
        }
        step = Steps.WAITING;
        this.players = players;
        curPlayer = 0;
        drawnFromDiscard = new boolean[players.length];
        for(int i = 0; i < players.length; i++) {
            drawnFromDiscard[i] = false;
        }
    }

    @Override
    public String[] getPlayers() {
        return players;
        // TODO Auto-generated method stub
    }

    @Override
    public int getNumPlayers() {
        return players.length;
        // TODO Auto-generated method stub
    }

    @Override
    public int getCurrentPlayer() {
        return curPlayer;
        // TODO Auto-generated method stub
    }

    @Override
    public int getNumCardsInDeck() {
        return cardsInDeck.size();
        // TODO Auto-generated method stub
    }

    @Override
    public int getNumCardsInDiscardPile() {
        return cardsInDiscardPile.size();
        // TODO Auto-generated method stub
    }

    @Override
    public String getTopCardOfDiscardPile() {
        if(cardsInDiscardPile.size() <= 0) {
            throw new RummyException("", 13);
        }
        return cardsInDiscardPile.peek();
        // TODO Auto-generated method stub
    }

    @Override
    public String[] getHandOfPlayer(int player) {
        if(player < playerHands.size() && player >= 0) {
            return playerHands.get(player).toArray(new String[playerHands.get(player).size()]);
        }
        throw new RummyException("", 10);
        // TODO Auto-generated method stub
    }

    @Override
    public int getNumMelds() {
        return melds.size();
        // TODO Auto-generated method stub
    }

    @Override
    public String[] getMeld(int i) {
        if(i < melds.size()) {
            return melds.get(i).toArray(new String[melds.get(i).size()]);
        }
        throw new RummyException("", 11);
        // TODO Auto-generated method stub
    }

    @Override
    public void rearrange(String card) {
        if(step != Steps.WAITING) {
            throw new RummyException("", 3);
        } else {
            if(cardsInDeck.size() <= 0) {
                throw new RummyException("", 7);
            } else {
                cardsInDeck.remove(card);
                cardsInDeck.push(card);
            }
        }
        // TODO Auto-generated method stub
    }

    @Override
    public void shuffle(Long l) {
        if(step != Steps.WAITING) {
            throw new RummyException("", 3);
        } else {
            Collections.shuffle(cardsInDeck, new Random(l));
        }
        // TODO Auto-generated method stub
    }

    @Override
    public Steps getCurrentStep() {
        return step;
        // TODO Auto-generated method stub
    }

    @Override
    public int isFinished() {
        if(getCurrentStep() != Steps.FINISHED) {
            return -1;
        }
        return curPlayer;
        // TODO Auto-generated method stub
    }

    @Override
    public void initialDeal() {
        int handCards = 0;
        if(step != Steps.WAITING) {
            throw new RummyException("", 3);
        } else {
            if(players.length == 2) {
                handCards = 10;
            } else if (players.length < 5) {
                handCards = 7;
            } else if (players.length < 7) {
                handCards = 6;
            }
            for(String player : players) {
                playerHands.add(new ArrayList<String>());
            }
            for(int i = 0; i < handCards; i++) {
                for(int j = 0; j < players.length; j ++) {
                    playerHands.get(j).addAll(new ArrayList<>(Arrays.asList(cardsInDeck.pop())));
                }
            }
            cardsInDiscardPile.push(cardsInDeck.pop());
            step = Steps.DRAW;
        }
        // TODO Auto-generated method stub
    }

    @Override
    public void drawFromDiscard() {
        if(step != Steps.DRAW) {
            throw new RummyException("", 4);
        } else {
            playerHands.get(curPlayer).add(cardsInDiscardPile.pop());
            drawnFromDiscard[curPlayer] = true;
            step = Steps.MELD;
        }
        // TODO Auto-generated method stub
    }

    @Override
    public void drawFromDeck() {
        if(getCurrentStep() != Steps.DRAW) {
            throw new RummyException("", 4);
        } else {
            if(cardsInDeck.size() == 0) {
                String card = cardsInDiscardPile.pop();
                while(cardsInDiscardPile.size() != 0) {
                    cardsInDeck.push(cardsInDiscardPile.pop());
                }
                //Collections.shuffle(cardsInDeck, new Random(1L));
                cardsInDiscardPile.push(card);
            }
            playerHands.get(curPlayer).add(cardsInDeck.pop());
            drawnFromDiscard[curPlayer] = false;
            step = Steps.MELD;
        }
        // TODO Auto-generated method stub
    }

    @Override
    public void meld(String... cards) {
        if((step != Steps.MELD && step != Steps.RUMMY) || !isValidMeld(cards)) {
            throw new RummyException("", 15);
        } else {
            for(String c : cards) {
                playerHands.get(curPlayer).remove(c);
            }

            melds.add(new ArrayList<>(Arrays.asList(cards)));
            if(getHandOfPlayer(curPlayer).length == 0 || (step == Steps.RUMMY && getHandOfPlayer(curPlayer).length == 1)) {
                step = Steps.FINISHED;
            }
        }
        // TODO Auto-generated method stub
    }

    private boolean isValidMeld(String... cards) {
        if(cards.length < 3) {
            throw new RummyException("", 7);
        }
        boolean suit = true;
        boolean rank = true;
        for(int i = 0; i < cards.length-1; i++) {
            if(!playerHands.get(curPlayer).contains(cards[i])) {
                meld = false;
                throw new RummyException("", 7);
            }
            Cards first = new Cards(cards[i]);
            Cards next = new Cards(cards[i+1]);

            if(rank) {
                rank = first.rank == next.rank;
            }
            if (suit && first.suit != next.suit) {
                suit = false;
            } else if (suit && !((next.rank - first.rank == 1) || (first.rank - next.rank == 12))) {
                suit = false;
            }

            if(!rank && !suit) {
                if(i<=2) {
                    meld = false;
                    throw new RummyException("", 1);
                } else {
                    meld = false;
                    throw new RummyException("", 7);
                }
            }
        }
        meld = true;
        return true;
    }

    @Override
    public void addToMeld(int meldIndex, String... cards) {
        if(step != Steps.MELD && step != Steps.RUMMY) {
            throw new RummyException("", 15);
        } else {
            for(String c : cards) {
                playerHands.get(curPlayer).remove(c);
            }
            melds.get(meldIndex).addAll(new ArrayList<>(Arrays.asList(cards)));
            if(getHandOfPlayer(curPlayer).length == 0) {
                step = Steps.FINISHED;
            }
        }
        // TODO Auto-generated method stub
    }

    @Override
    public void declareRummy() {
        if(step != Steps.MELD) {
            throw new RummyException("", 5);
        } else {
            step = Steps.RUMMY;
        }
        // TODO Auto-generated method stub
    }

    @Override
    public void finishMeld() {
        if(step != Steps.MELD && step != Steps.RUMMY) {
            throw new RummyException("", 15);
        } else {
            step = Steps.DISCARD;
            if (!meld) {
                throw new RummyException("", 16);
            }
        }
        // TODO Auto-generated method stub
    }

    @Override
    public void discard(String card) {
        if(step != Steps.DISCARD) {
            throw new RummyException("", 6);
        } else {
            if(!playerHands.get(curPlayer).contains(card)) {
                throw new RummyException("", 7);
            }

            if(card.equals(playerHands.get(curPlayer).get(playerHands.get(curPlayer).size() - 1))
                    && drawnFromDiscard[curPlayer]) {
                throw new RummyException("", 13);
            }

            playerHands.get(curPlayer).remove(card);
            cardsInDiscardPile.push(card);

            if(getHandOfPlayer(curPlayer).length != 0) {
                step = Steps.DRAW;
                if(curPlayer != players.length-1) {
                    curPlayer++;
                } else {
                    curPlayer = 0;
                }
            } else {
                step = Steps.FINISHED;
            }
        }
        // TODO Auto-generated method stub
    }
    
    

}
