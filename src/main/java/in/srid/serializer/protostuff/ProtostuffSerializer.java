package in.srid.serializer.protostuff;

import in.srid.serializer.AbstractProtoSerializer;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;

public class ProtostuffSerializer extends AbstractProtoSerializer {

    @Override
    public <T> byte[] serializeInternal( final T source , final Schema<T> schema , final LinkedBuffer buffer ) {
        return ProtostuffIOUtil.toByteArray( source, schema, buffer );
    }

    @Override
    public <T> T deserializeInternal( final byte[] bytes , final T result , final Schema<T> schema ) {
        ProtostuffIOUtil.mergeFrom( bytes, result, schema );
        return result;
    }
}
