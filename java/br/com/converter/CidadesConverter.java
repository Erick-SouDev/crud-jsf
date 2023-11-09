package br.com.converter;

import java.io.Serializable;

import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.com.entidades.Cidades;

@FacesConverter(forClass = Cidades.class, value = "cidadeConverter")
public class CidadesConverter implements Converter, Serializable {

	private static final long serialVersionUID = 7942337638899772351L;

	@Override
	/* Retorna obejto inteiro */
	public Object getAsObject(FacesContext context, UIComponent component,
			String codigoCidade) {
		if (codigoCidade != null && !codigoCidade.isEmpty()) {
		EntityManager entityManager = CDI.current().select(EntityManager.class).get();

		Cidades cidade = (Cidades) entityManager.find(Cidades.class,
				Long.parseLong(codigoCidade));

		System.out.println("cidade conver " + cidade);
		return cidade;
		}else {
			System.out.println("cidade conver vazio ");
			return "";
		}

	}

	@Override
	/* Retorna apenas o código em String */
	public String getAsString(FacesContext context, UIComponent component,
			Object cidade) {
		
			if (cidade == null || (cidade.toString() != null && cidade.toString().isEmpty())){
				System.out.println("cidade conver vazio 2 ");
				return "";
			}
			
			if (cidade instanceof Cidades){
				System.out.println("cidade conver sdsd " + ((Cidades) cidade).getId().toString());
				return ((Cidades) cidade).getId().toString();
			}else {
				System.out.println("cidade conver sdsd to string " + cidade.toString());
				return cidade.toString();
			}

	
	}

}
