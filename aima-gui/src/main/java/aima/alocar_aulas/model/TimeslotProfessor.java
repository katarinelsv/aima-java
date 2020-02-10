package aima.alocar_aulas.model;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Variable;
import aima.core.util.datastructure.Pair;

public class TimeslotProfessor {
	
	private Variable professor;
	private List<Pair<Variable, Variable>> timeslots;
	
	public TimeslotProfessor(Variable professor) {	
		this.professor = professor;
		this.timeslots = new ArrayList<Pair<Variable, Variable>>();
	}
	
	public void addTimeslot(Variable dia, Variable hora) {
		
		Pair<Variable, Variable> timeslot = new Pair<Variable, Variable>(dia, hora);
		timeslots.add(timeslot);
	}
	
	public Variable getProfessor() {
		return professor;
	}
	
	public List<Pair<Variable, Variable>> getTimeslots() {
		return timeslots;
	}
}
