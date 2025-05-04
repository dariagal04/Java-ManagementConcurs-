package src.mpp2024.repo.Interfete;

import java.util.List;

public interface Repository<EntityType,EntityId> {
    List<EntityType> getAll();

    EntityType getOne(EntityId id);

    boolean saveEntity(EntityType entity);

    boolean deleteEntity(EntityId id);

    boolean updateEntity(EntityType entity);
}