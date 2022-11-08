/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 12/01/2017
 * Author: Huyen Lam <email:huyen.lam@mediastep.com>
 *******************************************************************************/
package com.mediastep.beecow.common.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

/**
 * Base abstract class for entities which will hold definitions for created, last modified by and created,
 * last modified by date.
 *
 * @deprecated since sprint 31 (no used anymore)
 */
//@Component
@Deprecated
public class AuditingEntityUtil {

//    private static String DEFAULT_ORDER_BY_FIELD_NAME = "lastModifiedDate";
//
//    public static Sort DEFAULT_ORDER_BY = new Sort(Direction.ASC, DEFAULT_ORDER_BY_FIELD_NAME);
//
//    /**
//     * Inject default order-by
//     * @param pageable
//     * @return
//     */
//    public Pageable appendLastModifiedDateDescIfNotExisted(Pageable pageable) {
//        Sort orderBy = pageable.getSort();
//        if (orderBy == null || orderBy.getOrderFor(DEFAULT_ORDER_BY_FIELD_NAME) == null) {
//            if (orderBy == null) {
//                orderBy = DEFAULT_ORDER_BY;
//            }
//            else {
//                orderBy = orderBy.and(DEFAULT_ORDER_BY);
//            }
//            pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), orderBy);
//        }
//        return pageable;
//    }
}
