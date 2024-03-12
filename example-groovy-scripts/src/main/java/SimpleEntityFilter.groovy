import com.github.julyss2019.bukkit.julysafe.api.EntityFilter
import org.bukkit.entity.Entity

class SimpleEntityFilter implements EntityFilter {
    @Override
    boolean filter(Entity entity) {
        def handle = entity.class.getMethod("getHandle").invoke(entity)
        def name = handle.getClass().name

        if (name.contains("Lobster") || name.contains("XXX")) {
            return true
        }

        return false
    }
}