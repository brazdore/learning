package org.example.patterns.behavioral;

import org.example.utils.AnsiColor;

public class TemplateMethod {
    public static void main(String[] args) {
        Chess chess = new Chess();
        chess.run();

        Creature[] creatures = new Creature[]{new Creature(1, 1), new Creature(2, 2)};

        TemporaryCardDamageGame temporaryCardDamageGame = new TemporaryCardDamageGame(creatures);
        int winnerTemporary = temporaryCardDamageGame.combat(0, 1);
        System.out.println(AnsiColor.CYAN + "Winner of Temporary Damage is: " + winnerTemporary);

        System.out.println(AnsiColor.RESET);

        PermanentCardDamageGame permanentCardDamageGame = new PermanentCardDamageGame(creatures);
        permanentCardDamageGame.combat(0, 1); // 1/3 vs 1/2 => 1/2 vs 1/1
        int winnerPermanent = permanentCardDamageGame.combat(0, 1); // 1/2 vs 1/1 => 1/1 vs 1/0
        System.out.println(AnsiColor.PURPLE + "Winner of Permanent Damage is: " + winnerPermanent);

        System.out.println(AnsiColor.RESET);
    }

    static abstract class Game {
        protected int currentPlayer;
        protected final int numberOfPlayers;

        public Game(int numberOfPlayers) {
            this.numberOfPlayers = numberOfPlayers;
        }

        public void run() {
            start();
            while (!haveWinner()) {
                takeTurn();
            }
            System.out.println(AnsiColor.GREEN + "Winner is player number " + getWinner());
            System.out.println(AnsiColor.RESET);
        }

        protected abstract void start();

        protected abstract boolean haveWinner();

        protected abstract void takeTurn();

        protected abstract int getWinner();

    }

    static class Chess extends Game {
        private final int maxTurns = 10;
        private int turn = 1;

        public Chess() {
            super(2);
        }

        @Override
        protected void start() {
            System.out.println(AnsiColor.GREEN + "Starting a new game of chess");
            System.out.println(AnsiColor.RESET);
        }

        @Override
        protected boolean haveWinner() {
            return turn == maxTurns + 1;
        }

        @Override
        protected void takeTurn() {
            System.out.println(AnsiColor.YELLOW + "Player " + currentPlayer + " takes turn " + turn);
            System.out.println(AnsiColor.RESET);
            turn++;
            currentPlayer = (currentPlayer + 1) % 2;
        }

        @Override
        protected int getWinner() {
            return 0;
        }
    }

    // Exercise
    static class Creature {
        public int attack, health;

        public Creature(int attack, int health) {
            this.attack = attack;
            this.health = health;
        }
    }

    abstract static class CardGame {
        public Creature[] creatures;

        public CardGame(Creature[] creatures) {
            this.creatures = creatures;
        }

        // returns -1 if no clear winner (both alive or both dead)
        public int combat(int creature1, int creature2) {
            Creature first = creatures[creature1];
            Creature second = creatures[creature2];
            hit(first, second);
            hit(second, first);

            if ((first.health <= 0 && second.health <= 0) ||
                    (first.health > 0 && second.health > 0)) {
                return -1;
            }

            return first.health > second.health ?
                    creature1 :
                    creature2;
        }

        // attacker hits other creature
        protected abstract void hit(Creature attacker, Creature other);
    }

    static class TemporaryCardDamageGame extends CardGame {

        public TemporaryCardDamageGame(Creature[] creatures) {
            super(creatures);
        }

        @Override
        protected void hit(Creature attacker, Creature other) {
            if (other.health <= attacker.attack) {
                other.health = 0;
            }
        }
    }

    static class PermanentCardDamageGame extends CardGame {
        public PermanentCardDamageGame(Creature[] creatures) {
            super(creatures);
        }

        @Override
        protected void hit(Creature attacker, Creature other) {
            other.health -= attacker.attack;
        }
    }
}
