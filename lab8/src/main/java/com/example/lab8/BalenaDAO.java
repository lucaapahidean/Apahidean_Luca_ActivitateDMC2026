package com.example.lab8;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BalenaDAO {

    // 1. Inserare
    @Insert
    void insertBalena(Balena balena);

    // 2. Selectie toate
    @Query("SELECT * FROM Balena")
    List<Balena> getAllBalene();

    // 3. Selectie dupa nume (valoare String egala cu parametrul)
    @Query("SELECT * FROM Balena WHERE nume = :nume")
    List<Balena> getBaleneByNume(String nume);

    // 4. Selectie dupa interval de varsta (valoare intreaga intre doi parametri)
    @Query("SELECT * FROM Balena WHERE varsta BETWEEN :min AND :max")
    List<Balena> getBaleneByVarstaInterval(int min, int max);

    // 5. Stergere unde varsta > parametrul primit
    @Query("DELETE FROM Balena WHERE varsta > :prag")
    void deleteByVarstaGreaterThan(int prag);

    // 6. Creste cu 1 varsta balenelor al caror nume incepe cu litera primita
    @Query("UPDATE Balena SET varsta = varsta + 1 WHERE nume LIKE :litera || '%'")
    void incrementVarstaByLitera(String litera);
}