package br.com.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.entidades.Pessoa;

public interface IDaoPessoa extends Serializable {
	
	Pessoa consultarUsuario(String login, String senha);
	
	List<SelectItem> listaEstados();

	List<Pessoa> relatorioPessoa(String nome, Date dataIni, Date dataFim);

}
