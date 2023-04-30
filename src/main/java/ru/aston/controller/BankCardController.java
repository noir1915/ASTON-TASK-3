package ru.aston.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aston.model.BankCard;
import ru.aston.model.dto.BankCardDto;
import ru.aston.service.BankCardService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class BankCardController {

    private final BankCardService bankCardService;

    @Autowired
    public BankCardController(BankCardService bankCardService) {
        this.bankCardService = bankCardService;
    }

    @PostMapping("/cards/")
    public ResponseEntity<BankCardDto> addItem(@RequestBody final BankCardDto bankCardDto) {
        BankCard bankCard = bankCardService.addBankCard(BankCard.from(bankCardDto));
        return new ResponseEntity<>(BankCardDto.from(bankCard), HttpStatus.OK);
    }

    @GetMapping("/cards/")
    public ResponseEntity<List<BankCardDto>> getItems() {
        List<BankCard> bankCards = bankCardService.getBankCards();
        List<BankCardDto> itemsDto = bankCards.stream().map(BankCardDto::from).collect(Collectors.toList());
        return new ResponseEntity<>(itemsDto, HttpStatus.OK);
    }

    @GetMapping("/cards/{id}")
    public ResponseEntity<BankCardDto> getItem(@PathVariable final Long id) {
        BankCard bankCard = bankCardService.getBankCard(id);
        return new ResponseEntity<>(BankCardDto.from(bankCard), HttpStatus.OK);
    }

    @DeleteMapping("/cards/{id}")
    public ResponseEntity<BankCardDto> deleteItem(@PathVariable final Long id) {
        BankCard bankCard = bankCardService.deleteBankCard(id);
        return new ResponseEntity<>(BankCardDto.from(bankCard), HttpStatus.OK);
    }

    @PutMapping("/cards/{id}")
    public ResponseEntity<BankCardDto> editItem(@PathVariable final Long id,
                                                @RequestBody final BankCardDto bankCardDto) {
        BankCard editedBankCard = bankCardService.editBankCard(id, BankCard.from(bankCardDto));
        return new ResponseEntity<>(BankCardDto.from(editedBankCard), HttpStatus.OK);
    }
}
