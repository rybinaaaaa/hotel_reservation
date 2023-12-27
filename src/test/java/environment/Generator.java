package environment;

import com.hotel.reservationSystem.models.*;
import com.hotel.reservationSystem.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Transactional(readOnly = true)
public class Generator {
    private static final Random RAND = new Random();

    public static int randomInt() {
        return RAND.nextInt();
    }

    public static int randomInt(int max) {
        return RAND.nextInt(max);
    }


    public static int randomInt(int min, int max) {
        assert min >= 0;
        assert min < max;

        int result;
        do {
            result = randomInt(max);
        } while (result < min);
        return result;
    }
    public static Room generateRoom() {
        Integer id = randomInt();
        Room room = new Room();
        room.setId(id);
        return room;

    }
    public static User generateUser() {
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setId(1);
        user.setRole(Role.USER);
        return user;
    }

    public static Category generateCategory(){
        Category category = new Category();
        category.setName("test");
        category.setId(1);
        return  category;
    }


}
