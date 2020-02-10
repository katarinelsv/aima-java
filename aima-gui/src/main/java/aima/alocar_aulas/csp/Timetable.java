package aima.alocar_aulas.csp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import aima.core.search.csp.CSP;
import aima.core.search.csp.Domain;
import aima.core.search.csp.Variable;
import aima.alocar_aulas.constraint.AllDifferentConstraint;
import aima.alocar_aulas.constraint.HorarioDisciplinaConstraint;
import aima.alocar_aulas.constraint.PreferenciaDisciplinaProfessorConstraint;
import aima.alocar_aulas.constraint.TimeslotDiasDiferentesConstraint;
import aima.alocar_aulas.constraint.TimeslotDiasIguasConstraint;
import aima.alocar_aulas.constraint.TimeslotProfessorConstraint;

public class Timetable extends CSP<Variable, String> {

	/** Valores para o domínio: Professor **/

	String[] valuesProfessor = { "Walter", "Elena", "Evelyn", "Mia", "Robert" };

	/** Valores para o domínio: Disciplina **/

	String[] valuesDisciplina = { "Banco de Dados I", "Eletrônica I", "Engenharia de Software II",
			"Inteligência Artificial", "Laboratório de Redes de Computadores", "Programação Paralela e Concorrente",
			"Sistemas Distribuídos" };

	/** Valores para o domínio: Horario **/

	String[] valuesHorario = { "SEG_1300", "SEG_1350", "SEG_1500", "SEG_1550", "SEG_1700", "SEG_1750", "TER_1300",
			"TER_1350", "TER_1500", "TER_1550", "TER_1700", "TER_1750", "QUA_1300", "QUA_1350", "QUA_1500", "QUA_1550",
			"QUA_1700", "QUA_1750", "QUI_1300", "QUI_1350", "QUI_1500", "QUI_1550", "QUI_1700", "QUI_1750", "SEX_1300",
			"SEX_1350", "SEX_1500", "SEX_1550", "SEX_1700", "SEX_1750" };

	/** Domínio de dias letivos: utilizada em 'TimeslotDisciplinaConstraint' **/

	String[] valuesDia = { "SEG", "TER", "QUA", "QUI", "SEX" };

	/** Domínio de horários de aula: utilizada em '' **/

	Integer[] valuesHora = { 1300, 1350, 1500, 1550, 1700, 1750 };

	/**
	 * Valores para criar os horários proporcionais à carga horária de uma
	 * disciplina
	 **/

	Integer[] valuesAula = { 4, 4, 4, 4, 2, 4, 4 };

	/**
	 * Preferências por disciplinas para cada professor: utilizada em
	 * 'ProfessorDisciplinaConstraint'
	 **/

	HashMap<String, Integer[]> preferencias = new HashMap<String, Integer[]>();

	public Timetable() {

		preferencias.put("Walter", new Integer[] { 4, 6 });
		preferencias.put("Elena", new Integer[] { 3, 4 });
		preferencias.put("Evelyn", new Integer[] { 5, 0 });
		preferencias.put("Mia", new Integer[] { 0, 1 });
		preferencias.put("Robert", new Integer[] { 2, 6 });

		/**
		 * Coleção de timeslots (aula) = {1 professor, 1 disciplina, n horarios}:
		 * utilizada em 'TimeslotConstraint'
		 **/

		List<Timeslot> timeslots = new ArrayList<Timeslot>();

		/** Coleção de disciplinas: utilizada na constraint 'AllDifferent' **/

		List<Variable> disciplinas = new ArrayList<Variable>();

		/** Coleção de horários: **/

		List<Variable> horarios = new ArrayList<Variable>();

		for (int i = 0; i < valuesDisciplina.length; i++) {

			/**
			 * Para cada Disciplina, adicionar nova variável, a qual tem por domínio todas
			 * as disciplinas a serem ofertadas.
			 */

			Variable disciplina = new Variable("D" + (i + 1));
			addVariable(disciplina);
			setDomain(disciplina, new Domain(valuesDisciplina));

			disciplinas.add(disciplina);

			/**
			 * Cada Professor, representado como uma variável, a qual tem por domínio todos
			 * os professores que ministram ao menos 1 disciplina compreendida na matriz
			 * curricular.
			 */

			Variable professor = new Variable("P_" + disciplina.getName());
			addVariable(professor);
			setDomain(professor, new Domain(valuesProfessor));

			/**
			 * Um professor só poderá ser associado a uma disciplina a qual tem preferência
			 **/

			addConstraint(new PreferenciaDisciplinaProfessorConstraint(professor, disciplina, preferencias,
					valuesDisciplina));

			/** Slot de tempo (aula): {1 professor, 1 disciplina, n horarios} **/

			Timeslot slot = new Timeslot(professor, disciplina);

			for (int j = 0; j < valuesAula[i]; j++) {

				/** Horário para cada aula **/

				Variable horario = new Variable("H" + (j + 1) + "_" + disciplina.getName());
				addVariable(horario);
				setDomain(horario, new Domain(valuesHorario));

				horarios.add(horario);
				slot.addTimeslot(horario);
			}

			timeslots.add(slot);
		}

		/**
		 * Cada disciplina só poderá ter uma única oferta por semestre na grade
		 * curricular
		 **/

		addConstraint(new AllDifferentConstraint(disciplinas));

		/** Os horários de um professor não podem ser repetidos **/

		for (int i = 0; i < valuesProfessor.length; i++) {
			addConstraint(new TimeslotProfessorConstraint(timeslots, valuesProfessor[i]));
		}


		
		for (Timeslot timeslot1 : timeslots) {
			
			for (Timeslot timeslot2 : timeslots) {
				
				if (!timeslot1.getDisciplina().getName().equals(timeslot2.getDisciplina().getName())) {
					
					for (int i = 0; i < timeslot1.getHorarios().size(); i++) {
						
						for (int j = 0; j < timeslot2.getHorarios().size(); j++) {
													
							/** Disciplinas de mesmo período não podem ter as aulas ofertadas no mesmo horário **/
							
							addConstraint(new HorarioDisciplinaConstraint(timeslot1.getDisciplina(), timeslot1.getHorarios().get(i), timeslot2.getDisciplina(), timeslot2.getHorarios().get(j)));
						}
					}
				}
			}
		}
		
		for (Timeslot timeslot : timeslots) {

			for (int i = 0; i < timeslot.getHorarios().size(); i++) {

				Variable timeslot1 = timeslot.getHorarios().get(i);
				Variable timeslot2 = null;

				if ((i + 1) < timeslot.getHorarios().size()) {

					timeslot2 = timeslot.getHorarios().get(++i);

					/**
					 * Deve haver um número mínimo de ofertas de aula consecutivas. Para este
					 * problema, o mínimo é dois.
					 **/

					addConstraint(new TimeslotDiasIguasConstraint(timeslot1, timeslot2));
				}
			}

			for (int i = 0; i < timeslot.getHorarios().size(); i++) {

				Variable timeslot1 = timeslot.getHorarios().get(i);
				Variable timeslot2 = null;

				if (i == timeslot.getHorarios().size() - 1)
					break;

				if (i == timeslot.getHorarios().size() - 2) {
					timeslot2 = timeslot.getHorarios().get(0);

				} else if ((i + 2) < timeslot.getHorarios().size() && (i != timeslot.getHorarios().size() - 2)) {
					timeslot2 = timeslot.getHorarios().get(2 + i);
				}

				/**
				 * As ofertas de aula no mesmo dia não podem ultrapassar o mínimo definido para
				 * o problema
				 **/

				addConstraint(new TimeslotDiasDiferentesConstraint(timeslot1, timeslot2, valuesDia));
			}
		}
	}
}
