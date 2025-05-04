package src.mpp2024.repo.Interfete;


import src.mpp2024.domain.PersoanaOficiu;

public interface IPersoanaOficiuRepo extends Repository<PersoanaOficiu, Integer> {
    PersoanaOficiu getOneByUsername(String username);
}