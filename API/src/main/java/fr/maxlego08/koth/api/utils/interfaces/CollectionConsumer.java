package fr.maxlego08.koth.api.utils.interfaces;

import java.util.Collection;

/**
 * Function that converts a value into a collection of strings.
 *
 * @param <T> the input type
 */
@FunctionalInterface
public interface CollectionConsumer<T> {

	/**
	 * Converts the given value into a collection of strings.
	 *
	 * @param t the input value
	 * @return the resulting strings
	 */
	Collection<String> accept(T t);
	
}
