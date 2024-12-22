package jb.api_gateway.service;


import jb.api_gateway.controller.PersonController;
import jb.api_gateway.data.vo.v1.PersonVO;
import jb.api_gateway.exception.RequiredObjectIsNullException;
import jb.api_gateway.exception.ResourceNotFoundException;
import jb.api_gateway.mapper.PersonMapper;
import jb.api_gateway.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonServices {

    private final Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    private PersonRepository repository;

    @Autowired
    private PersonMapper mapper;

    public PersonVO findById(Long id) {
        logger.info("Buscando pessoa");

        // Busca a entidade do repositório
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this id"));

        // Usa o MapStruct para converter a entidade em VO
        var vo = mapper.toPersonVO(entity);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public List<PersonVO> findAll() {
        logger.info("Buscando todas as pessoas");

        // Busca todas as entidades do repositório e converte para VO usando o PersonMapper
        List<PersonVO> personVOList = mapper.toPersonVOList(repository.findAll());

        // Adiciona links HATEOAS para cada VO
        personVOList.forEach(personVO ->
                personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel())
        );

        return personVOList;
    }

    public PersonVO create(PersonVO personVO) {
        if (personVO == null) throw new RequiredObjectIsNullException();

        logger.info("Criando pessoa");

        // Converte VO para entidade, salva no banco e converte de volta para VO
        var entity = mapper.toPerson(personVO);
        var vo = mapper.toPersonVO(repository.saveAndFlush(entity));
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PersonVO update(PersonVO personVO) {
        if (personVO == null) throw new RequiredObjectIsNullException();

        logger.info("Atualizando pessoa");

        // Verifica se a entidade existe e atualiza os campos
        var entity = repository.findById(personVO.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this id"));

        entity.setFirstName(personVO.getFirstName());
        entity.setLastName(personVO.getLastName());
        entity.setAddress(personVO.getAddress());
        entity.setGender(personVO.getGender());

        // Salva a entidade atualizada e converte para VO
        var vo = mapper.toPersonVO(repository.saveAndFlush(entity));
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deletando pessoa");

        // Verifica se o registro existe e o remove
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this id"));

        repository.delete(entity);
    }
}