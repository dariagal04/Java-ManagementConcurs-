package src.mpp2024.repo.Interfete;


import src.mpp2024.domain.NumeProba;

public interface INumeProbaRepo extends Repository<NumeProba, Integer> {
    NumeProba getNumeProbaByName(String name);


}