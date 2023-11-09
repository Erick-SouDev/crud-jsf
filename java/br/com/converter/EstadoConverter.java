package br.com.converter;

import java.io.Serializable;

import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.com.entidades.Estados;

@FacesConverter(forClass = Estados.class, value = "estadoConverter")
public class EstadoConverter implements Converter, Serializable {

	private static final long serialVersionUID = -628943317877875062L;

	@Override
	/* Retorna obejto inteiro */
	public Object getAsObject(FacesContext context, UIComponent component,
			String codigoEstado) {

		try {
			if (codigoEstado != null && !codigoEstado.isEmpty()) {

				EntityManager entityManager = CDI.current()
						.select(EntityManager.class).get();

				Estados estados = (Estados) entityManager.find(Estados.class,
						Long.parseLong(codigoEstado));
				System.out.println("tetnando carregar estado" + estados + " codigoEstado " + codigoEstado);
				return estados;
			} else {
				System.out.println("estado vazio");
				return "";
			}
		} catch (java.lang.NumberFormatException e) {
			e.printStackTrace();
			
			return null;
		}
	}

	@Override
	/* Retorna apenas o código em String */
	public String getAsString(FacesContext context, UIComponent component,
			Object estado) {

		if (estado == null) {
			System.out.println("estado null");
			return null;
		}

		if (estado instanceof Estados) {
			System.out.println("--- 555" + ((Estados) estado).getId().toString());
			return ((Estados) estado).getId().toString();

		} else {
			System.out.println("--- dd" +estado.toString());
			return estado.toString();
		}

	}
}
