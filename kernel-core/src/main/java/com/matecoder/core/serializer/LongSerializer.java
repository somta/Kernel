package com.matecoder.core.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * 自定义Long数字序列化器
 * @since 3.2.0
 */
public class LongSerializer extends JsonSerializer<Long> {
    public static final LongSerializer INSTANCE = new LongSerializer();

    public LongSerializer() {
        super();
    }

    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeNumber(value);
        }
    }

    @Override
    public Class<Long> handledType() {
        return Long.class;
    }
}