/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 26/1/2017
 * Author: Huyen Lam <huyen.lam@mediastep.com>
 *******************************************************************************/
package com.mediastep.beecow.common.domain.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.junit.Test;

import com.mediastep.beecow.common.domain.enumeration.DataType;
import com.mediastep.beecow.common.errors.BeecowException;
import com.mediastep.beecow.common.errors.InvalidInputException;

public abstract class AbstractDataTypeMapperNumberTests<N extends Number> extends AbstractDataTypeMapperTests {

    private static final DataType TEST_TYPE = DataType.NUMBER;

    private static final DataType TEST_INVALID_TYPE = DataType.STRING;

    private static final DataType TEST_LIST_TYPE = DataType.NUMBER_LIST;

    private static final String TEST_INVALID_STRING = "ABCDEF";

    private static final Collection<Number> TEST_EMPTY_LIST = Collections.emptyList();

    protected abstract N getTestNumber();

    protected abstract String getTestNumberAsString();

    protected abstract Collection<Number> getTestNumberList();

    protected abstract String getTestListAsString();

    @Test
    public void toNumber() {
        String input = getTestNumberAsString();
        N expOutput = getTestNumber();
        runTest(mapper::toNumber, input, expOutput);
    }

    @Test
    public void toNumberEmpty() {
        String input = TEST_EMPTY_STRING;
        Class<BeecowException> expOutput = BeecowException.class;
        runTestThrow(mapper::toNumber, input, expOutput);
    }

    @Test
    public void toNumberInvalid() {
        String input = TEST_INVALID_STRING;
        Class<BeecowException> expOutput = BeecowException.class;
        runTestThrow(mapper::toNumber, input, expOutput);
    }

    @Test
    public void toNumberNull() {
        String input = null;
        N expOutput = null;
        runTest(mapper::toNumber, input, expOutput);
    }

    @Test
    public void toObject() {
        String input = getTestNumberAsString();
        DataType type = TEST_TYPE;
        N expOutput = getTestNumber();
        runTest2(mapper::toObject, input, type, expOutput);
    }

    @Test
    public void toObjectEmpty() {
        String input = TEST_EMPTY_STRING;
        DataType type = TEST_TYPE;
        Class<BeecowException> expOutput = BeecowException.class;
        runTestThrow2(mapper::toObject, input, type, expOutput);
    }

    @Test
    public void toObjectInvalid() {
        String input = TEST_INVALID_STRING;
        DataType type = TEST_TYPE;
        Class<BeecowException> expOutput = BeecowException.class;
        runTestThrow2(mapper::toObject, input, type, expOutput);
    }

    @Test
    public void toObjectNull() {
        String input = null;
        DataType type = TEST_TYPE;
        N expOutput = null;
        runTest2(mapper::toObject, input, type, expOutput);
    }

    @Test
    public void toObjectTypeNull() {
        String input = getTestNumberAsString();
        DataType type = null;
        Class<InvalidInputException> expOutput = InvalidInputException.class;
        runTestThrow2(mapper::toObject, input, type, expOutput);
    }

    @Test
    public void toList() {
        String input = getTestListAsString();
        Collection<Number> expOutput = getTestNumberList();
        runTestList(mapper::toListOfNumber, input, expOutput);
    }

    @Test
    public void toListEmpty() {
        String input = TEST_EMPTY_STRING;
        Collection<Number> expOutput = TEST_EMPTY_LIST;
        runTestList(mapper::toListOfNumber, input, expOutput);
    }

    @Test
    public void toListInvalid() {
        String input = TEST_INVALID_STRING;
        Class<BeecowException> expOutput = BeecowException.class;
        runTestThrow(mapper::toListOfNumber, input, expOutput);
    }

    @Test
    public void toListNull() {
        String input = null;
        Collection<Number> expOutput = null;
        runTestList(mapper::toListOfNumber, input, expOutput);
    }

    @Test
    public void toList2() {
        String input = getTestListAsString();
        Collection<Number> output = new HashSet<>();
        Collection<Number> expOutput = getTestNumberList();
        runTestList2(mapper::toListOfNumber, input, output, expOutput);
    }

    @Test
    public void toList2Empty() {
        String input = TEST_EMPTY_STRING;
        Collection<Number> output = new HashSet<>();
        Collection<Number> expOutput = TEST_EMPTY_LIST;
        runTestList2(mapper::toListOfNumber, input, output, expOutput);
    }

    @Test
    public void toList2Invalid() {
        String input = TEST_INVALID_STRING;
        Collection<Number> output = new HashSet<>();
        Class<BeecowException> expOutput = BeecowException.class;
        runTestThrow2(mapper::toListOfNumber, input, output, expOutput);
    }

    @Test
    public void toList2Null() {
        String input = null;
        Collection<Number> output = new HashSet<>();
        Collection<Number> expOutput = null;
        runTestList2(mapper::toListOfNumber, input, output, expOutput);
    }

    @Test
    public void toList2OutputListNull() {
        String input = getTestListAsString();
        Collection<Number> output = null;
        Class<AssertionError> expOutput = AssertionError.class;
        runTestThrow2(mapper::toListOfNumber, input, output, expOutput);
    }

    @Test
    public void toObjectList() {
        String input = getTestListAsString();
        DataType type = TEST_LIST_TYPE;
        Collection<Number> output = new HashSet<>();
        Collection<Number> expOutput = getTestNumberList();
        runTestList3(mapper::toObjectList, input, type, output, expOutput);
    }

    @Test
    public void toObjectListEmpty() {
        String input = TEST_EMPTY_STRING;
        DataType type = TEST_LIST_TYPE;
        Collection<Number> output = new HashSet<>();
        Collection<Number> expOutput = TEST_EMPTY_LIST;
        runTestList3(mapper::toObjectList, input, type, output, expOutput);
    }

    @Test
    public void toObjectListInvalid() {
        String input = TEST_INVALID_STRING;
        DataType type = TEST_LIST_TYPE;
        Collection<Number> output = new HashSet<>();
        Class<BeecowException> expOutput = BeecowException.class;
        runTestThrow3(mapper::toObjectList, input, type, output, expOutput);
    }

    @Test
    public void toObjectListNull() {
        String input = null;
        DataType type = TEST_LIST_TYPE;
        Collection<Number> output = new HashSet<>();
        Collection<Number> expOutput = null;
        runTestList3(mapper::toObjectList, input, type, output, expOutput);
    }

    @Test
    public void toObjectListTypeNull() {
        String input = getTestListAsString();
        DataType type = null;
        Collection<Number> output = new HashSet<>();
        Class<InvalidInputException> expOutput = InvalidInputException.class;
        runTestThrow3(mapper::toObjectList, input, type, output, expOutput);
    }

    @Test
    public void toObjectListTypeMismatch() {
        String input = getTestListAsString();
        DataType type = TEST_INVALID_TYPE;
        Collection<Number> output = new HashSet<>();
        Class<InvalidInputException> expOutput = InvalidInputException.class;
        runTestThrow3(mapper::toObjectList, input, type, output, expOutput);
    }

    @Test
    public void toObjectListOutputListNull() {
        String input = getTestListAsString();
        DataType type = TEST_LIST_TYPE;
        Collection<Number> output = null;
        Class<AssertionError> expOutput = AssertionError.class;
        runTestThrow3(mapper::toObjectList, input, type, output, expOutput);
    }

    @Test
    public void determineObjectType() {
        Object input = getTestNumber();
        DataType defaultType = DataType.STRING;
        DataType expOutput = DataType.NUMBER;
        runTest2(mapper::determineDataType, input, defaultType, expOutput);
    }

    @Test
    public void determineObjectTypeNull() {
        Object input = null;
        DataType defaultType = DataType.STRING;
        DataType expOutput = DataType.STRING;
        runTest2(mapper::determineDataType, input, defaultType, expOutput);
    }

    @Test
    public void determineObjectTypeList() {
        Object input = getTestNumberList();
        DataType defaultType = DataType.NUMBER;
        DataType expOutput = DataType.NUMBER_LIST;
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
        DataType defaultType = DataType.STRING;
        DataType expOutput = DataType.STRING;
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
        N input = getTestNumber();
        String expOutput = getTestNumberAsString();
        runTest(mapper::toString, input, expOutput);
    }

    @Test
    public void testToStringNull() {
        N input = null;
        String expOutput = null;
        runTest(mapper::toString, input, expOutput);
    }

    @Test
    public void testListToNumber() {
        Collection<Number> input = getTestNumberList();
        String expOutput = getTestListAsString();
        runTest(mapper::listToString, input, expOutput);
    }

    @Test
    public void testListToNumberEmpty() {
        Collection<Number> input = TEST_EMPTY_LIST;
        String expOutput = TEST_EMPTY_STRING;
        runTest(mapper::listToString, input, expOutput);
    }

    @Test
    public void testListToNumberNull() {
        Collection<Number> input = null;
        String expOutput = null;
        runTest(mapper::listToString, input, expOutput);
    }

}
