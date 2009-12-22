package iweb2.ch6.ensemble;

import java.util.HashMap;
import java.util.Map;

import iweb2.ch5.ontology.intf.Concept;
import iweb2.ch5.ontology.intf.Instance;

public class ConceptMajorityVoter {

    private Map<Concept, Integer> votes = new HashMap<Concept, Integer>();
    
    private Instance i;
    
    public ConceptMajorityVoter(Instance i) {
        this.i = i;
    }
    
    public void addVote(Concept c) {
        
        Integer conceptVoteCount = votes.get(c);
        
        if( conceptVoteCount == null ) {
            conceptVoteCount =  new Integer(1);
        }
        else {
            conceptVoteCount = conceptVoteCount + 1;

        }
        votes.put( c, conceptVoteCount );     
    }
    
    public Concept getWinner() {
        
        int winnerVoteCount = 0;
        Concept winnerConcept = null;
        
        for(Map.Entry<Concept, Integer> e : votes.entrySet()) {
            if( e.getValue() > winnerVoteCount ) {
                winnerConcept = e.getKey();
                winnerVoteCount = e.getValue();
            }
        }
        
        return winnerConcept;
    }
    
    public int getWinnerVoteCount() {
        Concept winner = getWinner();
        return votes.get(winner);
    }
    

    public void print() {
        System.out.println("Votes for instace [" + i + "] : " + votes);
        System.out.println("Winner concept: " + getWinner());
    }
    
}
