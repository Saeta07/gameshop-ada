package com.ada.gameshop.service;

import com.ada.gameshop.dto.TransactionDetailDTO;
import com.ada.gameshop.exception.ResourceNotFoundException;
import com.ada.gameshop.model.Product;
import com.ada.gameshop.model.Transaction;
import com.ada.gameshop.model.TransactionDetail;
import com.ada.gameshop.repository.ProductRepository;
import com.ada.gameshop.repository.TransactionDetailRepository;
import com.ada.gameshop.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionDetailService {
    private final TransactionDetailRepository transactionDetailRepository;
    private final ProductRepository productRepository;
    private final TransactionRepository transactionRepository;


    public void create(List<TransactionDetailDTO> transactionDetailDTOS, Product product) {
        List<TransactionDetail> transactionDetails = transactionDetailDTOS.stream()
                .map(transactionDetailDTO -> mapToEntity(transactionDetailDTO, product))
                .collect(Collectors.toList());
        transactionDetailRepository.saveAll(transactionDetails);
    }

    public void create(TransactionDetailDTO transactionDetailDTO) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionDetailDTO.getId());
        Optional<Product> product = productRepository.findById(transactionDetailDTO.getProductId());
        if (product.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        if (transaction.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        TransactionDetail transactionDetail = mapToEntity(transactionDetailDTO, transaction.get(), product.get());
        transactionDetail = transactionDetailRepository.save(transactionDetail);
        transactionDetailDTO.setId(transactionDetail.getId());
    }

    public TransactionDetailDTO retrieveById(Long transactionDetailId) {
        Optional<TransactionDetail> transactionDetail = transactionDetailRepository.findById(transactionDetailId);
        if (transactionDetail.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return mapToDTO(transactionDetail.get());
    }


    public List<TransactionDetailDTO> retrieveAll() {
        List<TransactionDetail> transactionDetails = transactionDetailRepository.findAll();
        return transactionDetails.stream()
                .map(transactionDetail -> mapToDTO(transactionDetail))
                .collect(Collectors.toList());
    }

    private TransactionDetail mapToEntity(TransactionDetailDTO transactionDetailDTO, Product product) {
        TransactionDetail transactionDetail = new TransactionDetail(transactionDetailDTO.getQuantityItem(),
                transactionDetailDTO.getTotalPrice(), product);

        return transactionDetail;
    }


    private TransactionDetailDTO mapToDTO(TransactionDetail transactionDetail) {
        TransactionDetailDTO transactionDetailDTO = new TransactionDetailDTO(transactionDetail.getQuantityItem(),
                transactionDetail.getTotalPrice()
                , transactionDetail.getProduct().getId(), transactionDetail.getProduct().getId());
        transactionDetailDTO.setId(transactionDetail.getId());

        return transactionDetailDTO;
    }

    private TransactionDetail mapToEntity(TransactionDetailDTO transactionDetailDTO, Transaction transaction, Product product) {
        TransactionDetail transactionDetail = new TransactionDetail(transactionDetailDTO.getQuantityItem(),
                transactionDetailDTO.getTotalPrice(), transaction, product);
        return transactionDetail;
    }

    public void delete(Long transactionDetailId) {
        try {
            transactionDetailRepository.deleteById(transactionDetailId);
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException();
        }
    }
}
