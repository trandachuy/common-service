/*******************************************************************************
 * Copyright 2017 (C) Mediastep Software Inc.
 *
 * Created on : 26/1/2017
 * Author: Huyen Lam <huyen.lam@mediastep.com>
 *******************************************************************************/
package com.mediastep.beecow.common.domain.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import javax.inject.Inject;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import com.mediastep.beecow.common.config.DataTypeConfiguration;
import com.mediastep.beecow.common.domain.enumeration.DataType;
import com.mediastep.beecow.common.errors.BeecowException;
import com.mediastep.beecow.common.errors.InvalidInputException;

/**
 * The class help to convert String to and from JSON.
 */
@Component
public final class DataTypeMapper {

    @Inject
    private DataTypeConfiguration config;

    /**
     * Convert value from string to given type.
     * <pre>
     * type = DataType.STRING, the method returns a String
     * type = DataType.NUMBER, the method returns a Number
     * type = DataType.LIST or DataType.LIST_STRING, the method returns a List&lt;String&gt;
     * type = DataType.LIST_NUMBER, the method returns a List&lt;Number&gt;
     * </pre>
     * @param input value to be converted
     * @param type data type that value should represent
     * @return
     * @throws InvalidInputException if <code>type</code> is null
     */
    public Object toObject(String input, DataType type) {
        // Input == null: nothing to convert
        if (input == null) {
            return null;
        }

        // Type == null: unknown type for conversion
        if (type == null) {
            throw new InvalidInputException("dataTypeMapper.toObject.typeNull", "Cannot convert '{}' to object: object type is not provided.", input);
        }

        // Convert input accordingly
        Object output;
        switch (type) {
        // Convert to number
        case NUMBER:
            output = toNumber(input);
            break;
        // Convert to list of string
        case LIST:
        case STRING_LIST:
            output = toList(input);
            break;
        // Convert to list of number
        case NUMBER_LIST:
            output = toListOfNumber(input);
            break;
        default:
            output = input;
            break;
        }
        return output;
    }

    /**
     * Convert to Number.
     * <pre>
     * Implementation of DataType.NUMBER.
     * </pre>
     * @param input
     * @return parsed number object, or null if input == null
     */
    public Number toNumber(String input) {
        if (input == null) {
            return null;
        }
        try {
            return NumberUtils.createNumber(input);
        }
        catch (NumberFormatException exc) {
            throw new BeecowException("dataTypeMapper.toNumber.numberFormatException", "Cannot convert '{}' to number", input);
        }
    }

    /**
     * Convert value from string to given type.
     * <pre>
     * type = DataType.STRING, the method returns a String
     * type = DataType.NUMBER, the method returns a Number
     * type = DataType.LIST or DataType.LIST_STRING, the method returns a List&lt;String&gt;
     * type = DataType.LIST_NUMBER, the method returns a List&lt;Number&gt;
     * </pre>
     * @param input value to be converted
     * @param type data type that value should represent
     * @return
     * @throws InvalidInputException if <code>type</code> is null or type.isSimpleType() == true
     */
    @SuppressWarnings({ "incomplete-switch", "unchecked" })
    public Object toObjectList(String input, DataType type, Collection<?> output) {
        // Input == null: nothing to convert
        if (input == null) {
            return null;
        }

        // Type errors
        if (type == null) {
            throw new InvalidInputException("dataTypeMapper.toObjectList.typeNull", "Cannot convert '{}' to list of objects: object type is not provided.", input);
        }
        if (type.isSimpleType()) {
            throw new InvalidInputException("dataTypeMapper.toObjectList.simpleType", "Cannot convert '{}' to list of objects: given object type {}, which is not list type.", input, type.name());
        }

        // Convert input accordingly
        switch (type) {
        // Convert to list of string
        case LIST:
        case STRING_LIST:
            output = toList(input, (Collection<String>) output);
            break;
        // Convert to list of number
        case NUMBER_LIST:
            output = toListOfNumber(input, (Collection<Number>) output);
            break;
        }
        return output;
    }

    /**
     * Convert to List of String.
     * <pre>
     * Implementation of DataType.LIST and DataType.LIST_STRING
     * </pre>
     * @param input
     * @return parsed string list, or null if input == null
     */
    public Collection<String> toList(String input) {
        if (input == null) {
            return null;
        }
        if (input.isEmpty()) {
            return Collections.emptyList();
        }
        // #performance note: not calling toList(String, Collection<String>) to prevent involving addAll()
        return Arrays.asList(toArray(input));
    }

    /**
     * Convert to List of String.
     * <pre>
     * Implementation of DataType.LIST and DataType.LIST_STRING
     * </pre>
     * @param input
     * @param output list to store conversion result
     * @return parsed string list, or null if input == null
     */
    public Collection<String> toList(String input, Collection<String> output) {
        if (input == null) {
            return null;
        }
        if (input.isEmpty()) {
            return output;
        }
        assert (output != null);
        List<String> result = Arrays.asList(toArray(input));
        output.addAll(result);
        return output;
    }

    /**
     * Split String to Array.
     * @param input
     * @return
     */
    private String[] toArray(String input) {
        assert (input != null);
        // TODO encode & decode list-separator character
        return input.split(config.getListSeparator());
    }

    /**
     * Convert to List of Number.
     * <pre>
     * Implementation of DataType.LIST_NUMBER
     * </pre>
     * @param input
     * @param output
     * @return parsed number list, or null if input == null
     */
    public Collection<Number> toListOfNumber(String input) {
        if (input == null) {
            return null;
        }
        if (input.isEmpty()) {
            return Collections.emptyList();
        }
        return toListOfNumber(input, new ArrayList<>());
    }

    /**
     * Convert to List of Number.
     * <pre>
     * Implementation of DataType.LIST_NUMBER
     * </pre>
     * @param input
     * @param output
     * @return parsed number list, or null if input == null
     */
    public Collection<Number> toListOfNumber(String input, Collection<Number> output) {
        if (input == null) {
            return null;
        }
        if (input.isEmpty()) {
            return output;
        }
        assert (output != null);
        String[] values = toArray(input);
        for (String value : values) {
            Number result = toNumber(value);
            output.add(result);
        }
        return output;
    }

    /**
     * Determine data-type from object-type.
     * @param object
     * @return corresponding data type
     * @throws BeecowException if object type is not determined
     * <pre>
     * NOTE: For the case which given object is an empty list
     *    The method return LIST (without knowing its item type),
     *    which is good enough to convert object to string. 
     * </pre>
     */
    public DataType determineDataType(Object object) {
        if (object == null) {
            throw new BeecowException("dataTypeMapper.determineDataType.inputObjectNull", "Cannot determine object type: given object is null");
        }
        if (object instanceof Collection) {
            return doDetermineListDataType((Collection<?>) object);
        }
        else {
            return doDetermineSimpleDataType(object);
        }
    }

    /**
     * Determine data-type from object-type.
     * @param object
     * @return corresponding data type, null if cannot determine object type
     * <pre>
     * NOTE: For the case which given object is an empty list
     *    The method return LIST (without knowing its item type),
     *    which is good enough to convert object to string. 
     * </pre>
     */
    private DataType doDetermineSimpleDataType(Object object) {
        // Check if given types match input object
        DataType type = null;
        for (DataType curType : DataType.allSimpleTypes()) {
            if (curType.getMappedClazz().isInstance(object)) {
                type = curType;
                break;
            }
        }
        return type;
    }

    /**
     * Determine data-type of items in a collection.
     * @return object type, null if cannot determine object type
     */
    private DataType doDetermineListDataType(Collection<?> objectAsList) {
        DataType type = DataType.LIST;
        if (!objectAsList.isEmpty()) {
            Object item = objectAsList.iterator().next();
            DataType itemType = doDetermineSimpleDataType(item);
            switch (itemType) {
            case STRING:
                type = DataType.STRING_LIST;
                break;
            case NUMBER:
                type = DataType.NUMBER_LIST;
                break;
            default:
                break;
            }
        }
        return type;
    }

    /**
     * Determine data-type from object-type.
     * @param object
     * @param defaultType
     * @return corresponding data type, null if object type was not able to be determined
     * @throws BeecowException if throwIfFail == true and object type is not determined
     * <pre>
     * NOTE: For the case which given object is an empty list
     *    The method return LIST (without knowing its item type),
     *    which is good enough to convert object to string. 
     * </pre>
     */
    public DataType determineDataType(Object object, DataType defaultType) {
        DataType type;
        try {
            type = determineDataType(object);
            if (type == null) {
                type = defaultType;
            }
        }
        catch (BeecowException exc) {
            type = defaultType;
        }
        return type;
    }

//    /**
//     * Convert value from given type to string.
//     * <pre>
//     * type = DataType.STRING, indicating that input object is a String
//     * type = DataType.NUMBER, indicating that input object is a Number (which can be Integer, Long or Number)
//     * type = DataType.LIST or DataType.LIST_STRING, indicating that input object is a List&lt;String&gt;
//     * type = DataType.LIST_NUMBER, indicating that input object is a List&lt;Number&gt;
//     * </pre>
//     * @param input value to be converted
//     * @param type data type that value should represent
//     * @return string that represent input object, or null if input == null
//     * @throws InvalidParameterException if <code>type</code> is not matched with <code>clazz</code>
//     */
//    public String toString(Object input, DataType type) {
//        // Input == null: nothing to convert
//        if (input == null) {
//            return null;
//        }
//        // Type == null: unknown type for conversion
//        if (type == null) {
//            throw new InvalidParameterException(); // TODO defined correct exception and error message
//        }
//        // Check if input object match input type, so that it knows which conversion method to call
//        if (!type.getMappedClazz().isInstance(input)) {
//            throw new InvalidParameterException(); // TODO defined correct exception and error message
//        }
//        // Convert input accordingly
//        return doToString(input, type);
//    }

    /**
     * Convert value from given type to string.
     * <pre>
     * type = DataType.STRING, indicating that input object is a String
     * type = DataType.NUMBER, indicating that input object is a Number (which can be Integer, Long or Number)
     * type = DataType.LIST or DataType.LIST_STRING, indicating that input object is a List&lt;String&gt;
     * type = DataType.LIST_NUMBER, indicating that input object is a List&lt;Number&gt;
     * </pre>
     * @param input value to be converted
     * @param type data type that value should represent
     * @return string that represent input object, or null if input == null
     */
    @SuppressWarnings("unchecked")
    private String doToString(Object input, DataType type) {
        // Convert input accordingly
        String output;
        switch (type) {
        // Convert to list of string
        case LIST:
        case STRING_LIST:
            output = listOfStringToString((Collection<String>) input);
            break;
        // Convert to list of number
        case NUMBER_LIST:
            output = listToString((Collection<Number>) input);
            break;
        default:
            output = input.toString();
            break;
        }
        return output;
    }

    /**
     * Convert value from given type to string.
     * <pre>
     * type = DataType.STRING, indicating that input object is a String
     * type = DataType.NUMBER, indicating that input object is a Number (which can be Integer, Long or Number)
     * type = DataType.LIST or DataType.LIST_STRING, indicating that input object is a List&lt;String&gt;
     * type = DataType.LIST_NUMBER, indicating that input object is a List&lt;Number&gt;
     * </pre>
     * @param input value to be converted
     * @param type data type that value should represent
     * @return string that represent input object, or null if input == null
     */
    public String toString(Object input) {
        // Input == null: nothing to convert
        if (input == null) {
            return null;
        }
        // Determine data-type for input object and covert it to string
        DataType type = determineDataType(input, DataType.STRING);
        return doToString(input, type);
    }

    /**
     * Convert to List of String to single String.
     * @param input
     * @return string that represent input object, or null if input == null
     */
    public String listOfStringToString(Collection<String> input) {
        if (input == null) {
            return null;
        }
        return String.join(config.getListSeparator(), input);
    }

    /**
     * Convert to List of Number to single String.
     * @param input
     * @return string that represent input object, or null if input == null
     */
    public String listToString(Collection<?> input) {
        if (input == null) {
            return null;
        }
        StringJoiner joiner = new StringJoiner(config.getListSeparator());
        for (Object value : input) {
            joiner.add(value.toString());
        }
        return joiner.toString();
    }
}
