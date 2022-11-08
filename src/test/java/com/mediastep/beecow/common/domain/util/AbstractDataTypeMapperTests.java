/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 26/1/2017
 * Author: Huyen Lam <huyen.lam@mediastep.com>
 *******************************************************************************/
package com.mediastep.beecow.common.domain.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import javax.inject.Inject;

import com.mediastep.beecow.common.domain.enumeration.DataType;

public abstract class AbstractDataTypeMapperTests {

    protected static final String LIST_SEPARATOR = ",";

    protected static final String TEST_EMPTY_STRING = "";

    protected static final Object TEST_UNKOWN_TYPE_OBJECT = new Object();

    protected static final List<String> TEST_EMPTY_STRING_LIST = Collections.emptyList();

    protected static final int TEST_LIST_SIZE = 5;

    @FunctionalInterface
    protected static interface Function2<T, U, R> {
        public R apply(T t, U u);
    }

    @FunctionalInterface
    protected static interface Function3<T, U, V, R> {
        public R apply(T t, U u, V v);
    }

    @Inject
    protected DataTypeMapper mapper;

    protected <E> void assertList(Collection<E> output, Collection<E> expOutput) {
        if (expOutput == null) {
            assertThat(output).isNull();
        }
        else if (expOutput.isEmpty()) {
            assertThat(output).isEmpty();
        }
        else {
            assertThat(output).containsAll(expOutput);
        }
    }

    /**
     * Run test with given input-value and expecting returned output to equal to expected-output-value.
     * @param function
     * @param input
     * @param expOutput
     */
    protected <T, R> void runTest(Function<T, R> function, T input, R expOutput) {
        R output = function.apply(input);
        assertThat(output).isEqualTo(expOutput);
    }

    /**
     * Run test with given input-value and input-type,
     * and expecting returned output to equal to expected-output-value.
     * @param function
     * @param input
     * @param type
     * @param expOutput
     */
    protected <T, R> void runTest2(Function2<T, DataType, R> function, T input, DataType type, R expOutput) {
        R output = function.apply(input, type);
        assertThat(output).isEqualTo(expOutput);
    }

    /**
     * Run test with given input-value and input-type,
     * and expecting returned output to equal to expected-output-value.
     * @param function
     * @param input
     * @param expOutput
     */
    protected <T, E> void runTestList(Function<T, Collection<E>> function, T input, Collection<E> expOutput) {
        Collection<E> output = function.apply(input);
        assertList(output, expOutput);
    }

    /**
     * Run test with given input-value and input-type,
     * and expecting returned output to equal to expected-output-value.
     * @param function
     * @param input
     * @param output
     * @param expOutput
     */
    protected <T, E> void runTestList2(Function2<T, Collection<E>, Collection<E>> function, T input, Collection<E> output, Collection<E> expOutput) {
        output = function.apply(input, output);
        assertList(output, expOutput);
    }

    /**
     * Run test with given input-value and input-type,
     * and expecting returned output to equal to expected-output-value.
     * @param function
     * @param input
     * @param output
     * @param expOutput
     */
    @SuppressWarnings("unchecked")
    protected <E> void runTestList3(Function3<String, DataType, Collection<E>, Object> function, String input, DataType type, Collection<E> output, Collection<E> expOutput) {
        output = (Collection<E>) function.apply(input, type, output);
        assertList(output, expOutput);
    }

    /**
     * Run test with given input-value and expecting exception.
     * @param function
     * @param input
     * @param expOutput
     */
    protected <T, R, E extends Throwable> void runTestThrow(Function<T, R> function, T input, Class<E> expOutput) {
        try {
            function.apply(input);
            fail();
        }
        catch (Throwable exp) {
            assertThat(exp).isInstanceOf(expOutput);
        }
    }

    /**
     * Run test with given input-values and expecting exception.
     * @param function
     * @param input
     * @param expOutput
     */
    protected <T, U, R, E extends Throwable> void runTestThrow2(Function2<T, U, R> function, T input, U input2, Class<E> expOutput) {
        try {
            function.apply(input, input2);
            fail();
        }
        catch (Throwable exp) {
            assertThat(exp).isInstanceOf(expOutput);
        }
    }

    /**
     * Run test with given input-values and expecting exception.
     * @param function
     * @param input
     * @param expOutput
     */
    protected <T, U, V, R, E extends Throwable> void runTestThrow3(Function3<T, U, V, R> function, T input, U input2, V input3, Class<E> expOutput) {
        try {
            function.apply(input, input2, input3);
            fail();
        }
        catch (Throwable exp) {
            assertThat(exp).isInstanceOf(expOutput);
        }
    }
}
