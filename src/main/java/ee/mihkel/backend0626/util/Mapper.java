package ee.mihkel.backend0626.util;

import ee.mihkel.backend0626.dto.PersonDTO;
import ee.mihkel.backend0626.entity.Person;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    PersonDTO personToPersonDTO(Person source);
    List<PersonDTO> personsToPersonDTOs(List<Person> source);

    Person personDTOToPerson(PersonDTO destination);
    List<Person> personDTOsToPersons(List<PersonDTO> destination);
}
