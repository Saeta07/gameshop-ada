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
        List<TransactionDetail> detalleFacturas = transactionDetailDTOS.stream()
                .map(detalleFacturaDTO -> mapToEntity(transactionDetailDTOS, product))
                .collect(Collectors.toList());
        transactionDetailRepository.saveAll(detalleFacturas);
    }

    public void create(TransactionDetailDTO transactionDetailDTO) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionDetailDTO.getId());
        Optional<Product> producto = productRepository.findById(transactionDetailDTO.getProductId());
        if (producto.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        if (transaction.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        TransactionDetail transactionDetail = mapToEntity(transactionDetailDTO, transaction.get(), producto.get());
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
        List<TransactionDetail> detallesFactura = transactionDetailRepository.findAll();

        return detallesFactura.stream()
                .map(transactionDetail -> mapToDTO(transactionDetail))
                .collect(Collectors.toList());
    }

    private TransactionDetail mapToEntity(TransactionDetailDTO transactionDetailDTO, Product product) {
        TransactionDetail transactionDetail = new TransactionDetail(transactionDetailDTO.getQuantityItem(),
                transactionDetailDTO.getTotalPrice(), product);

        return transactionDetail;
    }


    private TransactionDetailDTO mapToDTO(TransactionDetail transactionDetail) {
        TransactionDetailDTO detalleFacturaDTO = new TransactionDetailDTO(transactionDetail.getQuantityItem(),
                transactionDetail.getTotalPrice()
                , transactionDetail.getProduct().getId(), transactionDetail.getProduct().getId());
        detalleFacturaDTO.setId(transactionDetail.getId());

        return detalleFacturaDTO;
    }

    private TransactionDetail mapToEntity(TransactionDetailDTO detalleFacturaDTO, Transaction transaction, Product product) {
        TransactionDetail detalleFactura = new TransactionDetail(detalleFacturaDTO.getQuantityItem(),
                detalleFacturaDTO.getTotalPrice(), transaction, product);
        return detalleFactura;
    }
}
