package teodor.flavor_chaser_spring_backend.exceptions;

public class ErrorMessage {

    public static String getDoesNotExistErrorMessage(String entityName, Long id){
        return new StringBuilder().append(entityName)
                .append(" with id: ")
                .append(id.toString())
                .append(" doesn't exist")
                .toString();
    }

    public static String getUserDoesNotExistErrorMessage(String email){
        return new StringBuilder().append( "User with email: ")
                .append(email)
                .append(" doesn't exist")
                .toString();
    }
}
