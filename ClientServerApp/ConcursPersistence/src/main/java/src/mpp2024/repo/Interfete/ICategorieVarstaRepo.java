package src.mpp2024.repo.Interfete;

import src.mpp2024.domain.CategorieVarsta;

public interface ICategorieVarstaRepo extends Repository<CategorieVarsta, Integer> {

    CategorieVarsta getCategorieVarstaByAgeGroup(int minAge, int maxAge);



}