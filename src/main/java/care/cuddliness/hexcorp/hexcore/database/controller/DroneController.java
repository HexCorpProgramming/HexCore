package care.cuddliness.hexcorp.hexcore.database.controller;
import static org.jooq.impl.DSL.table;
import static org.jooq.impl.DSL.field;

import care.cuddliness.hexcorp.hexcore.database.Database;
import care.cuddliness.hexcorp.hexcore.database.model.Consent;
import care.cuddliness.hexcorp.hexcore.database.model.Drone;
import org.bukkit.entity.Player;
import org.jooq.*;
import org.jooq.Record;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class DroneController {
    private DSLContext dsl;

    public DroneController(Database database){
        this.dsl = database.getDsl();
    }

    public Drone getDrone(String uuid){
        if(dsl.select().from(table("drone")).where("id", uuid).fetchOne() == null){
            return null;
        }
        Record record = dsl.select().from(table("drone")).where("id", uuid).fetchOne();
        String id = record.get("id", String.class);
        int dId = record.get("drone_id", Integer.class);
        boolean active = record.get("active", Boolean.class);
        boolean batteryStatus =  record.get("battery_status", Boolean.class);
        boolean idprepend =  record.get("id_prepend", Boolean.class);
        boolean speechOptimalization =  record.get("speech_optimization", Boolean.class);
        int batteryCapacity =  record.get("battery_capacity", Integer.class);
        boolean textGlitch =  record.get("text_glitch", Boolean.class);

        Result<Record> result = dsl.select().from(table("consent")).where("droneid", dId).fetch();
        ArrayList<Consent> consents = new ArrayList<>();
        for(Record r : result){
            consents.add(new Consent(r.get("id", String.class), r.get("droneId", Integer.class)));
        }
        return new Drone(id, dId, consents, active, batteryStatus, idprepend, speechOptimalization, textGlitch, batteryCapacity);
    }

    public void insertDrone(Player player){
        dsl.insertInto(table("drone")).columns(field("id")).values(player.getUniqueId().toString()).execute();
    }

    public Drone getDroneById(int droneId){
        Record record = dsl.select().from(table("drone")).where("drone_id", droneId).fetchOne();
        String id = record.get("id", String.class);
        int dId = record.get("drone_id", Integer.class);
        boolean active = record.get("active", Boolean.class);
        boolean batteryStatus =  record.get("battery_status", Boolean.class);
        boolean idprepend =  record.get("id_prepend", Boolean.class);
        boolean speechOptimalization =  record.get("speech_optimization", Boolean.class);
        int batteryCapacity =  record.get("battery_capacity", Integer.class);
        boolean textGlitch =  record.get("text_glitch", Boolean.class);

        Result<Record> result = dsl.select().from(table("consent")).where("droneid", droneId).fetch();
        ArrayList<Consent> consents = new ArrayList<>();
        for(Record r : result){
            consents.add(new Consent(r.get("id", String.class), r.get("droneId", Integer.class)));
        }
        return new Drone(id, dId, consents, active, batteryStatus, idprepend, speechOptimalization, textGlitch, batteryCapacity);

    }
    public void insertDroneWithId(Player player, int id){
        dsl.insertInto(table("drone")).columns(field("id"), field("drone_id")).values(player.getUniqueId().toString(), id).execute();

    }

    public void updateDrone(Drone drone){
        List<Query> updates = new ArrayList<>();
        updates.add(dsl.update(table("drone")).set(field("active"), drone.isActive()).where("drone_id", drone.getDroneId()));
        updates.add(dsl.update(table("drone")).set(field("battery_status"), drone.isBatteryStatus()).where("drone_id", drone.getDroneId()));
        updates.add(dsl.update(table("drone")).set(field("id_prepend"), drone.isIdPrepend()).where("drone_id", drone.getDroneId()));
        updates.add(dsl.update(table("drone")).set(field("speech_optimization"), drone.isSpeechOptimization()).where("drone_id", drone.getDroneId()));
        updates.add(dsl.update(table("drone")).set(field("text_glitch"), drone.isTextGlitching()).where("drone_id", drone.getDroneId()));
        updates.add(dsl.update(table("drone")).set(field("battery_capacity"), drone.getBatteryCapacity()).where("drone_id", drone.getDroneId()));
        dsl.batch(updates).execute();


    }

    public void emergencyRelease(Drone drone){

    }

    public boolean isIdUsed(int id){
        return false;
    }

}
