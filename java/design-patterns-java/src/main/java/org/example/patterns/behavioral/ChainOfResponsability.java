package org.example.patterns.behavioral;

import org.example.utils.AnsiColor;
import org.example.utils.Line;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ChainOfResponsability {

    public static void main(String[] args) {
        Creature goblinsemi = new Creature("Goblinsemi", 1, 1);
        CreatureModifier creatureModifier = new CreatureModifier(goblinsemi);

        System.out.println(AnsiColor.BLUE + "Before: " + goblinsemi); // 1/1
        System.out.printf(AnsiColor.RESET);

        creatureModifier.add(new IncreaseAttackModifier(goblinsemi, 2)); // +2 ATK
        creatureModifier.add(new CurseModifier(goblinsemi)); // Cursed
        creatureModifier.add(new IncreaseDefenseModifier(goblinsemi, 3)); // Nothing
        creatureModifier.handle();

        System.out.println(AnsiColor.RED + "After: " + goblinsemi); // 3/1
        System.out.printf(AnsiColor.RESET);

        Line.split();

        Game game = new Game();
        Monster strongGoblinsemi = new Monster(game, "Strong Goblinsemi", 2, 2);

        System.out.println(AnsiColor.BLUE + "Start: " + strongGoblinsemi);
        Line.reset();

        IncreasedDefenseModifier icm = new IncreasedDefenseModifier(game, strongGoblinsemi); // +3 DEF

        try (DoubleAttackModifier dam = new DoubleAttackModifier(game, strongGoblinsemi)) { // x2 ATK when inside block
            System.out.println(AnsiColor.RED + "Inside try-with-resources: " + strongGoblinsemi);
            Line.reset();
        }
        System.out.println(AnsiColor.PURPLE + "End: " + strongGoblinsemi); // Keep +3 DEF, loses x2 ATK
        Line.reset();

        Line.split();

        GameExercise gameExercise = new GameExercise();
        Goblin g1 = new Goblin(gameExercise);
        Goblin g2 = new Goblin(gameExercise);
        Goblin g3 = new Goblin(gameExercise);
        GoblinKing gk = new GoblinKing(gameExercise);

        gameExercise.creatures.addAll(List.of(g1, g2, g3, gk));
        System.out.println(AnsiColor.RED + g1);
        System.out.println(AnsiColor.RED + g2);
        System.out.println(AnsiColor.RED + g3);
        System.out.println(AnsiColor.RED + gk);
    }

    static class Creature {
        protected String name;
        protected int atk, def;

        public Creature(String name, int atk, int def) {
            this.name = name;
            this.atk = atk;
            this.def = def;
        }


        @Override
        public String toString() {
            return "Creature{" +
                    "name='" + name + '\'' +
                    ", atk=" + atk +
                    ", def=" + def +
                    '}';
        }
    }

    static class CreatureModifier { // Base class for Chain of Command construction.
        protected final Creature creature;
        private CreatureModifier nextModifier;

        public CreatureModifier(Creature creature) {
            this.creature = creature;
        }

        public void add(CreatureModifier modifier) {
            if (nextModifier != null) {
                nextModifier.add(modifier);
            } else nextModifier = modifier;
        }

        public void handle() {
            if (nextModifier != null) {
                nextModifier.handle();
            }
        }
    }

    static class IncreaseAttackModifier extends CreatureModifier { // Specific modifiers or "commands"
        private int modifier;

        public IncreaseAttackModifier(Creature creature) {
            super(creature);
        }

        public IncreaseAttackModifier(Creature creature, int modifier) {
            super(creature);
            this.modifier = modifier;
        }

        @Override
        public void handle() {
            System.out.println(AnsiColor.YELLOW + "Increasing " + creature.name + " ATK by " + modifier);
            creature.atk += modifier;
            super.handle();
        }
    }

    static class IncreaseDefenseModifier extends CreatureModifier {
        private int modifier;

        public IncreaseDefenseModifier(Creature creature) {
            super(creature);
        }

        public IncreaseDefenseModifier(Creature creature, int modifier) {
            super(creature);
            this.modifier = modifier;
        }

        @Override
        public void handle() {
            System.out.println(AnsiColor.YELLOW + "Increasing " + creature.name + " DEF by " + modifier);
            creature.def += modifier;
            super.handle();
        }
    }

    static class CurseModifier extends CreatureModifier { // Short-circuit modifier to stop chaining
        public CurseModifier(Creature creature) {
            super(creature);
        }

        @Override
        public void handle() {
            System.out.println(AnsiColor.YELLOW + creature.name + " has been cursed and shall recive no more bonuses...");
        }
    }

    // Broker Chain
    static class Event<T> {
        private final Map<Integer, Consumer<T>> handlers = new HashMap<>();
        private int index = 0;

        public int subscribe(Consumer<T> handler) {
            int i = index;
            handlers.put(index++, handler);
            return i;
        }

        public void unsubscribe(int key) {
            handlers.remove(key);
        }

        public void fire(T args) {
            handlers.forEach((i, c) -> c.accept(args));
        }
    }

    static class Query {
        public String creatureName;
        public Argument argument;
        public int result;

        public Query(String creatureName, Argument argument, int result) {
            this.creatureName = creatureName;
            this.argument = argument;
            this.result = result;
        }

        enum Argument {
            ATTACK, DEFENSE
        }
    }

    static class Game {
        public Event<Query> queries = new Event<>();
    }

    static class Monster {
        public final String name;
        private final Game game;
        private final int baseAttack, baseDefense;

        public Monster(Game game, String name, int baseAttack, int baseDefense) {
            this.game = game;
            this.baseAttack = baseAttack;
            this.baseDefense = baseDefense;
            this.name = name;
        }

        int getAttack() {
            Query q = new Query(name, Query.Argument.ATTACK, baseAttack);
            game.queries.fire(q);
            return q.result;
        }

        int getDefense() {
            Query q = new Query(name, Query.Argument.DEFENSE, baseDefense);
            game.queries.fire(q);
            return q.result;
        }

        @Override
        public String toString() {
            return "Monster{" +
                    "name='" + name + '\'' +
                    ", attack=" + getAttack() +
                    ", defense=" + getDefense() +
                    '}';
        }
    }

    static class MonsterModifier { // protected, not private!
        protected Game game;
        protected Monster monster;

        public MonsterModifier(Game game, Monster monster) {
            this.game = game;
            this.monster = monster;
        }
    }

    static class IncreasedDefenseModifier extends MonsterModifier {
        public IncreasedDefenseModifier(Game game, Monster monster) {
            super(game, monster);

            game.queries.subscribe(q -> {
                if (q.creatureName.equals(monster.name) && q.argument == Query.Argument.DEFENSE) {
                    q.result += 3;
                }
            });
        }
    }

    static class DoubleAttackModifier extends MonsterModifier implements AutoCloseable {
        private final int token;

        public DoubleAttackModifier(Game game, Monster monster) {
            super(game, monster);

            token = game.queries.subscribe(q -> {
                if (q.creatureName.equals(monster.name) && q.argument == Query.Argument.ATTACK) {
                    q.result *= 2;
                }
            });
        }

        @Override
        public void close() {
            game.queries.unsubscribe(token);
        }
    }

    // Exercise
    static abstract class CreatureExercise {
        protected GameExercise game;
        protected int baseAttack, baseDefense;

        public CreatureExercise(GameExercise game, int baseAttack, int baseDefense) {
            this.game = game;
            this.baseAttack = baseAttack;
            this.baseDefense = baseDefense;
        }

        public abstract int getAttack();

        public abstract int getDefense();

        public abstract void query(Object source, StatQuery sq);

        @Override
        public String toString() {
            return "Creature{" +
                    "atk=" + getAttack() +
                    ", def=" + getDefense() +
                    '}';
        }
    }

    static class Goblin extends CreatureExercise {
        protected Goblin(GameExercise game, int baseAttack, int baseDefense) {
            super(game, baseAttack, baseDefense);
        }

        public Goblin(GameExercise game) {
            super(game, 1, 1);
        }

        @Override
        public int getAttack() {
            StatQuery q = new StatQuery(Statistic.ATTACK);
            for (CreatureExercise c : game.creatures) {
                c.query(this, q);
            }
            return q.result;
        }

        @Override
        public int getDefense() {
            StatQuery q = new StatQuery(Statistic.DEFENSE);
            for (CreatureExercise c : game.creatures) {
                c.query(this, q);
            }
            return q.result;
        }

        @Override
        public void query(Object source, StatQuery sq) {
            if (source == this) {
                switch (sq.statistic) {
                    case ATTACK -> sq.result += baseAttack;
                    case DEFENSE -> sq.result += baseDefense;
                }
            } else if (sq.statistic == Statistic.DEFENSE) {
                sq.result++;
            }
        }
    }

    static class GoblinKing extends Goblin {
        public GoblinKing(GameExercise game) {
            super(game, 3, 3);
        }

        @Override
        public void query(Object source, StatQuery sq) {
            if (source != this && sq.statistic == Statistic.ATTACK) {
                sq.result++;
            } else super.query(source, sq);
        }
    }

    enum Statistic {
        ATTACK, DEFENSE
    }

    static class StatQuery {
        public Statistic statistic;
        public int result;

        public StatQuery(Statistic statistic) {
            this.statistic = statistic;
        }
    }

    static class GameExercise {
        public List<CreatureExercise> creatures = new ArrayList<>();
    }
}
