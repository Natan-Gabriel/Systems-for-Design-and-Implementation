package config;

import domain.Entities.Client;
import domain.Entities.Movie;
import domain.Entities.Rental;
import domain.Validators.ClientValidator;
import domain.Validators.MovieValidator;
import domain.Validators.RentalValidator;
import domain.Validators.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.remoting.rmi.RmiServiceExporter;

import service.*;

@Configuration
public class ClientConfig {
    @Bean
    RmiProxyFactoryBean rmiClientProxyFactoryBean() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(IClientService.class);
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/ClientService");
        return rmiProxyFactoryBean;
    }

    @Bean
    RmiProxyFactoryBean rmiMovieProxyFactoryBean() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(IMovieService.class);
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/MovieService");
        return rmiProxyFactoryBean;
    }

    @Bean
    RmiProxyFactoryBean rmiRentalProxyFactoryBean() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceInterface(IRentalService.class);
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/RentalService");
        return rmiProxyFactoryBean;
    }

}
