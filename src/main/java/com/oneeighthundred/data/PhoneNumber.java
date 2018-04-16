package com.oneeighthundred.data;

import com.oneeighthundred.util.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PhoneNumber {
    /**
     * Phone number
     */
    private final List<Object> phoneNumber;

    private PhoneNumber(List<Object> parts) {
        this.phoneNumber = new ArrayList<>();
        parts.forEach(this::addPart);
    }

    public PhoneNumber(Object... parts) {
        this(Arrays.asList(parts));
    }

    public PhoneNumber(String number) {
        this(Collections.singletonList(number));
    }

    private void addPart(Object part) {
        if (part instanceof PhoneNumber) {
            this.phoneNumber.addAll(((PhoneNumber) part).phoneNumber);
        } else if ((part instanceof String) || (part instanceof Integer)) {
            this.phoneNumber.add(part);
        } else {
            throw new IllegalArgumentException("Unsupported phone number part type: " + part.getClass());
        }
    }

    public List<Object> getParts() {
        return this.phoneNumber;
    }

    @Override
    public int hashCode() {
        return this.phoneNumber.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PhoneNumber)) {
            return false;
        }
        PhoneNumber pn = (PhoneNumber) obj;
        return this.phoneNumber.equals(pn.phoneNumber);
    }

    @Override
    public String toString() {
        return this.phoneNumber.stream()
                .map(o -> {
                    if (o instanceof String) {
                        return "'" + o + "'";
                    } else if (o instanceof Integer) {
                        return o.toString();
                    } else {
                        throw new UnsupportedOperationException("This should never happen: " + o.getClass());
                    }
                })
                .collect(Collectors.joining(Constants.WORD_SEPARATOR));
    }
}
