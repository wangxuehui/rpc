package org.skyim.serializer.kryo;

import org.objenesis.strategy.StdInstantiatorStrategy;
import org.skyim.serializer.Deserializer;
import org.skyim.serializer.Serializer;
import org.skyim.serializer.model.ImmutableModel;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class KryoSerializer implements Serializer, Deserializer {
    private static final Kryo kryo = new Kryo();

    static {
        kryo.register( ImmutableModel.class );
        kryo.setInstantiatorStrategy( new StdInstantiatorStrategy() );
    }

    @Override
    public <T> T deserialize( final byte[] bytes , final Class<T> clazz ) {
        final Object value = kryo.readClassAndObject( new Input( bytes ) );
        return clazz.cast( value );
    }

    @Override
    public <T> byte[] serialize( T source ) {
        final Output output = new Output( 512 , 1024 * 8 );
        kryo.writeClassAndObject( output, source );
        return output.getBuffer();
    }
}
