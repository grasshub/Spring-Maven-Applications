package org.sonatype.mavenbook.persist.configuration;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.sonatype.mavenbook.persist.LocationsDAO;
import org.sonatype.mavenbook.persist.LocationsDAOImpl;
import org.sonatype.mavenbook.persist.WeatherDAO;
import org.sonatype.mavenbook.persist.WeatherDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:database.properties")
public class PersistJavaConfig {
	
	private final String ENTITY_PACKAGE = "org.sonatype.mavenbook.model";
	@Autowired
	private Environment environment;
	private final String FORMAT_SQL = "hibernate.format_sql";
	private final String DIALECT = "hibernate.dialect";
	private final String SHOW_SQL = "hibernate.show_sql";
	private final String AUTO = "hibernate.hbm2ddl.auto";
	private final String COORDINATOR = "hibernate.transaction.coordinator_class";
	private final String BATCH = "hibernate.jdbc.batch_size";
	private final String DRIVER = "database.driver";
	private final String URL = "database.url";
	private final String USER = "database.user";
	private final String PASSWORD = "database.password";

	@SuppressWarnings("serial")
	@Bean
    public Properties hibernateProperties() {	
        return new Properties() {
			{
                setProperty(FORMAT_SQL, environment.getProperty(FORMAT_SQL));
                setProperty(DIALECT, environment.getProperty(DIALECT));
                setProperty(SHOW_SQL, environment.getProperty(SHOW_SQL));
                // Update the DB schema with sessionFactory creation.
                setProperty(AUTO, environment.getProperty(AUTO));
                setProperty(COORDINATOR, environment.getProperty(COORDINATOR));
                setProperty(BATCH, environment.getProperty(BATCH));
            }
        };
    }
	
	@Bean
	public DataSource dataSource() {
	    BasicDataSource dataSource = new BasicDataSource();
	    dataSource.setDriverClassName(environment.getProperty(DRIVER));
	    dataSource.setUrl(environment.getProperty(URL));
	    dataSource.setUsername(environment.getProperty(USER));
	    dataSource.setPassword(environment.getProperty(PASSWORD));
	    return dataSource;
	}

	@Bean
	public SessionFactory sectionFactory() {	

		LocalSessionFactoryBean sessionFactory = new  LocalSessionFactoryBean();
		sessionFactory.setPackagesToScan(ENTITY_PACKAGE);
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setHibernateProperties(hibernateProperties());
		
		// This method allows the bean instance to perform initialization only all bean properties have been set 
		// and to throw an exception in the event of misconfiguration.
		try {
			sessionFactory.afterPropertiesSet();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sessionFactory.getObject();
	}
	
	@Bean
	public HibernateTemplate hibernateTemplate() {
		return new HibernateTemplate(sectionFactory());
	}
	
	@Bean
	public HibernateTransactionManager hibernateTransactionManager(){
		return new HibernateTransactionManager(sectionFactory());
	}
	
	@Bean 
	public LocationsDAO locationsDAO () {
		LocationsDAO locationsDAO = new LocationsDAOImpl(hibernateTemplate());
		return locationsDAO;
	}
	
	@Bean 
	public WeatherDAO weatherDAO () {
		WeatherDAO weatherDAO = new WeatherDAOImpl(hibernateTemplate());
		return weatherDAO;
	}

}
