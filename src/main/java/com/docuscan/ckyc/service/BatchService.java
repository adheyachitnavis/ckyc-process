package com.docuscan.ckyc.service;
import com.docuscan.ckyc.model.Batch;
import com.docuscan.ckyc.model.Client;
import com.docuscan.ckyc.repository.BatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class BatchService {

    private final BatchRepository batchRepository;

    public Batch saveBatch(Batch batch) {
        return batchRepository.save(batch);
    }

    public Batch getBatchByFiCode(String fiCode) {
        return batchRepository.findByFiCode(fiCode);
    }

    public String getSearchBatchNumber(Client client) {
        var fiCode = client.getFiCode();
        var batch = getBatchByFiCode(fiCode);

        int newNumber;
        String newBatchNumber;

        if (batch == null) {
            batch = Batch.builder()
                    .fiCode(fiCode)
                    .updatedAt(Instant.now())
                    .build();
        }
        var batchNo = batch.getBatchNumber() + 1;
        batch.setBatchNumber(batchNo);
        saveBatch(batch);
        return String.format("S%05d", batchNo) ;
    }

}



