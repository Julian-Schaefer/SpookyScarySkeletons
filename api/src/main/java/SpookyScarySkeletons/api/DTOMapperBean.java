package SpookyScarySkeletons.api;

import SpookyScarySkeletons.api.dto.MessageDTO;
import org.modelmapper.ModelMapper;

import javax.ejb.Stateless;

@Stateless
public class DTOMapperBean {

    public <T> T map(Object object, Class<T> clazz) {
        ModelMapper modelMapper = new ModelMapper();
        //modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        T dto = modelMapper.map(object, clazz);
        return dto;
    }

}
