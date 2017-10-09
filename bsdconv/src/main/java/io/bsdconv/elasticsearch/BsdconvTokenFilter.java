package io.bsdconv.elasticsearch;

import io.bsdconv.Bsdconv;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;

public class BsdconvTokenFilter extends TokenFilter {
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    private Bsdconv conv;

    public BsdconvTokenFilter(TokenStream tokenStream, final String conversion) {
        super(tokenStream);
        conv = new Bsdconv(conversion);
    }

    @Override
    public boolean incrementToken() throws IOException {
        if (!input.incrementToken()) {
            return false;
        }

        byte[] ba = conv.conv(new String(termAtt.buffer(), 0, termAtt.length()).getBytes("UTF-16LE"));
        char[] ca = new String(ba, "UTF-16LE").toCharArray();
        termAtt.copyBuffer(ca, 0, ca.length);
        termAtt.setLength(ca.length);
        return true;
    }
}
