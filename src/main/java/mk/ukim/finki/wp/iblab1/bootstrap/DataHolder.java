package mk.ukim.finki.wp.iblab1.bootstrap;

import mk.ukim.finki.wp.iblab1.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<User> users = new ArrayList<User>();
}
