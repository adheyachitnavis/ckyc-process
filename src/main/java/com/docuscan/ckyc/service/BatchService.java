package com.docuscan.ckyc.service;
import com.docuscan.ckyc.model.Batch;
import com.docuscan.ckyc.repository.BatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}



