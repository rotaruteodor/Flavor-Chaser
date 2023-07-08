package teodor.flavor_chaser_spring_backend.mappers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import teodor.flavor_chaser_spring_backend.dtos.UserDto;
import teodor.flavor_chaser_spring_backend.entities.IngredientInStash;
import teodor.flavor_chaser_spring_backend.entities.User;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-08T19:24:09+0300",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 19 (Oracle Corporation)"
)
*/
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( user.getId() );
        userDto.setFirstName( user.getFirstName() );
        userDto.setLastName( user.getLastName() );
        userDto.setEmailAddress( user.getEmailAddress() );
        userDto.setPassword( user.getPassword() );
        userDto.setCreationDate( user.getCreationDate() );
        List<IngredientInStash> list = user.getIngredientsInStash();
        if ( list != null ) {
            userDto.setIngredientsInStash( new ArrayList<IngredientInStash>( list ) );
        }
        else {
            userDto.setIngredientsInStash( null );
        }

        return userDto;
    }
}
