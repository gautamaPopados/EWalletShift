package ru.cft.template.core.service;

import ru.cft.template.api.dto.TransferCreateDto;
import ru.cft.template.api.dto.TransferDto;
import ru.cft.template.api.enums.Status;
import ru.cft.template.api.enums.TransferType;

import java.util.List;

public interface ITransferService {
    TransferDto createTransfer(TransferCreateDto transferCreateDto, String sessionId);

    List<TransferDto> getTransfers(TransferType transferType, Status status, String userId, String sessionId);
}
