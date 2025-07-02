package fr.maxlego08.koth.placeholder;
@FunctionalInterface
public interface ReturnBiConsumer<T, G, C> {

	C accept(T t, G g);
	
}