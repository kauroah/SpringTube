package org.example.springtube.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.springtube.models.User;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id; // ID of the user
    private String email; // Email of the user
    private String firstname; // First name of the user
    private String lastname; // Last name of the user
    private String role;
    private String State;
    private String Phone;// Role of the user

    /**
     * Static factory method to create a UserDto object from a User entity.
     * @param user The User entity from which to create the UserDto.
     * @return The created UserDto object.
     */
    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId()) // Set the ID of the user
                .firstname(user.getFirstName()) // Set the first name of the user
                .lastname(user.getLastName()) // Set the last name of the user
                .email(user.getEmail()) // Set the email of the user
                .role(user.getRole().toString())
                .State(user.getState().toString()) // Set the role of the user (converted to string)
                .build(); // Build the UserDto object
    }

    /**
     * Static method to convert a list of User entities to a list of UserDto objects.
     * @param users The list of User entities to convert.
     * @return The list of UserDto objects.
     */
    public static List<UserDto> usersList(List<User> users) {
        return users.stream()
                .map(UserDto::from) // Convert each User entity to UserDto using the from method
                .collect(Collectors.toList()); // Collect the UserDto objects into a list
    }

    public static List<UserDto> from(List<User> content) {
        return content.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }
}