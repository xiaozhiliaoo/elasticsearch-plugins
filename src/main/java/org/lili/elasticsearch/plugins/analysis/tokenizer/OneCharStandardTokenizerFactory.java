
package org.lili.elasticsearch.plugins.analysis.tokenizer;

import java.util.Map;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;


public class OneCharStandardTokenizerFactory extends TokenizerFactory {

    public OneCharStandardTokenizerFactory(Map<String, String> args) {
        super(args);
    }

    @Override
    public Tokenizer create(AttributeFactory factory) {
        StandardTokenizer standardTokenizer = new StandardTokenizer();
        //默认值255 org.apache.lucene.analysis.standard.StandardAnalyzer.DEFAULT_MAX_TOKEN_LENGTH
        standardTokenizer.setMaxTokenLength(1);
        return standardTokenizer;
    }
}