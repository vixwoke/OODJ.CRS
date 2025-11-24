package crsAppPackage;

import java.util.*;

public class User {
    private static final String FILE_PATH = "Resources/Data/User.txt";
    private static final int NUM_OF_COLUMNS = 7;

    private String username;
    private String password;
    private String role;
    private String email;
    private String status;
    private String name;
    private String gender;

    public User(String username, String password, String role, String email, boolean active, String name, String gender) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.status = (active) ? STATUS.ACTIVE : STATUS.INACTIVE;
        this.name = name;
        this.gender = gender;
    }

    public User(String username, String password) {
        // search for user that matches with the username and password (login System)
        
        // Validate null exception
        if (username == null || password == null) {
            throw new RuntimeException("Please, input the username or password properly");
        }

        ArrayList<String> user;
        try {
            user = getUserData(username);

            // Check the password
            if (!password.equals(user.get(1))) {
                throw new Exception("User is not found");
            }

            // Successfully found the user
            this.username = user.get(0);
            this.password = user.get(1);
            this.role = user.get(2);
            this.email = user.get(3);
            this.status = user.get(4);
            this.name = user.get(5);
            this.gender = user.get(6);

            // Make sure the role is also valid and active
            if (!(isAdmin() || isAcademicOfficer())) {
                throw new RuntimeException("Corrupted Data: Role is not found");
            }

            if (isInactive()) {
                throw new RuntimeException("Your account has been deactivated");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public User(String username) {
        // search for user that matches with the username (Update Information, delete, password recovery)
        
        // Validate null exception
        if (username == null) {
            throw new RuntimeException("Please, input the username or password properly");
        }

        ArrayList<String> user;
        try {
            user = getUserData(username);

            // Successfully found the user
            this.username = user.get(0);
            this.password = user.get(1);
            this.role = user.get(2);
            this.email = user.get(3);
            this.status = user.get(4);
            this.name = user.get(5);
            this.gender = user.get(6);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getUsername() {
        return username;
    }

    public boolean isAdmin() {
        return ROLE.ADMIN.equals(role);
    }

    public boolean isAcademicOfficer() {
        return ROLE.ACADEMIC_OFFICER.equals(role);
    }

    public boolean isActive() {
        return STATUS.ACTIVE.equals(status);
    }

    public boolean isInactive() {
        return !(isActive());
    }

    public boolean isMale() {
        return GENDER.MALE.equals(gender);
    }

    public boolean isFemale() {
        return GENDER.FEMALE.equals(gender);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void addUser() {
        // validation
        if (username == null || username.isBlank() ||
            password == null || password.isBlank() ||
            role == null || role.isBlank() ||
            email == null || email.isBlank() ||
            status == null || status.isBlank() ||
            name == null || name.isBlank() ||
            gender == null || gender.isBlank()) {
            throw new RuntimeException("Input cannot be blank");
        }

        try {
            validateUsername(username);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        try {
            validatePassword(password);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        if (!ROLE.ADMIN.equals(role) && !ROLE.ACADEMIC_OFFICER.equals(role)) {
            throw new RuntimeException("Invalid Role");
        }

        if (!STATUS.ACTIVE.equals(status) && !STATUS.INACTIVE.equals(status)) {
            throw new RuntimeException("Invalid Status");
        }

        if (!GENDER.MALE.equals(gender) && !GENDER.FEMALE.equals(gender)) {
            throw new RuntimeException("Invalid Gender");
        }

        // Username is unique
        ArrayList<ArrayList<String>> user = FileManager.readFile(FILE_PATH, NUM_OF_COLUMNS, new HashMap<Integer, String>() {{
                put(0, username);
        }});

        if (!user.isEmpty()) {
            throw new RuntimeException("Username has already been used");
        }

        // Validated
        try {
            FileManager.addData(FILE_PATH, new String[] {username, password, role, email, status, name, gender});
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public int updateInformation(String username, String password, String role, String email, boolean active, String name, String gender) {
        // validation
        // if null then no changes will be made
        String newUsername = (username == null) ? this.username : username;
        String newPassword = (password == null) ? this.password : password;
        String newRole = (role == null) ? this.role : role;
        String newEmail = (email == null) ? this.email : email;
        String newName = (name == null) ? this.name : name;
        String newGender = (gender == null) ? this.gender : gender;

        if (newUsername.isBlank() || newPassword.isBlank() || newRole.isBlank() || newEmail.isBlank() || status.isBlank() || newName.isBlank() || newGender.isBlank()) {
            throw new RuntimeException("Input cannot be blank");
        }

        if (!ROLE.ADMIN.equals(newRole) && !ROLE.ACADEMIC_OFFICER.equals(newRole)) {
            throw new RuntimeException("Invalid Role");
        }

        if (!STATUS.ACTIVE.equals(status) && !STATUS.INACTIVE.equals(status)) {
            throw new RuntimeException("Invalid Status");
        }

        if (!GENDER.MALE.equals(newGender) && !GENDER.FEMALE.equals(newGender)) {
            throw new RuntimeException("Invalid Gender");
        }

        if (!newPassword.equals(this.password)) {
            try {
                validatePassword(newPassword);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        if (!newUsername.equals(this.username)) {
            try {
                validateUsername(newUsername);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }

            // Username is unique
            ArrayList<ArrayList<String>> user = FileManager.readFile(FILE_PATH, NUM_OF_COLUMNS, new HashMap<Integer, String>() {{
                    put(0, newUsername);
            }});

            if (!user.isEmpty()) {
                throw new RuntimeException("Username has already been used");
            }
        }

        // Validated
        int numOfAffectedLine;
        try {
            numOfAffectedLine =  FileManager.updateData(FILE_PATH, new String[] {newUsername, newPassword, newRole, newEmail, (active) ? STATUS.ACTIVE : STATUS.INACTIVE,
                newName, newGender}, new HashMap<Integer, String>() { {put(0, newUsername);} });
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        this.username = newUsername;
        this.password = newPassword;
        this.role = newRole;
        this.email = newEmail;
        this.status = (active) ? STATUS.ACTIVE : STATUS.INACTIVE;
        this.name = newName;
        this.gender = newGender;

        return numOfAffectedLine;
    }

    public int deleteUser() {
        int numOfAffectedLine;
        try {
            numOfAffectedLine = FileManager.deleteData(FILE_PATH, new HashMap<Integer, String>() {
                {put(0, username);}
            });
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        this.username = null;
        this.password = null;
        this.role = null;
        this.email = null;
        this.status = null;
        this.name = null;
        this.gender = null;

        return numOfAffectedLine;
    }

    public void validateUsername(String username) {
        if (username == null) {
            throw new RuntimeException("Username cannot be blank");
        }

        if (username.length() < 3) {
            throw new RuntimeException("Minimum character for username is 3");
        }

        if (username.length() > 10) {
            throw new RuntimeException("Maximum character for username is 10");
        }
    }

    public void validatePassword(String password) {
        if (password == null) {
            throw new RuntimeException("Username cannot be blank");
        }

        if (password.length() < 5) {
            throw new RuntimeException("Minimum character for username is 5");
        }

        if (password.length() > 10) {
            throw new RuntimeException("Maximum character for username is 10");
        }

        boolean hasLowercase = false;
        boolean hasUppercase = false;
        boolean hasNumber = false;
        boolean hasSpecialCharacter = false;
        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else {
                hasSpecialCharacter = true;
            }
        }

        if (!(hasLowercase && hasUppercase && hasNumber && hasSpecialCharacter)) {
            throw new RuntimeException("Password must have at least 1 lowercase character, 1 uppercase character, 1 number, and 1 special character");
        }
    }

    public static ArrayList<ArrayList<String>> getUsers() {
        ArrayList<ArrayList<String>> users;
        try {
            users = FileManager.readFile(FILE_PATH, NUM_OF_COLUMNS);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return users;
    }

    private static ArrayList<String> getUserData(String username) {
        // search for user that matches with the username

        // Validate null exception
        if (username == null) {
            throw new RuntimeException("Please, input the username properly");
        }

        ArrayList<ArrayList<String>> user;
        try {
            // Read from the file and check the username
            user = FileManager.readFile(FILE_PATH, NUM_OF_COLUMNS, new HashMap<Integer, String>() {{
                put(0, username);
            }});

            // Validate the data
            if (user.isEmpty()) {
                throw new RuntimeException("User is not found");
            }
            if (user.size() > 1) {
                throw new RuntimeException("Corrupted data: Multiple username is found");
            }

            // Successfully found the user
            return user.getFirst();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static class ROLE {
        public static final String ADMIN = "Admin";
        public static final String ACADEMIC_OFFICER = "Academic Officer";
    }

    public static class GENDER {
        public static final String MALE = "Male";
        public static final String FEMALE = "Female";
    }

    public static class STATUS {
        public static final String ACTIVE = "Active";
        public static final String INACTIVE = "Inactive";
    }
}

