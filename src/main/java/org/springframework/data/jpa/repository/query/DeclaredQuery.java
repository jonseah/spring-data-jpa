/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.jpa.repository.query;

import java.util.List;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

/**
 * A wrapper for a String representation of a query offering information about the query.
 *
 * @author Jens Schauder
 * @since 2.0.3
 */
interface DeclaredQuery {

	/**
	 * Creates a {@literal DeclaredQuery} from a query {@literal String}.
	 *
	 * @param query might be {@literal null} or empty.
	 * @return a {@literal DeclaredQuery} instance even for a {@literal null} or empty argument.
	 */
	static DeclaredQuery of(@Nullable String query) {
		return StringUtils.isEmpty(query) ? EmptyDeclaredQuery.EMPTY_QUERY : new StringQuery(query);
	}

	/**
	 * @return whether the underlying query has at least one named parameter.
	 */
	boolean hasNamedParameter();

	/**
	 * Returns the query string.
	 */
	String getQueryString();

	/**
	 * Returns the main alias used in the query.
	 *
	 * @return the alias
	 */
	@Nullable
	String getAlias();

	/**
	 * Returns whether the query is using a constructor expression.
	 *
	 * @since 1.10
	 */
	boolean hasConstructorExpression();

	/**
	 * Returns whether the query uses the default projection, i.e. returns the main alias defined for the query.
	 */
	boolean isDefaultProjection();

	/**
	 * Returns the {@link StringQuery.ParameterBinding}s registered.
	 */
	List<StringQuery.ParameterBinding> getParameterBindings();

	/**
	 * Creates a new {@literal DeclaredQuery} representing a count query, i.e. a query returning the number of rows to be
	 * expected from the original query, either derived from the query wrapped by this instance or from the information
	 * passed as arguments.
	 * 
	 * @param countQuery an optional query string to be used if present.
	 * @param countQueryProjection an optional return type for the query.
	 * @return a new {@literal DeclaredQuery} instance.
	 */
	DeclaredQuery deriveCountQuery(@Nullable String countQuery, @Nullable String countQueryProjection);
}
