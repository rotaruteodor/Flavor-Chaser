package teodor.flavor_chaser_spring_backend.exceptions;

public class ErrorMessage {

    public static String getDoesNotExistErrorMessage(String entityName, Long id){
        return entityName +
                " with id: " +
                id.toString() +
                " doesn't exist";
    }

    public static String getUserEmailNotFoundErrorMessage(String email){
        return "Credentials were not found for user with email: " +
                email +
                " doesn't exist";
    }
}
