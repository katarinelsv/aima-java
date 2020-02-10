package aima.alocar_aulas.csp;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Variable;

public class Timeslot {
	
	private Variable professor;
	private Variable disciplina;
	private List<Variable> horarios;
	
	public Timeslot(Variable professor, Variable disciplina) {
		this.professor = professor;
		this.disciplina = disciplina;
		this.horarios = new ArrayList<Variable>();
	}
		
	public void addTimeslot(Variable horario) {
		this.horarios.add(horario);
	}
	
	public List<Variable> getHorarios() {
		return horarios;
	}
		
	public Variable getProfessor() {
		return professor;
	}
	
	public Variable getDisciplina() {
		return disciplina;
	}
}
