package br.com.jpautil;

import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Named
@ApplicationScoped
public class JPAUtil {

	private static final String JDBC_URL = "JDBC_URL";

	private static final String JDBC_USER = "JDBC_USER";

	private static final String JDBC_PASSWORD = "JDBC_PASSWORD";

	private EntityManagerFactory factory = null;

	public JPAUtil() {
		if (factory == null) {
			factory = Persistence.createEntityManagerFactory("meuprimeiroprojetojsf");
		}
	}

	@Produces
	@RequestScoped
	public EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

	public Object getPrimaryKey(Object entity) {
		return factory.getPersistenceUnitUtil().getIdentifier(entity);
	}

	private Properties javaProperties() {
		Properties props = new Properties();

		if (System.getProperties().containsKey(JDBC_URL)) {
			props.put("javax.persistence.jdbc.url", System.getProperty(JDBC_URL));
		}

		if (System.getProperties().containsKey(JDBC_USER)) {
			props.put("javax.persistence.jdbc.user", System.getProperty(JDBC_USER));
		}

		if (System.getProperties().containsKey(JDBC_PASSWORD)) {
			props.put("javax.persistence.jdbc.password", System.getProperty(JDBC_PASSWORD));
		}

		return props;
	}

}
