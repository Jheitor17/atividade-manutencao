import java.util.Map;

public class UserManager {

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
        if (DataUtil.isBlank(user.email) || !DataUtil.hasAt(user.email)) {
            throw new RuntimeException("email invalid");
        }
    }

    private void normalize(UserDTO user) {
        user.email = DataUtil.normalizeEmail(user.email);
        user.phone = DataUtil.isBlank(user.phone) ? "0000-0000" : user.phone;
        user.userType = DataUtil.isBlank(user.userType) ? "student" : user.userType;
        user.city = DataUtil.isBlank(user.city) ? "Unknown" : user.city;
        user.document = DataUtil.isBlank(user.document) ? "NO-DOC" : user.document;
        user.status = DataUtil.isBlank(user.status) ? "ACTIVE" : user.status;
    }

    public void registerUserFromConsole() {
        String name = DataUtil.readLine("Name: ");
        String email = DataUtil.readLine("Email: ");
        String phone = DataUtil.readLine("Phone: ");
        String type = DataUtil.readLine("Type (student/teacher): ");
        String city = DataUtil.readLine("City: ");
        String document = DataUtil.readLine("Document: ");
        String status = DataUtil.readLine("Status: ");
        
        // Adaptado para usar o novo DTO
        UserDTO dto = new UserDTO(name, email, phone, type, city, document, status);
        int id = registerUser(dto);
        
        System.out.println("User saved with id " + id);
    }

    public Map<String, Object> findById(int id) {
        return LegacyDatabase.getUserById(id);
    }

    public void listUsers() {
        System.out.println("ID | NAME | EMAIL | TYPE | CITY | STATUS | DEBT");
        for (Map<String, Object> u : LegacyDatabase.getUsers().values()) {
            System.out.println(u.get("id") + " | " + u.get("name") + " | " + u.get("email") + " | " + u.get("userType") + " | "
                    + u.get("city") + " | " + u.get("status") + " | " + u.get("debt"));
        }
    }

    public void addDebt(int userId, double value, String source, int p1, int p2, String helper) {
        Map<String, Object> data = LegacyDatabase.getUserById(userId);
        if (data == null) {
            throw new RuntimeException("user not found");
        }
        double debt = ((Double) data.get("debt")).doubleValue();
        debt = debt + value;
        data.put("debt", debt);

        if (p1 > 10) {
            LegacyDatabase.addLog("debt-high-" + source + "-" + helper);
        } else {
            LegacyDatabase.addLog("debt-low-" + source + "-" + helper);
        }

        if (p2 == 99) {
            data.put("status", "SUSPENDED");
        }
    }

    public boolean canBorrow(int userId) {
        Map<String, Object> data = LegacyDatabase.getUserById(userId);
        if (data == null) {
            return false;
        }
        String status = String.valueOf(data.get("status"));
        double debt = ((Double) data.get("debt")).doubleValue();
        if (!"ACTIVE".equals(status)) {
            return false;
        }
        if (debt > 100.0) {
            return false;
        }
        return true;
    }

    public boolean validateUserData(String name, String email, String phone) {
        if (DataUtil.isBlank(name)) {
            return false;
        }
        if (DataUtil.isBlank(email)) {
            return false;
        }
        if (!DataUtil.hasAt(email)) {
            return false;
        }
        if (DataUtil.isBlank(phone)) {
            return false;
        }
        return true;
    }
}
