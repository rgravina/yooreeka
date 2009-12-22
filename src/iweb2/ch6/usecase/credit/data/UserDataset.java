package iweb2.ch6.usecase.credit.data;


import iweb2.ch6.usecase.credit.data.users.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDataset {
    
    private Map<String, User> usersByUsernameMap;
    
    public UserDataset(List<User> userList) {
        this.usersByUsernameMap = new HashMap<String, User>(userList.size());
        
        for(User e : userList) {
            String username = e.getUsername();
            usersByUsernameMap.put(username, e);
        }
    }
    
    public List<User> getUsers() {
        return new ArrayList<User>(usersByUsernameMap.values());
    }
    
    public User findUserByUsername(String username) {
        return usersByUsernameMap.get(username);
    }
    
    public void printUser(String username) {
        User e = findUserByUsername(username);
        if( e != null ) {
            System.out.println(e.toString());
        }
        else {
            System.out.println("User not found (username: '" + username + "')");
        }
    }
    
    public void printAll() {
        for(Map.Entry<String, User> e : usersByUsernameMap.entrySet()) {
            User u = e.getValue();
            System.out.println(u);
        }
    }

    public int getSize() {
        return usersByUsernameMap.size();
    }
}
