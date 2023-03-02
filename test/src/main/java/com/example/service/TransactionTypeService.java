package com.example.service;

import com.example.model.TransactionType;
import com.example.repository.TransactionTypeRepository;
import com.example.repository.TransactionTypeServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactiontype")
public class TransactionTypeService {

    @Autowired
    TransactionTypeServiceRepository transactionTypeServiceRepository;

    @GetMapping("")
    public ResponseEntity<List<TransactionType>> getAllTransactionType(HttpServletRequest request){
        String trxTypeId = (String) request.getAttribute("transactionTypeId");
        List<TransactionType> trxType = transactionTypeServiceRepository.fetchAllCategories(trxTypeId);
        return new ResponseEntity<>(trxType, HttpStatus.OK);
    }

    @GetMapping("/{transactioTypeId}")
    public ResponseEntity<TransactionType> getCategoryById(HttpServletRequest request,
                                                    @PathVariable("transactioTypeId") String transactioTypeId) {
        int userId = (Integer) request.getAttribute("userId");
        TransactionType trxType = transactionTypeServiceRepository.fetchTrxTypeById(transactioTypeId);
        return new ResponseEntity<>(trxType, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<TransactionType> addTrxType(HttpServletRequest request,
                                                @RequestBody Map<String, Object> categoryMap) {
        String trxTypeId = (String) request.getAttribute("transactionTypeId");
        String trxTypeCode = (String) categoryMap.get("transactionCode");
        String trxName = (String) categoryMap.get("transactionName");
        TransactionType trxType = transactionTypeServiceRepository.addTrxType(trxTypeId, trxTypeCode, trxName);
        return new ResponseEntity<>(trxType, HttpStatus.CREATED);
    }

    @PutMapping("/{transactionTypeId}")
    public ResponseEntity<Map<String, Boolean>> updateCategory(HttpServletRequest request,
                                                               @PathVariable("transactionTypeId") String transactionTypeId,
                                                               @RequestBody TransactionType trxType) {
        String trxTypeId = (String) request.getAttribute("transactionTypeId");
        transactionTypeServiceRepository.updateTrxType(trxTypeId, trxType);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/{transactionTypeId}")
    public ResponseEntity<Map<String, Boolean>> deleteCategory(HttpServletRequest request,
                                                               @PathVariable("transactionTypeId") String transactionTypeId) {
        String trxTypeId = (String) request.getAttribute("transactionTypeId");
        transactionTypeServiceRepository.removeTrxType(transactionTypeId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
