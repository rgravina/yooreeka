package iweb2.ch6.usecase.credit.util;

import iweb2.ch6.usecase.credit.data.users.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataUtils {
    
    private DataUtils() {
        // empty
    }
    
    public static void saveUsers(String filename, List<User> users) {
        try {
            FileWriter fout = new FileWriter(filename);
            BufferedWriter writer = new BufferedWriter(fout);
            for(User user : users) {
                writer.write(user.toExternalString());
                writer.write("\n");
            }
            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            throw new RuntimeException(
                "Failed to save users in file: '" + filename + "' ", 
                e);
        }
    }

    public static List<User> loadUsers(String filename) {
        List<User> users = new ArrayList<User>();
        try {
            FileReader fReader = new FileReader(filename);
            BufferedReader reader = new BufferedReader(fReader);
            String line = null;
            while( (line = reader.readLine()) != null) {
                if( line.trim().length() > 0 ) {
                    User user = new User();
                    user.loadFromExternalString(line);
                    users.add(user);
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException(
                "Failed to load users from file: '" + filename + "' ", 
                e);
        }
        
        return users; 
    }

}
