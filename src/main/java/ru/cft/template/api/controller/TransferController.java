package ru.cft.template.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.cft.template.api.dto.TransferCreateDto;
import ru.cft.template.api.dto.TransferDto;
import ru.cft.template.api.enums.Status;
import ru.cft.template.api.enums.TransferType;
import ru.cft.template.core.exception.InvalidRequestException;
import ru.cft.template.core.exception.UnauthorizedException;
import ru.cft.template.core.service.implementation.TransferService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transfers")
public class TransferController {
    private final TransferService transferService;

    @PostMapping("")
    public TransferDto createTransfer(
            @RequestHeader(value = "Authorization", required = false) String sessionId,
            @RequestBody TransferCreateDto transferCreateDto) {

        if (sessionId == null) {
            throw new UnauthorizedException("Нет авторизации");
        }

        return transferService.createTransfer(transferCreateDto, sessionId);
    }

    @GetMapping("")
    public List<TransferDto> getTransfers(
            @RequestHeader(value = "Authorization", required = false) String sessionId,
            @RequestParam("transferType") String transferType,
            @RequestParam("active") String active,
            @RequestParam("userId") String userId) {

        if (sessionId == null) {
            throw new UnauthorizedException("Нет авторизации");
        }
        if (!TransferType.isValid(transferType)) {
            throw new InvalidRequestException("Неправильный тип");
        }
        if (!Status.isValid(active)) {
            throw new InvalidRequestException("Неправильный статус");
        }

        List<TransferDto> transfers = transferService.getTransfers(
                TransferType.valueOf(transferType.toUpperCase()),
                Status.valueOf(active.toUpperCase()),
                userId,
                sessionId
        );

        return transfers;
    }

    @GetMapping("/{transferId}")
    public TransferDto getTransfer(
            @RequestHeader(value = "Authorization", required = false) String sessionId,
            @RequestParam("transferType") Long transferId) {

        if (sessionId == null) {
            throw new UnauthorizedException("Нет авторизации");
        }
        if (transferId == null) {
            throw new InvalidRequestException("Нет айди");
        }

        return transferService.getTransferById(transferId, sessionId);

    }

}
