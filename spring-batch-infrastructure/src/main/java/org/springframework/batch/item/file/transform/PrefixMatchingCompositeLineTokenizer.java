/*
 * Copyright 2006-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.batch.item.file.transform;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.batch.support.PatternMatcher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * A {@link LineTokenizer} implementation that stores a mapping of String
 * prefixes to delegate {@link LineTokenizer}s. Each line tokenizied will be
 * checked for its prefix. If the prefix matches a key in the map of delegates,
 * then the corresponding delegate {@link LineTokenizer} will be used. Prefixes
 * are sorted starting with the most specific, and the first match always
 * succeeds.
 * 
 * @author Ben Hale
 * @author Dan Garrette
 * @author Dave Syer
 */
public class PrefixMatchingCompositeLineTokenizer implements LineTokenizer, InitializingBean {

	private PatternMatcher<LineTokenizer> tokenizers = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.file.transform.LineTokenizer#tokenize(
	 * java.lang.String)
	 */
	public FieldSet tokenize(String line) {
		return tokenizers.match(line).tokenize(line);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.isTrue(this.tokenizers != null, "The 'tokenizers' property must be non-empty");
	}

	public void setTokenizers(Map<String, LineTokenizer> tokenizers) {
		Assert.isTrue(!tokenizers.isEmpty(), "The 'tokenizers' property must be non-empty");
		LinkedHashMap<String, LineTokenizer> map = new LinkedHashMap<String, LineTokenizer>();
		for (String key : tokenizers.keySet()) {
			LineTokenizer value = tokenizers.get(key);
			if (!key.endsWith("*")) {
				key = key + "*";
			}
			map.put(key, value);
		}
		this.tokenizers = new PatternMatcher<LineTokenizer>(map);
	}
}
