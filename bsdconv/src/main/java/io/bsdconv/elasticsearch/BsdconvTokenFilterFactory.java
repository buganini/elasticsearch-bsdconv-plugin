package io.bsdconv.elasticsearch;

import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractTokenFilterFactory;

public class BsdconvTokenFilterFactory extends AbstractTokenFilterFactory {
    private final static String PASSTHRU = "PASS:PASS";

    private String conversion;

    public BsdconvTokenFilterFactory(IndexSettings indexSettings, Environment environment, String name, Settings settings) {
        super(indexSettings, name, settings);

        conversion = settings.get("conversion", PASSTHRU);
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return new BsdconvTokenFilter(tokenStream, conversion);
    }
}
