package in.srid.serializer.protobuf;

import in.srid.serializer.AbstractProtoSerializer;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;

public class ProtobufSerializer extends AbstractProtoSerializer {

    @Override
    public <T> byte[] serializeInternal( final T source , final Schema<T> schema , final LinkedBuffer buffer ) {
        return ProtobufIOUtil.toByteArray( source, schema, buffer );
    }

    @Override
    public <T> T deserializeInternal( final byte[] bytes , final T result , final Schema<T> schema ) {
        ProtobufIOUtil.mergeFrom( bytes, result, schema );
        return result;
    }
}
