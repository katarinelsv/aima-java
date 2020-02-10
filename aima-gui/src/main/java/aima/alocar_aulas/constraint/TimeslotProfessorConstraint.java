package aima.alocar_aulas.constraint;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;
import aima.alocar_aulas.csp.Timeslot;

public class TimeslotProfessorConstraint implements Constraint {
	
	private List<Variable> scope;
	private List<Timeslot> timeslots;
	String professor;
	
	public TimeslotProfessorConstraint(List<Timeslot> timeslots, String professor) {
		
		this.scope = new ArrayList<Variable>();
		this.timeslots = timeslots;
		this.professor = professor;
		
		for (Timeslot slot : timeslots) {
			
			this.scope.add(slot.getProfessor());
			
			for (Variable horario : slot.getHorarios()) {
				this.scope.add(horario);
			}
		}
	}
	
	public List<Timeslot> getTimeslots() {
		return timeslots;
	}

	@Override
	public List<Variable> getScope() {
		return scope;
	}
	
	public List<Variable> getHorariosByProfessor(String professor, Assignment assignment) {
		
		List<Variable> list = new ArrayList<Variable>();
		
		for (Timeslot timeslot : timeslots) {
			
			String prof = (String) assignment.getValue(timeslot.getProfessor());
			
			if (prof != null && prof.equals(professor)) {
				list.addAll(timeslot.getHorarios());
			}
		}
		
		return list;
	}
	
	@Override
	public boolean isSatisfiedWith(Assignment assignment) {
			
		List<Variable> horarios = getHorariosByProfessor(professor, assignment);
		
		return new AllDifferentConstraint(horarios).isSatisfiedWith(assignment);
	}
}
