package jb.api_gateway.mapper;


import jb.api_gateway.data.vo.v1.PersonVO;
import jb.api_gateway.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;


@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    // Mapeia de Entity para VO
    @Mapping(source = "id", target = "key")
    PersonVO toPersonVO(Person person);

    // Mapeia de VO para Entity
    @Mapping(source = "key", target = "id")
    Person toPerson(PersonVO personVO);

    // Mapeia uma lista de Entity para uma lista de VO
    List<PersonVO> toPersonVOList(List<Person> persons);

    // Mapeia uma lista de VO para uma lista de Entity
    List<Person> toPersonList(List<PersonVO> personVOs);

}