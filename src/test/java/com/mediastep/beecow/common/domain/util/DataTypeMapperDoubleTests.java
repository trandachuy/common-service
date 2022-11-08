/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 26/1/2017
 * Author: Huyen Lam <huyen.lam@mediastep.com>
 *******************************************************************************/
package com.mediastep.beecow.common.domain.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.mediastep.beecow.common.BeecowCommonApplication;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BeecowCommonApplication.class})
public class DataTypeMapperDoubleTests extends AbstractDataTypeMapperNumberTests<Double> {

    public static final Double TEST_NUMBER = 0.123456789;

    public static final String TEST_NUMBER_AS_STRING = TEST_NUMBER.toString();

    private static final List<Number> TEST_NUMBER_LIST;

    private static final String TEST_NUMBER_LIST_AS_STRING;

    static {
        StringJoiner joiner = new StringJoiner(LIST_SEPARATOR);
        TEST_NUMBER_LIST = new ArrayList<>();
        for (int i = 0; i < TEST_LIST_SIZE; i++) {
            Double value = TEST_NUMBER + i;
            joiner.add(value.toString());
            TEST_NUMBER_LIST.add(value);
        }
        TEST_NUMBER_LIST_AS_STRING = joiner.toString();
    }

    @Override
    protected Double getTestNumber() {
        return TEST_NUMBER;
    }

    @Override
    protected String getTestNumberAsString() {
        return TEST_NUMBER_AS_STRING;
    }

    @Override
    protected Collection<Number> getTestNumberList() {
        return TEST_NUMBER_LIST;
    }

    @Override
    protected String getTestListAsString() {
        return TEST_NUMBER_LIST_AS_STRING;
    }

}
