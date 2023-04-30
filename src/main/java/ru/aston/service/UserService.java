package ru.aston.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.aston.model.BankCard;
import ru.aston.model.User;
import ru.aston.model.exception.BankCardIsAlreadyAssignedException;
import ru.aston.model.exception.UserNotFoundException;
import ru.aston.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BankCardService bankCardService;

    @Autowired
    public UserService(UserRepository userRepository, BankCardService bankCardService) {
        this.userRepository = userRepository;
        this.bankCardService = bankCardService;
    }

    public User addUser(User user){
        return userRepository.save(user);
    }

    public List<User> getUsers(){
        return StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public User getUser(Long id){
        return userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException(id));
    }

    public User deleteUser(Long id){
        User user = getUser(id);
        userRepository.delete(user);
        return user;
    }

    @Transactional
    public User editUser(Long id, User user){
        User userToEdit = getUser(id);
        userToEdit.setName(user.getName());
        return userToEdit;
    }

    @Transactional
    public User addBankCardToUser(Long userId, Long cardId){
        User user = getUser(userId);
        BankCard bankCard = bankCardService.getBankCard(cardId);
        if(Objects.nonNull(bankCard.getUser())){
            throw new BankCardIsAlreadyAssignedException(cardId,
                    bankCard.getUser().getId());
        }
        user.addBankCard(bankCard);
        bankCard.setUser(user);
        return user;
    }

    @Transactional
    public User removeBankCardFromUser(Long userId, Long cardId){
        User user = getUser(userId);
        BankCard bankCard = bankCardService.getBankCard(cardId);
        user.removeBankCard(bankCard);
        return user;
    }
}
