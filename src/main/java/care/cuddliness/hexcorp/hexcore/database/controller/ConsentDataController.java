package care.cuddliness.hexcorp.hexcore.database.controller;

import care.cuddliness.hexcorp.hexcore.database.Database;
import care.cuddliness.hexcorp.hexcore.database.model.Drone;
import org.bukkit.entity.Player;
import org.jooq.DSLContext;

import java.util.UUID;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class ConsentDataController {
    private Database database;
    private DSLContext dsl;

    public ConsentDataController(Database database){

        this.database = database;
        this.dsl = database.getDsl();
    }

    public boolean isConsented(Drone drone, String uuid){
        return drone.getConsent().stream().filter(consent -> consent.getId().equalsIgnoreCase(uuid)).findAny().isPresent();

    }

    public void removeConsent(String dronePlayerId, String consentedId){
        dsl.deleteFrom(table("consent")).where("id", consentedId).and("droneId", dronePlayerId).execute();

    }

    public void createNewConsent(Player consentedId, String dronePlayerId){
        dsl.insertInto(table("consent")).columns(field("uuid"), field("id"), field("droneId")).values(UUID.randomUUID(), consentedId, dronePlayerId).execute();

    }
}
