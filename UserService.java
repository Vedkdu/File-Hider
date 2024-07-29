package Service;

import dao.userDAO;
import model.User;

import java.sql.SQLException;

public class UserService {
    public static Integer saveUser(User user){
        try{
            if(userDAO.isExist(user.getEmail())){
                return 0;
            }
            else {
                return userDAO.saveUser(user);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
