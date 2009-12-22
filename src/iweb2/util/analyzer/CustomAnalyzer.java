package iweb2.util.analyzer;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.PorterStemFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

public class CustomAnalyzer extends StandardAnalyzer {
    
  private static final String[] ADDITIONAL_STOP_WORDS = {
      "should", "would", "from", "up", "i", "s", "it", "his", "has", "he",
      "she", "her", "said", "been", "being", "final", "now", "hour", "minute", "second",
      "stop", "start", "first", "third", "fast", "slow", "large", "small"
  };
  
  private static String[] MERGED_STOP_WORDS;
  
  static {
      List<String> allStopWords = new ArrayList<String>(); 
      allStopWords.addAll(Arrays.asList(StandardAnalyzer.STOP_WORDS));
      allStopWords.addAll(Arrays.asList(ADDITIONAL_STOP_WORDS));
      MERGED_STOP_WORDS = allStopWords.toArray(new String[0]);
  }
  
  public CustomAnalyzer() {
      super(CustomAnalyzer.MERGED_STOP_WORDS);
  }
  @Override
public TokenStream tokenStream(String fieldName, Reader reader) {
      return new PorterStemFilter(super.tokenStream(fieldName, reader));
  }
}
