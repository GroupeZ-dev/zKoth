package fr.maxlego08.koth.api.utils.interfaces;

/**
 * Function that converts a value into a string.
 *
 * @param <T> the input type
 */
@FunctionalInterface
public interface StringConsumer<T> {

	/**
	 * Converts the given value into a string.
	 *
	 * @param t the input value
	 * @return the resulting string
	 */
	String accept(T t);
	
}
