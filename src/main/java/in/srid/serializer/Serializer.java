package in.srid.serializer;

public interface Serializer {
    <T> byte[] serialize( T source );
}
