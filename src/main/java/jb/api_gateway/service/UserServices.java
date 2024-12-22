package jb.api_gateway.service;


import jb.api_gateway.controller.PersonController;
import jb.api_gateway.data.vo.v1.PersonVO;
import jb.api_gateway.exception.RequiredObjectIsNullException;
import jb.api_gateway.exception.ResourceNotFoundException;
import jb.api_gateway.mapper.PersonMapper;
import jb.api_gateway.repository.PersonRepository;
import jb.api_gateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserServices implements UserDetailsService {

    private final Logger logger = Logger.getLogger(UserServices.class.getName());

    @Autowired
    private UserRepository repository;

    public UserServices(UserRepository repository) {this.repository = repository;}


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Finding one user by name");
        var user = repository.findByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Username não encontrado");
        }
    }
}