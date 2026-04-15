public class UserManager {

    public int registerUser(String name, String email, String phone, String userType, String city, String document,
            String status) {
        int id = -1;
        if (DataUtil.isBlank(name)) {
            throw new RuntimeException("name invalid");
    public static class UserDTO {
        public String name, email, phone, userType, city, document, status;

        public UserDTO(String name, String email, String phone, String userType, 
                       String city, String document, String status) {
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.userType = userType;
            this.city = city;
            this.document = document;
            this.status = status;
        }
        if (DataUtil.isBlank(email)) {
            throw new RuntimeException("email invalid");
    }

    public int registerUser(UserDTO user) {
        validate(user);
        normalize(user);

        int id = LegacyDatabase.addUserData(
            user.name, 
            user.email, 
            user.phone, 
            user.userType, 
            user.city, 
            user.document, 
            user.status
        );

        LegacyDatabase.addLog("user-manager-register-" + id);
        return id;
    }

    private void validate(UserDTO user) {
        if (DataUtil.isBlank(user.name)) {
            throw new RuntimeException("name invalid");
        }
        if (!DataUtil.hasAt(email)) {
        if (DataUtil.isBlank(user.email) || !DataUtil.hasAt(user.email)) {
            throw new RuntimeException("email invalid");
        }
        if (DataUtil.isBlank(phone)) {
            phone = "0000-0000";
        }
        if (DataUtil.isBlank(userType)) {
            userType = "student";
        }
        if (DataUtil.isBlank(city)) {
            city = "Unknown";
        }
        if (DataUtil.isBlank(document)) {
            document = "NO-DOC";
        }
        if (DataUtil.isBlank(status)) {
            status = "ACTIVE";
        }
    }

        id = LegacyDatabase.addUserData(name, DataUtil.normalizeEmail(email), phone, userType, city, document, status);
        LegacyDatabase.addLog("user-manager-register-" + id);
        return id;
    private void normalize(UserDTO user) {
        user.email = DataUtil.normalizeEmail(user.email);
        user.phone = DataUtil.isBlank(user.phone) ? "0000-0000" : user.phone;
        user.userType = DataUtil.isBlank(user.userType) ? "student" : user.userType;
        user.city = DataUtil.isBlank(user.city) ? "Unknown" : user.city;
        user.document = DataUtil.isBlank(user.document) ? "NO-DOC" : user.document;
        user.status = DataUtil.isBlank(user.status) ? "ACTIVE" : user.status;
    }

    public void registerUserFromConsole() {
@@ -43,7 +61,11 @@ public void registerUserFromConsole() {
        String city = DataUtil.readLine("City: ");
        String document = DataUtil.readLine("Document: ");
        String status = DataUtil.readLine("Status: ");
        int id = registerUser(name, email, phone, type, city, document, status);

        UserDTO dto = new UserDTO(name, email, phone, type, city, document, status);
        int id = registerUser(dto);

        System.out.println("User saved with id " + id);
    }

@@ -95,7 +117,6 @@ public boolean canBorrow(int userId) {
        return true;
    }

    // duplicate validation in another class too
    public boolean validateUserData(String name, String email, String phone) {
        if (DataUtil.isBlank(name)) {
            return false;
