package SpookyScarySkeletons.api;

import org.modelmapper.ModelMapper;

import javax.ejb.Stateless;

@Stateless
public class DTOMapperBean {

    public <T> T map(Object object, Class<T> clazz) {
        ModelMapper modelMapper = new ModelMapper();
        T dto = modelMapper.map(object, clazz);
        return dto;
    }

}
