package ru.aston.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.aston.model.BankCard;

@Repository
public interface BankCardRepository extends CrudRepository<BankCard, Long> {
}
