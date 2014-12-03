package in.srid.serializer.jackson;

import in.srid.serializer.Deserializer;
import in.srid.serializer.Serializer;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

public class JacksonSerializer implements Serializer, Deserializer {

    private final ObjectMapper mapper = new ObjectMapper();

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
