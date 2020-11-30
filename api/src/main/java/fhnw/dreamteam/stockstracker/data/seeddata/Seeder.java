package fhnw.dreamteam.stockstracker.data.seeddata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class Seeder {
    @Autowired
    private UserSeed userSeed;

    @PostConstruct
    public void init() {
        seedData();
    }

    public void seedData() {
        try {
            // add seed code here
            userSeed.seedUsers();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
