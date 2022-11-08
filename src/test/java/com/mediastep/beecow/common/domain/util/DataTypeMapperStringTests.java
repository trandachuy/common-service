/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 26/1/2017
 * Author: Huyen Lam <huyen.lam@mediastep.com>
 *******************************************************************************/
package com.mediastep.beecow.common.domain.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.ContextConfiguration;

import com.mediastep.beecow.common.BeecowCommonApplication;
import com.mediastep.beecow.common.domain.enumeration.DataType;
import com.mediastep.beecow.common.errors.InvalidInputException;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BeecowCommonApplication.class})
public class DataTypeMapperStringTests extends AbstractDataTypeMapperTests {

    private static final DataType TEST_TYPE = DataType.STRING;

    private static final DataType TEST_INVALID_TYPE = DataType.NUMBER;

    private static final DataType TEST_LIST_TYPE = DataType.LIST;

    private static final String TEST_STRING = "ABCDEF";

    private static final List<String> TEST_LIST;
    static {
        TEST_LIST = new ArrayList<>();
        for (int i = 0; i < TEST_LIST_SIZE; i++) {
            String value = TEST_STRING + i;
            TEST_LIST.add(value);
        }
    }

    private static final String TEST_LIST_AS_STRING = String.join(LIST_SEPARATOR, TEST_LIST);

    @Test
    public void toObject() {
        String input = TEST_STRING;
        DataType type = TEST_TYPE;
        String expOutput = TEST_STRING;
        runTest2(mapper::toObject, input, type, expOutput);
    }

    @Test
    public void toObjectEmpty() {
        String input = TEST_EMPTY_STRING;
        DataType type = TEST_TYPE;
        String expOutput = TEST_EMPTY_STRING;
        runTest2(mapper::toObject, input, type, expOutput);
    }

    @Test
    public void toObjectNull() {
        String input = null;
        DataType type = TEST_TYPE;
        String expOutput = null;
        runTest2(mapper::toObject, input, type, expOutput);
    }

    @Test
    public void toObjectTypeNull() {
        String input = TEST_STRING;
        DataType type = null;
        Class<InvalidInputException> expOutput = InvalidInputException.class;
        runTestThrow2(mapper::toObject, input, type, expOutput);
    }

    @Test
    public void toList() {
        String input = TEST_LIST_AS_STRING;
        Collection<String> expOutput = TEST_LIST;
        runTestList(mapper::toList, input, expOutput);
    }

    @Test
    public void toListEmpty() {
        String input = TEST_EMPTY_STRING;
        Collection<String> expOutput = TEST_EMPTY_STRING_LIST;
        runTestList(mapper::toList, input, expOutput);
    }

    @Test
    public void toListNull() {
        String input = null;
        Collection<String> expOutput = null;
        runTestList(mapper::toList, input, expOutput);
    }

    @Test
    public void toList2() {
        String input = TEST_LIST_AS_STRING;
        Collection<String> output = new HashSet<>();
        Collection<String> expOutput = TEST_LIST;
        runTestList2(mapper::toList, input, output, expOutput);
    }

    @Test
    public void toList2Empty() {
        String input = TEST_EMPTY_STRING;
        Collection<String> output = new HashSet<>();
        Collection<String> expOutput = TEST_EMPTY_STRING_LIST;
        runTestList2(mapper::toList, input, output, expOutput);
    }

    @Test
    public void toList2Null() {
        String input = null;
        Collection<String> output = new HashSet<>();
        Collection<String> expOutput = null;
        runTestList2(mapper::toList, input, output, expOutput);
    }

    @Test
    public void toList2OutputListNull() {
        String input = TEST_LIST_AS_STRING;
        Collection<String> output = null;
        Class<AssertionError> expOutput = AssertionError.class;
        runTestThrow2(mapper::toList, input, output, expOutput);
    }

    @Test
    public void toObjectList() {
        String input = TEST_LIST_AS_STRING;
        DataType type = TEST_LIST_TYPE;
        Collection<String> output = new HashSet<>();
        Collection<String> expOutput = TEST_LIST;
        runTestList3(mapper::toObjectList, input, type, output, expOutput);
    }

    @Test
    public void toObjectListEmpty() {
        String input = TEST_EMPTY_STRING;
        DataType type = TEST_LIST_TYPE;
        Collection<String> output = new HashSet<>();
        Collection<String> expOutput = TEST_EMPTY_STRING_LIST;
        runTestList3(mapper::toObjectList, input, type, output, expOutput);
    }

    @Test
    public void toObjectListNull() {
        String input = null;
        DataType type = TEST_LIST_TYPE;
        Collection<String> output = new HashSet<>();
        Collection<String> expOutput = null;
        runTestList3(mapper::toObjectList, input, type, output, expOutput);
    }

    @Test
    public void toObjectListTypeNull() {
        String input = TEST_LIST_AS_STRING;
        DataType type = null;
        Collection<String> output = new HashSet<>();
        Class<InvalidInputException> expOutput = InvalidInputException.class;
        runTestThrow3(mapper::toObjectList, input, type, output, expOutput);
    }

    @Test
    public void toObjectListTypeMismatch() {
        String input = TEST_LIST_AS_STRING;
        DataType type = TEST_INVALID_TYPE;
        Collection<String> output = new HashSet<>();
        Class<InvalidInputException> expOutput = InvalidInputException.class;
        runTestThrow3(mapper::toObjectList, input, type, output, expOutput);
    }

    @Test
    public void toObjectListOutputListNull() {
        String input = TEST_LIST_AS_STRING;
        DataType type = TEST_LIST_TYPE;
        Collection<String> output = null;
        Class<AssertionError> expOutput = AssertionError.class;
        runTestThrow3(mapper::toObjectList, input, type, output, expOutput);
    }

    @Test
    public void determineObjectType() {
        Object input = TEST_STRING;
        DataType defaultType = DataType.NUMBER;
        DataType expOutput = DataType.STRING;
        runTest2(mapper::determineDataType, input, defaultType, expOutput);
    }

    @Test
    public void determineObjectTypeNull() {
        Object input = null;
        DataType defaultType = DataType.NUMBER;
        DataType expOutput = DataType.NUMBER;
        runTest2(mapper::determineDataType, input, defaultType, expOutput);
    }

    @Test
    public void determineObjectTypeList() {
        Object input = TEST_LIST;
        DataType defaultType = DataType.NUMBER;
        DataType expOutput = DataType.STRING_LIST;
        runTest2(mapper::determineDataType, input, defaultType, expOutput);
    }

    @Test
    public void determineObjectTypeListEmpty() {
        Object input = TEST_EMPTY_STRING_LIST;
        DataType defaultType = DataType.NUMBER;
        DataType expOutput = DataType.LIST;
        runTest2(mapper::determineDataType, input, defaultType, expOutput);
    }

    @Test
    public void determineObjectTypeUnknownType() {
        Object input = TEST_UNKOWN_TYPE_OBJECT;
        DataType defaultType = DataType.NUMBER;
        DataType expOutput = DataType.NUMBER;
        runTest2(mapper::determineDataType, input, defaultType, expOutput);
    }

    @Test
    public void determineObjectTypeUnknownTypeWithDefaultNull() {
        Object input = TEST_UNKOWN_TYPE_OBJECT;
        DataType defaultType = null;
        DataType expOutput = null;
        runTest2(mapper::determineDataType, input, defaultType, expOutput);
    }

    @Test
    public void testToString() {
        Object input = TEST_STRING;
        String expOutput = TEST_STRING;
        runTest(mapper::toString, input, expOutput);
    }

    @Test
    public void testToStringNull() {
        Object input = null;
        String expOutput = null;
        runTest(mapper::toString, input, expOutput);
    }

    @Test
    public void testToStringEmpty() {
        Object input = TEST_EMPTY_STRING;
        String expOutput = TEST_EMPTY_STRING;
        runTest(mapper::toString, input, expOutput);
    }

    @Test
    public void testListToString() {
        Collection<String> input = TEST_LIST;
        String expOutput = TEST_LIST_AS_STRING;
        runTest(mapper::listOfStringToString, input, expOutput);
    }

    @Test
    public void testListToStringEmpty() {
        Collection<String> input = TEST_EMPTY_STRING_LIST;
        String expOutput = TEST_EMPTY_STRING;
        runTest(mapper::listOfStringToString, input, expOutput);
    }

    @Test
    public void testListToStringNull() {
        Collection<String> input = null;
        String expOutput = null;
        runTest(mapper::listOfStringToString, input, expOutput);
    }

}
