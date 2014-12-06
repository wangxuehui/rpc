package org.skyim.serializer.fasterxml;

import java.io.IOException;

import org.skyim.serializer.Deserializer;
import org.skyim.serializer.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class FasterxmlSerializer implements Serializer, Deserializer {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule( new JodaModule() );
    }

    @Override
    public <T> T deserialize( final byte[] bytes , final Class<T> clazz ) {
        try {
            return mapper.readValue( bytes, clazz );
        }
        catch ( final IOException e ) {
            throw new IllegalStateException( e.getMessage() , e );
        }
    }

    @Override
    public <T> byte[] serialize( final T source ) {
        try {
            return mapper.writeValueAsBytes( source );
        }
        catch ( final IOException e ) {
            throw new IllegalStateException( e.getMessage() , e );
        }
    }
}