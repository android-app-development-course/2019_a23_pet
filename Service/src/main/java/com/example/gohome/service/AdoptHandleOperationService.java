package com.example.gohome.service;

import com.example.gohome.entity.AdoptHandleOperation;

public interface AdoptHandleOperationService {

    boolean insertAdoptHandleOperation(AdoptHandleOperation adoptHandleOperation);

    boolean deleteAdoptHandleOperation(int operationId);

    boolean updateAdoptHandleOperation(AdoptHandleOperation adoptHandleOperation);

}
