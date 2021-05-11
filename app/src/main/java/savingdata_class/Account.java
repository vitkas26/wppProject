package savingdata_class;

public class Account {
    private static final String FILE = "Account";
    private static final Boolean IN_SYSTEM = false;
    private static final String USER_ID = "user_id";

    public static String getFILE() {
        return FILE;
    }

    public static Boolean getInSystem() {
        return IN_SYSTEM;
    }

    public static String getUserId() {
        return USER_ID;
    }
}
