package sample;

import com.github.cschen1205.ess.engine.*;
import java.util.Vector;

public class RuleEngine {

    public enum ProximityToResult {
        SMALL, MEDIUM, LARGE;
    }

    public RuleEngine(){
    }

    private RuleInferenceEngine getInferenceEngine(){

        RuleInferenceEngine engine = new KieRuleInferenceEngine();
        Rule rule=new Rule("1");
        rule.addAntecedent(new EqualsClause("wrongAnswer", "1"));
        rule.addAntecedent(new EqualsClause("time", "1"));
        rule.addAntecedent(new EqualsClause("proximity", ProximityToResult.SMALL.toString()));
        rule.setConsequent(new EqualsClause("clue", "Jesteś bardzo\n blisko rozwiązania!"));
        engine.addRule(rule);

        rule=new Rule("2");
        rule.addAntecedent(new EqualsClause("wrongAnswer", "1"));
        rule.addAntecedent(new EqualsClause("time", "1"));
        rule.addAntecedent(new EqualsClause("proximity", ProximityToResult.MEDIUM.toString()));
        rule.setConsequent(new EqualsClause("clue", "Idzie Ci świetnie!"));
        engine.addRule(rule);

        rule=new Rule("3");
        rule.addAntecedent(new EqualsClause("wrongAnswer", "1"));
        rule.addAntecedent(new EqualsClause("time", "1"));
        rule.addAntecedent(new EqualsClause("proximity", ProximityToResult.LARGE.toString()));
        rule.setConsequent(new EqualsClause("clue", "To było szybkie!\nAle pomyśl jeszcze trochę ;)"));
        engine.addRule(rule);

        rule=new Rule("4");
        rule.addAntecedent(new EqualsClause("wrongAnswer", "3"));
        rule.addAntecedent(new EqualsClause("time", "1"));
        rule.setConsequent(new EqualsClause("clue", "To nie jest wyścig! ;p"));
        engine.addRule(rule);

        rule=new Rule("5");
        rule.addAntecedent(new EqualsClause("wrongAnswer", "5+"));
        rule.addAntecedent(new EqualsClause("time", "1"));
        rule.setConsequent(new EqualsClause("clue", "Zwolnij trochę\ni zastanów się ;)"));
        engine.addRule(rule);

        rule=new Rule("6");
        rule.addAntecedent(new EqualsClause("wrongAnswer", "1"));
        rule.addAntecedent(new EqualsClause("time", "5"));
        rule.addAntecedent(new EqualsClause("proximity", ProximityToResult.SMALL.toString()));
        rule.setConsequent(new EqualsClause("clue", "Dobrze Ci idzie!"));
        engine.addRule(rule);

        rule=new Rule("7");
        rule.addAntecedent(new EqualsClause("wrongAnswer", "1"));
        rule.addAntecedent(new EqualsClause("time", "5"));
        rule.addAntecedent(new EqualsClause("proximity", ProximityToResult.MEDIUM.toString()));
        rule.setConsequent(new EqualsClause("clue", "Nie poddawaj się!"));
        engine.addRule(rule);

        rule=new Rule("8");
        rule.addAntecedent(new EqualsClause("wrongAnswer", "1"));
        rule.addAntecedent(new EqualsClause("time", "5"));
        rule.addAntecedent(new EqualsClause("proximity", ProximityToResult.LARGE.toString()));
        rule.setConsequent(new EqualsClause("clue", "Spróbuj innej metody ;)"));
        engine.addRule(rule);

        rule=new Rule("9");
        rule.addAntecedent(new EqualsClause("wrongAnswer", "3"));
        rule.addAntecedent(new EqualsClause("time", "5"));
        rule.addAntecedent(new EqualsClause("proximity", ProximityToResult.SMALL.toString()));
        rule.setConsequent(new EqualsClause("clue", "Jesteś całkiem blisko!"));
        engine.addRule(rule);

        rule=new Rule("10");
        rule.addAntecedent(new EqualsClause("wrongAnswer", "3"));
        rule.addAntecedent(new EqualsClause("time", "5"));
        rule.addAntecedent(new EqualsClause("proximity", ProximityToResult.MEDIUM.toString()));
        rule.setConsequent(new EqualsClause("clue", "Jesteś na dobrej drodze ;)"));
        engine.addRule(rule);

        rule=new Rule("11");
        rule.addAntecedent(new EqualsClause("wrongAnswer", "3"));
        rule.addAntecedent(new EqualsClause("time", "5"));
        rule.addAntecedent(new EqualsClause("proximity", ProximityToResult.LARGE.toString()));
        rule.setConsequent(new EqualsClause("clue", "Chyba musisz\n spróbować innej metody."));
        engine.addRule(rule);

        rule=new Rule("12");
        rule.addAntecedent(new EqualsClause("wrongAnswer", "5+"));
        rule.addAntecedent(new EqualsClause("time", "5"));
        rule.setConsequent(new EqualsClause("clue", "Spokojnie, próbuj dalej!\n Dasz radę ;)"));
        engine.addRule(rule);

        rule=new Rule("13");
        rule.addAntecedent(new EqualsClause("wrongAnswer", "1"));
        rule.addAntecedent(new EqualsClause("time", "10"));
        rule.addAntecedent(new EqualsClause("proximity", ProximityToResult.SMALL.toString()));
        rule.setConsequent(new EqualsClause("clue", "Jesteś na dobrym tropie ;)"));
        engine.addRule(rule);

        rule=new Rule("14");
        rule.addAntecedent(new EqualsClause("wrongAnswer", "1"));
        rule.addAntecedent(new EqualsClause("time", "10"));
        rule.addAntecedent(new EqualsClause("proximity", ProximityToResult.MEDIUM.toString()));
        rule.setConsequent(new EqualsClause("clue", "Prawie dobrze!"));
        engine.addRule(rule);

        rule=new Rule("15");
        rule.addAntecedent(new EqualsClause("wrongAnswer", "1"));
        rule.addAntecedent(new EqualsClause("time", "10"));
        rule.addAntecedent(new EqualsClause("proximity", ProximityToResult.LARGE.toString()));
        rule.setConsequent(new EqualsClause("clue", "Poszukaj błędów rachunkowych"));
        engine.addRule(rule);

        rule=new Rule("16");
        rule.addAntecedent(new EqualsClause("wrongAnswer", "3"));
        rule.addAntecedent(new EqualsClause("time", "10"));
        rule.addAntecedent(new EqualsClause("proximity", ProximityToResult.SMALL.toString()));
        rule.setConsequent(new EqualsClause("clue", "Bardzo blisko!"));
        engine.addRule(rule);

        rule=new Rule("17");
        rule.addAntecedent(new EqualsClause("wrongAnswer", "3"));
        rule.addAntecedent(new EqualsClause("time", "10"));
        rule.addAntecedent(new EqualsClause("proximity", ProximityToResult.MEDIUM.toString()));
        rule.setConsequent(new EqualsClause("clue", "Jesteś na dobrej drodze ;)"));
        engine.addRule(rule);
        
        rule=new Rule("18");
        rule.addAntecedent(new EqualsClause("wrongAnswer", "3"));
        rule.addAntecedent(new EqualsClause("time", "10"));
        rule.addAntecedent(new EqualsClause("proximity", ProximityToResult.LARGE.toString()));
        rule.setConsequent(new EqualsClause("clue", "Poszukaj innej metody ;)"));
        engine.addRule(rule);

        rule=new Rule("19");
        rule.addAntecedent(new EqualsClause("wrongAnswer", "5+"));
        rule.addAntecedent(new EqualsClause("time", "10"));
        rule.addAntecedent(new EqualsClause("proximity", ProximityToResult.SMALL.toString()));
        rule.setConsequent(new EqualsClause("clue", "Nie poddawaj się!\n Jesteś bardzo blisko ;D"));
        engine.addRule(rule);

        rule=new Rule("20");
        rule.addAntecedent(new EqualsClause("wrongAnswer", "5+"));
        rule.addAntecedent(new EqualsClause("time", "10"));
        rule.addAntecedent(new EqualsClause("proximity", ProximityToResult.MEDIUM.toString()));
        rule.setConsequent(new EqualsClause("clue", "Proponuję zrobić przerwę\ni wrócić do exercises."));
        engine.addRule(rule);

        rule=new Rule("21");
        rule.addAntecedent(new EqualsClause("wrongAnswer", "5+"));
        rule.addAntecedent(new EqualsClause("time", "10"));
        rule.addAntecedent(new EqualsClause("proximity", ProximityToResult.LARGE.toString()));
        rule.setConsequent(new EqualsClause("clue", "Proponuję zrobić przerwę\ni wrócić do exercises ;)"));
        engine.addRule(rule);

        return engine;
    }

    public String getClue(String wrongAnswer, String time, ProximityToResult proximity)
    {
        RuleInferenceEngine engine = getInferenceEngine();
        engine.addFact(new EqualsClause("wrongAnswer", wrongAnswer));
        engine.addFact(new EqualsClause("time", time));
        engine.addFact(new EqualsClause("proximity", proximity.toString()));

        Vector<Clause> unproved_conditions= new Vector<>();

        Clause conclusion = engine.infer("clue", unproved_conditions);

        System.out.println(conclusion.getValue());
        System.out.println(engine.getFacts());
        return conclusion.getValue();
    }
}
