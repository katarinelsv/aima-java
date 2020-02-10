package aima.alocar_aulas.constraint;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;

public class TimeslotDiasIguasConstraint implements Constraint {
	
	private List<Variable> scope;
	private Variable timeslot1;
	private Variable timeslot2;
	
	public TimeslotDiasIguasConstraint(Variable timeslot1, Variable timeslot2) {
		
		this.scope = new ArrayList<Variable>();
		
		this.timeslot1 = timeslot1;
		this.timeslot2 = timeslot2;
		
		this.scope.add(timeslot1);
		this.scope.add(timeslot2);
	}
	
	@Override
	public List<Variable> getScope() {
		return scope;
	}
	
	public Variable getTimeslot1() {
		return timeslot1;
	}
	
	public Variable getTimeslot2() {
		return timeslot2;
	}

	@Override
	public boolean isSatisfiedWith(Assignment assignment) {
		
		String valueTimeslot1 = (String) assignment.getValue(timeslot1);
		String valueTimeslot2 = (String) assignment.getValue(timeslot2);
		
		if (valueTimeslot1 != null && valueTimeslot2 != null) {
			
			String dia1 = valueTimeslot1.split("_")[0];
			String dia2 = valueTimeslot2.split("_")[0];
			
			if (!dia1.equals(dia2))
				return false;
		}
		
		return true;
	}
}
