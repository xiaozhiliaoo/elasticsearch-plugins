
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
        StandardTokenizer oneCharStandardTokenizer = new StandardTokenizer();
        oneCharStandardTokenizer.setMaxTokenLength(1);
        return oneCharStandardTokenizer;
    }
}