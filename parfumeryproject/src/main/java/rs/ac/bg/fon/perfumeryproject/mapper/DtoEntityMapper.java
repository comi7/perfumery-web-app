package rs.ac.bg.fon.perfumeryproject.mapper;

/**
 *
 * @author Milica
 */
public interface DtoEntityMapper<T, E> {
    T toDto(E e);
    E toEntity(T t);
}