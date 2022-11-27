
package org.lili.elasticsearch.plugins;

import lombok.SneakyThrows;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.elasticsearch.index.analysis.PreBuiltAnalyzerProviderFactory;
import org.elasticsearch.index.analysis.PreConfiguredTokenizer;
import org.elasticsearch.indices.analysis.PreBuiltCacheFactory.CachingStrategy;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;
import org.lili.elasticsearch.plugins.analysis.tokenizer.OneCharStandardTokenizerFactory;

import java.util.Arrays;
import java.util.List;

public class ESPlugin extends Plugin implements AnalysisPlugin {

    public static final String OneCharStandardTokenizerName = "onecharstandard";
    public static final String OneCharStandardAnalyzerName = "onecharstandard";


    @Override
    public List<PreConfiguredTokenizer> getPreConfiguredTokenizers() {
        PreConfiguredTokenizer oneCharStandardTokenizerFactory = PreConfiguredTokenizer
                .singleton(OneCharStandardTokenizerName, () -> new OneCharStandardTokenizerFactory(null).create());
        return Arrays.asList(oneCharStandardTokenizerFactory);
    }

    @Override
    @SneakyThrows
    public List<PreBuiltAnalyzerProviderFactory> getPreBuiltAnalyzerProviderFactories() {
        CustomAnalyzer oneCharStandardAnalyzer = CustomAnalyzer.builder()
                .withTokenizer(OneCharStandardTokenizerName).build();
        PreBuiltAnalyzerProviderFactory oneCharStandardAnalyzerFactory = new PreBuiltAnalyzerProviderFactory(
                OneCharStandardAnalyzerName, CachingStrategy.ONE, () -> oneCharStandardAnalyzer);

        return Arrays.asList(oneCharStandardAnalyzerFactory);
    }
}