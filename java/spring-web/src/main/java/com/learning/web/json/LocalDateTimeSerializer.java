package com.learning.web.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.learning.infranstructure.util.DateUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> implements ContextualSerializer {

    public static LocalDateTimeSerializer INSTANCE = new LocalDateTimeSerializer();

    private final DateTimeFormatter formatter;
    private final boolean useTimeStamp;

    public LocalDateTimeSerializer() {
        this(true, null);
    }

    public LocalDateTimeSerializer(DateTimeFormatter formatter) {
        this(false, formatter);
    }

    public LocalDateTimeSerializer(boolean useTimeStamp, DateTimeFormatter formatter) {
        this.useTimeStamp = useTimeStamp;
        this.formatter = formatter;
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (useTimeStamp) {
            gen.writeNumber(DateUtil.toTimeStamp(value));
        } else {
            gen.writeString(value.format(formatter));
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
        if (property != null) {
            JsonFormat.Value format = prov.getAnnotationIntrospector().findFormat(property.getMember());
            if (format != null) {
                if (format.getShape().isNumeric()) {
                    return new LocalDateTimeSerializer();
                }
                if (format.hasPattern()) {
                    return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(format.getPattern()));
                }
            }
        }
        return this;
    }
}