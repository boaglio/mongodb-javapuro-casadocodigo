package com.boaglio.casadocodigo.mongodb;

import java.io.Serializable;
import java.util.List;

public class Seriado implements Serializable {

	private static final long serialVersionUID = -6593997414513265205L;

	private Object id;
	private String nome;
	private List<String> personagens;

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<String> getPersonagens() {
		return personagens;
	}

	public void setPersonagens(List<String> personagens) {
		this.personagens = personagens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (nome == null ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		Seriado other = (Seriado) obj;
		if (id == null) {
			if (other.id != null) { return false; }
		} else if (!id.equals(other.id)) { return false; }
		if (nome == null) {
			if (other.nome != null) { return false; }
		} else if (!nome.equals(other.nome)) { return false; }
		return true;
	}

	@Override
	public String toString() {
		return "Seriado [id=" + id + ", nome=" + nome + ", personagens=" + personagens + "]";
	}

}
