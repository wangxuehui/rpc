package in.srid.serializer;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.Schema;

public abstract class AbstractProtoSerializer implements Serializer, Deserializer {

    private final Objenesis objenesis = new ObjenesisStd( true );

    @Override
    public <T> byte[] serialize( final T source ) {
        @SuppressWarnings( "unchecked" )
        final Class<T> clazz = (Class<T>) source.getClass();
        final LinkedBuffer buffer = LinkedBuffer.allocate( LinkedBuffer.DEFAULT_BUFFER_SIZE );
        try {
            final Schema<T> schema = SchemaUtils.getSchema( clazz );
            return serializeInternal( source, schema, buffer );
        }
        catch ( final Exception e ) {
            throw new IllegalStateException( e.getMessage() , e );
        }
        finally {
            buffer.clear();
        }
    }

    @Override
    public <T> T deserialize( final byte[] bytes , final Class<T> clazz ) {
        try {
            @SuppressWarnings( "unchecked" )
            final T result = (T) objenesis.newInstance( clazz );
            return deserializeInternal( bytes, result, SchemaUtils.getSchema( clazz ) );
        }
        catch ( final Exception e ) {
            throw new IllegalStateException( e.getMessage() , e );
        }
    }

    public abstract <T> byte[] serializeInternal( final T source , final Schema<T> schema , final LinkedBuffer buffer );

    public abstract <T> T deserializeInternal( final byte[] bytes , final T result , final Schema<T> schema );
}
