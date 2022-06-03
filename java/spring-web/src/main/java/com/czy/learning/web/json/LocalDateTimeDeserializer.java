package com.czy.learning.web.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.czy.learning.infranstructure.util.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> implements ContextualDeserializer {

    public static LocalDateTimeDeserializer INSTANCE = new LocalDateTimeDeserializer();

    private final DateTimeFormatter formatter;
    private final boolean useTimeStamp;

    public LocalDateTimeDeserializer() {
        this(true, null);
    }

    public LocalDateTimeDeserializer(DateTimeFormatter formatter) {
        this(false, formatter);
    }

    public LocalDateTimeDeserializer(boolean useTimeStamp, DateTimeFormatter formatter) {
        this.formatter = formatter;
        this.useTimeStamp = useTimeStamp;
    }

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (useTimeStamp) {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(p.getLongValue());
            return DateUtil.toLocalDateTime(p.getLongValue());
        }
        String value = StringUtils.trimToEmpty(p.getValueAsString());
        return StringUtils.isEmpty(value) ? null : LocalDateTime.parse(value, formatter);
    }


    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) {
        if (property != null) {
            JsonFormat.Value format = ctxt.getAnnotationIntrospector().findFormat(property.getMember());
            if (format != null) {
                if (format.getShape().isNumeric()) {
                    return new LocalDateTimeDeserializer();
                }
                if (format.hasPattern()) {
                    return new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(format.getPattern()));
                }
            }
        }
        return this;
    }
}