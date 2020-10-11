import org.bukkit.entity.Animals;
import org.bukkit.entity.Cow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        for (EntityType entityType : EntityType.values()) {
            Class<?> clazz = entityType.getEntityClass();

            if (clazz != null && !Mob.class.isAssignableFrom(clazz)) {
                System.out.println(entityType.name());
            }

        }


    }
}
