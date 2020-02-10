package aima.alocar_aulas.model;

import java.io.Serializable;
import java.util.Date;

public abstract class Entidade implements Serializable {

	private static final long serialVersionUID = 109632090320375714L;

	private String usuarioUltAlteracao;
	
	private Date dataUltAlteracao;
	
	private int versao;
	
	public abstract int getId();
	
	public Entidade() {
		
	}
	
	public String getUsuarioUltAlteracao() {
		return usuarioUltAlteracao;
	}
	
	public void setUsuarioUltAlteracao(String usuarioUltAlteracao) {
		this.usuarioUltAlteracao = usuarioUltAlteracao;
	}
	
	public Date getDataUltAlteracao() {
		return dataUltAlteracao;
	}
	
	public void setDataUltAlteracao(Date dataUltAlteracao) {
		this.dataUltAlteracao = dataUltAlteracao;
	}
	
	public int getVersao() {
		return versao;
	}
	
	public void setVersao(int versao) {
		this.versao = versao;
	}
}